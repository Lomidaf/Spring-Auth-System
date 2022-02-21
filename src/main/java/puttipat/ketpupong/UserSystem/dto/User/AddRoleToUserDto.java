package puttipat.ketpupong.UserSystem.dto.User;

import lombok.Data;

@Data
public class AddRoleToUserDto {
    private Long userId;
    private Long roleId;
}
