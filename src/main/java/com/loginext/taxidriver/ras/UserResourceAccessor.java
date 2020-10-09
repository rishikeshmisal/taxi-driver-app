package com.loginext.taxidriver.ras;

import com.loginext.taxidriver.entity.User;
import com.loginext.taxidriver.repository.IUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UserResourceAccessor {

    @Autowired
    private IUserRepo iUserRepo;

    @Transactional
    public User saveUser(User user){
        return iUserRepo.save(user);
    }


}
