package vn.back_end_best_practice.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String id;
    String username;
    String email;
    String firstName;
    String lastName;
    LocalDate dateOfBirth;
    Set<String> roles;
}

// khi cập nhập dto và dùng map struct nên khi update có thể bị catch vì vậy cần vào maven và clean đi