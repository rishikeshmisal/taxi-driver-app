package com.loginext.taxidriver.helper;

public interface Constants {

    String BASE_MAPPING = "/api/v1";

    String DRIVER_ASSIGNMENT_MSG = "Driver %s is assigned to pick you up. %s is currently %s km away from your current location.";

    String DRIVER_NON_ASSIGNMENT_MSG = "Sorry %s  we are unable to assign you a designated driver right now, as all of our driver partners are busy. " +
            "Please check again after sometime";

    String DRIVER_ALREADY_ASSIGNED = "You're ride is currently in %s status. We cannot assign you another driver.";

    String DRIVER_EXISTS = "Driver with name %s already exist";
}
