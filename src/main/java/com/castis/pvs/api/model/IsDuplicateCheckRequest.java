package com.castis.pvs.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(name = "IsDuplicateCheckRequest", description = "api 요청 모델")
public class IsDuplicateCheckRequest {
    @Schema(name = "user_id", description = "가입자 아이디", example = "homechoiceUser1", required = true)
    private String user_id;
    @Schema(name = "app_code", description = "앱 코드(1:HCN, 2:CMB)", example = "2", required = true)
    private int app_code;
}
