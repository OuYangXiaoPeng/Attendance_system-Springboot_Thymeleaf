package caijing.com.attendance.service;

import caijing.com.attendance.domain.Attendance;
import caijing.com.attendance.mapper.AttendanceMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public interface AttendanceService {


    void checkIn(Long userId, String location, String method);

    List<Attendance> findByUserId(Long userId);

    List<Attendance> findAll();

    void update(Attendance record);

    void delete(Long id);

    Attendance findById(Long id);

}

