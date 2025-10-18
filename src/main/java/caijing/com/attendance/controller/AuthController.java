package caijing.com.attendance.controller;

import caijing.com.attendance.domain.User;
import caijing.com.attendance.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login-register";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        HttpServletResponse response,
                        Model model) {

        // 1. 先判断是不是管理员
        if ("admin".equals(username) && "admin".equals(password)) {
            User admin = new User();
            admin.setId(0L); // 随便给个id
            admin.setUsername("admin");
            admin.setRole("ADMIN"); // 角色标识

            session.setAttribute("user", admin);

            Cookie cookie = new Cookie("loginUser", admin.getId().toString());
            cookie.setPath("/");
            cookie.setMaxAge(60 * 60 * 24 * 7);
            response.addCookie(cookie);

            return "redirect:/dashboard";
        }

        // 2. 普通用户，走数据库校验
        boolean ok = userService.checkCredentials(username, password);
        if (!ok) {
            model.addAttribute("error", "用户名或密码错误");
            return "login-register";
        }

        User user = userService.findByUsername(username);
        session.setAttribute("user", user);

        Cookie cookie = new Cookie("loginUser", user.getId().toString());
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24 * 7);
        response.addCookie(cookie);

        return "redirect:/dashboard";
    }

    @ResponseBody
    @GetMapping("/check-username")
    public Map<String, Object> checkUsername(@RequestParam String username) {
        Map<String, Object> result = new HashMap<>();
        boolean exists = userService.findByUsername(username) != null;
        result.put("exists", exists);
        return result;
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user, Model model) {
        // 用户名只能是字母和数字
        if (!user.getUsername().matches("^[A-Za-z0-9]+$")) {
            model.addAttribute("error", "用户名只能包含字母和数字");
            return "login-register";
        }

        // 根据角色强制加前缀
        if ("STUDENT".equals(user.getRole())) {
            if (!user.getUsername().startsWith("S")) {
                user.setUsername("S" + user.getUsername().replaceAll("^[ST]", ""));
            }
        } else if ("TEACHER".equals(user.getRole())) {
            if (!user.getUsername().startsWith("T")) {
                user.setUsername("T" + user.getUsername().replaceAll("^[ST]", ""));
            }
        } else {
            // 默认学生
            user.setRole("STUDENT");
            if (!user.getUsername().startsWith("S")) {
                user.setUsername("S" + user.getUsername().replaceAll("^[ST]", ""));
            }
        }

        // 检查用户名是否重复
        if (userService.findByUsername(user.getUsername()) != null) {
            model.addAttribute("error", "用户名已存在，请重新注册");
            return "login-register";
        }

        // 注册
        userService.register(user);
        return "redirect:/login";
    }


    @GetMapping("/dashboard")
    public String dashboard(HttpSession session,
                            HttpServletRequest request,
                            Model model) {
        User user = (User) session.getAttribute("user");

        // 如果 session 没有，尝试从 Cookie 取
        if (user == null) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie c : cookies) {
                    if ("loginUser".equals(c.getName())) {
                        try {
                            Long userId = Long.valueOf(c.getValue());
                            user = userService.findById(userId);
                            if (user != null) {
                                session.setAttribute("user", user);
                            }
                        } catch (NumberFormatException ignored) {}
                    }
                }
            }
        }

        if (user == null) return "redirect:/login";

        model.addAttribute("user", user);
        return "dashboard";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, HttpServletResponse response) {
        // 清掉 session
        session.invalidate();

        // 清掉 Cookie
        Cookie cookie = new Cookie("loginUser", null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return "redirect:/login";
    }
}
