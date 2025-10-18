package caijing.com.attendance.service.impl;

import caijing.com.attendance.domain.Attendance;
import caijing.com.attendance.mapper.AttendanceMapper;
import caijing.com.attendance.service.AttendanceService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class AttendanceServiceImpl implements AttendanceService {
    private final AttendanceMapper attendanceMapper;

    public AttendanceServiceImpl(AttendanceMapper attendanceMapper) {
        this.attendanceMapper = attendanceMapper;
    }

    public void checkIn(Long userId, String location, String method) {
        Attendance attendance = new Attendance();
        attendance.setUserId(userId);
        attendance.setCheckinTime(LocalDateTime.now());
        attendance.setLocation(location);
        attendance.setMethod(method);
        attendance.setStatus("正常");

        attendanceMapper.insert(attendance);
    }

    public List<Attendance> findByUserId(Long userId) {
        return attendanceMapper.findByUserId(userId);
    }

    public List<Attendance> findAll() {
        return attendanceMapper.findAll();
    }

    public void update(Attendance record) {
        attendanceMapper.update(record);
    }

    public void delete(Long id) {
        attendanceMapper.delete(id);
    }

    public Attendance findById(Long id) {
        return attendanceMapper.findById(id);
    }

}

