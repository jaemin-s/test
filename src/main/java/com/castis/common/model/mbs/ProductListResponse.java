package com.castis.common.model.mbs;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductListResponse {
    private List<Product> offers;
    private int totalSize;
    private int pageSize;
    private int pageNum;
}
