package com.ra.model.service;

import com.ra.model.dao.UserDao;
import com.ra.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserSerViceImpl implements UserService{
    @Autowired
    UserDao userDao;
    @Override
    public User checkUser(String email) {
        return userDao.checkUser(email);
    }

    @Override
    public boolean save(User user) {
        return userDao.save(user);
    }

    @Override
    public User findById(Integer id) {
        return null;
    }

    @Override
    public User checkLogin(String email, String password) {
        return userDao.checkLogin(email,password);
    }
}
