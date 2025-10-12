package caijing.com.attendance.mapper;

import caijing.com.attendance.domain.LeaveRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LeaveRequestMapper {

    void insert(LeaveRequest leaveRequest);

    List<LeaveRequest> findByUserId(Long userId);

    List<LeaveRequest> findAll();

    void updateStatus(Long id, String status);

    LeaveRequest findById(Long id);

    void update(LeaveRequest leave);

    void delete(Long id);

}
