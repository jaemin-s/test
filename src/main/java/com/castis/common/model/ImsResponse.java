package com.castis.common.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class ImsResponse {
    public ImsResponse(String result){
        this.result = result;
    }

    public ImsResponse(String result, String details){
        this.result = result;
        this.details = details;
    }

    @Schema(name = "result", description = "요청결과 (success/failure)", example = "success")
    private String result;
    @Schema(name = "details", description = "요청결과 상세", example = "")
    private String details;
}
