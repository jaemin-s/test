package com.castis.pvs.api.controller.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TestPasswordCheckResponse {
    private String transaction_id;
    private String result_code;
    private String result_msg;
}
