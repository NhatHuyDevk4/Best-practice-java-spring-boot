package vn.back_end_best_practice.service;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.back_end_best_practice.dto.request.UserCreationRequest;
import vn.back_end_best_practice.dto.request.UserUpdateRequest;
import vn.back_end_best_practice.entity.User;
import vn.back_end_best_practice.exception.AppException;
import vn.back_end_best_practice.exception.ErrorCode;
import vn.back_end_best_practice.mapper.UserMapper;
import vn.back_end_best_practice.repository.UserRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    UserRepository userRepository;

    UserMapper userMapper;

    public User createUser(UserCreationRequest request) {
        if(userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTS);
        }
        User user = userMapper.toUser(request);
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
