package com.swetha.foodtruckassessment.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swetha.foodtruckassessment.model.dto.UserNewDto;
import com.swetha.foodtruckassessment.container.ContainersEnvironment;
import com.swetha.foodtruckassessment.exception.FieldMessage;
import com.swetha.foodtruckassessment.exception.ValidationError;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest extends ContainersEnvironment {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Test inserting a new user with valid data")
    public void testInsertNewUserWithValidData() throws Exception {
        UserNewDto userNewDto = new UserNewDto();
        userNewDto.setName("John Doe");
        userNewDto.setEmail("john.doe@test.com");
        userNewDto.setPassword("mypassword");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UserNewDto> request = new HttpEntity<>(userNewDto, headers);

        ResponseEntity<Void> response = restTemplate.exchange(
                "/api/v1/user",
                HttpMethod.POST,
                request,
                Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    @DisplayName("Test inserting a new user with invalid data")
    public void testInsertNewUserWithInvalidData() throws Exception {
        UserNewDto userNewDto = new UserNewDto();
        userNewDto.setName("");
        userNewDto.setEmail("invalidemail");
        userNewDto.setPassword(null);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UserNewDto> request = new HttpEntity<>(userNewDto, headers);

        ResponseEntity<String> response = restTemplate.exchange("/api/v1/user", HttpMethod.POST, request, String.class);
        ValidationError validationError = objectMapper.readValue(response.getBody(), ValidationError.class);
        assertThat(validationError).isNotNull();
        assertThat(validationError.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(validationError.getMsg()).isEqualTo("Validation error.");

        List<FieldMessage> errors = validationError.getErrors();
        assertThat(errors).hasSize(3);

        List<String> expectedErrors = Arrays.asList(
                "must not be blank",
                "must be a well-formed email address",
                "must not be blank"
        );

        List<String> actualErrors = errors.stream()
                .map(FieldMessage::getMessage)
                .toList();

        assertTrue(actualErrors.containsAll(expectedErrors));
    }

    @Test
    @DisplayName("Should return conflict status code when try to create user that already exists")
    public void shouldReturnConflictWhenCreateUserThatAlreadyExists() throws JsonProcessingException {
        UserNewDto newUser = new UserNewDto();
        newUser.setName("User Test");
        newUser.setEmail("userexists@gmail.com");
        newUser.setPassword("password");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<UserNewDto> request = new HttpEntity<>(newUser, headers);

        restTemplate.postForEntity("/api/v1/user", request, Void.class);

        ResponseEntity<String> response = restTemplate.exchange("/api/v1/user", HttpMethod.POST, request, String.class);
        ValidationError validationError = objectMapper.readValue(response.getBody(), ValidationError.class);
        assertThat(validationError.getStatus()).isEqualTo(HttpStatus.CONFLICT.value());
        assertThat(validationError.getMsg()).isEqualTo("User already exists!");
    }
}
