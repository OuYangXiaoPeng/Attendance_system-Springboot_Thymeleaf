package caijing.com.attendance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AttendanceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AttendanceApplication.class, args);

        String url = "http://localhost:8080/login";
        System.out.println("===============================================");
        System.out.println("✅ 考勤系统启动成功！");
        System.out.println("👉 访问地址: " + url);
        System.out.println("===============================================");
    }
}
