package com.castis.pvs.swagger.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(name = "apiResponse", description = "api 응답 모델")
@Getter
public class DocApiResponse {

    @Schema(name = "result_code" , description = "100 : 성공", example = "100")
    private int result_code;

    @Schema(name = "result_msg" , description = "", example = "")
    private String result_msg;
}
