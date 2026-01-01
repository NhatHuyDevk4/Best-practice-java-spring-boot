package vn.back_end_best_practice.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import vn.back_end_best_practice.emus.Role;
import vn.back_end_best_practice.entity.User;
import vn.back_end_best_practice.repository.UserRepository;

import java.util.HashSet;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {


    PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository) {
        return args -> {
            if(userRepository.findByUsername("admin").isEmpty()){
                var roles = new HashSet<String>();
                roles.add(Role.ADMIN.name());

                User user = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin"))
                        .role(roles)
                        .build();

                userRepository.save(user);
                log.warn("Created default admin user with username 'admin' and password 'admin'");
            }
        };
    }
}


// ApplicationRunner là một interface trong Spring Boot được sử dụng để thực thi mã sau khi ứng dụng Spring Boot đã khởi động hoàn tất.
// Nó cung cấp một cách để chạy các tác vụ khởi tạo hoặc cấu hình bổ sung ngay sau khi ứng dụng đã sẵn sàng phục vụ các yêu cầu.
// Bằng cách triển khai interface ApplicationRunner và định nghĩa phương thức run(),
// bạn có thể thực hiện các hành động như tải dữ liệu ban đầu, thiết lập cấu hình, hoặc thực hiện các tác vụ khác cần thiết trước khi ứng dụng bắt đầu hoạt động bình thường.
