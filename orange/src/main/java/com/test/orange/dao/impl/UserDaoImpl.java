package com.test.orange.dao.impl;

import com.test.orange.dao.UserDao;
import com.test.orange.entity.User;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangkang on 2019/5/21
 */
@Repository
public class UserDaoImpl implements UserDao{
    @Resource
    private DataSource dataSource;

    @Override
    public List<User> getUserList() {
        String sql = "select * from user;";
        System.out.println(sql);
        List<User> users = new ArrayList<>();
        Connection conn = null;
        try{
            conn = dataSource.getConnection();
            Statement state = conn.createStatement();
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getString("id"));
                user.setName(rs.getString("name"));
                user.setSex(rs.getInt("sex"));
                user.setAge(rs.getInt("age"));
                user.setPhone(rs.getString("phone"));
                user.setEmail(rs.getString("email"));
                user.setUpdateTime(rs.getDate("updateTime"));
                user.setCreateTime(rs.getDate("createTime"));

                users.add(user);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return users;
    }

    @Override
    public User getUserById(String id) {
        String sql = "select * from user where id = '" + id + "';";
        System.out.println(sql);
        User user = null;
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            Statement state = conn.createStatement();
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                user = new User();
                user.setId(rs.getString("id"));
                user.setName(rs.getString("name"));
                user.setSex(rs.getInt("sex"));
                user.setAge(rs.getInt("age"));
                user.setPhone(rs.getString("phone"));
                user.setEmail(rs.getString("email"));
                user.setUpdateTime(rs.getDate("updateTime"));
                user.setCreateTime(rs.getDate("createTime"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return user;
    }

    @Override
    public void saveUser(User user) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sql = "insert into user values('" + user.getId() + "','" + user.getName() + "'," + user.getSex() +
                "," + user.getAge() + ",'" + user.getPhone() + "','" + user.getEmail() + "','" + user.getAddress() +
                "','" + df.format(user.getUpdateTime()) + "','" + df.format(user.getCreateTime())+ "');";
        System.out.println(sql);
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            Statement state = conn.createStatement();
            state.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void updateUser(User usesr) {

    }

    @Override
    public void deleteUser(User user) {
        String id = user.getId();
        if (id == null){
            System.out.println("cannot find user for no valid id");
            return;
        }
        String sql = "delete from user where id='" + user.getId() + "';";
        System.out.println(sql);
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            Statement state = conn.createStatement();
            state.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
