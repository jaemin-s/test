package com.castis.pvs.api.model.v3.response;

import com.castis.common.model.mbs.Product;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SiteInfoResponse{
    private Long id;

    private String mso_name;

    private String so_id;
    private String protocol;
    private String ip;
    private int port;
    private String sign_up_url;
    private String happy_call_url;
    private Boolean sign_up_use;
    private Boolean happy_call_use;

    private Boolean encrypt_use;

    private String encrypt_key;

}
