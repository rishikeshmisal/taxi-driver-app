package com.loginext.taxidriver.ras;

import com.loginext.taxidriver.entity.Driver;
import com.loginext.taxidriver.entity.DriverAssignmentStatus;
import com.loginext.taxidriver.enums.RideStatus;
import com.loginext.taxidriver.repository.IDriverAssignmentStatus;
import com.loginext.taxidriver.repository.IDriverRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class DriverResourceAccessor {


    @Autowired
    private IDriverAssignmentStatus iDriverAssignmentStatus;

    @Autowired
    private IDriverRepo iDriverRepo;

    @Transactional
    public void saveDriverAssignment(DriverAssignmentStatus driverAssignmentStatus){

        iDriverAssignmentStatus.save(driverAssignmentStatus);

    }

    @Transactional
    public void saveDriver(Driver driver){
        iDriverRepo.save(driver);
    }


    public List<Object[]> getAllDriverStatus(int page, int pageSize){

        List<String> list = new ArrayList<>();
        list.add(RideStatus.ON_THE_WAY.toString());
        list.add(RideStatus.ASSIGNED.toString());

        int offset = (page-1)*pageSize;

        return iDriverRepo.getAllDriverStatus(list, offset, pageSize);

    }


}
