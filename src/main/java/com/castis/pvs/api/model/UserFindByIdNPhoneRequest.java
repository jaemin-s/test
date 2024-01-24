package com.castis.pvs.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserFindByIdNPhoneRequest {

    @NotBlank
    private String user_id;

    @NotBlank
    private String phone_number;

    @NotBlank
    private String app_code;
}
