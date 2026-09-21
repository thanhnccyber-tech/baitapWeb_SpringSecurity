package vn.utepro;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import vn.utepro.entity.Role;
import vn.utepro.entity.User;
import vn.utepro.repository.RoleRepository;
import vn.utepro.repository.UserRepository;

@SpringBootApplication
public class SpringSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityApplication.class, args);
    }

    @Bean
    CommandLineRunner initData(RoleRepository roleRepository,
                               UserRepository userRepository,
                               PasswordEncoder passwordEncoder,
                               @Value("${ADMIN_EMAIL:admin@utepro.vn}") String adminEmail,
                               @Value("${ADMIN_PASSWORD:123456}") String adminPassword) {
        return args -> {
            // Tạo Role USER / ADMIN nếu chưa có
            Role userRole = roleRepository.findByName("ROLE_USER")
                    .orElseGet(() -> roleRepository.save(
                            Role.builder().name("ROLE_USER").build()
                    ));
            Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                    .orElseGet(() -> roleRepository.save(
                            Role.builder().name("ROLE_ADMIN").build()
                    ));

            // Tạo user Admin mẫu theo email
            if (!userRepository.existsByEmail(adminEmail.toLowerCase())) {
                User admin = User.builder()
                        .username(adminEmail.toLowerCase())   // username trùng email cho đơn giản
                        .email(adminEmail.toLowerCase())
                        .password(passwordEncoder.encode(adminPassword))
                        .fullName("Nguyễn Chí Thanh UTE")         // <-- Đổi tên của bạn ở đây
                        .images("/images/user.png")
                        .role(adminRole)
                        .enabled(true)
                        .build();
                userRepository.save(admin);
                System.out.println(">>> Đã tạo admin mẫu: " + adminEmail + " / " + adminPassword);
            }
        };
    }
}