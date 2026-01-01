package vn.back_end_best_practice.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import vn.back_end_best_practice.dto.request.AuthenticationRequest;
import vn.back_end_best_practice.dto.request.IntrospectRequest;
import vn.back_end_best_practice.dto.response.AuthenticationResponse;
import vn.back_end_best_practice.dto.response.IntrospectResponse;
import vn.back_end_best_practice.entity.User;
import vn.back_end_best_practice.exception.AppException;
import vn.back_end_best_practice.exception.ErrorCode;
import vn.back_end_best_practice.repository.UserRepository;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;
import java.util.StringJoiner;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {

    UserRepository userRepository;

    @NonFinal // Đánh dấu biến này là không final để có thể thay đổi giá trị nếu cần vào constructors
    @Value("${jwt.signerKey}") // Lấy giá trị từ file application.properties với key là jwt.signerKey
    protected String SIGNING_KEY ;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));

         PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
         boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

         if(!authenticated) {
             throw new AppException(ErrorCode.UNAUTHENTICATED);
         }

         var token = generateToken(user);

         return AuthenticationResponse.builder()
                 .token(token)
                 .authenticated(true)
                 .build();
    }

    private String generateToken(User user) {

        // Generate JWT token
        // header dùng để mô tả thuật toán mã hóa và các thông tin khác về token
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        // payload chứa các thông tin về người dùng và các tuyên bố (claims)
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("NhatHuyDev.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                ))
                .claim("scope", buildScope(user)) // Thêm các tuyên bố (claims) tùy chỉnh vào token, ví dụ như userId
                .build();

        // Này là phần dữ liệu chính của token, chứa các tuyên bố (claims) đã được định nghĩa trong JWTClaimsSet
        Payload payload = new Payload(claimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            // Tạo chữ ký số cho token sử dụng khóa bí mật (SIGNING_KEY)
            jwsObject.sign(new MACSigner(SIGNING_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    // Mục đích của phương thức này là để xây dựng một chuỗi (string) đại diện cho phạm vi (scope) của người dùng dựa trên các vai trò (role) mà người dùng đó sở hữu.
    private String buildScope(User user){
        // stringJoiner là một lớp trong Java được sử dụng để xây dựng một chuỗi (string) bằng cách nối các phần tử với một ký tự phân tách (delimiter) cụ thể.
        StringJoiner stringJoiner = new StringJoiner(" ");
        // Kiểm tra nếu tập hợp vai trò (role) của người dùng không rỗng
        if (!CollectionUtils.isEmpty(user.getRole())){
            // Duyệt qua từng vai trò (role) và thêm vào stringJoiner
            user.getRole().forEach(stringJoiner::add);
            // stringJoiner::add là một method reference trong Java, tương đương với việc sử dụng lambda expression như sau: role -> stringJoiner.add(role)
        }

        // Trả về chuỗi đã được xây dựng
        return stringJoiner.toString();
    }


    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
        String token = request.getToken();

        JWSVerifier verifier = new MACVerifier(SIGNING_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        // Kiểm tra thời gian hết hạn của token
        // getJWTClaimsSet(): Phương thức này trả về một đối tượng JWTClaimsSet, đại diện cho tập hợp các tuyên bố (claims) trong token JWT.
        // getExpirationTime(): Phương thức này trả về thời gian hết hạn (expiration time) của token dưới dạng một đối tượng Date.
        Date exppiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        return IntrospectResponse.builder()
                .valid(verified && exppiryTime.after(new Date()))
                .build();
    }
}
