package com.castis.pvs.swagger.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(name = "IsDuplicateCheckResponse", description = "api 응답 모델")
public class DocIsDuplicateCheckResponse extends DocApiResponse{
    @Schema(name = "is_duplicate" , description = "중복여부", example = "true")
    private boolean is_duplicate;
}
