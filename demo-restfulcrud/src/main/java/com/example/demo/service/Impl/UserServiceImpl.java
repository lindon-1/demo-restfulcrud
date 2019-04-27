package com.example.demo.service.Impl;

import com.example.demo.Mapper.UserMapper;
import com.example.demo.entities.User;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @param :
 * @author : lindonglin
 * @Description :
 * @ate : 10:55  2019/1/20
 * @return :
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public User findByUserName(String username) {
        return userMapper.findByUserName(username);
    }

    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }

    @Override
    public void add(User user) {
        userMapper.addUser(user);
    }

    @Override
    public Boolean checkUser(String username) {
        User user = userMapper.findByUserName(username);
        if(user != null)
            return true;
        else
            return false;
    }

    @Override
    public User selectById(Integer id) {
        return userMapper.selectById(id);
    }

    @Override
    public void update(User user) {
        userMapper.update(user);
    }

    @Override
    public void delete(Integer id) {
        userMapper.delete(id);
    }
}
