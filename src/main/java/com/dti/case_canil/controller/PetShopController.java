package com.dti.case_canil.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dti.case_canil.dto.BestPetShopDTO;
import com.dti.case_canil.dto.BestPetShopRequest;
import com.dti.case_canil.service.PetShopService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class PetShopController {
    
    private final PetShopService petShopService;

    @PostMapping("/best")
    public BestPetShopDTO getBestPetShop(@RequestBody BestPetShopRequest request) {
        return petShopService.findBestPetShop(request);
    }
}
