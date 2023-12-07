package com.ra.model.service;

import com.ra.model.entity.User;

public interface UserService {
    User checkUser(String email);
    boolean save(User user);
    User findById(Integer id);
    User checkLogin(String email ,String password);
}
