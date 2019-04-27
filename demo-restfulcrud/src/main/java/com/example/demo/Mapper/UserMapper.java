package com.example.demo.Mapper;

import com.example.demo.entities.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    public User findByUserName(String username);

    public List<User> findAll();

    public void addUser(User user);

    public User selectById(Integer id);

    public void update(User user);

    public void delete(Integer id);
}
