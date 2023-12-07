package com.ra.model.dao;

import com.ra.model.entity.User;
import com.ra.utils.ConnectionDatabase;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserDaoImpl implements UserDao{
    @Override
    public User checkUser(String email) {
        Connection connection= ConnectionDatabase.openConnection();
        User user=new User();
        try {
            CallableStatement callableStatement=connection.prepareCall("CALL PROC_CHECK_EMAIL(?)");
            callableStatement.setString(1,email);
            ResultSet resultSet=callableStatement.executeQuery();
            while (resultSet.next()){
                user.setId(resultSet.getInt("id"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return user;
    }

    @Override
    public boolean save(User user) {
        Connection connection=ConnectionDatabase.openConnection();
        try {
            String hasPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
            CallableStatement callableStatement=connection.prepareCall("CALL PROC_CREATE_USER(?,?,?)");

            callableStatement.setString(1,user.getFirstName());
            callableStatement.setString(2,user.getEmail());
            callableStatement.setString(3,hasPassword);
            int check=callableStatement.executeUpdate();
            if (check>0){
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return false;
    }

    @Override
    public User findById(Integer id) {
        return null;
    }

    @Override
    public User checkLogin(String email, String password) {
        User user = checkUser(email);
        if (user != null && BCrypt.checkpw(password, user.getPassword())) {
            return user;
        }
        return null;
    }

}
