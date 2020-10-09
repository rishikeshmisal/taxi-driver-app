package com.loginext.taxidriver.repository;

import com.loginext.taxidriver.entity.DriverAssignmentStatus;
import com.loginext.taxidriver.entity.User;
import com.loginext.taxidriver.enums.RideStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDriverAssignmentStatus extends JpaRepository<DriverAssignmentStatus,Integer> {

    DriverAssignmentStatus findByUserAndStatusIn(User user, List<RideStatus> rideStatus);
}
