package com.castis.pvs.api.model.v2.response;

import com.castis.common.model.mbs.Product;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductListResponse{
    private List<Product> products;
    private int totalSize;
    private int pageSize;
    private Long pageNum;
}
