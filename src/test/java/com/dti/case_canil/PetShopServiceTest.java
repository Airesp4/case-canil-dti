package com.dti.case_canil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dti.case_canil.dto.BestPetShopDTO;
import com.dti.case_canil.dto.BestPetShopRequest;
import com.dti.case_canil.model.PetShop;
import com.dti.case_canil.repository.PetShopRepository;
import com.dti.case_canil.service.PetShopService;

class PetShopServiceTest {

    @Mock
    private PetShopRepository petShopRepository;

    @InjectMocks
    private PetShopService petShopService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindBestPetShop_weekday() {
        PetShop petShop1 = new PetShop();
        petShop1.setName("PetShop A");
        petShop1.setDistanceInMeters(1000);
        petShop1.setWeekdayPriceSmallDog(BigDecimal.valueOf(20));
        petShop1.setWeekdayPriceBigDog(BigDecimal.valueOf(40));
        petShop1.setWeekendPriceSmallDog(BigDecimal.valueOf(25));
        petShop1.setWeekendPriceBigDog(BigDecimal.valueOf(50));

        PetShop petShop2 = new PetShop();
        petShop2.setName("PetShop B");
        petShop2.setDistanceInMeters(500);
        petShop2.setWeekdayPriceSmallDog(BigDecimal.valueOf(22));
        petShop2.setWeekdayPriceBigDog(BigDecimal.valueOf(38));
        petShop2.setWeekendPriceSmallDog(BigDecimal.valueOf(28));
        petShop2.setWeekendPriceBigDog(BigDecimal.valueOf(48));

        when(petShopRepository.findAll()).thenReturn(List.of(petShop1, petShop2));

        BestPetShopRequest request = new BestPetShopRequest(LocalDate.of(2025, 8, 5), 2, 1); // Tuesday (weekday)
        BestPetShopDTO result = petShopService.findBestPetShop(request);

        assertEquals("PetShop A", result.name());
        // Total price: (2*20) + (1*40) = 80
        assertEquals(BigDecimal.valueOf(80), result.totalPrice());
    }

    @Test
    void testFindBestPetShop_weekend() {
        PetShop petShop1 = new PetShop();
        petShop1.setName("PetShop A");
        petShop1.setDistanceInMeters(1000);
        petShop1.setWeekdayPriceSmallDog(BigDecimal.valueOf(20));
        petShop1.setWeekdayPriceBigDog(BigDecimal.valueOf(40));
        petShop1.setWeekendPriceSmallDog(BigDecimal.valueOf(25));
        petShop1.setWeekendPriceBigDog(BigDecimal.valueOf(50));

        PetShop petShop2 = new PetShop();
        petShop2.setName("PetShop B");
        petShop2.setDistanceInMeters(500);
        petShop2.setWeekdayPriceSmallDog(BigDecimal.valueOf(22));
        petShop2.setWeekdayPriceBigDog(BigDecimal.valueOf(38));
        petShop2.setWeekendPriceSmallDog(BigDecimal.valueOf(28));
        petShop2.setWeekendPriceBigDog(BigDecimal.valueOf(48));

        when(petShopRepository.findAll()).thenReturn(List.of(petShop1, petShop2));

        BestPetShopRequest request = new BestPetShopRequest(LocalDate.of(2025, 8, 3), 1, 2); // Sunday (weekend)
        BestPetShopDTO result = petShopService.findBestPetShop(request);

        // PetShop A: (1*25) + (2*50) = 125
        // PetShop B: (1*28) + (2*48) = 124 (mais barato e distância menor)
        // Resultado esperado: PetShop B
        
        assertEquals("PetShop B", result.name());
        assertEquals(BigDecimal.valueOf(124), result.totalPrice());
    }

    @Test
    void testFindBestPetShop_noPetShops() {
        when(petShopRepository.findAll()).thenReturn(List.of());

        BestPetShopRequest request = new BestPetShopRequest(LocalDate.now(), 1, 1);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            petShopService.findBestPetShop(request);
        });

        assertEquals("Nenhum pet shop encontrado.", exception.getMessage());
    }
}
