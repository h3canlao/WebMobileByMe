package prm.be.dto.response;

import lombok.Data;
import prm.be.enums.Role;

@Data
public class AuthAccountDTO {
    private String id;
    private String username;
    private String password;
    private Role role;
}
