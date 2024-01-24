package com.castis.pvs.api.controller.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class UsersignupTestRequest {
    private String member_id;
    private String password;
    private String user_name;
    private String social_number;

    private String tel;


    private String address_city;
    private String address_dist;
    private Integer adult_cert;
    private Integer app_code;
    private String so_id;
    private String email;
    private String recommender_id;
    private String ci;
    private String di;
    private String legal_name;
    private String legal_social_number;
    private String legal_ci;
    private String legal_di;
    private String legal_tel;
    private Integer so_happycall_auth;
    private String so_happycall_update_date;
    private Integer market_tm_yn;
    private Integer market_email_yn;
    private Integer market_text_yn;
    private Integer push_yn;
}
