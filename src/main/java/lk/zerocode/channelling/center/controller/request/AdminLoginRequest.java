package lk.zerocode.channelling.center.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AdminLoginRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
