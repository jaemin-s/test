package com.castis.pvs.api.model.v2.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@Schema(name = "UsersignupRequest", description = "회원가입 요청 모델")
public class UsersignupRequest {

    @Schema(description = "회원아이디", example = "homechoiceUser1", requiredMode = Schema.RequiredMode.REQUIRED)
    private String member_id;


    @NotEmpty
    @Schema(description = "회원패스워드", example = "9af15b336e6a9619928537df30b2e6a2376569fcf9d7e773eccede65606529a0", defaultValue = "9af15b336e6a9619928537df30b2e6a2376569fcf9d7e773eccede65606529a0", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

    @NotEmpty
    @Schema(description = "회원이름", example = "홍길동", requiredMode = Schema.RequiredMode.REQUIRED)
    private String user_name;

    @NotEmpty
    @Schema(description = "주민번호 앞자리 + 뒷자리 첫숫자", example = "900101-1", requiredMode = Schema.RequiredMode.REQUIRED)
    private String social_number;

    @NotEmpty
    @Schema(description = "회원의 핸드폰번호", example = "010-1234-5678", requiredMode = Schema.RequiredMode.REQUIRED)
    private String tel;

    // @NotEmpty
    @Schema(description = "", example = "씨씨에스 충북방송", requiredMode = Schema.RequiredMode.REQUIRED )
    private String mso_name;


    @Schema(description = "주소", example = "서울시 강남구")
    private String address_city;


    @Schema(description = "상세주소", example = "역삼동 123-456")
    private String address_dist;

    @Min(0)
    @Max(1)
    @NotNull
    @Schema(description = "성인인증 여부", example = "0", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer adult_cert;


    @NotNull
    @Schema(description = "appCode", example = "1")
    private Integer app_code;

    @Schema(description = "CIMS에서 발급하는 so별 고유id (홈초이스 제공 ex 4002-딜라이브, 4003-엘 지헬로비전, 4004-SKB)", example = "0")
    private String so_id;

    // @NotEmpty
    @Schema(description = "회원 이메일 주소", example = "home@email.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;


    @Schema(description = "추천인 id", example = "homechoice")
    private String recommender_id;

    @NotEmpty
    @Schema(description = "본인확인기관이 주민등록번호를 암호화하여 생성한 고유값", requiredMode = Schema.RequiredMode.REQUIRED, example = "9af15b336e6a9619928537df30b2e6a2376569fcf9d7e773eccede65606529a0")
    private String ci;

    @NotEmpty
    @Schema(description = "홈초이스가 개인식별을 위해 암호화한 고유값", requiredMode = Schema.RequiredMode.REQUIRED )
    private String di;

    @Schema(description = "보호자 이름")
    private String legal_name;

    @Schema(description = "보호자 주민번호 앞자리 + 뒷자리 첫숫자 (ex) 700101-2")
    private String legal_social_number;

    @Schema(description = "보호자 ci")
    private String legal_ci;

    @Schema(description = "보호자 di")
    private String legal_di;

    @Schema(description = "보호자 핸드폰번호")
    private String legal_tel;
    @Min(0)
    @Max(1)
    @Schema(description = "0 or 1 (so가입자 : 0, so미가입자 : 1)  default 1 -> 후에 so가입자라는 인증 후에 가입한 경우 '0', 가입하지 않은 경우 1로 PATCH")
    private Integer so_happycall_auth;

    @Schema(description = "회원사 해피콜 인증 처리시간")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime so_happycall_update_date;

    @Min(0)
    @Max(1)
    @Schema(description = "0 or 1 (전화 마케팅 미동의 : 0, 전화 마케팅 동의 : 1)")
    private Integer market_tm_yn;

    @Min(0)
    @Max(1)
    @Schema(description = "0 or 1 (이메일 마케팅 미동의 : 0, 이메일 마케팅 동의 : 1)")
    private Integer market_email_yn;

    @Min(0)
    @Max(1)
    @Schema(description = "0 or 1 (문자 마케팅 미동의 : 0, 문자 마케팅 동의 : 1)")
    private Integer market_text_yn;

    @Min(0)
    @Max(1)
    @Schema(description = "0 or 1 (푸시 알림 미동의 : 0, 푸시 알림 동의 : 1)")
    private Integer push_yn;

    @Schema(description = "app token")
    private String app_token;


    @Schema(description = "app version")
    private String app_version;

    @Schema(description = "device type")
    private String device_type;

    @Schema(description = "device uuid")
    private String device_uuid;

    @Schema(description = "wifi mac")
    private String wifi_mac;
}