package puttipat.ketpupong.UserSystem.payload.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class LoginRequest {
    @NotBlank
    @Size(min = 3,max = 100)
    private String username;

    @NotBlank
    @Size(max = 120)
    private String password;
}
