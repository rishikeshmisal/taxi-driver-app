package com.loginext.taxidriver.helper;

import com.loginext.taxidriver.models.DriverRegistration;
import com.loginext.taxidriver.models.UserRequest;

public class Utils {

    public static boolean validateCoordinateRequest(UserRequest userRequest) {

        return rangeCheck(userRequest.getCurrentLongitude(),-180.0d,180.0d) &&
                rangeCheck(userRequest.getCurrentLatitude(),-90.0d,90.0d);
    }

    private static boolean rangeCheck(Double value, Double v1, Double v2){

        return value>=v1 && value<=v2;

    }

    public static boolean validateCoordinateRequest(DriverRegistration driverRegistration) {
        return rangeCheck(driverRegistration.getCurrentLongitude(),-180.0d,180.0d) &&
                rangeCheck(driverRegistration.getCurrentLatitude(),-90.0d,90.0d);
    }
}
