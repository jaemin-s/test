package com.castis.common.model.mbs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Purchase {
    private String transaction_id;
    private String billing_id;
    private String device_type;
    private String wifi_mac;
    private String account_id;
    private String product_id;
    private String title;
    private Long price;
    private String period;
    private String app_code;
    private boolean discount;
    private Long price_point;
    private String pointspolicy_id;
    private String language;
    private String type;
    private String model;
    private String tel;
}
