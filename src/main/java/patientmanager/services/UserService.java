package patientmanager.services;

import patientmanager.dto.RegistrationDto;
import patientmanager.entities.UserEntity;

public interface UserService {
    void saveUser(RegistrationDto registrationDto);

    UserEntity findByName(String username);
}
