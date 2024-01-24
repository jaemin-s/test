package com.castis.pvs.connector;

import com.castis.common.ssl.CiHttpsUtil;
import com.castis.common.util.AES_256_ECB;
import com.castis.common.util.CiHttpUtil;
import com.castis.common.util.CiLogger;
import com.castis.pvs.api.dto.SignUpDTO;
import com.castis.pvs.api.model.SignUpResponse;
import com.castis.pvs.entity.SiteInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Objects;

@Component("imsConnector")
public class IMSConnector {

    @Value("${ims.scheme:http}")
    private String ims_scheme;

    @Value("${ims.ip:127.0.0.1}")
    private String ims_ip;

    @Value("${ims.port:8080}")
    private int ims_port;

    @Value("${ims.conn_timeout:3000}")
    private int ims_conn_timeout;

    @Value("${ims.read_timeout:10000}")
    private int ims_read_timeout;


    @Value("${ims.sign_up.use:true}")
    private boolean ims_sign_up_use;

    @Value("${ims.sign_up_url:/ims/sign-up}")
    private String ims_sign_up_url;

    @Value("${pvs.ssl.cacerts.verify:false}")
    private boolean ssl_cacerts_verify;

    @Value("${pvs.ssl.cacerts.protocol:TLS}")
    private String ssl_cacerts_protocol;

    @Value("${pvs.ssl.cacerts.path:D:\\Program Files\\Java\\jre1.8.0_191\\lib\\security\\cacerts}")
    private String ssl_cacerts_path;

    @Value("${pvs.ssl.cacerts.password:changeit}")
    private String ssl_cacerts_password;

    @Value("${pvs.ssl.hostname.verify:false}")
    private boolean ssl_hostname_verify;

    public boolean isIms_sign_up_use() {
        return ims_sign_up_use;
    }

    public void setIms_sign_up_use(boolean ims_sign_up_use) {
        this.ims_sign_up_use = ims_sign_up_use;
    }


    public boolean isSsl_hostname_verify() {
        return ssl_hostname_verify;
    }

    public void setSsl_hostname_verify(boolean ssl_hostname_verify) {
        this.ssl_hostname_verify = ssl_hostname_verify;
    }

    public String getIms_scheme() {
        return ims_scheme;
    }

    public void setIms_scheme(String ims_scheme) {
        this.ims_scheme = ims_scheme;
    }

    public String getIms_ip() {
        return ims_ip;
    }

    public void setIms_ip(String ims_ip) {
        this.ims_ip = ims_ip;
    }

    public int getIms_port() {
        return ims_port;
    }

    public void setIms_port(int ims_port) {
        this.ims_port = ims_port;
    }

    public String getIms_sign_up_url() {
        return ims_sign_up_url;
    }

    public void setIms_sign_up_url(String ims_sign_up_url) {
        this.ims_sign_up_url = ims_sign_up_url;
    }

    public int getIms_conn_timeout() {
        return ims_conn_timeout;
    }

    public void setIms_conn_timeout(int ims_conn_timeout) {
        this.ims_conn_timeout = ims_conn_timeout;
    }

    public int getIms_read_timeout() {
        return ims_read_timeout;
    }

    public void setIms_read_timeout(int ims_read_timeout) {
        this.ims_read_timeout = ims_read_timeout;
    }

    public boolean isSsl_cacerts_verify() {
        return ssl_cacerts_verify;
    }

    public void setSsl_cacerts_verify(boolean ssl_cacerts_verify) {
        this.ssl_cacerts_verify = ssl_cacerts_verify;
    }

    public String getSsl_cacerts_protocol() {
        return ssl_cacerts_protocol;
    }

    public void setSsl_cacerts_protocol(String ssl_cacerts_protocol) {
        this.ssl_cacerts_protocol = ssl_cacerts_protocol;
    }

    public String getSsl_cacerts_path() {
        return ssl_cacerts_path;
    }

    public void setSsl_cacerts_path(String ssl_cacerts_path) {
        this.ssl_cacerts_path = ssl_cacerts_path;
    }

