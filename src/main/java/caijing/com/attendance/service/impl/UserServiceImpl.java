package caijing.com.attendance.service.impl;

import caijing.com.attendance.domain.User;
import caijing.com.attendance.mapper.UserMapper;
import caijing.com.attendance.service.UserService;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    public User register(User user) {
        userMapper.insertUser(user);
        return user;
    }

    public boolean checkCredentials(String username, String password) {
        User u = userMapper.findByUsername(username);
        if (u == null) return false;
        return u.getPassword().equals(password);
    }

    public User findById(Long userId) {
        return userMapper.findById(userId);
    }

    public List<User> findAll() {
        return userMapper.findAll();
    }

    public void update(User user) {
        userMapper.update(user);
    }

    public void delete(Long id) {
        userMapper.delete(id);
    }

}

