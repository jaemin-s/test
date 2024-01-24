package com.castis.pvs.api.controller;

import com.castis.common.constants.CiResultCode;
import com.castis.common.exception.CiException;
import com.castis.common.model.CiRequest;
import com.castis.common.model.CiResponse;
import com.castis.common.model.ImsResponse;
import com.castis.common.util.AES_256_ECB;
import com.castis.common.util.CiLogUtil;
import com.castis.common.util.CiLogger;
import com.castis.pvs.api.model.*;
import com.castis.pvs.api.model.v2.request.UsersignupRequest;
import com.castis.pvs.api.model.v2.response.UsersignupResponse;
import com.castis.pvs.api.service.ApiService;
import com.castis.pvs.entity.SiteInfo;
import com.castis.pvs.member.dto.MemberDTO;
import com.castis.pvs.pay.model.PurchaseRequest;
import com.castis.pvs.swagger.model.DocApiResponse;
import com.castis.pvs.swagger.model.DocIsDuplicateCheckResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.springframework.ui.ModelMap;

@Controller
public class ApiController {

    @Autowired
    private ApiService apiService;

    private Validator validator; 
    public ApiController (ApiService apiService,Validator validator){
        this.apiService = apiService;
        this.validator = validator;
    }


    @Value("${pvs.preview_period_in_minute:1440}")
    private int previewPeriodInMinute;

    @PostMapping(value = "/api/passwordCheck")
    @ResponseBody
    @Hidden
    public PasswordCheckResponse passwordCheck(HttpServletRequest httpRequest, @Valid @RequestBody PasswordCheckRequest apiRequest) throws Exception {
        try {
            CiLogger.request(httpRequest, apiRequest.toString());
            PasswordCheckResponse response = apiService.passwordCheckRequestV2(apiRequest);
            return response;
        } catch (CiException e) {
            PasswordCheckResponse response = new PasswordCheckResponse();
            response.setResult_code(CiResultCode.code_101);
            response.setResult_msg(e.getErrorString());
            return response;
        } catch (Exception e) {
            PasswordCheckResponse response = new PasswordCheckResponse();
            response.setResult_code(CiResultCode.GENERAL_ERROR);
            response.setResult_msg(e.getMessage());
            return response;
        }
    }

