package com.example.demo.service;

import com.example.demo.entities.User;

import java.util.List;

public interface UserService {
    public User findByUserName(String username);

    public List<User> findAll();

    public void add(User user);

    public Boolean checkUser(String username);

    public User selectById(Integer id);

    public void update(User user);

    public void delete(Integer id);
}