    public String getSsl_cacerts_password() {
        return ssl_cacerts_password;
    }

    public void setSsl_cacerts_password(String ssl_cacerts_password) {
        this.ssl_cacerts_password = ssl_cacerts_password;
    }


    @PostConstruct
    public void initialize() {
        printProperties();
    }


    public void printProperties() {

        CiLogger.info("======================================== IMSConnector Properties ========================================");
        CiLogger.info("%s = %s", "ims.scheme", ims_scheme);
        CiLogger.info("%s = %s", "ims.ip", ims_ip);
        CiLogger.info("%s = %s", "ims.port", ims_port);
        CiLogger.info("%s = %s", "ims.conn_timeout", ims_conn_timeout);
        CiLogger.info("%s = %s", "ims.read_timeout", ims_read_timeout);
        CiLogger.info("%s = %s", "ims.sign_up_url", ims_sign_up_url);
        CiLogger.info("%s = %s", "ims.confirm_success.use", ims_sign_up_use);
        CiLogger.info("%s = %s", "pvs.ssl.cacerts.verify", ssl_cacerts_verify);
        CiLogger.info("%s = %s", "pvs.ssl.cacerts.protocol", ssl_cacerts_protocol);
        CiLogger.info("%s = %s", "pvs.ssl.cacerts.path", ssl_cacerts_path);
        CiLogger.info("%s = %s", "pvs.ssl.cacerts.password", ssl_cacerts_password);
        CiLogger.info("%s = %s", "pvs.ssl.hostname.verify", ssl_hostname_verify);
        CiLogger.info("======================================== IMSConnector Properties ========================================");
    }

    public <T> T sign_up(String encrytedBody, Class<T> outputClass) throws Exception {
        return CiHttpsUtil.post_json(
                ims_scheme
                , ims_ip
                , ims_port
                , ims_sign_up_url
                , encrytedBody
                , null
                , ims_conn_timeout
                , ims_read_timeout
                , outputClass
                , null
                , ssl_cacerts_protocol
                , ssl_cacerts_verify
                , ssl_cacerts_path
                , ssl_cacerts_password
                , ssl_hostname_verify
        );
    }

    public <T> T sign_up(SiteInfo siteInfo,String encrytedBody ,Class<T> outputClass) throws Exception {
        return CiHttpsUtil.post_json(
                siteInfo.getProtocol()
                , siteInfo.getIp()
                , siteInfo.getPort()
                , siteInfo.getSign_up_url()
                , encrytedBody
                , CiHttpUtil.HTTP_TEXT_PLAIN
                , ims_conn_timeout
                , ims_read_timeout
                , outputClass
                , null
                , ssl_cacerts_protocol
                , ssl_cacerts_verify
                , ssl_cacerts_path
                , ssl_cacerts_password
                , ssl_hostname_verify
        );
    }

    @Deprecated
    public int sign_up(SignUpDTO signUpDTO, Boolean signUpUse) {
        if (signUpUse == false) {
            CiLogger.info("ims_sign_up_use[off] :: SignUpDTO[%s]", signUpDTO.toString());
            return -2;
        }

        if (signUpDTO == null) {
            CiLogger.warn("Null signUpDTO");
            return -1;
        }
        try {
            AES_256_ECB aes = new AES_256_ECB("Hcnottrestfulapiservice20221111s");
            String AES_256 = aes.enc_aes_object(signUpDTO);
            String response = sign_up(AES_256, String.class);

            Map<String, String> responseMap = aes.dec_aes_map(response);
            if (responseMap.get("result").equals("success")) {
                CiLogger.info("SignUp Success :: response[%s]", responseMap.toString());
                return 0;
            } else {
                CiLogger.info(" sign_up SignUp Fail :: response[%s]", responseMap.toString());
                if (responseMap.get("details").equals("01")) {
                    return 1;
                } else if (responseMap.get("details").equals("02")) {
                    return 2;
                } else if (responseMap.get("details").equals("03")) {
                    return 3;
                }
            }
        } catch (Exception e) {
            CiLogger.warn(e, "Fail to send notification to IMS");
            return -1;
        }

        return -1;
    }


