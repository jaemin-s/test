package com.castis.pvs.api.model.v2.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(name = "OpenApiResponse", description = "api 요청 모델")
public class OpenApiResponse {
    @Schema(name = "resultCode" , description = "200 : 성공", example = "200")
    private int resultCode;

    @Schema(name = "resultMessage" , description = "", example = "")
    private String resultMessage;
}
