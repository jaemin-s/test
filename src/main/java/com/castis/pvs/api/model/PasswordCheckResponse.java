package com.castis.pvs.api.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PasswordCheckResponse {
    private String transaction_id;
    private int result_code;
    private String result_msg;
    private boolean isActive;
}
