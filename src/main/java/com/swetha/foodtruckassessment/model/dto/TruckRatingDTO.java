package com.swetha.foodtruckassessment.model.dto;


public class TruckRatingDTO {

    private Long id;
    private String applicant;
    private String facilityType;
    private String locationDescription;
    private String address;
    private String foodItems;
    private Double latitude;
    private Double longitude;
    private Double avgRating;


    public TruckRatingDTO(Long id, String applicant, String facilityType, String locationDescription, String address, String foodItems, Double latitude, Double longitude, Double avgRating) {
        this.id = id;
        this.applicant = applicant;
        this.facilityType = facilityType;
        this.locationDescription = locationDescription;
        this.address = address;
        this.foodItems = foodItems;
        this.latitude = latitude;
        this.longitude = longitude;
        this.avgRating = avgRating;
    }

    public TruckRatingDTO(){

    }

    public TruckRatingDTO(Long id, String applicant, Double avgRating) {
        this.id = id;
        this.applicant = applicant;
        this.avgRating = avgRating;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public String getFacilityType() {
        return facilityType;
    }

    public void setFacilityType(String facilityType) {
        this.facilityType = facilityType;
    }

    public String getLocationDescription() {
        return locationDescription;
    }

    public void setLocationDescription(String locationDescription) {
        this.locationDescription = locationDescription;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFoodItems() {
        return foodItems;
    }

    public void setFoodItems(String foodItems) {
        this.foodItems = foodItems;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(Double avgRating) {
        this.avgRating = avgRating;
    }
}
