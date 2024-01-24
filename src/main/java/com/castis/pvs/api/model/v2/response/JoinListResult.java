package com.castis.pvs.api.model.v2.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JoinListResult {
    @JsonProperty("Page")
    private PageInfo page;
    @JsonProperty("JoinList")
    private List<HappyCallMember> joinList;
}
