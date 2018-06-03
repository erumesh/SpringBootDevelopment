package com.designthink.service;


import java.util.List;

import com.designthink.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserService extends CrudRepository<User, Long> {
    public List<User> findAllBy();
    public User findById(long id);
}
