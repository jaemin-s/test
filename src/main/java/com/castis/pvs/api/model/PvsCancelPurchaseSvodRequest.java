package com.castis.pvs.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PvsCancelPurchaseSvodRequest {

    @NotBlank
    private String account_id;

    private String offer_id;

    @NotBlank
    private String[] billing_ids;

}
