package com.swetha.foodtruckassessment.service;

import com.swetha.foodtruckassessment.model.Truck;
import com.swetha.foodtruckassessment.model.dto.TruckRatingDTO;
import com.swetha.foodtruckassessment.exception.NotFoundException;
import com.swetha.foodtruckassessment.repository.TruckRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FoodtruckServiceTest {

    @InjectMocks
    FoodtruckService foodtruckService;

    @Mock
    TruckRepository truckRepository;

    @Test
    public void testFindAllTrucksOrderByAvgRatingDesc() {
        Page<TruckRatingDTO> trucks = new PageImpl<>(Arrays.asList(new TruckRatingDTO(), new TruckRatingDTO()));
        when(truckRepository.findAllOrderByAvgRatingDesc(PageRequest.of(0, 10))).thenReturn(trucks);
        Page<TruckRatingDTO> result = foodtruckService.findAllTrucksOrderByAvgRatingDesc(10, 0);
        assertEquals(trucks.getTotalElements(), result.getTotalElements());
    }

    @Test
    public void testFindById() throws NotFoundException {
        Optional<Truck> optionalTruck = Optional.of(new Truck());
        when(truckRepository.findById(anyLong())).thenReturn(optionalTruck);
        Truck result = foodtruckService.findById(1L);
        assertEquals(optionalTruck.get(), result);
    }

    @Test
    public void testFindById_notFound() throws NotFoundException {
        when(truckRepository.findById(anyLong())).thenReturn(Optional.empty());
        long foodTruckId = 1L;
        NotFoundException notFoundException = assertThrows(NotFoundException.class,
                () -> foodtruckService.findById(foodTruckId));
        assertTrue(notFoundException.getMessage().contains(String.format("Truck with id %s not exists!", foodTruckId)));
    }
}