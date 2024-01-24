package com.castis.pvs.api.controller;

import com.castis.common.exception.CiException;
import com.castis.common.util.AES_256_ECB;
import com.castis.common.util.CiLogger;
import com.castis.pvs.api.model.v2.PatchHappyCallUserByCiRequest;
import com.castis.pvs.api.model.v2.request.*;
import com.castis.pvs.api.model.v2.response.HappyCallListResponse;
import com.castis.pvs.api.model.v2.response.OpenApiResponse;
import com.castis.pvs.api.model.v2.response.ProductListResponse;
import com.castis.pvs.api.model.v2.response.UsersignupResponse;
import com.castis.pvs.api.service.OpenApiService;
import com.castis.pvs.entity.SiteInfo;
import com.castis.pvs.member.dto.MemberDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@RestController
public class OpenApiController {
    private final OpenApiService apiService;

    private Validator validator;
    public OpenApiController(OpenApiService apiService, Validator validator) {
        this.apiService = apiService;
        this.validator = validator;
    }

    @PostMapping(value = "/api/v2/usersignup")
    @ResponseBody
    @Operation(tags = "회원", summary = "회원가입", description = "암호화된(AES256) RequestBody를 사용해야함", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(schema = @Schema(implementation = UsersignupRequest.class))))
    //@Parameter(name = "encryptedBody" ,description = "AES256으로 암호화된 요청 바디", required = true, schema = @Schema(implementation = UserSignUpV2Request.class))
    @ApiResponses(value = {@ApiResponse(responseCode = "201", content = @Content(schema = @Schema(implementation = OpenApiResponse.class), examples = @ExampleObject(value = "{\"resultCode\": 201, \"resultMessage\": \"회원가입에 성공하였습니다.\"}"))), @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = UsersignupResponse.class), examples = @ExampleObject(value = "{\"resultCode\": 400, \"resultMessage\": \"잘못된 요청입니다.\"}"))), @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = OpenApiResponse.class), examples = @ExampleObject(value = "{\"resultCode\": 500, \"resultMessage\": \"회원가입에 실패하였습니다.\"}")))})
    public ResponseEntity<String> usersignup(@RequestHeader(name = "so_id") String soId,  @Valid @RequestBody String encryptedBody) throws Exception {
        SiteInfo siteInfo = null;
        String decryptedBody;

        if (Objects.nonNull(soId)) {
            siteInfo = apiService.getSiteInfo(soId);
            if (Objects.nonNull(siteInfo)) {
                try {
                    AES_256_ECB aesCipher = new AES_256_ECB(siteInfo.getEncrypt_key());
                    decryptedBody = aesCipher.dec_aes(encryptedBody);
                    UsersignupRequest apiRequest = new ObjectMapper().registerModule(new JavaTimeModule()).readValue(decryptedBody,UsersignupRequest.class);
                    //validation check
                    String validation = validateRequest(apiRequest);
                    if(validation == null){
                        MemberDTO memberDTO = new MemberDTO(apiRequest);
                        OpenApiResponse response = apiService.saveMember(memberDTO);
                        // 응답 암호화
                        String encryptedResponse = aesCipher.enc_aes_object(response);
                        return ResponseEntity.status(response.getResultCode()).body(encryptedResponse);
                    }else{
                        return getResponseEntity(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.value(), validation, siteInfo);
                    }
                } catch (JsonProcessingException | UnsupportedEncodingException | NoSuchAlgorithmException |
                         NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException |
                         InvalidKeyException e) {
                    return getResponseEntity(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.value(), "잘못된 요청입니다.", siteInfo);
                } catch (CiException e) {
                    return getResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), siteInfo);
                } catch (Exception e) {
                    return getResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), siteInfo);
                }
            } else {
                CiLogger.info("siteInfo is null");
                return getResponseEntity(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.value(), "잘못된 요청입니다.", siteInfo);
            }
        } else {
            CiLogger.info("site_name is null");
            return getResponseEntity(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.value(), "잘못된 요청입니다.", siteInfo);
        }
    }

    private <T> String validateRequest(T apiRequest) {
        Set<ConstraintViolation<T>> violations = validator.validate(apiRequest);
        String message = null;
        if (!violations.isEmpty()) {
            ConstraintViolation<T> violation = violations.iterator().next();
            message = String.format("[%s] %s", violation.getPropertyPath(), violation.getMessage());
        }
        return message;
    }

    @GetMapping(value = "/api/v2/gethappycalllist")
    @ResponseBody
    @Operation(tags = "해피콜", summary = "해피콜 회원 정보 조회", description = "암호화된(AES256) RequestBody를 사용해야함 ")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = HappyCallListResponse.class))), @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = UsersignupResponse.class), examples = @ExampleObject(value = "{\"resultCode\": 400, \"resultMessage\": \"잘못된 요청입니다.\"}"))), @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = OpenApiResponse.class), examples = @ExampleObject(value = "{\"resultCode\": 500, \"resultMessage\": \"\"}")))})
    public ResponseEntity<String> gethappycalllist(@RequestHeader(name = "so_id") String soId, @RequestParam("search_startdate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate searchStartDate , @RequestParam("search_enddate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate searchEndDate , @RequestParam("per_page") Integer perPage , @RequestParam("current_page") Integer currentPage) throws Exception {
        SiteInfo siteInfo = null;
        String decryptedBody;

        if (Objects.nonNull(soId)) {
            siteInfo = apiService.getSiteInfo(soId);
            if (Objects.nonNull(siteInfo)) {
                try {
                    AES_256_ECB aesCipher = new AES_256_ECB(siteInfo.getEncrypt_key());
                    HappyCallListRequest apiRequest = HappyCallListRequest.builder().search_startdate(searchStartDate).search_enddate(searchEndDate).per_page(perPage).current_page(currentPage).build();

                    // 응답 암호화
                    String validation = validateRequest(apiRequest);
                    if(validation == null) {
                        HappyCallListResponse response = apiService.getHappyCallList(soId, apiRequest);
                        String encryptedResponse = aesCipher.enc_aes_object(response);
                        return ResponseEntity.ok(encryptedResponse);
                    }else{
                        return getResponseEntity(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.value(), validation, siteInfo);
                    }
                } catch (JsonProcessingException | UnsupportedEncodingException | NoSuchAlgorithmException |
                         NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException |
                         InvalidKeyException e) {
                    return getResponseEntity(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.value(), "잘못된 요청입니다.", siteInfo);
                } catch (CiException e) {
                    return getResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), siteInfo);
                } catch (Exception e) {
                    return getResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), siteInfo);
                }
            } else {
                CiLogger.info("siteInfo is null");
                return getResponseEntity(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.value(), "잘못된 요청입니다.", siteInfo);
            }
        } else {
            CiLogger.info("site_name is null");
            return getResponseEntity(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.value(), "잘못된 요청입니다.", siteInfo);
        }
    }

    @PatchMapping(value = "/api/v2/patchhappycalluserbyci")
    @ResponseBody
    @Operation(tags = "해피콜", summary = "해피콜 회원 정보 수정", description = "암호화된(AES256) RequestBody를 사용해야함 ", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(schema = @Schema(implementation = PatchHappyCallUserByCiRequest.class))))
    @ApiResponses(value = {@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = OpenApiResponse.class), examples = @ExampleObject(value = "{\"resultCode\": 200, \"resultMessage\": \"성공\"}"))), @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = OpenApiResponse.class), examples = @ExampleObject(value = "{\"resultCode\": 400, \"resultMessage\": \"잘못된 요청입니다.\"}"))), @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = OpenApiResponse.class), examples = @ExampleObject(value = "{\"resultCode\": 500, \"resultMessage\": \"\"}")))})
    public ResponseEntity<String> patchhappycalluserbyci(@RequestHeader(name = "so_id") String soId, @Valid @RequestBody String encryptedBody) throws Exception {
        SiteInfo siteInfo = null;
        String decryptedBody;

        if (Objects.nonNull(soId)) {
            siteInfo = apiService.getSiteInfo(soId);
            if (Objects.nonNull(siteInfo)) {
                try {
                    AES_256_ECB aesCipher = new AES_256_ECB(siteInfo.getEncrypt_key());
                    decryptedBody = aesCipher.dec_aes(encryptedBody);
                    PatchHappyCallUserByCiRequest apiRequest = new ObjectMapper().registerModule(new JavaTimeModule()).readValue(decryptedBody, PatchHappyCallUserByCiRequest.class);
                    String validation = validateRequest(apiRequest);
                    if(validation == null) {
                        OpenApiResponse response = apiService.patchHappyCallUserByCi(apiRequest);
                        return getResponseEntity(siteInfo, aesCipher, response);
                    }else{
                        return getResponseEntity(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.value(), validation, siteInfo);
                    }
                } catch (JsonProcessingException | UnsupportedEncodingException | NoSuchAlgorithmException |
                         NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException |
                         InvalidKeyException e) {
                    return getResponseEntity(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.value(), "잘못된 요청입니다.", siteInfo);
                } catch (CiException e) {
                    return getResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), siteInfo);
                } catch (Exception e) {
                    return getResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), siteInfo);
                }
            } else {
                CiLogger.info("siteInfo is null");
                return getResponseEntity(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.value(), "잘못된 요청입니다.", siteInfo);
            }
        } else {
            CiLogger.info("site_name is null");
            return getResponseEntity(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.value(), "잘못된 요청입니다.", siteInfo);
        }
    }

    @PostMapping(value = "/api/v2/closeaccount")
    @ResponseBody
    @Operation(tags = "회원", summary = "회원탈퇴", description = "암호화된(AES256) RequestBody를 사용해야함 ", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(schema = @Schema(implementation = CloseAccountRequest.class))))
    @ApiResponses(value = {@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = OpenApiResponse.class), examples = @ExampleObject(value = "{\"resultCode\": 200, \"resultMessage\": \"성공\"}"))), @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = OpenApiResponse.class), examples = @ExampleObject(value = "{\"resultCode\": 400, \"resultMessage\": \"잘못된 요청입니다.\"}"))), @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = OpenApiResponse.class), examples = @ExampleObject(value = "{\"resultCode\": 500, \"resultMessage\": \"\"}")))})
    public ResponseEntity<String> closeAccount(@RequestHeader(name = "so_id") String soId, @Valid @RequestBody String encryptedBody) throws Exception {
        SiteInfo siteInfo = null;
        String decryptedBody;

        if (Objects.nonNull(soId)) {
            siteInfo = apiService.getSiteInfo(soId);
            if (Objects.nonNull(siteInfo)) {
                try {
                    AES_256_ECB aesCipher = new AES_256_ECB(siteInfo.getEncrypt_key());
                    decryptedBody = aesCipher.dec_aes(encryptedBody);
                    CloseAccountRequest apiRequest = new ObjectMapper().registerModule(new JavaTimeModule()).readValue(decryptedBody, CloseAccountRequest.class);
                    String validation = validateRequest(apiRequest);
                    if(validation == null) {
                        OpenApiResponse response = apiService.closeAccount(apiRequest);
                        String encryptedResponse = aesCipher.enc_aes_object(response);
                        return ResponseEntity.status(response.getResultCode()).body(encryptedResponse);
                    }else{
                        return getResponseEntity(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.value(), validation, siteInfo);
                    }
                } catch (JsonProcessingException | UnsupportedEncodingException | NoSuchAlgorithmException |
                         NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException |
                         InvalidKeyException e) {
                    return getResponseEntity(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.value(), "잘못된 요청입니다.", siteInfo);
                } catch (CiException e) {
                    return getResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), siteInfo);
                } catch (Exception e) {
                    return getResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), siteInfo);
                }
            } else {
                return getResponseEntity(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.value(), "잘못된 요청입니다.", siteInfo);
            }
        } else {
            return getResponseEntity(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.value(), "잘못된 요청입니다.", siteInfo);
        }
    }



    @PostMapping(value = "/api/v2/passwordcheck")
    @ResponseBody
    @Operation(tags = "비밀번호", summary = "비밀번호 확인", description = "암호화된(AES256) RequestBody를 사용해야함 ", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(schema = @Schema(implementation = PasswordcheckRequest.class))))
    @ApiResponses(value = {@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = OpenApiResponse.class), examples = @ExampleObject(value = "{\"resultCode\": 200, \"resultMessage\": \"성공\"}"))), @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = OpenApiResponse.class), examples = @ExampleObject(value = "{\"resultCode\": 400, \"resultMessage\": \"잘못된 요청입니다.\"}"))), @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = OpenApiResponse.class), examples = @ExampleObject(value = "{\"resultCode\": 500, \"resultMessage\": \"\"}")))})
    public ResponseEntity<String> passwordcheck(@RequestHeader(name = "so_id" , required = false) String soId, @Valid @RequestBody String encryptedBody) throws Exception {
        SiteInfo siteInfo = null;
        String decryptedBody;

        if (Objects.nonNull(soId)) {
            siteInfo = apiService.getSiteInfo(soId);
            if (Objects.nonNull(siteInfo)) {
                try {
                    AES_256_ECB aesCipher = new AES_256_ECB(siteInfo.getEncrypt_key());
                    decryptedBody = aesCipher.dec_aes(encryptedBody);
                    PasswordcheckRequest apiRequest = new ObjectMapper().registerModule(new JavaTimeModule()).readValue(decryptedBody, PasswordcheckRequest.class);
                    String validation = validateRequest(apiRequest);
                    if(validation == null){
                        OpenApiResponse response = apiService.passwordcheck(apiRequest);
                        String encryptedResponse = aesCipher.enc_aes_object(response);
                        return ResponseEntity.status(response.getResultCode()).body(encryptedResponse);
                    }else{
                        return getResponseEntity(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.value(), validation, siteInfo);
                    }
                } catch (JsonProcessingException | UnsupportedEncodingException | NoSuchAlgorithmException |
                         NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException |
                         InvalidKeyException e) {
                    return getResponseEntity(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.value(), "잘못된 요청입니다.", siteInfo);
                } catch (CiException e) {
                    return getResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), siteInfo);
                } catch (Exception e) {
                    return getResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), siteInfo);
                }
            } else {
                return getResponseEntity(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.value(), "잘못된 요청입니다.", siteInfo);
            }
        } else {
            return getResponseEntity(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.value(), "잘못된 요청입니다.", siteInfo);
        }
    }

    @PostMapping(value = "/api/v2/passwordchange")
    @ResponseBody
    @Operation(tags = "비밀번호", summary = "비밀번호 변경", description = "암호화된(AES256) RequestBody를 사용해야함 ", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(schema = @Schema(implementation = PasswordchangeRequest.class))))
    @ApiResponses(value = {@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = OpenApiResponse.class), examples = @ExampleObject(value = "{\"resultCode\": 200, \"resultMessage\": \"성공\"}"))), @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = OpenApiResponse.class), examples = @ExampleObject(value = "{\"resultCode\": 400, \"resultMessage\": \"잘못된 요청입니다.\"}"))), @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = OpenApiResponse.class), examples = @ExampleObject(value = "{\"resultCode\": 500, \"resultMessage\": \"\"}")))})
    public ResponseEntity<String> passwordchange(@RequestHeader(name = "so_id") String soId, @Valid @RequestBody String encryptedBody) throws Exception {
        SiteInfo siteInfo = null;
        String decryptedBody;

        if (Objects.nonNull(soId)) {
            siteInfo = apiService.getSiteInfo(soId);
            if (Objects.nonNull(siteInfo)) {
                try {
                    AES_256_ECB aesCipher = new AES_256_ECB(siteInfo.getEncrypt_key());
                    decryptedBody = aesCipher.dec_aes(encryptedBody);
                    PasswordchangeRequest apiRequest = new ObjectMapper().registerModule(new JavaTimeModule()).readValue(decryptedBody, PasswordchangeRequest.class);
                    String validation = validateRequest(apiRequest);
                    if(validation == null) {
                        OpenApiResponse response = apiService.passwordChange(apiRequest);
                        String encryptedResponse = aesCipher.enc_aes_object(response);
                        return ResponseEntity.status(response.getResultCode()).body(encryptedResponse);
                    }else{
                        return getResponseEntity(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.value(), validation, siteInfo);
                    }
                } catch (JsonProcessingException | UnsupportedEncodingException | NoSuchAlgorithmException |
                         NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException |
                         InvalidKeyException e) {
                    return getResponseEntity(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.value(), "잘못된 요청입니다.", siteInfo);
                } catch (CiException e) {
                    return getResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), siteInfo);
                } catch (Exception e) {
                    return getResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), siteInfo);
                }
            } else {
                return getResponseEntity(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.value(), "잘못된 요청입니다.", siteInfo);
            }
        } else {
            return getResponseEntity(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.value(), "잘못된 요청입니다.", siteInfo);
        }
    }


    @PostMapping(value = "/api/v2/idduplicatecheck")
    @ResponseBody
    @Operation(tags = "ID 중복체크", summary = "ID 중복체크", description = "AES256(ECB)로 인코딩한 RequestBody를 사용해야함  ",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(schema = @Schema(implementation = IdDuplicateCheckRequest.class))),
            responses = {@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = OpenApiResponse.class), examples = @ExampleObject(value = "{\"resultCode\": 200, \"resultMessage\": \"성공\"}"))), @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = OpenApiResponse.class), examples = @ExampleObject(value = "{\"resultCode\": 400, \"resultMessage\": \"잘못된 요청입니다.\"}"))), @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = OpenApiResponse.class), examples = @ExampleObject(value = "{\"resultCode\": 500, \"resultMessage\": \"\"}")))})
    public ResponseEntity<String> idDuplicateCheck(@RequestHeader(name = "so_id") String soId,HttpServletRequest httpRequest, @Valid @RequestBody String encryptedBody) throws Exception {
        SiteInfo siteInfo = null;
        String decryptedBody;

        if (Objects.nonNull(soId)) {
            siteInfo = apiService.getSiteInfo(soId);
            if (Objects.nonNull(siteInfo)) {
                try {
                    AES_256_ECB aesCipher = new AES_256_ECB(siteInfo.getEncrypt_key());
                    decryptedBody = aesCipher.dec_aes(encryptedBody);
                    IdDuplicateCheckRequest apiRequest = new ObjectMapper().registerModule(new JavaTimeModule()).readValue(decryptedBody, IdDuplicateCheckRequest.class);
                    String validation = validateRequest(apiRequest);
                    if(validation == null) {
                        OpenApiResponse response = apiService.idDuplicateCheck(apiRequest);
                        String encryptedResponse = aesCipher.enc_aes_object(response);
                        return ResponseEntity.status(response.getResultCode()).body(encryptedResponse);
                    }else{
                        return getResponseEntity(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.value(), validation, siteInfo);
                    }
                } catch (JsonProcessingException | UnsupportedEncodingException | NoSuchAlgorithmException |
                         NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException |
                         InvalidKeyException e) {
                    return getResponseEntity(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.value(), "잘못된 요청입니다.", siteInfo);
                } catch (CiException e) {
                    return getResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), siteInfo);
                } catch (Exception e) {
                    return getResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), siteInfo);
                }
            } else {
                return getResponseEntity(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.value(), "잘못된 요청입니다.", siteInfo);
            }
        } else {
            return getResponseEntity(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.value(), "잘못된 요청입니다.", siteInfo);
        }
    }

    @PostMapping(value = "/api/v2/productpurchase")
    @ResponseBody
    @Operation(tags = "Ochoice 프리미엄 구독", summary = "상품가입", description = "SVOD(가입형상품) 구매 , AES256(ECB)로 인코딩한 RequestBody를 사용해야함 ",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(schema = @Schema(implementation = ProductPurchaseRequest.class))),
            responses = {@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = OpenApiResponse.class), examples = @ExampleObject(value = "{\"resultCode\": 200, \"resultMessage\": \"성공\"}"))), @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = OpenApiResponse.class), examples = @ExampleObject(value = "{\"resultCode\": 400, \"resultMessage\": \"잘못된 요청입니다.\"}"))), @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = OpenApiResponse.class), examples = @ExampleObject(value = "{\"resultCode\": 500, \"resultMessage\": \"\"}")))})
    public ResponseEntity<String> productPurchase(@RequestHeader(name = "so_id") String soId,HttpServletRequest httpRequest, @Valid @RequestBody String encryptedBody) throws Exception {
        SiteInfo siteInfo = null;
        String decryptedBody;

        if (Objects.nonNull(soId)) {
            siteInfo = apiService.getSiteInfo(soId);
            if (Objects.nonNull(siteInfo)) {
                try {
                    AES_256_ECB aesCipher = new AES_256_ECB(siteInfo.getEncrypt_key());
                    decryptedBody = aesCipher.dec_aes(encryptedBody);
                    ProductPurchaseRequest apiRequest = new ObjectMapper().registerModule(new JavaTimeModule()).readValue(decryptedBody, ProductPurchaseRequest.class);
                    String validation = validateRequest(apiRequest);
                    if(validation == null) {
                        OpenApiResponse response = apiService.productPurchase(apiRequest);
                        String encryptedResponse = aesCipher.enc_aes_object(response);
                        return ResponseEntity.status(response.getResultCode()).body(encryptedResponse);
                    }else{
                        return getResponseEntity(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.value(), validation, siteInfo);
                    }
                } catch (JsonProcessingException | UnsupportedEncodingException | NoSuchAlgorithmException |
                         NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException |
                         InvalidKeyException e) {
                    return getResponseEntity(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.value(), "잘못된 요청입니다.", siteInfo);
                } catch (CiException e) {
                    return getResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), siteInfo);
                } catch (Exception e) {
                    return getResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), siteInfo);
                }
            } else {
                return getResponseEntity(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.value(), "잘못된 요청입니다.", siteInfo);
            }
        } else {
            return getResponseEntity(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.value(), "잘못된 요청입니다.", siteInfo);
        }
    }

    @PostMapping(value = "/api/v2/productpurchasecancel")
    @ResponseBody
    @Operation(tags = "Ochoice 프리미엄 구독", summary = "구독 취소", description = "SVOD(가입형상품) 구매 , AES256(ECB)로 인코딩한 RequestBody를 사용해야함 ",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(schema = @Schema(implementation = ProductPurchaseRequest.class))),
            responses = {@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = OpenApiResponse.class), examples = @ExampleObject(value = "{\"resultCode\": 200, \"resultMessage\": \"성공\"}"))), @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = OpenApiResponse.class), examples = @ExampleObject(value = "{\"resultCode\": 400, \"resultMessage\": \"잘못된 요청입니다.\"}"))), @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = OpenApiResponse.class), examples = @ExampleObject(value = "{\"resultCode\": 500, \"resultMessage\": \"\"}")))})
    public ResponseEntity<String> productPurchaseCancel(@RequestHeader(name = "so_id") String soId,HttpServletRequest httpRequest, @Valid @RequestBody String encryptedBody) throws Exception {
        SiteInfo siteInfo = null;
        String decryptedBody;

        if (Objects.nonNull(soId)) {
            siteInfo = apiService.getSiteInfo(soId);
            if (Objects.nonNull(siteInfo)) {
                try {
                    AES_256_ECB aesCipher = new AES_256_ECB(siteInfo.getEncrypt_key());
                    decryptedBody = aesCipher.dec_aes(encryptedBody);
                    ProductPurchaseRequest apiRequest = new ObjectMapper().registerModule(new JavaTimeModule()).readValue(decryptedBody, ProductPurchaseRequest.class);
                    String validation = validateRequest(apiRequest);
                    if(validation == null) {
                        OpenApiResponse response = apiService.productPurchaseCancel(apiRequest);
                        String encryptedResponse = aesCipher.enc_aes_object(response);
                        return ResponseEntity.status(response.getResultCode()).body(encryptedResponse);
                    }else{
                        return getResponseEntity(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.value(), validation, siteInfo);
                    }
                } catch (JsonProcessingException | UnsupportedEncodingException | NoSuchAlgorithmException |
                         NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException |
                         InvalidKeyException e) {
                    return getResponseEntity(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.value(), "잘못된 요청입니다.", siteInfo);
                } catch (CiException e) {
                    return getResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), siteInfo);
                } catch (Exception e) {
                    return getResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), siteInfo);
                }
            } else {
                return getResponseEntity(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.value(), "잘못된 요청입니다.", siteInfo);
            }
        } else {
            return getResponseEntity(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.value(), "잘못된 요청입니다.", siteInfo);
        }
    }

    @Hidden
    @GetMapping(value = "/api/v2/getproductlist")
    public ResponseEntity<String> getProductList(@RequestHeader(name = "so_id") String soId) throws Exception {
        SiteInfo siteInfo = null;
        String decryptedBody;

        if (Objects.nonNull(soId)) {
            siteInfo = apiService.getSiteInfo(soId);
            if (Objects.nonNull(siteInfo)) {
                try {
                    AES_256_ECB aesCipher = new AES_256_ECB(siteInfo.getEncrypt_key());
                    //decryptedBody = aesCipher.dec_aes(encryptedBody);
                    //ProductListRequest apiRequest = new ObjectMapper().registerModule(new JavaTimeModule()).readValue(decryptedBody, ProductListRequest.class);
                    ProductListRequest apiRequest = ProductListRequest.builder().productType("subscription").packageType("package").currentPage(1).pageSize(10).build();
                    String validation = validateRequest(apiRequest);
                    if(validation == null) {
                        ProductListResponse response = apiService.getProductList(apiRequest);
                        String encryptedResponse = aesCipher.enc_aes_object(response);
                        return ResponseEntity.status(HttpStatus.OK).body(encryptedResponse);
                    }else{
                        return getResponseEntity(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.value(), validation, siteInfo);
                    }
                } catch (JsonProcessingException | UnsupportedEncodingException | NoSuchAlgorithmException |
                         NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException |
                         InvalidKeyException e) {
                    return getResponseEntity(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.value(), "잘못된 요청입니다.", siteInfo);
                } catch (CiException e) {
                    return getResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), siteInfo);
                } catch (Exception e) {
                    return getResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), siteInfo);
                }
            } else {
                CiLogger.info("siteInfo is null");
                return getResponseEntity(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.value(), "잘못된 요청입니다.", siteInfo);
            }
        } else {
            CiLogger.info("soId is null");
            return getResponseEntity(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.value(), "잘못된 요청입니다.", siteInfo);
        }
    }

    private static ResponseEntity<String> getResponseEntity(int httpStatus , int resultCode , String resultMessage , SiteInfo siteInfo) throws Exception {
        UsersignupResponse response = new UsersignupResponse();
        response.setResultCode(resultCode);
        response.setResultMessage(resultMessage);
        // 에러 응답 암호화 및 반환
        String encryptedResponse;
        if (siteInfo != null) {
            AES_256_ECB aesCipher = new AES_256_ECB(siteInfo.getEncrypt_key());
            encryptedResponse = aesCipher.enc_aes_object(response);
        } else {
            encryptedResponse = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(response);
        }
        return ResponseEntity.status(httpStatus).body(encryptedResponse);
    }

    private ResponseEntity<String> getResponseEntity(SiteInfo siteInfo, AES_256_ECB aesCipher, OpenApiResponse response) throws Exception {
        String validation = validateRequest(response);
        if(validation == null) {
            String encryptedResponse = aesCipher.enc_aes_object(response);
            return ResponseEntity.status(response.getResultCode()).body(encryptedResponse);
        }else{
            return getResponseEntity(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.value(), validation, siteInfo);
        }
    }
}
