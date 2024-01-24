package com.castis.pvs.api.model.v2.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductListRequest {
    private Integer pageSize;
    private Integer currentPage;
    private String packageType;
    private String productType;
    private Integer status;
}
