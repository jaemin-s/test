package com.castis.pvs.api.controller;

import com.castis.common.constants.CiResultCode;
import com.castis.common.exception.CiException;
import com.castis.common.model.CiResponse;
import com.castis.common.util.AES_256_ECB;
import com.castis.common.util.CiLogger;
import com.castis.pvs.api.model.*;
import com.castis.pvs.api.model.v2.request.UsersignupRequest;
import com.castis.pvs.api.model.v2.response.OpenApiResponse;
import com.castis.pvs.api.model.v2.response.UsersignupResponse;
import com.castis.pvs.api.model.v3.MsoType;
import com.castis.pvs.api.service.ApiService;
import com.castis.pvs.api.service.OpenApiService;
import com.castis.pvs.api.service.V3Service;
import com.castis.pvs.entity.SiteInfo;
import com.castis.pvs.member.dto.MemberDTO;
import com.castis.pvs.member.entity.Member;
import com.castis.pvs.member.service.MemberService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.swagger.v3.oas.annotations.Hidden;
import org.apache.commons.codec.binary.Base64;
import org.apache.tomcat.util.json.JSONParser;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.regex.Pattern;

@Controller
public class V3Controller {

    @Autowired
    private OpenApiService openApiService;
    @Autowired
    private V3Service v3Service;
    @Autowired
    private MemberService memberService;
    private Validator validator;

    public V3Controller(Validator validator) {
        this.validator = validator;
    }

    @GetMapping(value = "/api/v3/test")
    @ResponseBody
    @Hidden
    public ResponseEntity<String> testAPI(HttpServletRequest httpRequest) throws Exception {
        return ResponseEntity.ok("OK");
    }

    @PostMapping(value = "/api/v3/renew_register")
    @ResponseBody
    @Hidden
    public ResponseEntity<String> renewRegister(@Valid @RequestBody String encryptedBody) throws Exception {
        try {
            String str2 = encryptedBody.trim().replaceAll("\r", "").replaceAll("\n", "");

            UsersignupRequest apiRequest = new ObjectMapper().registerModule(new JavaTimeModule()).readValue(str2, UsersignupRequest.class);
            //validation check
            String validation = validateRequest(apiRequest);
            if (validation == null) {
                MemberDTO memberDTO = new MemberDTO(apiRequest);
                OpenApiResponse response = openApiService.saveMember(memberDTO);

                Gson gson = new Gson();
                JsonObject jobj = new JsonObject();
                jobj.addProperty("result_code",100);
                jobj.addProperty("resultMessage",response.getResultMessage());
                jobj.addProperty("happycall",memberDTO.getSo_happycall_auth());
                jobj.addProperty("user_pass",memberDTO.getPassword());
                jobj.addProperty("user_name",memberDTO.getUser_name());
                jobj.addProperty("social_number",memberDTO.getSocial_number());
                jobj.addProperty("phone_number",memberDTO.getTel());
                jobj.addProperty("zipcode",memberDTO.getZipcode());

                return ResponseEntity.status(response.getResultCode()).body(gson.toJson(jobj));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validation);
            }
        } catch (JsonProcessingException | UnsupportedEncodingException | NoSuchAlgorithmException |
                 NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException |
                 InvalidKeyException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 요청입니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping(value = "/api/v3/login")
    @ResponseBody
    @Hidden
    public ResponseEntity<String> renewLogin(@Valid @RequestBody LinkedHashMap request) throws Exception {
        try {
            Gson gson = new Gson();
            String id = request.get("memberId").toString();

            
            Pattern isPhoneRegex = Pattern.compile("^(010|011|019)\\d{8}$");
            Member member;
            if(isPhoneRegex.matcher(id).matches()){
                member = memberService.findMemberByIdTelPw(request.get("memberId").toString(),request.get("memberPw").toString());
            }else{
                member = memberService.findMemberByIdEmailPw(request.get("memberId").toString(),request.get("memberPw").toString());
            }

            //validation check
            if (member != null) {
                JsonObject jobj = new JsonObject();
                jobj.addProperty("result_code",200);
                jobj.addProperty("isActive",true);
                jobj.addProperty("cardName","");
                jobj.addProperty("isAdult",member.isAdult_cert());
                jobj.addProperty("memberId",member.getMember_id());

                return ResponseEntity.status(200).body(gson.toJson(jobj));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
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
