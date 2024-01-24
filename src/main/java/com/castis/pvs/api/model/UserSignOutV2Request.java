package com.castis.pvs.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(name = "UserSignOut 요청모델", description = "api 요청 모델")
public class UserSignOutV2Request {

    @NotBlank
    @Schema(description = "가입자 아이디", example = "homechoiceUser1", required = true)
    private String user_id;
}
