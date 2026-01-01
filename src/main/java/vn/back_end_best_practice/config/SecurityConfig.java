package vn.back_end_best_practice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.AbstractConfiguredSecurityBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final String[] PUBLIC_ENDPOINTS = {
            "/auth/**",
            "/user"
    };

    @Value("${jwt.signerKey}")
    private String signerKey;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(request ->
                request.requestMatchers(HttpMethod.POST, PUBLIC_ENDPOINTS).permitAll().anyRequest().authenticated());


        // oauth2ResourceServer de cau hinh spring security su dung JWT de xac thuc nguoi dung
        httpSecurity.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwtConfigurer -> jwtConfigurer.decoder(jwtDecoder())));

        // Spring security mặt định sẽ bật CSRF protection, tuy nhiên trong trường hợp này ta tắt nó đi
        // csrf là một cơ chế bảo mật để ngăn chặn các cuộc tấn công giả mạo yêu cầu từ các trang web độc hại.
        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        return httpSecurity.build();
    }


    // Mục đích của phương thức này là để cấu hình JwtDecoder cho việc giải mã và xác thực JWT token trong quá trình xác thực người dùng.
    @Bean
    JwtDecoder jwtDecoder() {
        SecretKeySpec secretKeySpec = new SecretKeySpec(signerKey.getBytes(), "HS512");
        return NimbusJwtDecoder.withSecretKey(secretKeySpec).macAlgorithm(MacAlgorithm.HS512).build();
    }

    // SecrectKeySpec là một lớp trong Java được sử dụng để đại diện cho một khóa bí mật (secret key) trong các thuật toán mã hóa đối xứng.
    // Nó cung cấp một cách để tạo và quản lý các khóa bí mật được sử dụng trong các thuật toán mã hóa như AES, DES, và HMAC.
    // NinbusJwtDecoder là một lớp trong Spring Security được sử dụng để giải mã và xác thực JWT (JSON Web Token) token.
    // Lớp này sử dụng thư viện Nimbus JOSE + JWT để thực hiện việc
    // build là một phương thức tĩnh được sử dụng để tạo một đối tượng JwtDecoder mới với các cấu hình cụ thể. ==> Tóm lại build() dùng để tạo đối tượng cuối cùng sau khi đã cấu hình xong các tham số cần thiết.
}

// anyRequest().authenticated() de yeu cau xac thuc cho tat ca cac endpoint khac
// permit all cho endpoint POST /user de co the dang ky nguoi dung moi ma khong can xac thuc