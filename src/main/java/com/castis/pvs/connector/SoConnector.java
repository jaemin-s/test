package com.castis.pvs.connector;

import com.castis.common.ssl.CiHttpsUtil;
import com.castis.common.util.AES_256_ECB;
import com.castis.common.util.AES_128_CBC;
import com.castis.common.util.CiHttpUtil;
import com.castis.common.util.CiLogger;
import com.castis.pvs.api.dto.SignUpDTO;
import com.castis.pvs.api.dto.SignUpSoDTO;
import com.castis.pvs.api.model.SignUpResponse;
import com.castis.pvs.entity.SiteInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Objects;

@Component("SoConnector")
public class SoConnector {

    @Value("${so.scheme:https}")
    private String so_scheme;

    @Value("${so.ip:127.0.0.1}")
    private String so_ip;

    @Value("${so.port:8080}")
    private String so_port;

    @Value("${so.conn_timeout:3000}")
    private  int so_conn_timeout;

    @Value("${so.read_timeout:10000}")
    private  int so_read_timeout;


    @Value("${so.sign_up.use:true}")
    private boolean so_sign_up_use;

    @Value("${so.sign_up_url:/ims/sign-up}")
    private String so_sign_up_url;

    @Value("${pvs.ssl.cacerts.verify:false}")
    private  boolean ssl_cacerts_verify;

    @Value("${pvs.ssl.cacerts.protocol:TLS}")
    private  String ssl_cacerts_protocol;

    @Value("${pvs.ssl.cacerts.path:D:\\Program Files\\Java\\jre1.8.0_191\\lib\\security\\cacerts}")
    private  String ssl_cacerts_path;

    @Value("${pvs.ssl.cacerts.password:changeit}")
    private  String ssl_cacerts_password;

    @Value("${pvs.ssl.hostname.verify:false}")
    private  boolean ssl_hostname_verify;

    public boolean isSo_sign_up_use() {
        return so_sign_up_use;
    }

    public void setSo_sign_up_use(boolean so_sign_up_use) {
        this.so_sign_up_use = so_sign_up_use;
    }


    public boolean isSsl_hostname_verify() {
        return ssl_hostname_verify;
    }

    public void setSsl_hostname_verify(boolean ssl_hostname_verify) {
        this.ssl_hostname_verify = ssl_hostname_verify;
    }

    public String getSo_scheme() {
        return so_scheme;
    }

    public void setSo_scheme(String so_scheme) {
        this.so_scheme = so_scheme;
    }

    public String getSo_ip() {
        return so_ip;
    }

    public void setSo_ip(String so_ip) {
        this.so_ip = so_ip;
    }

    public String getSo_port() {
        return so_port;
    }

    public void setSo_port(String so_port) {
        this.so_port = so_port;
    }

    public String getSo_sign_up_url() {
        return so_sign_up_url;
    }

    public void setSo_sign_up_url(String so_sign_up_url) {
        this.so_sign_up_url = so_sign_up_url;
    }

    public int getSo_conn_timeout() {
        return so_conn_timeout;
    }

    public void setSo_conn_timeout(int so_conn_timeout) {
        this.so_conn_timeout = so_conn_timeout;
    }

    public int getSo_read_timeout() {
        return so_read_timeout;
    }

    public void setSo_read_timeout(int so_read_timeout) {
        this.so_read_timeout = so_read_timeout;
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

        CiLogger.info("======================================== SOConnector Properties ========================================");
        // CiLogger.info("%s = %s", "So.scheme", so_scheme);
        // CiLogger.info("%s = %s", "So.ip", so_ip);
        // CiLogger.info("%s = %s", "So.port", so_port);
        CiLogger.info("%s = %s", "So.conn_timeout", so_conn_timeout);
        CiLogger.info("%s = %s", "So.read_timeout", so_read_timeout);
        // CiLogger.info("%s = %s", "So.sign_up_url", so_sign_up_url);
        CiLogger.info("%s = %s", "So.confirm_success.use", so_sign_up_use);
        CiLogger.info("%s = %s", "pvs.ssl.cacerts.verify", ssl_cacerts_verify);
        CiLogger.info("%s = %s", "pvs.ssl.cacerts.protocol", ssl_cacerts_protocol);
        CiLogger.info("%s = %s", "pvs.ssl.cacerts.path", ssl_cacerts_path);
        CiLogger.info("%s = %s", "pvs.ssl.cacerts.password", ssl_cacerts_password);
        CiLogger.info("%s = %s", "pvs.ssl.hostname.verify", ssl_hostname_verify);
        CiLogger.info("======================================== SOConnector Properties ========================================");
    }

