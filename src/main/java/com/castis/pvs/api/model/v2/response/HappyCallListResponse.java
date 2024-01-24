package com.castis.pvs.api.model.v2.response;

import com.castis.pvs.api.model.v2.Info;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class HappyCallListResponse {
    @JsonProperty("MSG")
    private String msg;

    @JsonProperty("resultCode")
    @Schema(example = "200")
    private Integer resultCode;

    @JsonProperty("Info")
    private Info info;

    @JsonProperty("Result")
    private JoinListResult result;

}
