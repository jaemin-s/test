package com.castis.pvs.api.model.v2.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotEmpty;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(name = "ProductPurchaseRequest", description = "api 요청 모델")
public class ProductPurchaseRequest {

    @NotEmpty
    @Schema(description = "회원아이디", example = "homechoiceUser1", requiredMode = Schema.RequiredMode.REQUIRED)
    private String member_id;

    @NotBlank
    @Schema(description = "상품 ID (ex. 60330,60331)", example = "67486", required = true)
    private String offer_id;

}
