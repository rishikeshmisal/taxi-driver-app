package com.loginext.taxidriver.models;

import com.loginext.taxidriver.enums.FreeStatus;

public class DriverStatus {

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public FreeStatus getStatus() {
        return status;
    }

    public void setStatus(FreeStatus status) {
        this.status = status;
    }

    String driverName;
    String userName;
    FreeStatus status;
}
