package com.ra.model.dao;

import com.ra.model.entity.User;

public interface UserDao {
    User checkUser(String email);
    boolean save(User user);
    User findById(Integer id);
    User checkLogin(String email ,String password);

}
