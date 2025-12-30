package vn.back_end_best_practice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import vn.back_end_best_practice.dto.request.UserCreationRequest;
import vn.back_end_best_practice.dto.request.UserUpdateRequest;
import vn.back_end_best_practice.entity.User;

@Mapper(componentModel = "spring") // Đánh dấu đây là một MapStruct mapper
public interface UserMapper {
    User toUser(UserCreationRequest request);
    User updateUserFromRequest(@MappingTarget User user, UserUpdateRequest request);
}



// Mapper là gì ?
// Mapper là một thành phần trong lập trình được sử dụng để chuyển đổi dữ liệu giữa các định dạng hoặc cấu trúc khác nhau.
// Trong ngữ cảnh của phát triển phần mềm, mapper thường được sử dụng để chuyển đổi giữa các đối tượng trong ứng dụng và các định dạng dữ liệu khác nhau,
// chẳng hạn như JSON, XML, hoặc các bảng cơ sở dữ liệu.
// Mapper giúp tách biệt logic chuyển đổi dữ liệu khỏi logic nghiệp vụ chính của ứng dụng,
// làm cho mã nguồn trở nên dễ đọc, bảo trì và mở rộng hơn.


// componentModel = "spring" nghĩa là gì?
// Khi bạn sử dụng MapStruct để tạo các mapper trong một ứng dụng Spring,
// việc chỉ định componentModel = "spring" cho phép MapStruct tạo các bean mapper dưới dạng các thành phần Spring.
// Điều này có nghĩa là các mapper sẽ được quản lý bởi Spring Container,
// cho phép bạn dễ dàng tiêm (inject) chúng vào các lớp khác trong ứng dụng của bạn thông qua cơ chế Dependency Injection của Spring.
// Ví dụ, bạn có thể sử dụng @Autowired để tiêm mapper vào các dịch vụ hoặc bộ điều khiển (controller) trong ứng dụng Spring