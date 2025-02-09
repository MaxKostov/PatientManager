package patientmanager.data;

import org.springframework.data.jpa.repository.JpaRepository;
import patientmanager.entities.UserEntity;

public interface UserRepo extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);
    UserEntity findFirstByUsername(String username);
}
