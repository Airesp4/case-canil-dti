package com.dti.case_canil.dto;

import java.time.LocalDate;

public record BestPetShopRequest(LocalDate date, int smallDogs, int bigDogs) {
    
}
