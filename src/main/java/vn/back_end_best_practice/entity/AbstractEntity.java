package vn.back_end_best_practice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Getter
@Setter
@MappedSuperclass // Đánh dấu lớp này là lớp cha để các lớp con kế thừa các thuộc tính
public abstract class AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "created_at")
    @CreationTimestamp // Tự động gán giá trị thời gian hiện tại khi bản ghi được tạo
    @Temporal(TemporalType.TIMESTAMP) // Lưu trữ cả ngày và giờ
    private Date createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
}
