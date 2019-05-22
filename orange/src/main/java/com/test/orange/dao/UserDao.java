package com.test.orange.dao;

import com.test.orange.entity.User;

import java.util.List;

/**
 * Created by yangkang on 2019/5/21
 */
public interface UserDao {
    List<User> getUserList();

    User getUserById(String id);

    void saveUser(User user);

    void updateUser(User usesr);

    void deleteUser(User user);
}
