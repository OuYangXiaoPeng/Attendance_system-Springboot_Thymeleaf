package caijing.com.attendance.mapper;

import caijing.com.attendance.domain.Attendance;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface AttendanceMapper {
    
    int insert(Attendance attendance);
    List<Attendance> findByUserId(Long userId);

    List<Attendance> findAll();

    void update(Attendance record);

    void delete(Long id);

    Attendance findById(Long id);

}

