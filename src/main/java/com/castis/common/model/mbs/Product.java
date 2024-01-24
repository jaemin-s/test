package com.castis.common.model.mbs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.checkerframework.checker.units.qual.A;

import java.math.BigInteger;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private BigInteger id;
    private String title;
    private String packageType; // 상품묶음방식. single:단일, package:묶음, series:시리즈
    private String productType; // 상품구매방식. rent:대여, buy:소장, subsription:월정액
    private Integer price; // 0:무료
    private String rentalPeriod; // 시청기간(대여기간). 무료일경우 종료날짜. pattern[yyyy.MM.dd]
    private boolean isAdult;
}
