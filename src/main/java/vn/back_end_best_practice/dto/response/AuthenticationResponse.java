package vn.back_end_best_practice.dto.response;


import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AuthenticationResponse {
    String token;
    boolean authenticated;
}
