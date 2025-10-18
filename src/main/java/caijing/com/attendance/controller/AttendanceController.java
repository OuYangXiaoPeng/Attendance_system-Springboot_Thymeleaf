package caijing.com.attendance.controller;

import caijing.com.attendance.domain.Attendance;
import caijing.com.attendance.domain.User;
import caijing.com.attendance.service.AttendanceService;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    // 显示打卡页面
    @GetMapping("/checkin")
    public String checkinPage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        System.out.println("checkinPage, user: " + user);
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        return "attendance/checkin";
    }

    // 学生提交打卡
    @ResponseBody
    @PostMapping("/checkin")
    public Map<String, Object> checkin(@RequestParam("location") String location,
                                       @RequestParam("method") String method,
                                       HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            result.put("success", false);
            result.put("message", "请先登录！");
            return result;
        }

        try {
            attendanceService.checkIn(user.getId(), location, method);
            result.put("success", true);
            result.put("message", "打卡成功，请查看考勤记录！");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "打卡失败：" + e.getMessage());
        }
        return result;
    }


    // 学生查看个人打卡记录
    @GetMapping("/my")
    public String myAttendance(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        List<Attendance> records = attendanceService.findByUserId(user.getId());
        model.addAttribute("records", records);
        return "attendance/my";
    }

    // 老师/管理员查看所有打卡记录
    @GetMapping("/list")
    public String allAttendance(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || (!"TEACHER".equals(user.getRole()) && !"ADMIN".equals(user.getRole()))) {
            return "redirect:/login";
        }
        List<Attendance> records = attendanceService.findAll();
        model.addAttribute("records", records);
        return "attendance/list";
    }

}
