package vn.back_end_best_practice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcType;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;
import org.springframework.format.annotation.DateTimeFormat;
import vn.back_end_best_practice.utils.Gender;
import vn.back_end_best_practice.utils.UserType;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Builder // anotation lombok để tạo builder pattern tức là có thể khởi tạo đối tượng theo kiểu chuỗi
@NoArgsConstructor // anotation lombok để tạo constructor không tham số
@AllArgsConstructor // anotation lombok để tạo constructor có tham số
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class User extends AbstractEntity{
    @Column(name = "first_name")
    String firstName;

    @Column(name = "last_name")
    String lastName;

    @Column(name = "email", unique = true)
    @Email(message = "Email should be valid")
    String email;

    @Enumerated(EnumType.STRING)
//    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "type")
    UserType type;

    @Enumerated(EnumType.STRING)
//    @JdbcTypeCode(SqlTypes.NAMED_ENUM) // Chuyển đổi enum sang kiểu tên được định nghĩa trong cơ sở dữ liệu
    @Column(name = "gender")
    Gender gender;

    @Column(name = "date_of_birth")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    LocalDate dateOfBirth;

    @Column(name = "username", unique = true, nullable = false)
    String username;

    @Column(name = "password", nullable = false)
    String password;

    @Column(name = "role")
    Set<String> role;

}
