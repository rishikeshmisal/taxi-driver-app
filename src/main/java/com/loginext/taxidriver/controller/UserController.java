package com.loginext.taxidriver.controller;

import com.loginext.taxidriver.helper.Constants;
import com.loginext.taxidriver.models.UserRequest;
import com.loginext.taxidriver.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping(value = Constants.BASE_MAPPING)
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(
            value = "user registration",
            produces = "application/json",
            notes = "This route registers a user")
    @RequestMapping(value = "/user/register",method = RequestMethod.POST)
    public ResponseEntity userRegistration(
            @ApiParam(name = "UserRequest body ",value = "to get user request details")
                    @RequestBody UserRequest userRequest) {
        return new ResponseEntity(userService.registerUser(userRequest),HttpStatus.OK);
    }

    @ApiOperation(
            value = "assign driver to user",
            produces = "application/json",
            notes = "This route assigns a driver to a user")
    @RequestMapping(value = "/user/{username}/assign-driver",method = RequestMethod.POST)
    public ResponseEntity assignDriverToUser(
            @ApiParam(name = "UserRequest body ",value = "to get user request details")
            @RequestBody UserRequest userRequest, @ApiParam(name = "username", value = "to get current user's username")
    @PathVariable("username")String username) throws Exception {
        return new ResponseEntity(userService.assignDriver(userRequest,username),HttpStatus.OK);
    }

}
