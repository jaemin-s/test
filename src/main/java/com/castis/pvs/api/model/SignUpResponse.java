package com.castis.pvs.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpResponse{
    private String offerId;
    private int code;
    public SignUpResponse(int code, String offerId){
        this.offerId = offerId;
        this.code = code;
    }
    public SignUpResponse(int code){
        this.code = code;
        this.offerId = null;
    }
}