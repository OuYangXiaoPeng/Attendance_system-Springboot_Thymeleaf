package caijing.com.attendance.domain;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class User {
    private Long id;
    private String username;
    private String password;
    private String fullName;
    private String role; // STUDENT / TEACHER / ADMIN
    private String phone;
    private LocalDateTime createdAt;
}
