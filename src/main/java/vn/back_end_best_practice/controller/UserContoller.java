package vn.back_end_best_practice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vn.back_end_best_practice.dto.request.UserCreationRequest;
import vn.back_end_best_practice.dto.request.UserUpdateRequest;
import vn.back_end_best_practice.dto.response.ResponseData;
import vn.back_end_best_practice.entity.User;
import vn.back_end_best_practice.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
@Tag(name = "User Controller", description = "APIs for user management")
@RequiredArgsConstructor // Tự động tạo constructor với các trường được khai báo là final hoặc được đánh dấu với @NonNull
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true) // Tự động đặt tất cả các trường trong lớp là private và final trừ khi được chỉ định khác
public class UserContoller {

     UserService userService;

    @Operation(summary = "Create a new user", description = "Creates a new user with the provided details")
    @PostMapping
    ResponseData createUser(@RequestBody @Valid UserCreationRequest request) {
        User user = userService.createUser(request);
        return new ResponseData<>(HttpStatus.CREATED.value(), "User created successfully", user);
    }

    @PutMapping("/{userId}")
    ResponseData updateUser(@PathVariable("userId") Long userId, @RequestBody @Valid UserUpdateRequest request) {
        User user = userService.updateUser(userId, request);
        return new ResponseData<>(HttpStatus.OK.value(), "User updated successfully", user);
    }

    @GetMapping("/{userId}")
    ResponseData<UserCreationRequest> getUser(@PathVariable("userId") Long userId) {
        User user = userService.getUserById(userId);
        UserCreationRequest userCreationRequest = UserCreationRequest.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .dateOfBirth(user.getDateOfBirth())
                .build(); // build dùng để tạo một đối tượng từ Builder pattern tức là sau khi set các thuộc tính thì gọi build để trả về đối tượng cuối cùng của lớp đó
        return new ResponseData<>(HttpStatus.OK.value(), "User fetched successfully", userCreationRequest);
    }

   @GetMapping("/list")
    ResponseData<List<UserCreationRequest>> getListUser() {
         List<User> users = userService.getAllUsers();
         List<UserCreationRequest> userCreationRequests = users.stream().map(user -> UserCreationRequest.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .dateOfBirth(user.getDateOfBirth())
                .build()).toList();
         return new ResponseData<>(HttpStatus.OK.value(), "User list fetched successfully", userCreationRequests);
   }

   @DeleteMapping("/{userId}")
    ResponseData<Void> deleteUser(@PathVariable("userId") Long userId) {
        userService.deleteUser(userId);
        return new ResponseData<>(HttpStatus.OK.value(), "User deleted successfully", null);
   }
}
