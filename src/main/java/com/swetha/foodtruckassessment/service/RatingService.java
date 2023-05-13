package com.swetha.foodtruckassessment.service;

import com.swetha.foodtruckassessment.model.Rating;
import com.swetha.foodtruckassessment.repository.RatingRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@SecurityRequirement(name = "foodtruckfinder")
@AllArgsConstructor
@Service
public class RatingService {

    private RatingRepository ratingRepository;

    public Rating insert (Rating rating){
        return ratingRepository.save(rating);
    }
}
