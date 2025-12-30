package vn.back_end_best_practice.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.back_end_best_practice.dto.request.AuthenticationRequest;
import vn.back_end_best_practice.dto.response.AuthenticationResponse;
import vn.back_end_best_practice.dto.response.ResponseData;
import vn.back_end_best_practice.service.AuthenticationService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenicationController {

    AuthenticationService authenticationService;

    @PostMapping("/login")
    ResponseData<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest) {
        var result = authenticationService.authenticate(authenticationRequest);
        return ResponseData.<AuthenticationResponse>builder()
                .data(result)
                .build();
    }
}
