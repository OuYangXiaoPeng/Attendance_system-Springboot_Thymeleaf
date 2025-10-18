package caijing.com.attendance.service;

import java.util.Map;

public interface AdminDashboardService {
    Map<String, Object> getUserRoleStats();
    Map<String, Object> getLeaveStatusStats();
    Map<String, Object> getAttendanceLocationStats();
    Map<String, Object> getAttendanceMethodStats();
}
