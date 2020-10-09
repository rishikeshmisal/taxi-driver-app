package com.loginext.taxidriver.repository;

import com.loginext.taxidriver.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepo extends JpaRepository<User,Integer> {

    Optional<User> findByUserName(String userName);

}
