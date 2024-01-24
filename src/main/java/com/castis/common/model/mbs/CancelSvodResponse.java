package com.castis.common.model.mbs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class CancelSvodResponse {
    private String offerId;
    private String accountId;
    private String[] billingIds;
    private int resultCode;
    private String resultMessage;
}
