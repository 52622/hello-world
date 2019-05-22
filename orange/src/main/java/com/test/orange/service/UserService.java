package com.test.orange.service;

import com.test.orange.dao.UserDao;
import com.test.orange.entity.User;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.jws.soap.SOAPBinding;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by yangkang on 2019/5/21
 */
@Service
public class UserService {
    @Resource
    private UserDao userDao;

    public List<User> getUserList() {
        List<User> users = userDao.getUserList();

        return users;
    }

    public User getUserById(String id) {
        return userDao.getUserById(id);
    }

    public void saveUser(User user) {
        user.setId(UUID.randomUUID().toString().replaceAll("-",""));
        user.setUpdateTime(new Date());
        user.setCreateTime(new Date());

        userDao.saveUser(user);
    }

    public void deleteUser(User user) {
        userDao.deleteUser(user);
    }
}
