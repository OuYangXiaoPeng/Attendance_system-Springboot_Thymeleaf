package caijing.com.attendance.controller;

import caijing.com.attendance.domain.LeaveRequest;
import caijing.com.attendance.domain.User;
import caijing.com.attendance.service.LeaveRequestService;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/leave")
public class LeaveRequestController {

    private final LeaveRequestService leaveRequestService;

    public LeaveRequestController(LeaveRequestService leaveRequestService) {
        this.leaveRequestService = leaveRequestService;
    }

    // 请假申请页面
    @GetMapping("/apply")
    public String applyPage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";
        model.addAttribute("user", user);
        return "leave/apply";
    }

    // 提交请假申请
    @PostMapping("/apply")
    public String apply(@RequestParam String startTime,
                        @RequestParam String endTime,
                        @RequestParam String reason,
                        HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        LeaveRequest req = new LeaveRequest();
        req.setUserId(user.getId());
        req.setStartTime(LocalDateTime.parse(startTime));
        req.setEndTime(LocalDateTime.parse(endTime));
        req.setReason(reason);
        req.setCreatedAt(LocalDateTime.now());
        req.setStatus("待审批");

        leaveRequestService.insert(req);
        return "redirect:/leave/my";
    }

    // 学生查看自己的请假
    @GetMapping("/my")
    public String myLeaves(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        List<LeaveRequest> list = leaveRequestService.findByUserId(user.getId());
        model.addAttribute("leaves", list);
        return "leave/my";
    }

    // 老师查看所有请假
    @GetMapping("/manage")
    public String manageLeaves(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"TEACHER".equals(user.getRole())) return "redirect:/login";

        List<LeaveRequest> list = leaveRequestService.findAll();
        model.addAttribute("leaves", list);
        return "leave/manage";
    }

    // 老师审批
    @PostMapping("/approve")
    public String approve(@RequestParam Long id, @RequestParam String status, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"TEACHER".equals(user.getRole())) return "redirect:/login";

        leaveRequestService.updateStatus(id, status);
        return "redirect:/leave/manage";
    }
}
