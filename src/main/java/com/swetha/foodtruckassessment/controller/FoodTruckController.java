package com.swetha.foodtruckassessment.controller;

import com.swetha.foodtruckassessment.model.Truck;
import com.swetha.foodtruckassessment.model.dto.TruckRatingDTO;
import com.swetha.foodtruckassessment.service.FoodtruckService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/foodtruck")
@AllArgsConstructor
@Slf4j
public class FoodTruckController {

    private FoodtruckService foodtruckService;

    @GetMapping
    @Operation(
            summary = "Finds all foodtrucks",
            description = "Finds all foodtrucks and order by rating.",
            tags = { "Trucks" },
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
            }
    )
    public ResponseEntity<Page<TruckRatingDTO>> findAllTrucksOrderByAvgRatingDesc(
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "page", defaultValue = "0") int page) {
        Page<TruckRatingDTO> truckList = foodtruckService.findAllTrucksOrderByAvgRatingDesc(size, page);
        return ResponseEntity.ok(truckList);
    }
    @GetMapping("/getFoodTruck/{name}")
    @Operation(
            summary = "Finds all foodtrucks by name",
            description = "Finds all foodtrucks Name.",
            tags = { "Trucks" },
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
            }
    )
    public List<Truck> getFoodTrucksByName(@PathVariable String name){
       return foodtruckService.findFoodTruckByName(name);
    }

}
