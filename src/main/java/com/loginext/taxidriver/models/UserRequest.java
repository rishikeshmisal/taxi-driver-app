package com.loginext.taxidriver.models;

import com.fasterxml.jackson.annotation.JsonInclude;

public class UserRequest {

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Double getCurrentLatitude() {
        return currentLatitude;
    }

    public void setCurrentLatitude(Double currentLatitude) {
        this.currentLatitude = currentLatitude;
    }

    public Double getCurrentLongitude() {
        return currentLongitude;
    }

    public void setCurrentLongitude(Double currentLongitude) {
        this.currentLongitude = currentLongitude;
    }

    public Double getDestinationLatitude() {
        return destinationLatitude;
    }

    public void setDestinationLatitude(Double destinationLatitude) {
        this.destinationLatitude = destinationLatitude;
    }

    public Double getDestinationLongitude() {
        return destinationLongitude;
    }

    public void setDestinationLongitude(Double destinationLongitude) {
        this.destinationLongitude = destinationLongitude;
    }

    private String userName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double currentLatitude;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double currentLongitude;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double destinationLatitude;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double destinationLongitude;


}
