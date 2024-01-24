package com.castis.pvs.api.model.v2.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(name = "HappyCallListRequest", description = "api 요청 모델")
public class HappyCallListRequest {
    @Schema(description = "검색 조건 시작 시간")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate search_startdate;

    @Schema(description = "검색 조건 끝 시간")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDate search_enddate;

    @NotNull
    @Schema(description = "페이지 당 갯수")
    private Integer per_page;

    @NotNull
    @Schema(description = "검색 조건 현재 페이지")
    private Integer current_page;

}
