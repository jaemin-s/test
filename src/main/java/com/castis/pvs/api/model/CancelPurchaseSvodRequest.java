package com.castis.pvs.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.validator.constraints.NotBlank;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class CancelPurchaseSvodRequest {
    @NotBlank
    @Schema(description = "가입자 아이디", example = "homechoiceUser1", required = true)
    private String userId;

    @NotBlank
    @Schema(description = "상품 ID (ex. 60330,60331)", example = "67486", required = true)
    private String offerId;

}
