package com.castis.pvs.api.model.v2;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Info {
    private boolean status;
    @Schema(description = "reason", example = "가져오기 성공")
    private String reason;
    private int type;
}
