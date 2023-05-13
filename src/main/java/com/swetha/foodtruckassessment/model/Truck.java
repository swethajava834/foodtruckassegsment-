package com.swetha.foodtruckassessment.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "trucks")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Truck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String applicant;
    @Column(name = "facility_type")
    private String facilityType;
    @Column(name = "location_description")
    private String locationDescription;
    private String address;
    @Column(name = "food_items")
    private String foodItems;
    private Double latitude;
    private Double longitude;
    @OneToMany(mappedBy = "truck")
    private List<Rating> ratings;
}
