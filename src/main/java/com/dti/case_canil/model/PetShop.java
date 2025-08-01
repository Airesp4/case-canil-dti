package com.dti.case_canil.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "name")
public class PetShop {
    
    private String name;
    private double distanceInMeters;
    private BigDecimal weekdayPriceSmallDog;
    private BigDecimal weekdayPriceBigDog;
    private BigDecimal weekendPriceSmallDog;
    private BigDecimal weekendPriceBigDog;
}