    public  <T> T sign_up(SiteInfo siteInfo,String encrytedBody ,Class<T> outputClass) throws Exception {
        return CiHttpsUtil.post_json(
                siteInfo.getProtocol()
                , siteInfo.getIp()
                , siteInfo.getPort()
                , siteInfo.getSign_up_url()
                , encrytedBody
                , CiHttpUtil.HTTP_TEXT_PLAIN
                , so_conn_timeout
                , so_read_timeout
                , outputClass
                , null
                , ssl_cacerts_protocol
                , ssl_cacerts_verify
                , ssl_cacerts_path
                , ssl_cacerts_password
                , ssl_hostname_verify
        );
    }


    /**
     * SO 가입자 회원 인증   
     * @param signUpDTO
     * @param siteInfo
     * @return
     * @throws Exception
     */
    public  SignUpResponse sign_up_to_so(SignUpSoDTO signUpSoDTO, SiteInfo siteInfo) throws Exception {
        String secretKey = siteInfo.getEncrypt_key();
        Map<String, String> responseMap = null;

        if (siteInfo.getSign_up_use() == false) {
            CiLogger.info("so_sign_up_use[off] :: SignUpSoDTO[%s]", signUpSoDTO.toString());
            return new SignUpResponse(-2); // 회원인증을 사용하지 않는 so 입니다. 회원인증 API Url확인하세요 
        }

        if (signUpSoDTO == null) {
            CiLogger.warn("Null SignUpSoDTO");
            return new SignUpResponse(2);//회원인증 요청 정보가 없거나 유효하지 않습니다. 
        }
        try {
            CiLogger.info("so_sign_up_use[on] :: SignUpSoDTO[%s]", signUpSoDTO.toString());
            if(siteInfo.getSo_id().equals("4002")){
                // AES_D128_CBC aes = new AES_D128_CBC();
                // String AES_128 = aes.enc_aes_object(signUpSoDTO);
                String AES_128 = AES_128_CBC.encAES128CBC_object(signUpSoDTO, siteInfo.getEncrypt_128key());

                CiLogger.info("so_sign_up_use[on] :: Encrypted SignUpSoDTO[%s]", AES_128.toString());
                String response = sign_up(siteInfo, AES_128, String.class);

                responseMap = AES_128_CBC.decAES128CBC_map(response, siteInfo.getEncrypt_128key());
                // responseMap = AES_128_CBC.decAES128CBC_map(response);
            }else{
                AES_256_ECB aes = new AES_256_ECB(secretKey);
                String AES_256 = aes.enc_aes_object(signUpSoDTO);

                CiLogger.info("so_sign_up_use[on] :: Encrypted SignUpSoDTO[%s]", AES_256.toString());
                String response = sign_up(siteInfo, AES_256, String.class);       
                
               responseMap = aes.dec_aes_map(response);                
            }
           

            if (responseMap.get("resultCode").equals("201")) {

                CiLogger.info("SO 가입자 인증 Success :: response[%s]", responseMap.toString());
                // if(responseMap.get("details").equalsIgnoreCase("s01") && Objects.nonNull(responseMap.get("offerId"))){
                //     return new SignUpResponse(0 , responseMap.get("offerId").replaceAll(" " , ""));
                // }else {
                //     return new SignUpResponse(0);
                // }
                return new SignUpResponse(0); //SO 가입자 입니다. 
            } else {
                // 미가입자  처리 
                CiLogger.info("SO 가입자 인증 Fail :: response[%s]", responseMap.toString());
                return new SignUpResponse(3); // 미가입자
                // if (responseMap.get("details").equals("f01")) {
                    // return new SignUpResponse(1);
                // } else if (responseMap.get("details").equals("f02")) {
                    // return new SignUpResponse(2);
                // } else if (responseMap.get("details").equals("f03")) {
                    // return new SignUpResponse(3);
                // }
            }
        } catch (Exception e) {
            CiLogger.error(e, "SO Sign Up Fail to send notification to SO Server [reason]: "+ e.toString());
            return new SignUpResponse(-1);
        }
    }


}