    @PostMapping(value = "/api/passwordCheck/v2")
    @ResponseBody
    @Operation(tags = "비밀번호", summary = "비밀번호 확인", description = "암호화된(AES256) RequestBody를 사용해야함 \n 인코딩하지 않았을 경우 500에러", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(schema = @Schema(implementation = PasswordCheckRequest.class))))
    public ResponseEntity<String> passwordCheckV2(@RequestHeader(name = "site_name" , required = false) String siteName,HttpServletRequest httpRequest, @Valid @RequestBody String encryptedBody) throws Exception {
        SiteInfo siteInfo = null;
        try {
            String msoName = siteName;
            String decryptedBody;
            CiLogger.request(httpRequest, encryptedBody);

            if (Objects.nonNull(msoName)) {
                siteInfo = apiService.getSiteInfo(msoName);
                if (siteInfo.getEncrypt_use()) {
                    AES_256_ECB aesCipher = new AES_256_ECB(siteInfo.getEncrypt_key());
                    decryptedBody = aesCipher.dec_aes_object(encryptedBody);
                } else {
                    decryptedBody = encryptedBody;
                }
            } else {
                decryptedBody = encryptedBody;
            }

            PasswordCheckRequest apiRequest = new ObjectMapper().readValue(decryptedBody, PasswordCheckRequest.class);
            PasswordCheckResponse response = apiService.passwordCheckRequestV2(apiRequest);

            String encryptedResponse;
            if (siteInfo != null && siteInfo.getEncrypt_use()) {
                AES_256_ECB aesCipher = new AES_256_ECB(siteInfo.getEncrypt_key());
                encryptedResponse = aesCipher.enc_aes_object(response);
            } else {
                encryptedResponse = new ObjectMapper().writeValueAsString(response);
            }

            return ResponseEntity.ok(encryptedResponse);
        } catch (CiException e) {
            CiResponse response = new CiResponse();
            response.setResultCode(CiResultCode.code_101);
            response.setErrorMessage(e.getErrorString());

            String encryptedResponse;
            if (siteInfo != null && siteInfo.getEncrypt_use()) {
                AES_256_ECB aesCipher = new AES_256_ECB(siteInfo.getEncrypt_key());
                encryptedResponse = aesCipher.enc_aes_object(response);
            }else{
                encryptedResponse = new ObjectMapper().writeValueAsString(response);
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(encryptedResponse);
        } catch (Exception e) {
            e.printStackTrace();
            CiResponse response = new CiResponse();
            response.setResultCode(CiResultCode.GENERAL_ERROR);
            response.setErrorMessage(e.getMessage());

            String encryptedResponse;
            if (siteInfo != null && siteInfo.getEncrypt_use()) {
                AES_256_ECB aesCipher = new AES_256_ECB(siteInfo.getEncrypt_key());
                encryptedResponse = aesCipher.enc_aes_object(response);
            }else{
                encryptedResponse = new ObjectMapper().writeValueAsString(response);
            }

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(encryptedResponse);
        }
    }


    @PostMapping(value = "/api/memberUnregister")
    @ResponseBody
    @Hidden
    public CiResponse memberUnregister(HttpServletRequest httpRequest, @Valid @RequestBody ApiRequest apiRequest) throws Exception {

        try {
            CiLogger.request(httpRequest, apiRequest.toString());
            CiResponse response = apiService.unregistMember(apiRequest);
            return response;

        } catch (CiException e) {
            CiResponse response = new CiResponse();
            response.setResultCode(CiResultCode.INVALID_ACCOUNT_ID);
            response.setErrorMessage(e.getErrorString());
            return response;
        } catch (Exception e) {
            CiResponse response = new CiResponse();
            response.setResultCode(CiResultCode.GENERAL_ERROR);
            response.setErrorMessage(e.getMessage());
            return response;
        }
    }

    @PostMapping(value = "/api/adultCheck")
    @ResponseBody
    @Deprecated
    @Hidden
    public CiResponse adultCheck(HttpServletRequest httpRequest, @Valid @RequestBody ApiRequest apiRequest) throws Exception {

        try {
            CiLogger.request(httpRequest, apiRequest.toString());
            CiResponse response = apiService.adultCheckStb(apiRequest);
            return response;

        } catch (CiException e) {
            CiResponse response = new CiResponse();
            response.setResultCode(CiResultCode.INVALID_ACCOUNT_ID);
            response.setErrorMessage(e.getErrorString());
            return response;
        } catch (Exception e) {
            CiResponse response = new CiResponse();
            response.setResultCode(CiResultCode.GENERAL_ERROR);
            response.setErrorMessage(e.getMessage());
            return response;
        }
    }

    /**
     * 성인 비밀번호 변경
     *
     * @param httpRequest
     * @param apiRequest
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/api/passwordSet")
    @ResponseBody
    @Hidden
    public CiResponse passwordSet(HttpServletRequest httpRequest, @Valid @RequestBody ApiRequest apiRequest) throws Exception {

        try {
            CiLogger.request(httpRequest, apiRequest.toString());
            CiResponse response = apiService.passwordSet(apiRequest);
            return response;

        } catch (CiException e) {
            CiResponse response = new CiResponse();
            response.setResultCode(CiResultCode.INVALID_ACCOUNT_ID);
            response.setErrorMessage(e.getErrorString());
            return response;
        } catch (Exception e) {
            CiResponse response = new CiResponse();
            response.setResultCode(CiResultCode.GENERAL_ERROR);
            response.setErrorMessage(e.getMessage());
            return response;
        }
    }

    @PostMapping(value = "/api/memberInactive")
    @ResponseBody
    @Hidden
    public CiResponse memberInactive(HttpServletRequest httpRequest, @Valid @RequestBody ApiRequest apiRequest) throws Exception {

        try {
            CiLogger.request(httpRequest, apiRequest.toString());
            CiResponse response = apiService.inactiveMember(apiRequest);
            return response;

        } catch (CiException e) {
            CiResponse response = new CiResponse();
            response.setResultCode(CiResultCode.INVALID_ACCOUNT_ID);
            response.setErrorMessage(e.getErrorString());
            return response;
        } catch (Exception e) {
            CiResponse response = new CiResponse();
            response.setResultCode(CiResultCode.GENERAL_ERROR);
            response.setErrorMessage(e.getMessage());
            return response;
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<CiResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        CiResponse response = new CiResponse();
        response.setResultCode(CiResultCode.BAD_REQUEST);
        response.setErrorMessage(ex.getBindingResult().getFieldError().getField() + " " + CiResultCode.MSG.code_198);
        response.addAttribute();
        return ResponseEntity.badRequest().body(response);
    }

    @Deprecated
    @PostMapping(value = "/api/userSignUp")
    @ResponseBody
    @Hidden
    public UserSignUpResponse userSignUp(HttpServletRequest httpRequest, @Valid @RequestBody UserSignUpRequest apiRequest) throws Exception {
        try {
            CiLogger.request(httpRequest, apiRequest.toString());
            MemberDTO memberDTO = new MemberDTO(apiRequest);
            UserSignUpResponse response = apiService.saveMember(memberDTO);
            return response;

        } catch (CiException e) {
            UserSignUpResponse response = new UserSignUpResponse();
            response.setResult_code(e.getCode());
            response.setResult_msg(e.getErrorString());
            return response;
        } catch (Exception e) {
            UserSignUpResponse response = new UserSignUpResponse();
            response.setResult_code(CiResultCode.GENERAL_ERROR);
            response.setResult_msg(e.getMessage());
            return response;
        }
    }

    /**
     * CMB 전용
     * @param siteName
     * @param httpRequest
     * @param encryptedBody
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/api/userSignUp/v2")
    @ResponseBody
    @Operation(tags = "회원", summary = "회원가입", description = "암호화된(AES256) RequestBody를 사용해야함 \n 인코딩하지 않았을 경우 500에러", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(schema = @Schema(implementation = UserSignUpV2Request.class))))
    //@Parameter(name = "encryptedBody" ,description = "AES256으로 암호화된 요청 바디", required = true, schema = @Schema(implementation = UserSignUpV2Request.class))
    @ApiResponses(value = {@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = DocApiResponse.class))),})
    public ResponseEntity<String> userSignUpV2(@RequestHeader(name = "site_name") String siteName,HttpServletRequest httpRequest, @Valid @RequestBody String encryptedBody) throws Exception {
        SiteInfo siteInfo = null;
        try {
            String msoName = siteName;
            String decryptedBody;
            CiLogger.request(httpRequest, encryptedBody);

            if (Objects.nonNull(msoName)) {
                siteInfo = apiService.getSiteInfo(msoName);
                if (siteInfo.getEncrypt_use()) {
                    AES_256_ECB aesCipher = new AES_256_ECB(siteInfo.getEncrypt_key());
                    decryptedBody = aesCipher.dec_aes_object(encryptedBody);
                } else {
                    // 요청 바디 복호화
                    decryptedBody = encryptedBody;
                }
            } else {
                decryptedBody = encryptedBody;
            }
            // 복호화된 데이터로 UserSignUpV2Request 객체 생성
            UserSignUpV2Request apiRequest = new ObjectMapper().readValue(decryptedBody, UserSignUpV2Request.class);
            MemberDTO memberDTO = new MemberDTO(apiRequest);

            UserSignUpResponse response = apiService.saveMemberV2(memberDTO);

            // 응답 암호화
            String encryptedResponse;
            if (siteInfo != null && siteInfo.getEncrypt_use()) {
                AES_256_ECB aesCipher = new AES_256_ECB(siteInfo.getEncrypt_key());
                encryptedResponse = aesCipher.enc_aes_object(response);
            } else {
                encryptedResponse = new ObjectMapper().writeValueAsString(response);
            }

            return ResponseEntity.ok(encryptedResponse);
        } catch (CiException e) {
            CiResponse response = new CiResponse();
            response.setResultCode(CiResultCode.INVALID_ACCOUNT_ID);
            response.setErrorMessage(e.getErrorString());

            // 에러 응답 암호화 및 반환
            String encryptedResponse;
            if (siteInfo != null && siteInfo.getEncrypt_use()) {
                AES_256_ECB aesCipher = new AES_256_ECB(siteInfo.getEncrypt_key());
                encryptedResponse = aesCipher.enc_aes_object(response);
            }else{
                encryptedResponse = new ObjectMapper().writeValueAsString(response);
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(encryptedResponse);
        } catch (Exception e) {
            CiResponse response = new CiResponse();
            response.setResultCode(CiResultCode.GENERAL_ERROR);
            response.setErrorMessage(e.getMessage());

            // 에러 응답 암호화 및 반환
            String encryptedResponse;
            if (siteInfo != null && siteInfo.getEncrypt_use()) {
                AES_256_ECB aesCipher = new AES_256_ECB(siteInfo.getEncrypt_key());
                encryptedResponse = aesCipher.enc_aes_object(response);
            }else{
                encryptedResponse = new ObjectMapper().writeValueAsString(response);
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(encryptedResponse);
        }
    }

    /**
     * HCN 전용 회원인증 
     * 가입 처리 
     * @param httpRequest
     * @param apiRequest
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/api/userSignUp/v3")
    @ResponseBody
    @Hidden
    public CiResponse userSignUpV3(HttpServletRequest httpRequest, @Valid @RequestBody UserSignUpV2Request apiRequest) throws Exception {
        try {
            CiLogger.request(httpRequest, apiRequest.toString());
            MemberDTO memberDTO = new MemberDTO(apiRequest);
            SiteInfo siteInfo = apiService.getSiteInfo(memberDTO.getMso_name());
            //so 회원 인증 추가 
            CiResponse response = apiService.saveMemberV3(memberDTO, siteInfo);
            return response;
        } catch (CiException e) {
            CiResponse response = new CiResponse();
            response.setResultCode(CiResultCode.INVALID_ACCOUNT_ID);
            response.setErrorMessage(e.getErrorString());
            return response;
        } catch (Exception e) {
            CiResponse response = new CiResponse();
            response.setResultCode(CiResultCode.GENERAL_ERROR);
            response.setErrorMessage(e.getMessage());
            return response;
        }
    }

    // @GetMapping(value = "/api/v3/getSiteInfo")
    // @ResponseBody
    // @Hidden
    // // ResponseEntity<String> gethappycalllist(@RequestHeader(name = "so_id") String soId, @RequestParam("search_startdate") 
    // public ResponseEntity<String> siteInfo(@RequestHeader(name = "so_id") String soId, @RequestParam("mso_name") String mso_name) throws Exception{
    //     SiteInfo siteInfo = null;
    //     String decryptedBody;

    //     if (Objects.nonNull(soId)) {
    //         siteInfo = apiService.getSiteInfoBySoId(soId);
    //         if(Objects.nonNull(siteInfo)){
    //             SiteInfo resposne = apiService.getSiteInfo(soId);

    //         }else{

    //         }
    //     }
       
    //     return null;
    // }


    /**
     * SO 전용 회원인증 가입 처리 
     * 앱 -> pvs -> so system 회원인증 요청 
     * 
     * @param httpRequest
     * @param apiRequest
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/api/v3/userSignUpBySo")
    @ResponseBody
    @Hidden
    public ResponseEntity<String> userSignUpBySoV3(@RequestHeader(name = "so_id") String soId,  @Valid @RequestBody String encryptedBody) throws Exception {
        SiteInfo siteInfo = null;
        String validation =  null;
        UsersignupRequest apiRequestSo = null;
        MemberDTO memberDTO = null;
        String decryptedBody;

        if(Objects.nonNull(soId)){
            //회원인증 계약이 되서 회원인증 서버가 존재하는 so별 처리 
            siteInfo = apiService.getSiteInfoBySoId(soId);
            if(Objects.nonNull(siteInfo)){
                try {
                    AES_256_ECB aesCipher = new AES_256_ECB(siteInfo.getEncrypt_key());
                    decryptedBody = aesCipher.dec_aes(encryptedBody);
                    apiRequestSo = new ObjectMapper().registerModule(new JavaTimeModule()).readValue(decryptedBody,UsersignupRequest.class);
                    //validation check
                    validation = validateRequest(apiRequestSo);
                    if(validation == null){
                        memberDTO = new MemberDTO(apiRequestSo);                       
                        //so서버 회원인증 후 회원 등록 진행 
                        CiResponse response = apiService.saveMemberV3(memberDTO, siteInfo);

                        ModelMap responseMap = response;
                        Integer happycall =(Integer) responseMap.getAttribute("happycall");//해피콜 인증 변수 1:해피콜 대상, 0: 정상 가입자 
                        int resultcode = response.getResultCode();                        
                        return getResponseEntity(HttpStatus.OK.value(), resultcode, siteInfo.getMso_name().toString()+ "케이블 TV 가입자, "+ memberDTO.getTel()+ "인증 및 오초이스 회원등록 완료", siteInfo, happycall);
                    }else{
                        return getResponseEntity(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.value(),  validation.toString(), siteInfo);
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

            }else {
                CiLogger.info("siteInfo is null");
                return getResponseEntity(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.value(), "잘못된 요청입니다.", siteInfo);
            }

        } else {
            CiLogger.info("so id is null");
            return getResponseEntity(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.value(), "so id 잘못된 요청입니다.", siteInfo);
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

  


    @PostMapping(value = "/api/userInfo")
    @ResponseBody
    @Hidden
    public CiResponse userInfo(HttpServletRequest httpRequest, @Valid @RequestBody UserSignUpRequest apiRequest) throws Exception {

        try {
            CiLogger.request(httpRequest, apiRequest.toString());
            CiResponse response = apiService.getMemberbyMemberId(apiRequest);
            return response;

        } catch (CiException e) {
            CiResponse response = new CiResponse();
            response.setResultCode(CiResultCode.INVALID_ACCOUNT_ID);
            response.setErrorMessage(e.getErrorString());
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            CiResponse response = new CiResponse();
            response.setResultCode(CiResultCode.GENERAL_ERROR);
            response.setErrorMessage(e.getMessage());
            return response;
        }
    }

    /**
     * 정보 수정. 현재 전화번호 unique하게 사용되야 하므로. 저장전에 전화번호 중복체크 필요
     * @param httpRequest
     * @param apiRequest
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/api/userInfoUpdate")
    @ResponseBody
    @Hidden
    public CiResponse userInfoUpdate(HttpServletRequest httpRequest, @Valid @RequestBody UserSignUpRequest apiRequest) throws Exception {

        try {
            CiLogger.request(httpRequest, apiRequest.toString());
            MemberDTO memberDTO = new MemberDTO(apiRequest);
            CiResponse response = apiService.updateMember(memberDTO);
            return response;

        } catch (CiException e) {
            CiResponse response = new CiResponse();
            response.setResultCode(CiResultCode.INVALID_ACCOUNT_ID);
            response.setErrorMessage(e.getErrorString());
            return response;
        } catch (Exception e) {
            CiResponse response = new CiResponse();
            response.setResultCode(CiResultCode.GENERAL_ERROR);
            response.setErrorMessage(e.getMessage());
            return response;
        }
    }

    @PostMapping(value = "/api/close-account")
    @ResponseBody
    @Hidden
    public CiResponse closeAccount(HttpServletRequest httpRequest, @Valid @RequestBody UserSignOutV2Request apiRequest) throws Exception {
        CiLogger.request(httpRequest, apiRequest.toString());
        return apiService.signOutMemberVer3(apiRequest);
    }

    @PostMapping(value = "/api/close-account/v2")
    @ResponseBody
    @Operation(tags = "회원", summary = "회원탈퇴", description = "암호화된(AES256) RequestBody를 사용해야함 \n 인코딩하지 않았을 경우 500에러", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(schema = @Schema(implementation = UserSignOutV2Request.class))))
    @ApiResponses(value = {@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = DocApiResponse.class))),})
    public ResponseEntity<String> closeAccountV2(@RequestHeader(name = "site_name") String siteName,HttpServletRequest httpRequest, @Valid @RequestBody String encryptedBody) throws Exception {
        SiteInfo siteInfo = null;
        try {
            String msoName = siteName;
            String decryptedBody;
            CiLogger.request(httpRequest, encryptedBody);

            if (Objects.nonNull(msoName)) {
                siteInfo = apiService.getSiteInfo(msoName);
                if (siteInfo.getEncrypt_use()) {
                    AES_256_ECB aesCipher = new AES_256_ECB(siteInfo.getEncrypt_key());
                    decryptedBody = aesCipher.dec_aes_object(encryptedBody);
                } else {
                    // 요청 바디 복호화
                    decryptedBody = encryptedBody;
                }
            } else {
                decryptedBody = encryptedBody;
            }
            // 복호화된 데이터로 UserSignOutV2Request 객체 생성
            UserSignOutV2Request apiRequest = new ObjectMapper().readValue(decryptedBody, UserSignOutV2Request.class);

            CiResponse response = apiService.signOutMemberVer2(apiRequest);

            // 응답 암호화
            String encryptedResponse;
            if (siteInfo != null && siteInfo.getEncrypt_use()) {
                AES_256_ECB aesCipher = new AES_256_ECB(siteInfo.getEncrypt_key());
                encryptedResponse = aesCipher.enc_aes_object(response);
            } else {
                encryptedResponse = new ObjectMapper().writeValueAsString(response);
            }

            return ResponseEntity.ok(encryptedResponse);
        } catch (CiException e) {
            CiResponse response = new CiResponse();
            response.setResultCode(CiResultCode.INVALID_ACCOUNT_ID);
            response.setErrorMessage(e.getErrorString());

            // 에러 응답 암호화 및 반환
            String encryptedResponse;
            if (siteInfo != null && siteInfo.getEncrypt_use()) {
                AES_256_ECB aesCipher = new AES_256_ECB(siteInfo.getEncrypt_key());
                encryptedResponse = aesCipher.enc_aes_object(response);
            }else{
                encryptedResponse = new ObjectMapper().writeValueAsString(response);
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(encryptedResponse);
        } catch (Exception e) {
            CiResponse response = new CiResponse();
            response.setResultCode(CiResultCode.GENERAL_ERROR);
            response.setErrorMessage(e.getMessage());

            // 에러 응답 암호화 및 반환
            String encryptedResponse;
            if (siteInfo != null && siteInfo.getEncrypt_use()) {
                AES_256_ECB aesCipher = new AES_256_ECB(siteInfo.getEncrypt_key());
                encryptedResponse = aesCipher.enc_aes_object(response);
            }else{
                encryptedResponse = new ObjectMapper().writeValueAsString(response);
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(encryptedResponse);
        }
    }


    @PostMapping(value = "/api/previewPeriod")
    @ResponseBody
    @Hidden
    public CiResponse previewPeriod(HttpServletRequest httpRequest, @Valid @RequestBody UserSignUpRequest apiRequest) throws Exception {
        try {
            CiLogger.request(httpRequest, apiRequest.toString());
            CiResponse response = apiService.previewPeriod(apiRequest, previewPeriodInMinute);
            return response;

        } catch (CiException e) {
            CiResponse response = new CiResponse();
            response.setResultCode(e.getCode());
            response.setErrorMessage(e.getErrorString());
            return response;
        } catch (Exception e) {
            CiResponse response = new CiResponse();
            response.setResultCode(CiResultCode.GENERAL_ERROR);
            response.setErrorMessage(e.getMessage());
            return response;
        }
    }

    @PostMapping(value = "/api/passwordMatch")
    @ResponseBody
    @Hidden
    public CiResponse passwordMatch(HttpServletRequest httpRequest, @Valid @RequestBody UserSignUpRequest apiRequest) throws Exception {
        try {
            CiLogger.request(httpRequest, apiRequest.toString());
            CiResponse response = apiService.passwordMatch(apiRequest);
            return response;

        } catch (CiException e) {
            CiResponse response = new CiResponse();
            response.setResultCode(e.getCode());
            response.setErrorMessage(e.getErrorString());
            return response;
        } catch (Exception e) {
            CiResponse response = new CiResponse();
            response.setResultCode(CiResultCode.GENERAL_ERROR);
            response.setErrorMessage(e.getMessage());
            return response;
        }
    }

    @PostMapping(value = "/api/userExist")
    @ResponseBody
    @Hidden
    public CiResponse userExist(HttpServletRequest httpRequest, @Valid @RequestBody UserFindByIdNPhoneRequest apiRequest) throws Exception {
        try {
            CiLogger.request(httpRequest, apiRequest.toString());
            CiResponse response = apiService.userExist(apiRequest);
            return response;

        } catch (CiException e) {
            CiResponse response = new CiResponse();
            response.setResultCode(e.getCode());
            response.setErrorMessage(e.getErrorString());
            return response;
        } catch (Exception e) {
            CiResponse response = new CiResponse();
            response.setResultCode(CiResultCode.GENERAL_ERROR);
            response.setErrorMessage(e.getMessage());
            return response;
        }
    }

    @PostMapping(value = "/api/passwordChange")
    @ResponseBody
    @Hidden
    public CiResponse passwordChange(HttpServletRequest httpRequest, @Valid @RequestBody UserPassRequest apiRequest) throws Exception {
        try {
            CiLogger.request(httpRequest, apiRequest.toString());
            CiResponse response = apiService.passwordChange(apiRequest);
            return response;

        } catch (CiException e) {
            CiResponse response = new CiResponse();
            response.setResultCode(e.getCode());
            response.setErrorMessage(e.getErrorString());
            return response;
        } catch (Exception e) {
            CiResponse response = new CiResponse();
            response.setResultCode(CiResultCode.GENERAL_ERROR);
            response.setErrorMessage(e.getMessage());
            return response;
        }
    }

    @PostMapping(value = "/api/passwordChange/v2")
    @ResponseBody
    @Operation(tags = "비밀번호", summary = "비밀번호 변경", description = "암호화된(AES256) RequestBody를 사용해야함 \n 인코딩하지 않았을 경우 500에러", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(schema = @Schema(implementation = UserPassRequest.class))))
    @ApiResponses(value = {@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = DocApiResponse.class))),})
    public ResponseEntity<String> passwordChange2(@RequestHeader(name = "site_name") String siteName,HttpServletRequest httpRequest, @Valid @RequestBody String encryptedBody) throws Exception {
        SiteInfo siteInfo = null;
        try {
            String msoName = siteName;
            String decryptedBody;
            CiLogger.request(httpRequest, encryptedBody);

            if (Objects.nonNull(msoName)) {
                siteInfo = apiService.getSiteInfo(msoName);
                if (siteInfo.getEncrypt_use()) {
                    AES_256_ECB aesCipher = new AES_256_ECB(siteInfo.getEncrypt_key());
                    decryptedBody = aesCipher.dec_aes_object(encryptedBody);
                } else {
                    decryptedBody = encryptedBody;
                }
            } else {
                decryptedBody = encryptedBody;
            }

            UserPassRequest apiRequest = new ObjectMapper().readValue(decryptedBody, UserPassRequest.class);
            CiResponse response = apiService.passwordChange(apiRequest);

            String encryptedResponse;
            if (siteInfo != null && siteInfo.getEncrypt_use()) {
                AES_256_ECB aesCipher = new AES_256_ECB(siteInfo.getEncrypt_key());
                encryptedResponse = aesCipher.enc_aes_object(response);
            } else {
                encryptedResponse = new ObjectMapper().writeValueAsString(response);
            }

            return ResponseEntity.ok(encryptedResponse);
        } catch (CiException e) {
            CiResponse response = new CiResponse();
            response.setResultCode(e.getCode());
            response.setErrorMessage(e.getErrorString());

            String encryptedResponse;
            if (siteInfo != null && siteInfo.getEncrypt_use()) {
                AES_256_ECB aesCipher = new AES_256_ECB(siteInfo.getEncrypt_key());
                encryptedResponse = aesCipher.enc_aes_object(response);
            }else{
                encryptedResponse = new ObjectMapper().writeValueAsString(response);
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(encryptedResponse);
        } catch (Exception e) {
            CiResponse response = new CiResponse();
            response.setResultCode(CiResultCode.GENERAL_ERROR);
            response.setErrorMessage(e.getMessage());

            String encryptedResponse;
            if (siteInfo != null && siteInfo.getEncrypt_use()) {
                AES_256_ECB aesCipher = new AES_256_ECB(siteInfo.getEncrypt_key());
                encryptedResponse = aesCipher.enc_aes_object(response);
            }else{
                encryptedResponse = new ObjectMapper().writeValueAsString(response);
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(encryptedResponse);
        }
    }


    @PostMapping(value = "/api/findUserId")
    @ResponseBody
    @Hidden
    public CiResponse findUserId(HttpServletRequest httpRequest, @Valid @RequestBody UserFindByPhoneRequest apiRequest) throws Exception {
        try {
            CiLogger.request(httpRequest, apiRequest.toString());
            CiResponse response = apiService.findUserId(apiRequest);
            return response;

        } catch (CiException e) {
            CiResponse response = new CiResponse();
            response.setResultCode(e.getCode());
            response.setErrorMessage(e.getErrorString());
            return response;
        } catch (Exception e) {
            CiResponse response = new CiResponse();
            response.setResultCode(CiResultCode.GENERAL_ERROR);
            response.setErrorMessage(e.getMessage());
            return response;
        }
    }

    @PostMapping(value = "/api/idDuplicateCheck")
    @ResponseBody
    @Hidden
    public CiResponse idDuplicateCheck(HttpServletRequest httpRequest, @Valid @RequestBody IsDuplicateCheckRequest apiRequest) throws Exception {
        try {
            CiLogger.request(httpRequest, apiRequest.toString());
            CiResponse response = apiService.idDuplicateCheck(apiRequest);
            return response;

        } catch (CiException e) {
            CiResponse response = new CiResponse();
            response.setResultCode(e.getCode());
            response.setErrorMessage(e.getErrorString());
            return response;
        } catch (Exception e) {
            CiResponse response = new CiResponse();
            response.setResultCode(CiResultCode.GENERAL_ERROR);
            response.setErrorMessage(e.getMessage());
            return response;
        }
    }

    @PostMapping(value = "/api/idDuplicateCheck/v2")
    @ResponseBody
    @Operation(tags = "ID 중복체크", summary = "ID 중복체크", description = "AES256(ECB)로 인코딩한 RequestBody를 사용해야함  \n 인코딩하지 않았을 경우 500에러",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(schema = @Schema(implementation = IsDuplicateCheckRequest.class))),
            responses = {@ApiResponse(responseCode = "200", description = "AES256(ECB) 인코딩 된 결과", content = @Content(schema = @Schema(implementation = DocIsDuplicateCheckResponse.class))),})
    public ResponseEntity<String> idDuplicateCheckV2(@RequestHeader(name = "site_name") String siteName,HttpServletRequest httpRequest, @Valid @RequestBody String encryptedBody) throws Exception {
        SiteInfo siteInfo = null;
        try {
            String msoName = siteName;
            String decryptedBody;
            CiLogger.request(httpRequest, encryptedBody);

            if (Objects.nonNull(msoName)) {
                siteInfo = apiService.getSiteInfo(msoName);
                if (siteInfo.getEncrypt_use()) {
                    AES_256_ECB aesCipher = new AES_256_ECB(siteInfo.getEncrypt_key());
                    decryptedBody = aesCipher.dec_aes_object(encryptedBody);
                } else {
                    decryptedBody = encryptedBody;
                }
            } else {
                decryptedBody = encryptedBody;
            }

            IsDuplicateCheckRequest apiRequest = new ObjectMapper().readValue(decryptedBody, IsDuplicateCheckRequest.class);
            CiResponse response = apiService.idDuplicateCheck(apiRequest);

            String encryptedResponse;
            if (siteInfo != null && siteInfo.getEncrypt_use()) {
                AES_256_ECB aesCipher = new AES_256_ECB(siteInfo.getEncrypt_key());
                encryptedResponse = aesCipher.enc_aes_object(response);
            } else {
                encryptedResponse = new ObjectMapper().writeValueAsString(response);
            }

            return ResponseEntity.ok(encryptedResponse);
        } catch (CiException e) {
            CiResponse response = new CiResponse();
            response.setResultCode(e.getCode());
            response.setErrorMessage(e.getErrorString());

            String encryptedResponse;
            if (siteInfo != null && siteInfo.getEncrypt_use()) {
                AES_256_ECB aesCipher = new AES_256_ECB(siteInfo.getEncrypt_key());
                encryptedResponse = aesCipher.enc_aes_object(response);
            }else{
                encryptedResponse = new ObjectMapper().writeValueAsString(response);
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(encryptedResponse);
        } catch (Exception e) {
            CiResponse response = new CiResponse();
            response.setResultCode(CiResultCode.GENERAL_ERROR);
            response.setErrorMessage(e.getMessage());

            String encryptedResponse;
            if (siteInfo != null && siteInfo.getEncrypt_use()) {
                AES_256_ECB aesCipher = new AES_256_ECB(siteInfo.getEncrypt_key());
                encryptedResponse = aesCipher.enc_aes_object(response);
            }else{
                encryptedResponse = new ObjectMapper().writeValueAsString(response);
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(encryptedResponse);
        }
    }


    @PostMapping(value = "/api/checkDuplicateEmail")
    @ResponseBody
    @Hidden
    public CiResponse checkDuplicateEmail(HttpServletRequest httpRequest, @Valid @RequestBody CheckDuplicateEmailRequest apiRequest) throws Exception {
        try {
            CiLogger.request(httpRequest, apiRequest.toString());
            CiResponse response = apiService.checkDuplicateEmail(apiRequest);
            return response;

        } catch (CiException e) {
            CiResponse response = new CiResponse();
            response.setResultCode(e.getCode());
            response.setErrorMessage(e.getErrorString());
            return response;
        } catch (Exception e) {
            CiResponse response = new CiResponse();
            response.setResultCode(CiResultCode.GENERAL_ERROR);
            response.setErrorMessage(e.getMessage());
            return response;
        }
    }

    private ImsResponse createImsResponse(CiResponse ciResponse) {
        if (ciResponse.getResultCode() == CiResultCode.SUCCESS) {
            return new ImsResponse("success");
        } else {
            return new ImsResponse("failure");
        }
    }

    @PostMapping(value = "/api/purchase-svod/v2")
    @ResponseBody
    @Operation(tags = "상품구매", summary = "상품가입", description = "SVOD(가입형상품) 구매 , AES256(ECB)로 인코딩한 RequestBody를 사용해야함 \n 인코딩하지 않았을 경우 500에러",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(schema = @Schema(implementation = PurchaseSvodRequest.class))),
            responses = {@ApiResponse(responseCode = "200", description = "AES256(ECB) 인코딩 된 결과", content = @Content(schema = @Schema(implementation = ImsResponse.class))),})
    public ResponseEntity<String> purchaseSvod(@RequestHeader(name = "site_name") String siteName,HttpServletRequest httpRequest, @Valid @RequestBody String encryptedBody) throws Exception {
        SiteInfo siteInfo = null;
        try {

            String msoName = siteName;
            CiLogger.info("purchase-svod siteName : " + msoName);
            String decryptedBody;
            CiLogger.request(httpRequest, encryptedBody);

            if (Objects.nonNull(msoName)) {
                siteInfo = apiService.getSiteInfo(msoName);
                if (Objects.nonNull(siteInfo)) {
                    if (siteInfo.getEncrypt_use()) {
                        AES_256_ECB aesCipher = new AES_256_ECB(siteInfo.getEncrypt_key());
                        decryptedBody = aesCipher.dec_aes_object(encryptedBody);
                    } else {
                        decryptedBody = encryptedBody;
                    }
                } else {
                    ImsResponse response = new ImsResponse("failure", "siteInfo is null");
                    String encryptedResponse = new ObjectMapper().writeValueAsString(response);
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(encryptedResponse);
                }
            } else {
                ImsResponse response = new ImsResponse("failure", "site_name is null");
                String encryptedResponse = new ObjectMapper().writeValueAsString(response);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(encryptedResponse);
            }

            PurchaseSvodRequest apiRequest = new ObjectMapper().readValue(decryptedBody, PurchaseSvodRequest.class);
            CiResponse ciResponse = apiService.purchaseSvod(apiRequest);
            ImsResponse response;
            response = createImsResponse(ciResponse);
            String encryptedResponse;
            if (siteInfo != null && siteInfo.getEncrypt_use()) {
                AES_256_ECB aesCipher = new AES_256_ECB(siteInfo.getEncrypt_key());
                encryptedResponse = aesCipher.enc_aes_object(response);
            } else {
                //encryptedResponse = new ObjectMapper().writeValueAsString(response);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("site_name is null");
            }

            return ResponseEntity.ok(encryptedResponse);
        } catch (CiException e) {
			/*CiResponse response = new CiResponse();
			response.setResultCode(e.getCode());
			response.setErrorMessage(e.getErrorString());
*/
            ImsResponse response = new ImsResponse("failure");
            String encryptedResponse;
            if (siteInfo != null && siteInfo.getEncrypt_use()) {
                AES_256_ECB aesCipher = new AES_256_ECB(siteInfo.getEncrypt_key());
                encryptedResponse = aesCipher.enc_aes_object(response);
            }else{
                encryptedResponse = new ObjectMapper().writeValueAsString(response);
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(encryptedResponse);
        } catch (Exception e) {
			/*CiResponse response = new CiResponse();
			response.setResultCode(CiResultCode.GENERAL_ERROR);
			response.setErrorMessage(e.getMessage());*/
            ImsResponse response = new ImsResponse("failure");
            String encryptedResponse;
            if (siteInfo != null && siteInfo.getEncrypt_use()) {
                AES_256_ECB aesCipher = new AES_256_ECB(siteInfo.getEncrypt_key());
                encryptedResponse = aesCipher.enc_aes_object(response);
            }else{
                encryptedResponse = new ObjectMapper().writeValueAsString(response);
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(encryptedResponse);
        }
    }

    @PostMapping(value = "/api/cancel-purchase-svod/v2")
    @Operation(tags = "상품구매", summary = "상품가입취소", description = "AES256(ECB)로 인코딩한 RequestBody를 사용해야함 \n 인코딩하지 않았을 경우 500에러",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(schema = @Schema(implementation = CancelPurchaseSvodRequest.class))),
            responses = {@ApiResponse(responseCode = "200", description = "AES256(ECB) 인코딩 된 결과", content = @Content(schema = @Schema(implementation = ImsResponse.class))),})
    public ResponseEntity<String> cancelPurchaseSvod(@RequestHeader(name = "site_name") String siteName,HttpServletRequest httpRequest, @Valid @RequestBody String encryptedBody) throws Exception {
        SiteInfo siteInfo = null;
        try {
            String msoName = siteName;
            CiLogger.info("cancel-purchase-svod siteName : " + msoName);
            String decryptedBody;
            CiLogger.request(httpRequest, encryptedBody);

            if (Objects.nonNull(msoName)) {
                siteInfo = apiService.getSiteInfo(msoName);
                if (Objects.nonNull(siteInfo)) {
                    if (siteInfo.getEncrypt_use()) {
                        AES_256_ECB aesCipher = new AES_256_ECB(siteInfo.getEncrypt_key());
                        decryptedBody = aesCipher.dec_aes_object(encryptedBody);
                    } else {
                        decryptedBody = encryptedBody;
                    }
                } else { //fail
                    ImsResponse response = new ImsResponse("failure", "siteInfo is null");
                    String encryptedResponse = new ObjectMapper().writeValueAsString(response);
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(encryptedResponse);
                }
            } else { // fail
                ImsResponse response = new ImsResponse("failure", "site_name is null");
                CiLogger.error("site_name is null");
                String encryptedResponse = new ObjectMapper().writeValueAsString(response);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(encryptedResponse);
            }

            CancelPurchaseSvodRequest apiRequest = new ObjectMapper().readValue(decryptedBody, CancelPurchaseSvodRequest.class);

            CiResponse ciResponse = apiService.cancelPurchaseSvod(apiRequest);
            ImsResponse response = createImsResponse(ciResponse);
            String encryptedResponse;
            if (siteInfo != null && siteInfo.getEncrypt_use()) {
                AES_256_ECB aesCipher = new AES_256_ECB(siteInfo.getEncrypt_key());
                encryptedResponse = aesCipher.enc_aes_object(response);
            } else {
                encryptedResponse = new ObjectMapper().writeValueAsString(response);
            }

            return ResponseEntity.ok(encryptedResponse);
        } catch (CiException e) {
            ImsResponse response = new ImsResponse("failure");
            String encryptedResponse;
            if (siteInfo != null && siteInfo.getEncrypt_use()) {
                AES_256_ECB aesCipher = new AES_256_ECB(siteInfo.getEncrypt_key());
                encryptedResponse = aesCipher.enc_aes_object(response);
            }else{
                encryptedResponse = new ObjectMapper().writeValueAsString(response);
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(encryptedResponse);
        } catch (Exception e) {
            ImsResponse response = new ImsResponse("failure");
            String encryptedResponse;
            if (siteInfo != null && siteInfo.getEncrypt_use()) {
                AES_256_ECB aesCipher = new AES_256_ECB(siteInfo.getEncrypt_key());
                encryptedResponse = aesCipher.enc_aes_object(response);
            }else{
                encryptedResponse = new ObjectMapper().writeValueAsString(response);
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(encryptedResponse);
        }
    }


    @PostMapping(value = "/api/happyCallCheck")
    @ResponseBody
    @Hidden
    public CiResponse happyCallCheck(HttpServletRequest httpRequest, @Valid @RequestBody UserSignUpRequest apiRequest) throws Exception {

        try {
            CiLogger.request(httpRequest, apiRequest.toString());
            CiResponse response = apiService.getHappyCallbyMemberId(apiRequest);
            return response;

        } catch (CiException e) {
            CiResponse response = new CiResponse();
            response.setResultCode(CiResultCode.INVALID_ACCOUNT_ID);
            response.setErrorMessage(e.getErrorString());
            return response;
        } catch (Exception e) {
            CiResponse response = new CiResponse();
            response.setResultCode(CiResultCode.GENERAL_ERROR);
            response.setErrorMessage(e.getMessage());
            return response;
        }
    }

    @Hidden
    @RequestMapping(value="/api/purchase", method=RequestMethod.POST, produces="application/json", consumes="application/json")
    @ResponseBody
    public CiResponse purchase(ModelMap model, HttpServletRequest httpRequest
            , @RequestBody String requestBody) {

        long startTime = System.currentTimeMillis();
        try { MDC.remove("startTime"); MDC.remove("processingTimeMSec");} catch (Exception e) {}
        CiRequest request = null;
        try{

            CiLogger.request(httpRequest
                    , CiLogUtil.hiddenRequest(requestBody
                            , new ModelMap("\"account_pay_pwd\":[\\s]*\"(\\w*)\"", "\"account_pay_pwd\": \"%s\"")
                    ));
            try { MDC.put("startTime", Long.toString(startTime)); } catch (Exception e) {}
            request = new PurchaseRequest(httpRequest, requestBody);
            request.validate();
            CiResponse response = apiService.purchase(request);
            if(response==null) {
                CiLogger.warn("Null response.");
                throw new CiException(CiResultCode.GENERAL_ERROR,"Null response");
            }
            response.result(model);
            return response;

        } catch( CiException e) {
            model.put("resultCode", e.getCode());
            model.put("errorMessage",CiResultCode.convert(e.getCode(), e.getMessage()));
            if(request!=null) {return request.makeCiResponse(e.getCode(), CiResultCode.convert(e.getCode(),e.getMessage()));}
            else { return new CiResponse(e.getCode(), CiResultCode.convert(e.getCode(),e.getMessage())); }
        }catch(Exception e){
            CiLogger.error(e, e.getMessage());
            model.put("resultCode", CiResultCode.GENERAL_ERROR);
            model.put("errorMessage",e.getMessage());
            if(request!=null) { return request.makeCiResponse(CiResultCode.GENERAL_ERROR);}
            else { return new CiResponse(CiResultCode.GENERAL_ERROR); }

        }finally {
            CiLogger.response(model);
        }

    }
    /**
     * 암호화 리턴 처리 
     * @param httpStatus
     * @param resultCode
     * @param resultMessage
     * @param siteInfo
     * @return
     * @throws Exception
     */
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


    private static ResponseEntity<String> getResponseEntity(int httpStatus , int resultCode , String resultMessage , SiteInfo siteInfo, Integer happycall) throws Exception {
        UsersignupResponse response = new UsersignupResponse();
        response.setResultCode(resultCode);
        response.setResultMessage(resultMessage);
        response.setHappycall(happycall);
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

}
