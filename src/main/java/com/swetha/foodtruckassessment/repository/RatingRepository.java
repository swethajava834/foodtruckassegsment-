package com.swetha.foodtruckassessment.repository;

import com.swetha.foodtruckassessment.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating,Long> {
}
