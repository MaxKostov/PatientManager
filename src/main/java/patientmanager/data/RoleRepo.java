package patientmanager.data;

import org.springframework.data.jpa.repository.JpaRepository;
import patientmanager.entities.Role;

public interface RoleRepo extends JpaRepository<Role, Long> {
    Role findByName(String role);
}
