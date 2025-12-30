package vn.back_end_best_practice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest implements Serializable {

    @NotBlank(message = "Username is mandatory")
    String username;

    @Size(min = 6, message = "Password must be at least 6 characters long")
    @NotBlank(message = "Password is mandatory") // @NotBlank kiểm tra không null, không rỗng VÀ không chỉ có khoảng trắng
    String password;

    String email;
    String firstName;
    String lastName;
    LocalDate dateOfBirth;
}

// Serializable là một interface trong Java cho phép chuyển đổi một đối tượng thành một chuỗi byte để lưu trữ hoặc truyền qua mạng.
// Dùng trong các lớp DTO (Data Transfer Object) để đảm bảo rằng các đối tượng có thể được dễ dàng chuyển đổi và truyền tải.
