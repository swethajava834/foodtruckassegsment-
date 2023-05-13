package com.swetha.foodtruckassessment.service;

import com.swetha.foodtruckassessment.exception.NotFoundException;
import com.swetha.foodtruckassessment.model.Truck;
import com.swetha.foodtruckassessment.model.dto.TruckRatingDTO;
import com.swetha.foodtruckassessment.repository.TruckRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class FoodtruckService {

    private TruckRepository truckRepository;


    public Page<TruckRatingDTO> findAllTrucksOrderByAvgRatingDesc(int size, int page) {
        return truckRepository.findAllOrderByAvgRatingDesc(PageRequest.of(page,size));
    }

    public Truck findById (Long id) throws NotFoundException {
        Optional<Truck> truck = truckRepository.findById(id);
        if(truck.isEmpty()){
            throw new NotFoundException(String.format("Truck with id %s not exists!",id));
        }

        return truck.get();
    }

    public List<Truck> findFoodTruckByName(String name){
        return truckRepository.findByApplicant(name);
    }
}
