package com.castis.pvs.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@Schema(name = "UserSignUpRequest",description = "회원가입 요청 모델")
public class UserSignUpV2Request {

    @NotBlank
    @Schema(description = "가입자 아이디", example = "homechoiceUser1", required = true)
    private String user_id;

    @Schema(description = "가입자 비밀번호", example = "9af15b336e6a9619928537df30b2e6a2376569fcf9d7e773eccede65606529a0", defaultValue = "9af15b336e6a9619928537df30b2e6a2376569fcf9d7e773eccede65606529a0", required = true)
    private String user_pass;

    @Schema(description = "가입자명" , example = "홍길동", required = true)
    private String user_name;

    @Schema(description = "주민번호" , example = "123456-1", required = true)
    private String social_number;

    @Schema(description = "핸드폰 번호" , example = "010-1234-5678", required = true)
    private String phone_number;

    @Schema(description = "우편번호", example = "145556", required = true)
    private String zipcode;

    @Schema(description = "주소", example = "서울시 강남구", required = true)
    private String address;

    @Schema(description = "상세주소", example = "역삼동 123-456", required = true)
    private String detail_adrdess;

    @Schema(description = "회원가입을 요청한 디바이스 타입", example = "mobile", required = true)
    private String device_type;

    @Schema(description = "성인인증 여부", example = "0" , required = true)
    private String adult_cert;

    @Schema(description = "Mac을 보안 정책상 사용하기 어려울 경우를 대비하여 추가")
    private String device_uuid;

    // @NotBlank
    @Schema(description = "앱 코드", example = "1", required = true)
    private String app_code;

    @Schema(description = "MSO 이름", example = "CMB", required = true)
    private String mso_name;

    @Schema(description = "가입자 이메일", example = "home@email.com" , required = true)
    private String email;

    @Schema(description = "추천인 ID", example = "homechoice")
    private String recommender_id;

    @Schema(description = "본인확인기관이 주민등록번호를 암호화하여 생성한 고유값", example = "9af15b336e6a9619928537df30b2e6a2376569fcf9d7e773eccede65606529a0")
    private String ci;

    @Schema(description = "DI")
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

    @Schema(description = "회원가입시간")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime so_happycall_update_date;

    @Schema(description = "CIMS에서 발급하는 so별 고유id (홈초이스 제공 ex 4002-딜라이브, 4003-엘 지헬로비전, 4004-SKB)", example = "0")
    private String so_id;

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
}
