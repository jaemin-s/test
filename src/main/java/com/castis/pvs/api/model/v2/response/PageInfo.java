package com.castis.pvs.api.model.v2.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageInfo {
    private long total;
    private long per_page;
    private long current_page;
    private long last_page;
}
