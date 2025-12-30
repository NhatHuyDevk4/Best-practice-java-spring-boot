package vn.back_end_best_practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.back_end_best_practice.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username); // khi viết như này jpa sẽ tự động hiểu và tạo câu truy vấn tương ứng
    Optional<User> findByUsername(String username);
}


// Optional là gì?
// Optional là một lớp trong Java được giới thiệu từ phiên bản Java 8,
// được sử dụng để đại diện cho một giá trị có thể tồn tại hoặc không tồn tại.
// Nó giúp tránh lỗi NullPointerException bằng cách cung cấp các phương thức để kiểm tra và xử lý giá trị một cách an toàn.
// Thay vì trả về null khi không có giá trị, bạn có thể trả về một đối tượng Optional rỗng,
// và người dùng của phương thức đó có thể kiểm tra xem giá trị có tồn tại hay không trước khi sử dụng nó.
