package com.swetha.foodtruckassessment.service;

import com.swetha.foodtruckassessment.model.Rating;
import com.swetha.foodtruckassessment.model.Truck;
import com.swetha.foodtruckassessment.model.User;
import com.swetha.foodtruckassessment.repository.RatingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RatingServiceTest {

    @InjectMocks
    private RatingService ratingService;
    @Mock
    private RatingRepository ratingRepository;

    @Test
    void testInsert() {
        Rating ratingRequest = new Rating();
        Rating expetedRatingReturn = new Rating(1L, new User(), new Truck(),5);
        when(ratingRepository.save(any())).thenReturn(expetedRatingReturn);

        Rating ratingResponse = ratingService.insert(ratingRequest);
        assertNotNull(ratingResponse);
        assertNotNull(ratingResponse.getId());
    }
}