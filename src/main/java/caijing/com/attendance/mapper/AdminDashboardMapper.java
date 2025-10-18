package caijing.com.attendance.mapper;

import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface AdminDashboardMapper {
    List<Map<String, Object>> countUserByRole();
    List<Map<String, Object>> countLeaveByStatus();
    List<Map<String, Object>> countAttendanceByLocation();
    List<Map<String, Object>> countAttendanceByMethod();
}
