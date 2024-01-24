package com.castis.pvs.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

@Setter
@Getter
@ToString(exclude= {"password"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(name = "PasswordCheckRequest", description = "api 요청 모델")
public class PasswordCheckRequest {

    @NotBlank
    private String transaction_id;

    @NotBlank
    private String device_type;

    @Schema(description = "가입자 아이디", example = "homechoiceUser1", required = true)
    private String account_id;

    @NotBlank
    private String wifi_mac;

    @NotBlank
    @Schema(description = "앱 코드(1:HCN, 2:CMB)", example = "2", required = true)
    private String app_code;

    @Schema(description = "가입자 비밀번호", example = "9af15b336e6a9619928537df30b2e6a2376569fcf9d7e773eccede65606529a0", required = true)
    private String password;
    @Schema(description = "요청 타입\n" +
            "LOGIN : 로그인\n" +
            "ADULT : 성인인증\n" , example = "LOGIN", required = true)
    private String checkType;

    @Schema(description = "가입자 모델명", example = "")
    private String model;

    @Schema(description = "언어 종류\n reserve\n" +
            "Multi-language \n" +
            "Ex) 한국어- ko\n", example = "ko")
    private String language;
}
