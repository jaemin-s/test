package com.castis.pvs.api.model.v2.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(name = "IdDuplicateCheckRequest", description = "api 요청 모델")
public class IdDuplicateCheckRequest {
    @NotEmpty
    @Schema(description = "회원아이디", example = "homechoiceUser1", requiredMode = Schema.RequiredMode.REQUIRED)
    private String member_id;
    /*@NotEmpty
    @Schema(description = "본인확인기관이 주민등록번호를 암호화하여 생성한 고유값", requiredMode = Schema.RequiredMode.REQUIRED)
    private String ci;*/

}