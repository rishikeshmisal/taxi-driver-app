package com.loginext.taxidriver.repository;

import com.loginext.taxidriver.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IDriverRepo extends JpaRepository<Driver,Integer> {

    @Query(value = "select d.id,d.driver_name ,d.free_status,d.latitude ,d.longitude," +
            " distance(d.latitude ,d.longitude ,u.latitude,u.longitude) as dist from driver d, " +
            "users u where u.id = :uid and d.free_status order by dist limit 1",nativeQuery = true)
    List<Object[]> getClosestFreeDriver(@Param("uid") Integer userId);

    Optional<Driver> findByDriverName(String name);

    @Query(value = "select res.driver_name, case when res.user_id is null then null else " +
            "(select u.user_name from users u where res.user_id=u.id) end ,res.free_status, res.count " +
            "from ( select d.driver_name ,das.user_id , d.free_status, count(1) over() " +
            "from driver d left join driver_assignment_status das on d.id = das.driver_id and " +
            "das.ride_status in :status offset :offset limit :limit) res", nativeQuery = true)
    List<Object[]> getAllDriverStatus(@Param("status")List<String> rideStatusList, @Param("offset") int offset, @Param("limit") int limit);
}
