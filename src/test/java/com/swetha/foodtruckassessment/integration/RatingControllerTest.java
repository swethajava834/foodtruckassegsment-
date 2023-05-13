package com.swetha.foodtruckassessment.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swetha.foodtruckassessment.exception.UserAlreadyExistsException;
import com.swetha.foodtruckassessment.service.UserService;
import com.swetha.foodtruckassessment.container.ContainersEnvironment;
import com.swetha.foodtruckassessment.exception.NotFoundException;
import com.swetha.foodtruckassessment.exception.ValidationError;
import com.swetha.foodtruckassessment.model.Rating;
import com.swetha.foodtruckassessment.model.Truck;
import com.swetha.foodtruckassessment.model.User;
import com.swetha.foodtruckassessment.model.dto.RatingNewDto;
import com.swetha.foodtruckassessment.repository.RatingRepository;
import com.swetha.foodtruckassessment.repository.TruckRepository;
import com.swetha.foodtruckassessment.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RatingControllerTest extends ContainersEnvironment {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String API_V1_RATING = "/api/v1/rating";
    private static final String PASSWORLD = "test";
    private static final String EMAIL = "test@test.com";

    @Autowired
    private TruckRepository foodtruckRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RatingRepository ratingRepository;

    private Truck foodtruck;
//    private User user;

    @BeforeEach
    public void setUp() throws UserAlreadyExistsException {

        foodtruck = foodtruckRepository.save(Truck.builder()
                .applicant("Food Truck Company")
                .facilityType("Truck")
                .locationDescription("Nearby the park")
                .address("123 Main St.")
                .foodItems("Burgers, fries, shakes")
                .latitude(37.7749)
                .longitude(-122.4194)
                .build());

        userService.insert(User.builder()
                .email(EMAIL)
                .password(PASSWORLD)
                .name("Test User")
                .build());
    }

    @AfterEach
    public void tearDown() {
        ratingRepository.deleteAll();
        foodtruckRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void shouldRateFoodTruck() throws NotFoundException {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(EMAIL, PASSWORLD);
        RatingNewDto ratingNewDto = RatingNewDto.builder()
                .truckId(foodtruck.getId())
                .rating(5)
                .build();

        ResponseEntity<String> responseEntity = restTemplate.exchange(API_V1_RATING, HttpMethod.POST,
                new HttpEntity<>(ratingNewDto, headers), String.class);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(1, ratingRepository.findAll().size());
        Rating rating = ratingRepository.findAll().get(0);
        assertEquals(5, rating.getRating());
    }

    @Test
    public void shouldReturnNotFoundWhenFoodTruckDoesNotExist() throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(EMAIL, PASSWORLD);
        RatingNewDto ratingNewDto = RatingNewDto.builder()
                .truckId(0L)
                .rating(5)
                .build();

        ResponseEntity<String> response = restTemplate.exchange(API_V1_RATING, HttpMethod.POST,
                new HttpEntity<>(ratingNewDto, headers), String.class);

        ValidationError validationError = objectMapper.readValue(response.getBody(), ValidationError.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertThat(validationError.getMsg())
                .isEqualTo(String.format("Truck with id %s not exists!", ratingNewDto.getTruckId()));
        assertEquals(0, ratingRepository.findAll().size());
    }

    @Test
    public void shouldReturnBadRequestWhenInvalidRating() throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(EMAIL, PASSWORLD);
        RatingNewDto ratingNewDto = RatingNewDto.builder()
                .truckId(foodtruck.getId())
                .rating(0)
                .build();

        ResponseEntity<String> response = restTemplate.exchange(API_V1_RATING, HttpMethod.POST,
                new HttpEntity<>(ratingNewDto, headers), String.class);
        ValidationError validationError = objectMapper.readValue(response.getBody(), ValidationError.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertThat(validationError.getErrors().get(0).getMessage()).isEqualTo("must be between 1 and 5");
    }
}
