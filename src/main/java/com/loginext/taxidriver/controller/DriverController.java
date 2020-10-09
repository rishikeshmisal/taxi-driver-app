package com.loginext.taxidriver.controller;

import com.loginext.taxidriver.helper.Constants;
import com.loginext.taxidriver.models.DriverRegistration;
import com.loginext.taxidriver.service.DriverService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = Constants.BASE_MAPPING)
public class DriverController {

    @Autowired
    private DriverService driverService;

    @ApiOperation(
            value = "driver registration",
            produces = "application/json",
            notes = "This route registers a driver")
    @PostMapping(value = "/driver/register")
    public ResponseEntity driverRegistration(
            @ApiParam(name = "DriverRegistration body ",value = "to get driver registration details")
            @RequestBody DriverRegistration driverRegistration) {
        return new ResponseEntity(driverService.registerDriver(driverRegistration),HttpStatus.OK);
    }

    @ApiOperation(
            value = "drivers statuses",
            produces = "application/json",
            notes = "This route gets all drivers status")
    @GetMapping(value = "/drivers/status")
    public ResponseEntity allDriverStatus(
            @ApiParam(name = "Map of query param ",value = "to get request params of the http request")
                   @RequestParam Map<String,String> allRequestParams) {
        return new ResponseEntity(driverService.getDriversStatus(allRequestParams),HttpStatus.OK);
    }




}
