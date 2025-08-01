package com.dti.case_canil.service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.dti.case_canil.dto.BestPetShopDTO;
import com.dti.case_canil.dto.BestPetShopRequest;
import com.dti.case_canil.model.PetShop;
import com.dti.case_canil.repository.PetShopRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PetShopService {
    
    private final PetShopRepository petShopRepository;

    public BestPetShopDTO findBestPetShop(BestPetShopRequest request) {
        List<PetShop> petShops = petShopRepository.findAll();
        boolean isWeekend = isWeekend(request.date());

        Map<PetShop, BigDecimal> totalPrices = petShops.stream()
            .collect(Collectors.toMap(
                Function.identity(),
                petShop -> calculateTotalPrice(
                    petShop,
                    isWeekend,
                    request.smallDogs(),
                    request.bigDogs()
                )
            ));

        PetShop bestPetShop = totalPrices.entrySet().stream()
            .min(Comparator.<Map.Entry<PetShop, BigDecimal>, BigDecimal>comparing(Map.Entry::getValue)
                .thenComparing(entry -> entry.getKey().getDistanceInMeters()))
            .map(Map.Entry::getKey)
            .orElseThrow(() -> new RuntimeException("Nenhum pet shop encontrado."));

        return new BestPetShopDTO(bestPetShop.getName(), totalPrices.get(bestPetShop));
    }

    private boolean isWeekend(LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();
        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
    }

    private BigDecimal calculateTotalPrice(PetShop petShop, boolean isWeekend, int smallDogs, int bigDogs) {
        var smallDogPrice = isWeekend ? petShop.getWeekendPriceSmallDog() : petShop.getWeekdayPriceSmallDog();
        var bigDogPrice = isWeekend ? petShop.getWeekendPriceBigDog() : petShop.getWeekdayPriceBigDog();

        return smallDogPrice.multiply(BigDecimal.valueOf(smallDogs))
                .add(bigDogPrice.multiply(BigDecimal.valueOf(bigDogs)));
    }
}