    public SignUpResponse sign_up(SignUpDTO signUpDTO, String secretKey) throws Exception {

        if (ims_sign_up_use == false) {
            CiLogger.info("ims_sign_up_use[off] :: SignUpDTO[%s]", signUpDTO.toString());
            return new SignUpResponse(-2);
        }

        if (signUpDTO == null) {
            CiLogger.warn("Null signUpDTO");
            return new SignUpResponse(-1);
        }
        try {
            AES_256_ECB aes = new AES_256_ECB(secretKey);
            String AES_256 = aes.enc_aes_object(signUpDTO);
            String response = sign_up(AES_256, String.class);

            Map<String, String> responseMap = aes.dec_aes_map(response);
            if (responseMap.get("result").equals("success")) {
                CiLogger.info("SignUp Success :: response[%s]", responseMap.toString());
                if(Objects.nonNull(responseMap.get("offerId"))){
                    return new SignUpResponse(0 , responseMap.get("offerId"));
                }else {
                    return new SignUpResponse(0);
                }
            } else {
                CiLogger.info(" sign_up SignUp Fail :: response[%s]", responseMap.toString());
                if (responseMap.get("details").equals("01")) {
                    return new SignUpResponse(1);
                } else if (responseMap.get("details").equals("02")) {
                    return new SignUpResponse(2);
                } else if (responseMap.get("details").equals("03")) {
                    return new SignUpResponse(3);
                }
            }
        } catch (Exception e) {
            CiLogger.warn(e, "Fail to send notification to IMS");
            return new SignUpResponse(-1);
        }

        return new SignUpResponse(-1);
    }

    /**
     *  HCN 연동 시행 
     * @param signUpDTO
     * @param siteInfo
     * @return
     * @throws Exception
     */
    public SignUpResponse sign_up_v3(SignUpDTO signUpDTO, SiteInfo siteInfo) throws Exception {
        String secretKey = siteInfo.getEncrypt_key();

        if (siteInfo.getSign_up_use() == false) {
            CiLogger.info("ims_sign_up_use[off] :: SignUpDTO[%s]", signUpDTO.toString());
            return new SignUpResponse(-2);
        }

        if (signUpDTO == null) {
            CiLogger.warn("Null signUpDTO");
            return new SignUpResponse(-1);
        }
        try {
            AES_256_ECB aes = new AES_256_ECB(secretKey);
            String AES_256 = aes.enc_aes_object(signUpDTO);
            String response = sign_up(siteInfo, AES_256, String.class);

            Map<String, String> responseMap = aes.dec_aes_map(response);
            if (responseMap.get("result").equals("success")) {
                CiLogger.info("SignUp Success :: response[%s]", responseMap.toString());
                if(responseMap.get("details").equalsIgnoreCase("s01") && Objects.nonNull(responseMap.get("offerId"))){
                    return new SignUpResponse(0 , responseMap.get("offerId").replaceAll(" " , ""));
                }else {
                    return new SignUpResponse(0);
                }
            } else {
                CiLogger.info("HCN sign_up_v3 SignUp Fail :: response[%s]", responseMap.toString());
                if (responseMap.get("details").equals("f01")) { //중복 아이디 
                    return new SignUpResponse(1);
                } else if (responseMap.get("details").equals("f02")) { //필수값 미 입력 - userid, 이름, 전화번호, 주민번호, 주소 
                    return new SignUpResponse(2);
                } else if (responseMap.get("details").equals("f03")) {// 미가입자 - 이름, 전화번호, 생년월일 존재x, 주소 존재x, 
                    return new SignUpResponse(3);
                }
            }
        } catch (Exception e) {
            CiLogger.warn(e, "Fail to send notification to IMS");
            return new SignUpResponse(-1);
        }

        return new SignUpResponse(-1);
    }


}
