package com.castis.common.model.mbs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class PurchaseSvodResponse {
    private List<Purchase> payLogs;
    private int resultCode;
    private String resultMessage;
}
