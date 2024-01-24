package com.castis.pvs.api.controller;

import com.castis.common.model.ImsResponse;
import com.castis.common.util.AES_256_ECB;
import com.castis.pvs.api.model.*;
import com.castis.pvs.constants.PVSConstants;
import com.castis.pvs.dao.SiteInfoDao;
import com.castis.pvs.entity.SiteInfo;
import com.castis.pvs.swagger.model.DocApiResponse;
import com.castis.pvs.swagger.model.DocIsDuplicateCheckResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.apache.commons.text.StringEscapeUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Disabled
@ExtendWith(MockitoExtension.class)
class ApiControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Mock
    private SiteInfoDao siteInfoDao;

    @Test
    @DisplayName("회원가입")
    void userSignupV3() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(post("/api/userSignUp/v3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(UserSignUpV2Request.builder()
                                .user_id("ttttt")
                                .user_name("테스터")
                                .app_code("3")
                                .user_pass("c745c4f448c8f0a16869e6756e53e0d4b85bd02c6bcd7a0c1185cb366f4124f4")
                                .phone_number("01077777777")
                                .device_type("MOBILE.dash.android")
                                .adult_cert("0")
                                .mso_name("castis")
                                .email("test@test.com")
                                .recommender_id("castis")
                                .ci("1234567890123456789012345678901234567890123456789012345678901234")
                                .di("1234567890123456789012345678901234567890123456789012345678901234")
                                .build())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result_code").value("101")) // 이 부분은 실제 예상되는 응답 코드에 맞게 수정해주세요.
                .andExpect(jsonPath("$.result_msg").isNotEmpty());
    }


    @Test
    @DisplayName("비밀번호 체크 실패 시, 101 응답 코드와 에러 메시지를 반환한다.")
    void passwordCheck_fail() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(post("/api/passwordCheck")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(PasswordCheckRequest.builder()
                                .password("incorrectPassword")
                                .checkType(PVSConstants.PASSCHECKTYPE.LOGIN)
                                .model("TEST-1")
                                .language("ko")
                                .app_code("1")
                                .account_id("testuser2")
                                .device_type("mobile")
                                .wifi_mac("00:00:00:00:00:00")
                                .transaction_id("1234567890").build())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result_code").value("101")) // 이 부분은 실제 예상되는 응답 코드에 맞게 수정해주세요.
                .andExpect(jsonPath("$.result_msg").isNotEmpty());
    }

    @Test
    @DisplayName("비밀번호 변경 성공 시, 100 응답 코드를 반환한다.")
    void passwordChange_success() throws Exception {
        String password = "12345";
        ObjectMapper objectMapper = new ObjectMapper();


        mockMvc.perform(post("/api/passwordChange")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(UserPassRequest.builder()
                                .app_code("1")
                                .user_id("testuser2")
                                .user_pass(password)
                                .build())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result_code").value("100")) // 이 부분은 실제 예상되는 응답 코드에 맞게 수정해주세요.
                .andExpect(jsonPath("$.result_msg").isEmpty());

        mockMvc.perform(post("/api/passwordCheck")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(PasswordCheckRequest.builder()
                                .password(password)
                                .checkType(PVSConstants.PASSCHECKTYPE.LOGIN)
                                .model("TEST-1")
                                .language("ko")
                                .app_code("1")
                                .account_id("testuser2")
                                .device_type("mobile")
                                .wifi_mac("00:00:00:00:00:00")
                                .transaction_id("1234567890").build())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result_code").value("100")) // 이 부분은 실제 예상되는 응답 코드에 맞게 수정해주세요.
                .andExpect(jsonPath("$.result_msg").isEmpty());
    }


    @Test
    @DisplayName("비밀번호 체크 성공 시, 100 응답 코드를 반환한다.")
    void passwordCheck_success() throws Exception {
        String password = "1234";
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(PasswordCheckRequest.builder()
                .password(password)
                .checkType(PVSConstants.PASSCHECKTYPE.LOGIN)
                .model("TEST-1")
                .language("ko")
                .app_code("1")
                .account_id("ttttt")
                .device_type("mobile")
                .wifi_mac("00:00:00:00:00:00")
                .transaction_id("1234567890").build());

        mockMvc.perform(post("/api/passwordCheck")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result_code").value("100")) // 이 부분은 실제 예상되는 응답 코드에 맞게 수정해주세요.
                .andExpect(jsonPath("$.result_msg").isEmpty());
    }

    @Test
    @DisplayName("외부연동 비밀번호 체크 성공, 비밀번호 일치, 100 응답 코드를 반환한다.")
    void passwordCheckV2_success() throws Exception {
        SiteInfo mockSiteInfo = new SiteInfo(); // 필요한 필드를 채워주세요
        mockSiteInfo.setEncrypt_key("Cmbottrestfulapiservice20230317s");
        when(siteInfoDao.getSiteInfobyMsoName("CMB")).thenReturn(mockSiteInfo);

        String password = "12345";

        SiteInfo siteInfo = siteInfoDao.getSiteInfobyMsoName("CMB");
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println("siteInfo.getEncrypt_key() = " + siteInfo.getEncrypt_key());
        AES_256_ECB aesCipher = new AES_256_ECB(siteInfo.getEncrypt_key());


        MvcResult result = mockMvc.perform(post("/api/passwordCheck/v2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("site_name", "CMB")
                        .content(aesCipher.enc_aes_object(PasswordCheckRequest.builder()
                                .password(password)
                                .checkType(PVSConstants.PASSCHECKTYPE.LOGIN)
                                .model("TEST-1")
                                .language("ko")
                                .app_code("1")
                                .account_id("testuser2")
                                .device_type("mobile")
                                .wifi_mac("00:00:00:00:00:00")
                                .transaction_id("1234567890").build())))
                .andExpect(status().isOk())
                .andReturn();
        Gson gson = new Gson();
        System.out.println("dummy:" + aesCipher.enc_aes_object(PasswordCheckRequest.builder()
                .password(password)
                .checkType(PVSConstants.PASSCHECKTYPE.LOGIN)
                .model("TEST-1")
                .language("ko")
                .app_code("1")
                .account_id("testuser2")
                .device_type("mobile")
                .wifi_mac("00:00:00:00:00:00")
                .transaction_id("1234567890").build()));

        String decryptedBody = aesCipher.dec_aes_object(result.getResponse().getContentAsString());
        System.out.println("real:" + decryptedBody);
        System.out.println(StringEscapeUtils.unescapeJson(decryptedBody));
        PasswordCheckResponse response = gson.fromJson(decryptedBody, PasswordCheckResponse.class);
        assertEquals(100, response.getResult_code());
        System.out.println(response.getResult_msg());
        //.andExpect(jsonPath("$.result_code").value("100")) // 이 부분은 실제 예상되는 응답 코드에 맞게 수정해주세요.
        //.andExpect(jsonPath("$.result_msg").isEmpty());
    }

    @Test
    @DisplayName("외부연동 비밀번호 체크 성공, 비밀번호 틀렸을때,  101 응답 코드와 에러 메시지를 반환한다.")
    void passwordCheckV2_incorrect() throws Exception {
        SiteInfo mockSiteInfo = new SiteInfo(); // 필요한 필드를 채워주세요
        mockSiteInfo.setEncrypt_key("Cmbottrestfulapiservice20230317s");
        when(siteInfoDao.getSiteInfobyMsoName("CMB")).thenReturn(mockSiteInfo);

        String password = "incorrectpassword";

        SiteInfo siteInfo = siteInfoDao.getSiteInfobyMsoName("CMB");
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println("siteInfo.getEncrypt_key() = " + siteInfo.getEncrypt_key());
        AES_256_ECB aesCipher = new AES_256_ECB(siteInfo.getEncrypt_key());

        MvcResult result = mockMvc.perform(post("/api/passwordCheck/v2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("site_name", "CMB")
                        .content(aesCipher.enc_aes_object(PasswordCheckRequest.builder()
                                .password(password)
                                .checkType(PVSConstants.PASSCHECKTYPE.LOGIN)
                                .model("TEST-1")
                                .language("ko")
                                .app_code("1")
                                .account_id("testuser2")
                                .device_type("mobile")
                                .wifi_mac("00:00:00:00:00:00")
                                .transaction_id("1234567890").build())))
                .andExpect(status().isOk())
                .andReturn();

        Gson gson = new Gson();
        String decryptedBody = aesCipher.dec_aes_object(result.getResponse().getContentAsString());
        System.out.println("real:" + decryptedBody);
        System.out.println(StringEscapeUtils.unescapeJson(decryptedBody));
        PasswordCheckResponse response = gson.fromJson(StringEscapeUtils.unescapeJava(decryptedBody), PasswordCheckResponse.class);
        assertEquals(101, response.getResult_code());
        System.out.println(response.getResult_msg());
    }

    @Test
    @DisplayName("외부연동 비밀번호 체크 요청 성공, 없는 가입자일때,  101 응답 코드와 에러 메시지를 반환한다.")
    void passwordCheckV2_not_Exist_id() throws Exception {
        SiteInfo mockSiteInfo = new SiteInfo(); // 필요한 필드를 채워주세요
        mockSiteInfo.setEncrypt_key("Cmbottrestfulapiservice20230317s");
        when(siteInfoDao.getSiteInfobyMsoName("CMB")).thenReturn(mockSiteInfo);

        String password = "incorrectpassword";

        SiteInfo siteInfo = siteInfoDao.getSiteInfobyMsoName("CMB");
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println("siteInfo.getEncrypt_key() = " + siteInfo.getEncrypt_key());
        AES_256_ECB aesCipher = new AES_256_ECB(siteInfo.getEncrypt_key());

        MvcResult result = mockMvc.perform(post("/api/passwordCheck/v2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("site_name", "CMB")
                        .content(aesCipher.enc_aes_object(PasswordCheckRequest.builder()
                                .password(password)
                                .checkType(PVSConstants.PASSCHECKTYPE.LOGIN)
                                .model("TEST-1")
                                .language("ko")
                                .app_code("1")
                                .account_id("testuser2not")
                                .device_type("mobile")
                                .wifi_mac("00:00:00:00:00:00")
                                .transaction_id("1234567890").build())))
                .andExpect(status().isOk())
                .andReturn();

        Gson gson = new Gson();
        String decryptedBody = aesCipher.dec_aes_object(result.getResponse().getContentAsString());
        PasswordCheckResponse response = gson.fromJson(decryptedBody, PasswordCheckResponse.class);
        assertEquals(101, response.getResult_code());
        System.out.println(response.getResult_msg());
    }

    @Test
    @DisplayName("외부연동 비밀번호 체크 요청, 인크립션 키가 틀렸을때")
    void passwordCheckV2_invalid_key() throws Exception {
        SiteInfo mockSiteInfo = new SiteInfo(); // 필요한 필드를 채워주세요
        mockSiteInfo.setEncrypt_key("devottrestfulapiservice20230317s");
        when(siteInfoDao.getSiteInfobyMsoName("CMB")).thenReturn(mockSiteInfo);

        String password = "incorrectpassword";

        SiteInfo siteInfo = siteInfoDao.getSiteInfobyMsoName("CMB");
        AES_256_ECB aesCipher = new AES_256_ECB(siteInfo.getEncrypt_key());

        MvcResult result = mockMvc.perform(post("/api/passwordCheck/v2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("site_name", "CMB")
                        .content(aesCipher.enc_aes_object(PasswordCheckRequest.builder()
                                .password(password)
                                .checkType(PVSConstants.PASSCHECKTYPE.LOGIN)
                                .model("TEST-1")
                                .language("ko")
                                .app_code("1")
                                .account_id("testuser2not")
                                .device_type("mobile")
                                .wifi_mac("00:00:00:00:00:00")
                                .transaction_id("1234567890").build())))
                .andExpect(status().is(500))
                .andReturn();
    }

    /*
      "user_id": "ttttt",
      "user_name":  "테스터",
      "app_code": "3",
      "user_pass": "c745c4f448c8f0a16869e6756e53e0d4b85bd02c6bcd7a0c1185cb366f4124f4",
      "phone_number" : "01077777777",
      "device_type" : "MOBILE.dash.android",
      "adult_cert": "1",
      "mso_name": "castis",
      "email": "jjangpil@gmail.com",
      "recommender_id": "jjangpil",
      "ci": "1234567890123456789012345678901234567890123456789012345678901234",
      "di": "1234567890123456789012345678901234567890123456789012345678901234"
     */


    @Test
    //@Disabled
    @DisplayName("회원가입 외부연동")
    void userSignUpV2_success() throws Exception {
        SiteInfo mockSiteInfo = new SiteInfo(); // 필요한 필드를 채워주세요
        mockSiteInfo.setEncrypt_key("Cmbottrestfulapiservice20230317s");
        when(siteInfoDao.getSiteInfobyMsoName("CMB")).thenReturn(mockSiteInfo);

        SiteInfo siteInfo = siteInfoDao.getSiteInfobyMsoName("CMB");
        ObjectMapper objectMapper = new ObjectMapper();
        AES_256_ECB aesCipher = new AES_256_ECB(siteInfo.getEncrypt_key());

        MvcResult result = mockMvc.perform(post("/api/userSignUp/v2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("site_name", "CMB")
                        .content(aesCipher.enc_aes_object(UserSignUpV2Request.builder()
                                .user_id("ttttt")
                                .user_name("테스터")
                                .app_code("3")
                                .user_pass("c745c4f448c8f0a16869e6756e53e0d4b85bd02c6bcd7a0c1185cb366f4124f4")
                                .phone_number("01077777777")
                                .device_type("MOBILE.dash.android")
                                .adult_cert("0")
                                .mso_name("castis")
                                .email("test@test.com")
                                .recommender_id("castis")
                                .ci("1234567890123456789012345678901234567890123456789012345678901234")
                                .di("1234567890123456789012345678901234567890123456789012345678901234")
                                .build())))
                .andExpect(status().isOk())
                .andReturn();

        Gson gson = new Gson();
        String decryptedBody = aesCipher.dec_aes_object(result.getResponse().getContentAsString());
        UserSignUpResponse response = gson.fromJson(decryptedBody, UserSignUpResponse.class);
        assertEquals(100, response.getResult_code());
        System.out.println("resultMessage : " +  response.getResult_msg());
    }


    @Test
    @Disabled
    @DisplayName("회원가입 외부연동_이미 가입된 회원")
    void userSignUpV2_already_subscribed() throws Exception {
        SiteInfo mockSiteInfo = new SiteInfo(); // 필요한 필드를 채워주세요
        mockSiteInfo.setEncrypt_key("Cmbottrestfulapiservice20230317s");
        when(siteInfoDao.getSiteInfobyMsoName("CMB")).thenReturn(mockSiteInfo);

        SiteInfo siteInfo = siteInfoDao.getSiteInfobyMsoName("CMB");
        ObjectMapper objectMapper = new ObjectMapper();
        AES_256_ECB aesCipher = new AES_256_ECB(siteInfo.getEncrypt_key());

        MvcResult result = mockMvc.perform(post("/api/userSignUp/v2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("site_name", "CMB")
                        .content(aesCipher.enc_aes_object(UserSignUpV2Request.builder()
                                .user_id("ttttt")
                                .user_name("테스터")
                                .app_code("3")
                                .user_pass("c745c4f448c8f0a16869e6756e53e0d4b85bd02c6bcd7a0c1185cb366f4124f4")
                                .phone_number("01077777777")
                                .device_type("MOBILE.dash.android")
                                .adult_cert("1")
                                .mso_name("castis")
                                .email("test@test.com")
                                .recommender_id("castis")
                                .ci("1234567890123456789012345678901234567890123456789012345678901234")
                                .di("1234567890123456789012345678901234567890123456789012345678901234")
                                .build())))
                .andExpect(status().is(500))
                .andReturn();

        Gson gson = new Gson();
        String decryptedBody = aesCipher.dec_aes_object(result.getResponse().getContentAsString());
        UserSignUpResponse response = gson.fromJson(decryptedBody, UserSignUpResponse.class);
        assertEquals(114, response.getResult_code());
        System.out.println(response.getResult_msg());
    }

    @Test
    @DisplayName("회원가입")
    @Disabled
    void userSignUp() throws Exception {
        MvcResult result = mockMvc.perform(post("/api/userSignUp/v3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(UserSignUpV2Request.builder()
                                .user_id("ttttt")
                                .user_name("테스터")
                                .app_code("3")
                                .user_pass("c745c4f448c8f0a16869e6756e53e0d4b85bd02c6bcd7a0c1185cb366f4124f4")
                                .phone_number("01077777777")
                                .device_type("MOBILE.dash.android")
                                .adult_cert("0")
                                .mso_name("castis")
                                .email("test@test.com")
                                .recommender_id("castis")
                                .ci("1234567890123456789012345678901234567890123456789012345678901234")
                                .di("1234567890123456789012345678901234567890123456789012345678901234")
                                .build())))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        UserSignUpResponse response = new Gson().fromJson(content, UserSignUpResponse.class);
        assertEquals(100, response.getResult_code());
        System.out.println("result_msg : " + response.getResult_msg());
    }

    @Test
    @DisplayName("ID 존재여부_이미 가입된 회원")
    void idDuplicateCheckV2() throws Exception {
        SiteInfo mockSiteInfo = new SiteInfo(); // 필요한 필드를 채워주세요
        mockSiteInfo.setEncrypt_key("Cmbottrestfulapiservice20230317s");
        when(siteInfoDao.getSiteInfobyMsoName("CMB")).thenReturn(mockSiteInfo);

        SiteInfo siteInfo = siteInfoDao.getSiteInfobyMsoName("CMB");
        ObjectMapper objectMapper = new ObjectMapper();
        AES_256_ECB aesCipher = new AES_256_ECB(siteInfo.getEncrypt_key());

        MvcResult result = mockMvc.perform(post("/api/idDuplicateCheck/v2")
                        .header("site_name", "CMB")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(aesCipher.enc_aes_object(IsDuplicateCheckRequest.builder()
                                .user_id("ttttt")
                                .app_code(3)
                                .build())))
                .andExpect(status().isOk())
                .andReturn();

        Gson gson = new Gson();
        String decryptedBody = aesCipher.dec_aes_object(result.getResponse().getContentAsString());
        System.out.println(decryptedBody);
        DocIsDuplicateCheckResponse response = gson.fromJson(decryptedBody, DocIsDuplicateCheckResponse.class);
        assertEquals(100, response.getResult_code());
        assertEquals(true, response.is_duplicate());
    }

    @Test
    @DisplayName("ID 존재여부 확인_없는 회원")
    void idDuplicateCheckV2_already_subscribed() throws Exception {
        SiteInfo mockSiteInfo = new SiteInfo(); // 필요한 필드를 채워주세요
        mockSiteInfo.setEncrypt_key("Cmbottrestfulapiservice20230317s");
        when(siteInfoDao.getSiteInfobyMsoName("CMB")).thenReturn(mockSiteInfo);

        SiteInfo siteInfo = siteInfoDao.getSiteInfobyMsoName("CMB");
        ObjectMapper objectMapper = new ObjectMapper();
        AES_256_ECB aesCipher = new AES_256_ECB(siteInfo.getEncrypt_key());

        MvcResult result = mockMvc.perform(post("/api/idDuplicateCheck/v2")
                        .header("site_name", "CMB")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(aesCipher.enc_aes_object(IsDuplicateCheckRequest.builder()
                                .user_id("notexistid")
                                .app_code(3)
                                .build())))
                .andExpect(status().isOk())
                .andReturn();

        Gson gson = new Gson();
        String decryptedBody = aesCipher.dec_aes_object(result.getResponse().getContentAsString());
        System.out.println(decryptedBody);
        DocIsDuplicateCheckResponse response = gson.fromJson(decryptedBody, DocIsDuplicateCheckResponse.class);
        assertEquals(100, response.getResult_code());
        assertEquals(false, response.is_duplicate());
    }

    @Test
    @DisplayName("외부연동_회원탈퇴")
    void closeAccountV2() throws Exception {
        SiteInfo mockSiteInfo = new SiteInfo(); // 필요한 필드를 채워주세요
        mockSiteInfo.setEncrypt_key("Cmbottrestfulapiservice20230317s");
        when(siteInfoDao.getSiteInfobyMsoName("CMB")).thenReturn(mockSiteInfo);

        SiteInfo siteInfo = siteInfoDao.getSiteInfobyMsoName("CMB");
        AES_256_ECB aesCipher = new AES_256_ECB(siteInfo.getEncrypt_key());

        MvcResult result = mockMvc.perform(post("/api/close-account/v2")
                        .header("site_name", "CMB")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(aesCipher.enc_aes_object(UserSignOutV2Request.builder()
                                .user_id("ttttt")
                                .build())))
                .andExpect(status().isOk())
                .andReturn();

        Gson gson = new Gson();
        String decryptedBody = aesCipher.dec_aes_object(result.getResponse().getContentAsString());
        System.out.println(decryptedBody);
        DocIsDuplicateCheckResponse response = gson.fromJson(decryptedBody, DocIsDuplicateCheckResponse.class);
        assertEquals(100, response.getResult_code());
        /*assertEquals(false, response.is_duplicate());*/
    }

    @Test
    @DisplayName("회원탈퇴_아이디 찾을 수 없음")
    @Disabled
    void closeAccountV2_invalid_id() throws Exception {
        SiteInfo mockSiteInfo = new SiteInfo(); // 필요한 필드를 채워주세요
        mockSiteInfo.setEncrypt_key("Cmbottrestfulapiservice20230317s");
        when(siteInfoDao.getSiteInfobyMsoName("CMB")).thenReturn(mockSiteInfo);

        SiteInfo siteInfo = siteInfoDao.getSiteInfobyMsoName("CMB");
        AES_256_ECB aesCipher = new AES_256_ECB(siteInfo.getEncrypt_key());

        MvcResult result = mockMvc.perform(post("/api/close-account/v2")
                        .header("site_name", "CMB")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(aesCipher.enc_aes_object(UserSignOutV2Request.builder()
                                .user_id("ttttt")
                                .build())))
                .andExpect(status().isOk())
                .andReturn();

        Gson gson = new Gson();
        String decryptedBody = aesCipher.dec_aes_object(result.getResponse().getContentAsString());
        System.out.println(decryptedBody);
        DocIsDuplicateCheckResponse response = gson.fromJson(decryptedBody, DocIsDuplicateCheckResponse.class);
        assertEquals(101, response.getResult_code());
        /*assertEquals(false, response.is_duplicate());*/
    }

    @Test
    @DisplayName("비밀번호 변경_invalid user")
    void passwordChange2_invalid_user() throws Exception {
        SiteInfo mockSiteInfo = new SiteInfo(); // 필요한 필드를 채워주세요
        mockSiteInfo.setEncrypt_key("Cmbottrestfulapiservice20230317s");
        when(siteInfoDao.getSiteInfobyMsoName("CMB")).thenReturn(mockSiteInfo);

        SiteInfo siteInfo = siteInfoDao.getSiteInfobyMsoName("CMB");
        AES_256_ECB aesCipher = new AES_256_ECB(siteInfo.getEncrypt_key());

        MvcResult result = mockMvc.perform(post("/api/passwordChange/v2")
                        .header("site_name", "CMB")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(aesCipher.enc_aes_object(UserPassRequest.builder()
                                .user_id("invalidUser")
                                .user_pass("1234")
                                .build())))
                .andExpect(status().is(500))
                .andReturn();

        Gson gson = new Gson();
        String decryptedBody = aesCipher.dec_aes_object(result.getResponse().getContentAsString());
        System.out.println(decryptedBody);
        DocApiResponse response = gson.fromJson(decryptedBody, DocApiResponse.class);
        assertEquals(101, response.getResult_code());
    }

    @Test
    @DisplayName("비밀번호 변경_success")
    void passwordChange2_success() throws Exception {
        SiteInfo mockSiteInfo = new SiteInfo(); // 필요한 필드를 채워주세요
        mockSiteInfo.setEncrypt_key("Cmbottrestfulapiservice20230317s");
        when(siteInfoDao.getSiteInfobyMsoName("CMB")).thenReturn(mockSiteInfo);

        SiteInfo siteInfo = siteInfoDao.getSiteInfobyMsoName("CMB");
        AES_256_ECB aesCipher = new AES_256_ECB(siteInfo.getEncrypt_key());

        MvcResult result = mockMvc.perform(post("/api/passwordChange/v2")
                        .header("site_name", "CMB")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(aesCipher.enc_aes_object(UserPassRequest.builder()
                                .user_id("ttttt")
                                .user_pass("1234")
                                .app_code("1")
                                .build())))
                .andExpect(status().isOk())
                .andReturn();

        Gson gson = new Gson();
        String decryptedBody = aesCipher.dec_aes_object(result.getResponse().getContentAsString());
        System.out.println("passwordChange2:" + decryptedBody);
        DocApiResponse response = gson.fromJson(decryptedBody, DocApiResponse.class);
        assertEquals(100, response.getResult_code());
    }


    @Test
    @DisplayName("회원탈퇴")
    @Disabled
    void memberUnregister() throws Exception {
        MvcResult result = mockMvc.perform(post("/api/memberUnregister")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(ApiRequest.builder()
                                .wifi_mac("testmac")
                                .device_type("test").app_code("1")
                                .account_id("ttttt").transaction_id("transaction").build())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result_code").value(100))
                .andReturn();
    }

    @Test
    void adultCheck() throws Exception {
        MvcResult result = mockMvc.perform(post("/api/adultCheck")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(ApiRequest.builder()
                                .wifi_mac("ttttt")
                                .device_type("test").app_code("1")
                                .transaction_id("transaction").build())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result_code").value(100))
                .andExpect(jsonPath("$.adult_certi_yn").value("Y"))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    void passwordSet() throws Exception {
        MvcResult result = mockMvc.perform(post("/api/passwordSet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(ApiRequest.builder()
                                .wifi_mac("ttttt")
                                .new_password("123456")
                                .password("12345")
                                .type(PVSConstants.PASSSETTYPE.INIT)
                                .device_type("test").app_code("1")
                                .transaction_id("transaction").build())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result_code").value(100))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }


    @Test
    @Disabled
    void userSignUpV3() throws Exception {
        MvcResult result = mockMvc.perform(post("/api/userSignUp/v3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(UserSignUpV2Request.builder()
                                .user_id("ttttt")
                                .user_name("테스터")
                                .app_code("3")
                                .user_pass("c745c4f448c8f0a16869e6756e53e0d4b85bd02c6bcd7a0c1185cb366f4124f4")
                                .phone_number("01077777777")
                                .device_type("MOBILE.dash.android")
                                .adult_cert("0")
                                .mso_name("castis")
                                .email("test@test.com")
                                .recommender_id("castis")
                                .ci("1234567890123456789012345678901234567890123456789012345678901234")
                                .di("1234567890123456789012345678901234567890123456789012345678901234")
                                        .legal_ci("1234567890123456789012345678901234567890123456789012345678901234")
                                        .legal_di("1234567890123456789012345678901234567890123456789012345678901234")
                                        .legal_tel("010-7777-7777")
                                        .app_token("1234567890123456789012345678901234567890123456789012345678901234")
                                .build())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result_code").value(100))
                //.andExpect(jsonPath("$.adult_certi_yn").value("N"))
                .andReturn();

    }

    @Test
    @Disabled
    void userSignUpVV() throws Exception {
        MvcResult result = mockMvc.perform(post("/api/userSignUp/v3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(UserSignUpV2Request.builder()
                                .user_id("admin")
                                .user_name("장민성")
                                .app_code("3")
                                .user_pass("8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92")
                                .phone_number("010-5958-3207")
                                .device_type("MOBILE")
                                .adult_cert("1")
                                .mso_name("HCN")
                                .zipcode("08801")
                                .address("서울 관악구 남부순환로 1931")
                                .detail_adrdess("xxx-xxx")
                                .social_number("990814-1")
                                .build())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result_code").value(100))
                //.andExpect(jsonPath("$.adult_certi_yn").value("N"))
                .andReturn();
    }

    @Test
    @DisplayName("회원 정보 조회 성공 시")
    void userInfo() throws Exception {
        MvcResult result = mockMvc.perform(post("/api/userInfo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(UserSignUpV2Request.builder()
                                .user_id("ttttt").app_code("3")
                                .build())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result_code").value(100))
                //.andExpect(jsonPath("$.adult_certi_yn").value("N"))
                .andReturn();
    }


    @Test
    @DisplayName("회원 정보 수정 성공 시")
    void userInfoUpdate() throws Exception {
        MvcResult result = mockMvc.perform(post("/api/userInfoUpdate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(UserSignUpV2Request.builder()
                                .user_id("ttttt").app_code("3")
                                .phone_number("010-7777-8888")
                                .device_type("MOBILE.dash.android")
                                .address("서울시 강남구")
                                .user_pass("1234")
                                .detail_adrdess("상세주소")
                                .build())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result_code").value(100))
                //.andExpect(jsonPath("$.adult_certi_yn").value("N"))
                .andReturn();
    }

    @Test
    @DisplayName("회원탈퇴")
    @Disabled
    void closeAccount() throws Exception {
        MvcResult result = mockMvc.perform(post("/api/close-account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(UserSignOutV2Request.builder()
                                .user_id("ttttt")
                                .build())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result_code").value(100))
                .andReturn();
    }

    @Test
    void previewPeriod() throws Exception {
        MvcResult result = mockMvc.perform(post("/api/previewPeriod")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(UserSignOutV2Request.builder()
                                .user_id("ttttt")
                                .build())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result_code").value(100))
                .andReturn();
    }

    @Test
    @DisplayName("비밀번호 일치 여부")
    void passwordMatch() throws Exception {
        MvcResult result = mockMvc.perform(post("/api/passwordMatch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(UserSignUpV2Request.builder()
                                .user_id("ttttt")
                                .app_code("3")
                                .user_pass("1234")
                                .build())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result_code").value(100))
                .andExpect(jsonPath("$.is_password_match").value(true))
                .andReturn();
    }

    @Test
    @DisplayName("회원정보 조회")
    void userExist() throws Exception {
        MvcResult result = mockMvc.perform(post("/api/userExist")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(UserFindByIdNPhoneRequest.builder()
                                .user_id("ttttt")
                                .app_code("3")
                                .phone_number("01077778888")
                                .build())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result_code").value(100))
                .andExpect(jsonPath("$.is_user_exist").value(true))
                .andReturn();
    }

    @Test
    @DisplayName("userId 조회")
    void findUserId() throws Exception {
        MvcResult result = mockMvc.perform(post("/api/findUserId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(UserFindByPhoneRequest.builder()
                                .app_code("3")
                                .phone_number("01077778888")
                                .build())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result_code").value(100))
                .andExpect(jsonPath("$.user_id").value("ttttt"))
                .andReturn();
    }

    @Test
    @DisplayName("아이디 중복 체크")
    void idDuplicateCheck() throws Exception {
        MvcResult result = mockMvc.perform(post("/api/idDuplicateCheck")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(IsDuplicateCheckRequest.builder()
                                .app_code(3)
                                .user_id("ttttt")
                                .build())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result_code").value(100))
                .andExpect(jsonPath("$.is_duplicate").value(true))
                .andReturn();
    }


    @Test
    @DisplayName("이메일 중복 체크")
    void checkDuplicateEmail() throws Exception {
        MvcResult result = mockMvc.perform(post("/api/checkDuplicateEmail")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(CheckDuplicateEmailRequest.builder()
                                .app_code("3")
                                .email("test@test.com")
                                .build())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result_code").value(100))
                .andExpect(jsonPath("$.is_duplicate").value(true))
                .andReturn();
    }

    @Test
    void purchaseSvodV2() throws Exception {
        SiteInfo mockSiteInfo = new SiteInfo(); // 필요한 필드를 채워주세요
        mockSiteInfo.setEncrypt_key("Cmbottrestfulapiservice20230317s");
        when(siteInfoDao.getSiteInfobyMsoName("CMB")).thenReturn(mockSiteInfo);

        SiteInfo siteInfo = siteInfoDao.getSiteInfobyMsoName("CMB");
        AES_256_ECB aesCipher = new AES_256_ECB(siteInfo.getEncrypt_key());

        MvcResult result = mockMvc.perform(post("/api/purchase-svod/v2")
                        .header("site_name", "CMB")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(aesCipher.enc_aes_object(PurchaseSvodRequest.builder()
                                .userId("ttttt")
                                .offerId("67486")
                                .build())))
                .andExpect(status().isOk())
                .andReturn();
        Gson gson = new Gson();
        String decryptedBody = aesCipher.dec_aes_object(result.getResponse().getContentAsString());
        System.out.println(decryptedBody);
        ImsResponse response = gson.fromJson(decryptedBody, ImsResponse.class);
        assertEquals("success", response.getResult());
    }


    @Test
    @DisplayName("SVOD 구매 취소")
    void cancelPurchaseSvodV2() throws Exception {
        SiteInfo mockSiteInfo = new SiteInfo(); // 필요한 필드를 채워주세요
        mockSiteInfo.setEncrypt_key("Cmbottrestfulapiservice20230317s");
        when(siteInfoDao.getSiteInfobyMsoName("CMB")).thenReturn(mockSiteInfo);

        SiteInfo siteInfo = siteInfoDao.getSiteInfobyMsoName("CMB");
        AES_256_ECB aesCipher = new AES_256_ECB(siteInfo.getEncrypt_key());

        MvcResult result = mockMvc.perform(post("/api/cancel-purchase-svod/v2")
                        .header("site_name", "CMB")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(aesCipher.enc_aes_object(CancelPurchaseSvodRequest.builder()
                                .userId("ttttt")
                                .offerId("67486")
                                .build())))
                .andExpect(status().isOk())
                .andReturn();

        Gson gson = new Gson();
        String decryptedBody = aesCipher.dec_aes_object(result.getResponse().getContentAsString());
        System.out.println(decryptedBody);
        ImsResponse response = gson.fromJson(decryptedBody, ImsResponse.class);
        assertEquals("success", response.getResult());
    }


    @Test
    void happyCallCheck() throws Exception {
        MvcResult result = mockMvc.perform(post("/api/happyCallCheck")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(UserSignUpV2Request.builder()
                                .app_code("3")
                                .user_id("1111")
                                .build())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result_code").value(100))
                .andExpect(jsonPath("$.happy_call_auth").value(true))
                .andReturn();
    }



}