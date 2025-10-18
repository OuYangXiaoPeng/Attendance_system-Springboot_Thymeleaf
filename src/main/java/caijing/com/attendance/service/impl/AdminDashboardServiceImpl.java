package caijing.com.attendance.service.impl;

import caijing.com.attendance.mapper.AdminDashboardMapper;
import caijing.com.attendance.service.AdminDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminDashboardServiceImpl implements AdminDashboardService {

    @Autowired
    private AdminDashboardMapper dashboardMapper;

    private Map<String, Object> convertToMap(List<Map<String, Object>> list) {
        Map<String, Object> map = new LinkedHashMap<>();
        for (Map<String, Object> item : list) {
            map.put(item.get("name").toString(), item.get("value"));
        }
        return map;
    }

    @Override
    public Map<String, Object> getUserRoleStats() {
        return convertToMap(dashboardMapper.countUserByRole());
    }

    @Override
    public Map<String, Object> getLeaveStatusStats() {
        return convertToMap(dashboardMapper.countLeaveByStatus());
    }

    @Override
    public Map<String, Object> getAttendanceLocationStats() {
        return convertToMap(dashboardMapper.countAttendanceByLocation());
    }

    @Override
    public Map<String, Object> getAttendanceMethodStats() {
        return convertToMap(dashboardMapper.countAttendanceByMethod());
    }
}
