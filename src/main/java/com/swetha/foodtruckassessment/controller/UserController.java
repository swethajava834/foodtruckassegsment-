package com.swetha.foodtruckassessment.controller;

import com.swetha.foodtruckassessment.exception.UserAlreadyExistsException;
import com.swetha.foodtruckassessment.model.dto.UserNewDto;
import com.swetha.foodtruckassessment.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @PostMapping
    @Operation(
            summary = "Inserts a new User",
            description = "Inserts a new User.",
            tags = { "User" },
            responses = {
                    @ApiResponse(
                            description = "Created",
                            responseCode = "201",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserNewDto.class))
                    ),
                    @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Conflict", responseCode = "409", content = @Content),
                    @ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
            }
    )
    public ResponseEntity<Void> insert(@Valid @RequestBody UserNewDto userNewDto) throws UserAlreadyExistsException {
        userService.insert(userService.fromDto(userNewDto));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
