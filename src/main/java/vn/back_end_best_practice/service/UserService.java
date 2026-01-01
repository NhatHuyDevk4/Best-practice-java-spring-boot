package vn.back_end_best_practice.service;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.back_end_best_practice.dto.request.UserCreationRequest;
import vn.back_end_best_practice.dto.request.UserUpdateRequest;
import vn.back_end_best_practice.emus.Role;
import vn.back_end_best_practice.entity.User;
import vn.back_end_best_practice.exception.AppException;
import vn.back_end_best_practice.exception.ErrorCode;
import vn.back_end_best_practice.mapper.UserMapper;
import vn.back_end_best_practice.repository.UserRepository;

import java.util.HashSet;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    UserRepository userRepository;

    UserMapper userMapper;

    PasswordEncoder passwordEncoder;

    public User createUser(UserCreationRequest request) {
        if(userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTS);
        }
        User user = userMapper.toUser(request);
        //---------- bcrypt password encoding --------------
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        //---------- end bcrypt password encoding ----------

        //---------- set role user ----------
            // lý do dùng HashSet là vì trong entity User, trường role được định nghĩa là Set<String> role;
            // Set là một tập hợp không cho phép trùng lặp, và HashSet là một triển khai phổ biến của giao diện Set trong Java.
            // Bằng cách sử dụng HashSet, chúng ta đảm bảo rằng mỗi vai trò (role) được gán cho người dùng là duy nhất và không bị trùng lặp.
        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name()); // thêm role USER vào tập hợp roles
        user.setRole(roles);
        //---------- end set role user ----------

        return userRepository.save(user);
    }

    public User updateUser(Long userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        User existingUser = userMapper.updateUserFromRequest(user, request);
        return userRepository.save(existingUser);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
    }

}
