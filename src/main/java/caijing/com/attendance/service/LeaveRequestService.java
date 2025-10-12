package caijing.com.attendance.service;

import caijing.com.attendance.domain.LeaveRequest;

import java.util.List;
import java.util.Map;

public interface LeaveRequestService {
    void insert(LeaveRequest leaveRequest);

    List<LeaveRequest> findByUserId(Long userId);

    List<LeaveRequest> findAll();

    void updateStatus(Long id, String status);

    LeaveRequest findById(Long id);

    void update(LeaveRequest leave);

    void delete(Long id);

}
