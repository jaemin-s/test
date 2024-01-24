package com.castis.pvs.api.model.v2.response;

import com.fasterxml.jackson.annotation.JsonKey;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(name = "UsersignupResponse", description = "usersignup 응답 모델")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsersignupResponse { 

    @JsonProperty("resultCode")
    private int resultCode;

    @JsonProperty("resultMessage")
    private String resultMessage;

    @JsonProperty("happycall")
    private Integer happycall;


}
