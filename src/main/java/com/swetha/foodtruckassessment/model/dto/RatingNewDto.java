package com.swetha.foodtruckassessment.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
@Builder
public class RatingNewDto {
    @NotNull(message = "must not be  null")
    private Long truckId;
    @NotNull(message = "must not be null")
    @Range(min = 1, max = 5, message = "must be between 1 and 5")
    private Integer rating;
}
