package com.test.orange.controller;

import com.test.orange.entity.User;
import com.test.orange.service.UserService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yangkang on 2019/5/21
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @RequestMapping("/list")
    public List<User> getUserList() {
        return userService.getUserList();
    }

    @RequestMapping("/add")
    public void addUser(@RequestBody User user) {
        userService.saveUser(user);
    }

    @RequestMapping("/{id}")
    public User getUserById(@PathVariable(name = "id") String id) {
        return userService.getUserById(id);
    }

    @RequestMapping("/delete")
    public void removeUser(String id) {
        User user = new User();
        user.setId(id);

        userService.deleteUser(user);
    }
}
