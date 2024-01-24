package com.castis.pvs.api.model.v2.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotEmpty;

@Setter
@Getter
@ToString(exclude= {"check_password"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(name = "PasswordcheckRequest", description = "api 요청 모델")
public class PasswordcheckRequest {
    @NotEmpty
    @Schema(description = "회원아이디", example = "homechoiceUser1", requiredMode = Schema.RequiredMode.REQUIRED)
    private String member_id;

    @Schema(description = "패스워드", example = "9af15b336e6a9619928537df30b2e6a2376569fcf9d7e773eccede65606529a0", required = true)
    private String check_password;
}
