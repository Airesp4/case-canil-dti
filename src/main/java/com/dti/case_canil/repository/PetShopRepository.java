package com.dti.case_canil.repository;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.dti.case_canil.model.PetShop;

@Repository
public class PetShopRepository {
    
    public List<PetShop> findAll() {
        return Arrays.asList(
            new PetShop(
                "Meu Canino Feliz",
                2000.0,
                new BigDecimal("20.00"),
                new BigDecimal("40.00"),
                new BigDecimal("20.00").multiply(new BigDecimal("1.2")),
                new BigDecimal("40.00").multiply(new BigDecimal("1.2"))
            ),
            new PetShop(
                "Vai Rex",
                1700.0,
                new BigDecimal("15.00"),
                new BigDecimal("50.00"),
                new BigDecimal("20.00"),
                new BigDecimal("55.00")
            ),
            new PetShop(
                "ChowChawgas",
                800.0,
                new BigDecimal("30.00"),
                new BigDecimal("45.00"),
                new BigDecimal("30.00"),
                new BigDecimal("45.00")
            )
        );
    }
}
