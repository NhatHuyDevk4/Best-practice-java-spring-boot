package vn.back_end_best_practice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.AbstractConfiguredSecurityBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(
                request -> request.requestMatchers(HttpMethod.POST, "/user").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/**").permitAll()
                .anyRequest().authenticated());

        // Spring security mặt định sẽ bật CSRF protection, tuy nhiên trong trường hợp này ta tắt nó đi
        // csrf là một cơ chế bảo mật để ngăn chặn các cuộc tấn công giả mạo yêu cầu từ các trang web độc hại.
        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        return httpSecurity.build();
    }

}

// anyRequest().authenticated() de yeu cau xac thuc cho tat ca cac endpoint khac
// permit all cho endpoint POST /user de co the dang ky nguoi dung moi ma khong can xac thuc