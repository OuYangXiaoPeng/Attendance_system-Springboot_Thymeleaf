package caijing.com.attendance.controller;

import caijing.com.attendance.domain.Attendance;
import caijing.com.attendance.domain.LeaveRequest;
import caijing.com.attendance.domain.User;
import caijing.com.attendance.service.AdminDashboardService;
import caijing.com.attendance.service.AttendanceService;
import caijing.com.attendance.service.LeaveRequestService;
import caijing.com.attendance.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final AttendanceService attendanceService;
    private final LeaveRequestService leaveRequestService;
    private final AdminDashboardService dashboardService;

    public AdminController(UserService userService,
                           AttendanceService attendanceService,
                           LeaveRequestService leaveRequestService,
                           AdminDashboardService dashboardService) {
        this.userService = userService;
        this.attendanceService = attendanceService;
        this.leaveRequestService = leaveRequestService;
        this.dashboardService = dashboardService;
    }


    // 用户管理首页（列表）
    @GetMapping("/user_manage")
    public String manageUsers(Model model, HttpSession session) {
        User userRole = (User) session.getAttribute("user");
        if (userRole == null || !"ADMIN".equals(userRole.getRole())) {
            return "redirect:/login";
        }

        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "admin/user_manage";
    }

    // 编辑用户页面
    @GetMapping("/user_edit/{id}")
    public String editUserPage(@PathVariable Long id, Model model, HttpSession session) {
        User userRole = (User) session.getAttribute("user");
        if (userRole == null || !"ADMIN".equals(userRole.getRole())) {
            return "redirect:/login";
        }

        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "admin/user_edit";
    }

    // 保存编辑结果
    @PostMapping("/user_edit")
    public String editUser(@ModelAttribute User user) {
        userService.update(user);
        return "redirect:/admin/user_manage";
    }

    // 删除用户
    @GetMapping("/user_delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return "redirect:/admin/user_manage";
    }

    // 管理员查看所有考勤数据
    @GetMapping("/attendance_manage")
    public String manageAttendance(Model model, HttpSession session) {
        User userRole = (User) session.getAttribute("user");
        if (userRole == null || !"ADMIN".equals(userRole.getRole())) {
            return "redirect:/login";
        }

        List<Attendance> list = attendanceService.findAll();
        model.addAttribute("attendanceList", list);
        return "admin/attendance_manage";
    }

    // 管理员编辑考勤数据（先跳转到编辑页面）
    @GetMapping("/attendance_edit/{id}")
    public String editAttendance(@PathVariable Long id, Model model, HttpSession session) {
        User userRole = (User) session.getAttribute("user");
        if (userRole == null || !"ADMIN".equals(userRole.getRole())) {
            return "redirect:/login";
        }

        Attendance record = attendanceService.findById(id);
        model.addAttribute("record", record);
        return "admin/attendance_edit";
    }

    // 保存修改
    @PostMapping("/attendance_update")
    public String updateAttendance(@ModelAttribute Attendance record) {
        attendanceService.update(record);
        return "redirect:/admin/attendance_manage";
    }

    // 删除考勤数据
    @GetMapping("/attendance_delete/{id}")
    public String deleteAttendance(@PathVariable Long id) {
        attendanceService.delete(id);
        return "redirect:/admin/attendance_manage";
    }

    // 查看所有请假申请
    @GetMapping("/leave_manage")
    public String manageLeaves(Model model, HttpSession session) {
        User userRole = (User) session.getAttribute("user");
        if (userRole == null || !"ADMIN".equals(userRole.getRole())) {
            return "redirect:/login";
        }

        List<LeaveRequest> leaveList = leaveRequestService.findAll();
        model.addAttribute("leaveList", leaveList);
        return "admin/leave_manage";
    }

    // 编辑请假申请（跳转页面）
    @GetMapping("/leave_edit/{id}")
    public String editLeave(@PathVariable Long id, Model model, HttpSession session) {
        User userRole = (User) session.getAttribute("user");
        if (userRole == null || !"ADMIN".equals(userRole.getRole())) {
            return "redirect:/login";
        }

        LeaveRequest leave = leaveRequestService.findById(id);
        System.out.println(leave);
        model.addAttribute("leave", leave);
        return "admin/leave_edit";
    }

    // 保存修改（比如批准/拒绝/调整理由等）
    @PostMapping("/leave_update")
    public String updateLeave(@ModelAttribute LeaveRequest leave) {
        leaveRequestService.update(leave);
        return "redirect:/admin/leave_manage";
    }

    // 删除请假申请
    @GetMapping("/leave_delete/{id}")
    public String deleteLeave(@PathVariable Long id) {
        leaveRequestService.delete(id);
        return "redirect:/admin/leave_manage";
    }

    // 管理员查看所有用户角色统计数据
    @GetMapping("/dashboard")
    public String dashboardPage(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"ADMIN".equals(user.getRole())) {
            return "redirect:/login";
        }
        return "/admin/dashboard";
    }

    // 管理员获取所有统计数据
    @ResponseBody
    @GetMapping("/dashboardData")
    public Map<String, Object> getDashboardData(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"ADMIN".equals(user.getRole())) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "No permission");
            return error;
        }

        Map<String, Object> result = new HashMap<>();
        result.put("userRole", dashboardService.getUserRoleStats());
        result.put("leaveStatus", dashboardService.getLeaveStatusStats());
        result.put("attendanceLocation", dashboardService.getAttendanceLocationStats());
        result.put("attendanceMethod", dashboardService.getAttendanceMethodStats());
        return result;
    }


}
