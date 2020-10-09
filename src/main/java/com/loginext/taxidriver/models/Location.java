package com.loginext.taxidriver.models;

import java.math.BigDecimal;

public class Location {

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    private BigDecimal latitude;
    private BigDecimal longitude;
}
