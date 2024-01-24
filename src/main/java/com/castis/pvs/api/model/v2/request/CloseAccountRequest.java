package com.castis.pvs.api.model.v2.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotEmpty;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(name = "CloseAccountRequest", description = "api 요청 모델")
public class CloseAccountRequest {
    @NotBlank
    @Schema(description = "회원 아이디", example = "homechoiceUser1", required = true)
    private String member_id;

    @NotEmpty
    @Schema(description = "본인확인기관이 주민등록번호를 암호화하여 생성한 고유값", requiredMode = Schema.RequiredMode.REQUIRED, example = "9af15b336e6a9619928537df30b2e6a2376569fcf9d7e773eccede65606529a0")
    private String ci;
}
