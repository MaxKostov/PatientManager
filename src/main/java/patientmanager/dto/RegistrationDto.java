package patientmanager.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import patientmanager.entities.Role;

@Data
public class RegistrationDto {
    private long id;
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
    @NotNull(message = "Role must be selected")
    private Role role;
}
