package com.loginext.taxidriver.helper;

public interface ErrorMessages {

    String USER_NAME_EXISTS = "user-name already exists!";

    String USER_NAME_EMPTY = "username field can't be empty";

    String USER_NAME_MISMATCH = "username mismatch in request body and path variable";

    String  LAT_LONG_INCORRECT = "incorrect or missing latitude/longitude value(s)";

    String SOMETHING_WRONG = "Something went wrong! Please try again later";

    String USER_NOT_FOUND = "User not found";


}
