package com.castis.pvs.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(name = "passwordChange 요청모델", description = "api 요청 모델")
public class UserPassRequest {

    @NotBlank
    @Schema(description = "가입자 아이디", example = "homechoiceUser1", required = true)
    private String user_id;

    @NotBlank
    @Schema(description = "가입자 비밀번호 (협의된 인크립션 방식으로 사용)", example = "SgAiyt2eEb5M/ZEQb3+tBDv8PhXdqbwCp6tlMEdQdJCkRiyXN5MFrysNfJAhNf3+VTKVXvSW9Wm3AJ7mXCEO4g==", required = true)
    private String user_pass;

    @NotBlank
    @Schema(description = "앱 코드(1:HCN, 2:CMB)", example = "2", required = true)
    private String app_code;
}
