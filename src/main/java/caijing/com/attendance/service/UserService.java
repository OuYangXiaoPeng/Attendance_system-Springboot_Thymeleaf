package caijing.com.attendance.service;

import caijing.com.attendance.domain.User;
import caijing.com.attendance.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public interface UserService{

    User findByUsername(String username);

    User register(User user);

    boolean checkCredentials(String username, String password);

    User findById(Long userId);

    List<User> findAll();

    void update(User user);

    void delete(Long id);

}

