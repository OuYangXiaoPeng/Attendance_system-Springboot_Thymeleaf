package caijing.com.attendance.service.impl;

import caijing.com.attendance.domain.LeaveRequest;
import caijing.com.attendance.mapper.LeaveRequestMapper;
import caijing.com.attendance.service.LeaveRequestService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LeaveRequestServiceImpl implements LeaveRequestService {

    private final LeaveRequestMapper leaveRequestMapper;

    public LeaveRequestServiceImpl(LeaveRequestMapper leaveRequestMapper) {
        this.leaveRequestMapper = leaveRequestMapper;
    }

    public void insert(LeaveRequest leaveRequest) {
        leaveRequestMapper.insert(leaveRequest);
    }

    public List<LeaveRequest> findByUserId(Long userId) {
        return leaveRequestMapper.findByUserId(userId);
    }

    public List<LeaveRequest> findAll() {
        return leaveRequestMapper.findAll();
    }

    public void updateStatus(Long id, String status) {
        leaveRequestMapper.updateStatus(id, status);
    }

    public LeaveRequest findById(Long id) {
        return leaveRequestMapper.findById(id);
    }

    public void update(LeaveRequest leave) {
        leaveRequestMapper.update(leave);
    }

    public void delete(Long id) {
        leaveRequestMapper.delete(id);
    }

}
