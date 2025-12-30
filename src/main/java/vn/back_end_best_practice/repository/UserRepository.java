package vn.back_end_best_practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.back_end_best_practice.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username); // khi viết như này jpa sẽ tự động hiểu và tạo câu truy vấn tương ứng
}
