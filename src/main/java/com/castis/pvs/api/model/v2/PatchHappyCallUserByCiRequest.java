package com.castis.pvs.api.model.v2;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(name = "PatchHappyCallUserByCiRequest", description = "api 요청 모델")
public class PatchHappyCallUserByCiRequest {
    @NotBlank
    @Schema(description = "본인확인기관이 주민등록번호를 암호화하여 생성한 고유값", requiredMode = Schema.RequiredMode.REQUIRED, example = "9af15b336e6a9619928537df30b2e6a2376569fcf9d7e773eccede65606529a0")
    private String ci;

    @NotBlank
    @Schema(description = "회원 아이디", example = "homechoiceUser1", required = true)
    private String member_id;

    @Max(1)
    @Min(0)
    @NotNull
    @Schema(description = "0 or 1 (so가입자 : 0, so미가입자 : 1)  default 1 -> 후에 so가입자라는 인증 후에 가입한 경우 '0', 가입하지 않은 경우 1로 PATCH")
    private Integer happycall_auth;

}
