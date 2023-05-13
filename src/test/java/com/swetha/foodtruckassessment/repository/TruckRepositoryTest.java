package com.swetha.foodtruckassessment.repository;

import com.swetha.foodtruckassessment.model.Rating;
import com.swetha.foodtruckassessment.model.Truck;
import com.swetha.foodtruckassessment.model.User;
import com.swetha.foodtruckassessment.model.dto.TruckRatingDTO;
import com.swetha.foodtruckassessment.FoodTruckAssessmentApplication;
import com.swetha.foodtruckassessment.container.ContainersEnvironment;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = FoodTruckAssessmentApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TruckRepositoryTest extends ContainersEnvironment {

    @Autowired
    private TruckRepository truckRepository;
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RatingRepository ratingRepository;
    

    @Test
    void testFindAllOrderByAvgRatingDesc() {

        User user = createUser();
        // Create and save a few trucks
        Truck truck1 = new Truck(null, "Applicant 1", "Facility Type 1", "Location Description 1",
                "Address 1", "Food Items 1", 40.7128, -74.0060, null);

        Truck truck2 = new Truck(null, "Applicant 2", "Facility Type 2", "Location Description 2",
                "Address 2", "Food Items 2", 37.7749, -122.4194, null);

        Truck truck3 = new Truck(null, "Applicant 3", "Facility Type 3", "Location Description 3",
                "Address 3", "Food Items 3", 34.0522, -118.2437, null);

        Truck truck4 = new Truck(null, "Applicant 4", "Facility Type 4", "Location Description 4",
                "Address 4", "Food Items 4", 41.8781, -87.6298, null);

        Truck truck5 = new Truck(null, "Applicant 5", "Facility Type 5", "Location Description 5",
                "Address 5", "Food Items 5", 51.5074, -0.1278, null);

        List<Truck> trucks = truckRepository.saveAll(List.of(truck1, truck2, truck3, truck4, truck5));

        createRating(user, trucks.get(4),5);
        createRating(user, trucks.get(3),4);

        Pageable pageable = PageRequest.of(0, 10);
        List<TruckRatingDTO> trucksDtos = truckRepository.findAllOrderByAvgRatingDesc(pageable).getContent();

        // Verify that the trucks are ordered by their average rating in descending order
        assertNotNull(trucksDtos);
        assertEquals(trucks.size(), trucksDtos.size());
        assertTrue(trucks.stream()
                .allMatch( truck -> trucksDtos.stream().anyMatch(dto -> dto.getId().equals(truck.getId()))));
        assertEquals(trucks.get(4).getId(), trucksDtos.get(0).getId());
        assertEquals(trucks.get(3).getId(), trucksDtos.get(1).getId());
    }

    private User createUser() {
        return userRepository.save(User.builder()
                .email("email@email.com")
                .name("Name")
                .password("password")
                .build());
    }

    private Rating createRating(User user, Truck truck, Integer ratingStars ){
        Rating rating = Rating.builder()
                .user(user)
                .truck(truck)
                .rating(ratingStars)
                .build();
        return ratingRepository.save(rating);
    }
    
}