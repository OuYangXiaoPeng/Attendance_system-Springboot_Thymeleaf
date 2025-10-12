package caijing.com.attendance.mapper;

import caijing.com.attendance.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UserMapper {
    User findByUsername(@Param("username") String username);

    int insertUser(User user);

    User findById(@Param("id") Long id);

    List<User> findAll();

    void update(User user);

    void delete(Long id);

}

