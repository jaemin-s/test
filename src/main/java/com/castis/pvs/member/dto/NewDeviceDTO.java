package com.castis.pvs.member.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewDeviceDTO {

    private long id;
    private String accountId;
    private String deviceInfo;
    private String deviceType;
}

