package com.ra.model.dao;

import com.ra.model.entity.Category;
import com.ra.utils.ConnectionDatabase;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CategoryImpl implements CategoryDao{
    @Override
    public List<Category> findAll() {
        Connection connection= ConnectionDatabase.openConnection();
        List<Category>categoryList=new ArrayList<>();
        try {
            CallableStatement callableStatement=connection.prepareCall("CALL PROC_FIN_ALL");
            ResultSet resultSet=callableStatement.executeQuery();
            while (resultSet.next()){
                Category category=new Category();
                category.setCategoryId(resultSet.getInt("id"));
                category.setCategoryName(resultSet.getString("name"));
                category.setCategoryStatus(resultSet.getBoolean("status"));
                categoryList.add(category);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return categoryList;
    }

    @Override
    public boolean save(Category category) {
        Connection connection=ConnectionDatabase.openConnection();
        try {
            CallableStatement callableStatement=connection.prepareCall("CALL PROC_CREATE_CATE(?,?)");
            callableStatement.setString(1,category.getCategoryName());
            callableStatement.setBoolean(2,category.isCategoryStatus());
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
    public boolean update(Category category) {
        Connection connection=ConnectionDatabase.openConnection();
        try {
            CallableStatement callableStatement=connection.prepareCall("CALL PROC_UPDATE_CATE(?,?,?)");
            callableStatement.setInt(1,category.getCategoryId());
            callableStatement.setString(2,category.getCategoryName());
            callableStatement.setBoolean(3,category.isCategoryStatus());
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
    public Category findById(Integer id) {
        Connection connection=ConnectionDatabase.openConnection();
        Category category=new Category();
        try {
            CallableStatement callableStatement=connection.prepareCall("CALL PROC_FIND_BY_ID(?)");
            callableStatement.setInt(1,id);
            ResultSet resultSet=callableStatement.executeQuery();
            while (resultSet.next()){
                category.setCategoryId(resultSet.getInt("id"));
                category.setCategoryName(resultSet.getString("name"));
                category.setCategoryStatus(resultSet.getBoolean("status"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDatabase.closeConnection(connection);
        }

        return category;
    }

    @Override
    public void changeStatus(Integer id) {
        Connection connection=ConnectionDatabase.openConnection();
        try {
            CallableStatement callableStatement=connection.prepareCall("CALL PROC_CHANGE_STATUS(?)");
            callableStatement.setInt(1,id);
            int check=callableStatement.executeUpdate();
            if (check>0){
                System.out.println(id);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDatabase.closeConnection(connection);
        }
    }
}
