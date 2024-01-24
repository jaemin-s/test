package com.castis.pvs.api.controller;

import com.castis.common.util.AES_256_ECB;
import com.castis.pvs.api.controller.model.UsersignupTestRequest;
import com.castis.pvs.api.model.v2.PatchHappyCallUserByCiRequest;
import com.castis.pvs.api.model.v2.request.*;
import com.castis.pvs.api.model.v2.response.HappyCallListResponse;
import com.castis.pvs.api.model.v2.response.OpenApiResponse;
import com.castis.pvs.api.model.v2.response.ProductListResponse;
import com.castis.pvs.api.model.v2.response.UsersignupResponse;
import com.castis.pvs.dao.SiteInfoDao;
import com.castis.pvs.entity.SiteInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.junit.jupiter.api.*;
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

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Disabled
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class OpenApiControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Mock
    private SiteInfoDao siteInfoDao;

    @BeforeEach
    void setUp() {
    }

    private final String testEncrpytionKey = "Hcnottrestfulapiservice20221111s";


    @DisplayName("회원가입/탈퇴")
    @Disabled
    @Nested
    public class UserTest {
        @Test
        @DisplayName("회원가입")
        void usersignupV2_success() throws Exception {
            SiteInfo mockSiteInfo = new SiteInfo(); // 필요한 필드를 채워주세요
            mockSiteInfo.setEncrypt_key(testEncrpytionKey);
            when(siteInfoDao.getSiteInfobySoId("4012")).thenReturn(mockSiteInfo);

            SiteInfo siteInfo = siteInfoDao.getSiteInfobySoId("4012");
            ObjectMapper objectMapper = new ObjectMapper();
            AES_256_ECB aesCipher = new AES_256_ECB(siteInfo.getEncrypt_key());

            MvcResult result = mockMvc.perform(post("/api/v2/usersignup")
                            .contentType(MediaType.TEXT_PLAIN)
                            .header("so_id", 4012)
                            .content(aesCipher.enc_aes_object(UsersignupTestRequest.builder()
                                    .member_id("admin")
                                    .password("8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92")
                                    .user_name("장민성")
                                    .social_number("990814-1")
                                    .tel("010-5958-3207")
                                    .address_city("서울 관악구 남부순환로 1931")
                                    .address_dist("xxx-xxx")
                                    .adult_cert(1)
                                    .app_code(2)
                                    .so_id("4012")
                                    .email("test@gmail.com")
                                    .recommender_id("test")
                                    .ci("1234567890123456789012345678901234567890123456789012345678901234")
                                    .di("1234567890123456789012345678901234567890123456789012345678901234")
                                    .legal_name("장민성")
                                    .legal_social_number("990814-1")
                                    .legal_ci("1234567890123456789012345678901234567890123456789012345678901234")
                                    .legal_di("1234567890123456789012345678901234567890123456789012345678901234")
                                    .legal_tel("010-5958-3207")
                                    .so_happycall_auth(1)
                                    .so_happycall_update_date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                                    .market_text_yn(0)
                                    .market_email_yn(0)
                                    .market_tm_yn(0)
                                    .push_yn(0)
                                    .build())))
                    .andReturn();

            System.out.println("status" + result.getResponse().getStatus());
            System.out.println("status" + result.getResponse().getErrorMessage());

            Gson gson = new Gson();
            String decryptedBody = aesCipher.dec_aes_object(result.getResponse().getContentAsString());
            UsersignupResponse response = gson.fromJson(decryptedBody, UsersignupResponse.class);
            System.out.println("result : " + response.getResultMessage());
            assertEquals(201, result.getResponse().getStatus());
            assertEquals(201, response.getResultCode());
        }

        @Test
        @DisplayName("회원가입_실패")
        void usersignupV2_fail() throws Exception {
            SiteInfo mockSiteInfo = new SiteInfo(); // 필요한 필드를 채워주세요
            mockSiteInfo.setEncrypt_key(testEncrpytionKey);
            when(siteInfoDao.getSiteInfobySoId("4012")).thenReturn(mockSiteInfo);

            SiteInfo siteInfo = siteInfoDao.getSiteInfobySoId("4012");
            ObjectMapper objectMapper = new ObjectMapper();
            AES_256_ECB aesCipher = new AES_256_ECB(siteInfo.getEncrypt_key());

            MvcResult result = mockMvc.perform(post("/api/v2/usersignup")
                            .contentType(MediaType.TEXT_PLAIN)
                            .header("so_id", 4012)
                            .content(aesCipher.enc_aes_object(UsersignupTestRequest.builder()
                                    .member_id("admin")
                                    .password("8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92")
                                    .user_name("장민성")
                                    .social_number("990814-1")
                                    .tel("010-5958-3207")
                                    .address_city("서울 관악구 남부순환로 1931")
                                    .address_dist("xxx-xxx")
                                    .adult_cert(1)
                                    .app_code(2)
                                    .so_id("4012")
                                    .email("test@gmail.com")
                                    .recommender_id("test")
                                    .ci("1234567890123456789012345678901234567890123456789012345678901234")
                                    .di("1234567890123456789012345678901234567890123456789012345678901234")
                                    .legal_name("장민성")
                                    .legal_social_number("990814-1")
                                    .legal_ci("1234567890123456789012345678901234567890123456789012345678901234")
                                    .legal_di("1234567890123456789012345678901234567890123456789012345678901234")
                                    .legal_tel("010-5958-3207")
                                    .so_happycall_auth(2)
                                    .so_happycall_update_date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                                    .market_text_yn(0)
                                   // .market_email_yn(2)
                                   //  .market_tm_yn(2)
                                   // .push_yn(2)
                                    .build())))
                    .andReturn();

            System.out.println("status" + result.getResponse().getStatus());
            System.out.println("status" + result.getResponse().getErrorMessage());

            Gson gson = new Gson();
            String decryptedBody = aesCipher.dec_aes_object(result.getResponse().getContentAsString());
            UsersignupResponse response = gson.fromJson(decryptedBody, UsersignupResponse.class);
            System.out.println("result : " + response.getResultMessage());
            assertEquals(400, result.getResponse().getStatus());
            assertEquals(400, response.getResultCode());
        }


        @Test
        @DisplayName("회원탈퇴")
        void closeAccount() throws Exception {
            SiteInfo mockSiteInfo = new SiteInfo(); // 필요한 필드를 채워주세요
            mockSiteInfo.setEncrypt_key(testEncrpytionKey);
            when(siteInfoDao.getSiteInfobySoId("4012")).thenReturn(mockSiteInfo);

            SiteInfo siteInfo = siteInfoDao.getSiteInfobySoId("4012");
            ObjectMapper objectMapper = new ObjectMapper();
            AES_256_ECB aesCipher = new AES_256_ECB(siteInfo.getEncrypt_key());

            MvcResult result = mockMvc.perform(post("/api/v2/closeaccount")
                            .contentType(MediaType.TEXT_PLAIN)
                            .header("so_id", 4012)
                            .content(aesCipher.enc_aes_object(CloseAccountRequest.builder()
                                    .member_id("admin")
                                    .ci("1234567890123456789012345678901234567890123456789012345678901234")
                                    .build())))
                    .andReturn();
            System.out.println("status" + result.getResponse().getStatus());
            System.out.println("status" + result.getResponse().getErrorMessage());

            Gson gson = new Gson();
            String decryptedBody = aesCipher.dec_aes_object(result.getResponse().getContentAsString());
            OpenApiResponse response = gson.fromJson(decryptedBody, OpenApiResponse.class);
            System.out.println("result : " + response.getResultMessage());
            assertEquals(200, response.getResultCode());
        }
    }

    @DisplayName("패스워드")
    @Disabled
    @Nested
    public class PasswordTest {
        @Test
        void passwordcheck() throws Exception {
            SiteInfo mockSiteInfo = new SiteInfo(); // 필요한 필드를 채워주세요
            mockSiteInfo.setEncrypt_key(testEncrpytionKey);
            when(siteInfoDao.getSiteInfobySoId("4012")).thenReturn(mockSiteInfo);

            SiteInfo siteInfo = siteInfoDao.getSiteInfobySoId("4012");
            ObjectMapper objectMapper = new ObjectMapper();
            AES_256_ECB aesCipher = new AES_256_ECB(siteInfo.getEncrypt_key());

            MvcResult result = mockMvc.perform(post("/api/v2/passwordcheck")
                            .contentType(MediaType.TEXT_PLAIN)
                            .header("so_id", 4012)
                            .content(aesCipher.enc_aes_object(PasswordcheckRequest.builder()
                                    .member_id("admin")
                                    .check_password("8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92")
                                    .build())))
                    .andReturn();

            System.out.println("status" + result.getResponse().getStatus());
            System.out.println("status" + result.getResponse().getErrorMessage());

            Gson gson = new Gson();
            String decryptedBody = aesCipher.dec_aes_object(result.getResponse().getContentAsString());
            OpenApiResponse response = gson.fromJson(decryptedBody, OpenApiResponse.class);
            System.out.println("result : " + response.getResultMessage());
            assertEquals(200, result.getResponse().getStatus());
            assertEquals(200, response.getResultCode());


        }

        @Test
        void passwordchange() throws Exception {
            SiteInfo mockSiteInfo = new SiteInfo(); // 필요한 필드를 채워주세요
            mockSiteInfo.setEncrypt_key(testEncrpytionKey);
            when(siteInfoDao.getSiteInfobySoId("4012")).thenReturn(mockSiteInfo);

            SiteInfo siteInfo = siteInfoDao.getSiteInfobySoId("4012");
            ObjectMapper objectMapper = new ObjectMapper();
            AES_256_ECB aesCipher = new AES_256_ECB(siteInfo.getEncrypt_key());

            MvcResult result = mockMvc.perform(post("/api/v2/passwordchange")
                            .contentType(MediaType.TEXT_PLAIN)
                            .header("so_id", 4012)
                            .content(aesCipher.enc_aes_object(PasswordchangeRequest.builder()
                                    .member_id("admin")
                                    .password("8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92")
                                    .ci("1234567890123456789012345678901234567890123456789012345678901234")
                                    .new_password("2345")
                                    .build())))
                    .andExpect(status().isOk())
                    .andReturn();

            Gson gson = new Gson();
            String decryptedBody = aesCipher.dec_aes_object(result.getResponse().getContentAsString());
            OpenApiResponse response = gson.fromJson(decryptedBody, OpenApiResponse.class);
            System.out.println("result : " + response.getResultMessage());
            assertEquals(200, response.getResultCode());
        }

    }

    @Test
    void idDuplicatecheck() throws Exception {
        SiteInfo mockSiteInfo = new SiteInfo(); // 필요한 필드를 채워주세요
        mockSiteInfo.setEncrypt_key(testEncrpytionKey);
        when(siteInfoDao.getSiteInfobySoId("4012")).thenReturn(mockSiteInfo);

        SiteInfo siteInfo = siteInfoDao.getSiteInfobySoId("4012");
        AES_256_ECB aesCipher = new AES_256_ECB(siteInfo.getEncrypt_key());

        MvcResult result = mockMvc.perform(post("/api/v2/idduplicatecheck")
                        .contentType(MediaType.TEXT_PLAIN)
                        .header("so_id", 4012)
                        .content(aesCipher.enc_aes_object(IdDuplicateCheckRequest.builder()
                                .member_id("guest")
                                .build())))
                .andExpect(status().isOk())
                .andReturn();

        Gson gson = new Gson();
        String decryptedBody = aesCipher.dec_aes_object(result.getResponse().getContentAsString());
        OpenApiResponse response = gson.fromJson(decryptedBody, OpenApiResponse.class);
        System.out.println("result : " + response.getResultMessage());
        assertEquals(200, response.getResultCode());
    }

    @DisplayName("해피콜")
    @Nested
    @Disabled
    public class HappyCallTest {
        @Test
        void patchhappycalluserbyci() throws Exception {
            SiteInfo mockSiteInfo = new SiteInfo(); // 필요한 필드를 채워주세요
            mockSiteInfo.setEncrypt_key(testEncrpytionKey);
            when(siteInfoDao.getSiteInfobySoId("4012")).thenReturn(mockSiteInfo);

            SiteInfo siteInfo = siteInfoDao.getSiteInfobySoId("4012");
            AES_256_ECB aesCipher = new AES_256_ECB(siteInfo.getEncrypt_key());

            MvcResult result = mockMvc.perform(patch("/api/v2/patchhappycalluserbyci")
                            .contentType(MediaType.TEXT_PLAIN)
                            .header("so_id", 4012)
                            .content(aesCipher.enc_aes_object(PatchHappyCallUserByCiRequest.builder()
                                    .ci("1234567890123456789012345678901234567890123456789012345678901234")
                                    .member_id("admin")
                                    .happycall_auth(1)
                                    .build())))
                    .andReturn();
            Gson gson = new Gson();
            String decryptedBody = aesCipher.dec_aes_object(result.getResponse().getContentAsString());
            OpenApiResponse response = gson.fromJson(decryptedBody, OpenApiResponse.class);
            System.out.println("result : " + response.getResultMessage());
            assertEquals(200, response.getResultCode());
        }

        @Test
        void gethappycalllist() throws Exception {
            SiteInfo mockSiteInfo = new SiteInfo(); // 필요한 필드를 채워주세요
            mockSiteInfo.setEncrypt_key(testEncrpytionKey);
            when(siteInfoDao.getSiteInfobySoId("4012")).thenReturn(mockSiteInfo);

            SiteInfo siteInfo = siteInfoDao.getSiteInfobySoId("4012");
            AES_256_ECB aesCipher = new AES_256_ECB(siteInfo.getEncrypt_key());

            MvcResult result = mockMvc.perform(get("/api/v2/gethappycalllist")
                            .contentType(MediaType.TEXT_PLAIN)
                            .header("so_id", 4012)
                            .param("search_startdate","2021-01-01")
                            .param("search_enddate","2023-06-30")
                            .param("per_page" , "10")
                            .param("current_page" , "0")
                            /*.content(aesCipher.enc_aes_object(HappyCallListRequest.builder()
                                    .current_page(0).per_page(10)
                                    .build()))*/)
                    //.andExpect(status().isOk())
                    .andReturn();

            Gson gson = new Gson();
            String decryptedBody = aesCipher.dec_aes_object(result.getResponse().getContentAsString());
            HappyCallListResponse response = gson.fromJson(decryptedBody, HappyCallListResponse.class);
            System.out.println(new Gson().toJson(response));

        }
    }

    @Test
    @Disabled
    void getProductList() throws Exception {
        SiteInfo mockSiteInfo = new SiteInfo(); // 필요한 필드를 채워주세요
        mockSiteInfo.setEncrypt_key(testEncrpytionKey);
        when(siteInfoDao.getSiteInfobySoId("4012")).thenReturn(mockSiteInfo);

        SiteInfo siteInfo = siteInfoDao.getSiteInfobySoId("4012");
        AES_256_ECB aesCipher = new AES_256_ECB(siteInfo.getEncrypt_key());
        MvcResult result = mockMvc.perform(get("/api/v2/getproductlist")
                        .contentType(MediaType.TEXT_PLAIN)
                        .header("so_id", 4012)
                        .param("pageNum", "1")
                        .param("pageSize", "10")
                        .param("status", "1")
                        .param("startDatetime" , "2021-01-01 00:00:00")
                        .param("endDatetime" , "2023-06-01 23:59:59")
                )
                .andExpect(status().isOk())
                .andReturn();

        Gson gson = new Gson();
        String decryptedBody = aesCipher.dec_aes_object(result.getResponse().getContentAsString());
        ProductListResponse response = gson.fromJson(decryptedBody, ProductListResponse.class);
    }

    @Test
    void productPurchase() throws Exception {
        SiteInfo mockSiteInfo = new SiteInfo(); // 필요한 필드를 채워주세요
        mockSiteInfo.setEncrypt_key(testEncrpytionKey);
        when(siteInfoDao.getSiteInfobySoId("4012")).thenReturn(mockSiteInfo);

        SiteInfo siteInfo = siteInfoDao.getSiteInfobySoId("4012");
        AES_256_ECB aesCipher = new AES_256_ECB(siteInfo.getEncrypt_key());

        MvcResult result = mockMvc.perform(post("/api/v2/productpurchase")
                        .contentType(MediaType.TEXT_PLAIN)
                        .header("so_id", 4012)
                        .content(aesCipher.enc_aes_object(ProductPurchaseRequest.builder()
                                .member_id("admin").offer_id("67486")
                                .build())))
                .andReturn();
        Gson gson = new Gson();
        String decryptedBody = aesCipher.dec_aes_object(result.getResponse().getContentAsString());
        OpenApiResponse response = gson.fromJson(decryptedBody, OpenApiResponse.class);
    }


    @Test
    void productPurchaseCancel() {
    }
}