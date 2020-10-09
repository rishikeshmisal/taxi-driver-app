package com.loginext.taxidriver.service;

import com.loginext.taxidriver.entity.Driver;
import com.loginext.taxidriver.entity.DriverAssignmentStatus;
import com.loginext.taxidriver.entity.User;
import com.loginext.taxidriver.enums.RideStatus;
import com.loginext.taxidriver.exception.BadRequestException;
import com.loginext.taxidriver.exception.TaxiDriverApplicationException;
import com.loginext.taxidriver.helper.Constants;
import com.loginext.taxidriver.helper.ErrorMessages;
import com.loginext.taxidriver.helper.Utils;
import com.loginext.taxidriver.models.DriverAssignmentResponse;
import com.loginext.taxidriver.models.Location;
import com.loginext.taxidriver.models.UserRequest;
import com.loginext.taxidriver.ras.DriverResourceAccessor;
import com.loginext.taxidriver.repository.IDriverAssignmentStatus;
import com.loginext.taxidriver.repository.IDriverRepo;
import com.loginext.taxidriver.repository.IUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private IUserRepo iUserRepo;

    @Autowired
    private IDriverAssignmentStatus iDriverAssignmentStatus;

    @Autowired
    private IDriverRepo iDriverRepo;

    @Autowired
    private DriverResourceAccessor driverResourceAccessor;

    /**
     * register user with the given username and optional current location
     * @param userRequest
     * @return
     */

    @Transactional
    public User registerUser(UserRequest userRequest){

        String userName = userRequest.getUserName();

        if(iUserRepo.findByUserName(userName).isPresent()){
            throw new BadRequestException(ErrorMessages.USER_NAME_EXISTS);
        }

        User user = new User();
        user.setUserName(userName.toLowerCase());
        user.setLatitude(userRequest.getCurrentLatitude());
        user.setLongitude(userRequest.getCurrentLongitude());
        return iUserRepo.save(user);
    }

    /**
     * assign driver to the requesting user. Additionally, create user if user does not exist.
     * Current location is mandatory
     * @param userRequest
     * @param username
     * @return
     * @throws Exception
     */

    @Transactional
    public DriverAssignmentResponse assignDriver(UserRequest userRequest, String username) throws Exception {

        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(userRequest.getUserName())){
            throw new BadRequestException(ErrorMessages.USER_NAME_EMPTY);
        }

        if(!username.equals(userRequest.getUserName())){
            throw new BadRequestException(ErrorMessages.USER_NAME_MISMATCH);
        }

        User user = new User();

        DriverAssignmentResponse driverAssignmentResponse = new DriverAssignmentResponse();

        Optional<User> optionalUser = iUserRepo.findByUserName(userRequest.getUserName());
        if(optionalUser.isPresent()){
            user = optionalUser.get();
            DriverAssignmentStatus driverAssignmentStatus = iDriverAssignmentStatus.findByUserAndStatusIn(user,new ArrayList<RideStatus>(){
                {
                    add(RideStatus.ON_THE_WAY);
                    add(RideStatus.ASSIGNED);
                }});
            if(driverAssignmentStatus!=null){
                driverAssignmentResponse.setMessage(String.format(Constants.DRIVER_ALREADY_ASSIGNED,driverAssignmentStatus.getStatus().toString()));
                return driverAssignmentResponse;
            }
        }else{
            user.setUserName(userRequest.getUserName());
        }

        if(user.getLatitude()==null || user.getLongitude()==null){

            if(userRequest.getCurrentLatitude()==null || userRequest.getCurrentLongitude()==null||
                    !Utils.validateCoordinateRequest(userRequest)){
                throw new BadRequestException(ErrorMessages.LAT_LONG_INCORRECT);
            }
            user.setLongitude(userRequest.getCurrentLongitude());
            user.setLatitude(userRequest.getCurrentLatitude());
        }

        user = iUserRepo.save(user);


        List<Object[]> result = iDriverRepo.getClosestFreeDriver(user.getId());

        if(result==null || result.isEmpty()){
            driverAssignmentResponse.setMessage(String.format(Constants.DRIVER_NON_ASSIGNMENT_MSG,username));
        }else{
            Object[] row = result.get(0);

            driverAssignmentResponse.setDriverName(row[1].toString());
            Location location = new Location();
            location.setLatitude(new BigDecimal(row[3].toString()));
            location.setLongitude(new BigDecimal(row[4].toString()));
            BigDecimal distance = new BigDecimal(row[5].toString());
            driverAssignmentResponse.setMessage(String.format(Constants.DRIVER_ASSIGNMENT_MSG,driverAssignmentResponse.getDriverName(),
                    driverAssignmentResponse.getDriverName(),distance.setScale(2,BigDecimal.ROUND_HALF_EVEN)));
            driverAssignmentResponse.setLocation(location);

            if(!updateDriverAssignment(result,user)){
                throw new TaxiDriverApplicationException(ErrorMessages.SOMETHING_WRONG);
            }

        }
        return driverAssignmentResponse;

    }

    private boolean updateDriverAssignment(List<Object[]> result, User user) {

        DriverAssignmentStatus driverAssignmentStatus = new DriverAssignmentStatus();
        driverAssignmentStatus.setUser(user);
        Optional<Driver> optionalDriver = iDriverRepo.findById((Integer) result.get(0)[0]);
        Driver driver = optionalDriver.orElseGet(Driver::new);
        driverAssignmentStatus.setDriver(driver);
        driverAssignmentStatus.setStatus(RideStatus.ASSIGNED);

        try{
            driver.setFreeStatus(false);
            driverResourceAccessor.saveDriver(driver);
            driverResourceAccessor.saveDriverAssignment(driverAssignmentStatus);
        }catch (Exception e){

            return false;
        }

        return true;
    }

}
