package patientmanager.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import patientmanager.data.RoleRepo;
import patientmanager.data.UserRepo;
import patientmanager.dto.RegistrationDto;
import patientmanager.entities.Role;
import patientmanager.entities.UserEntity;
import patientmanager.services.UserService;

import java.util.Arrays;

@Service
public class UserServiceImpl implements UserService {
    private UserRepo userRepo;
    private RoleRepo roleRepo;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepo userRepo, RoleRepo roleRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void saveUser(RegistrationDto registrationDto) {
        try {
            UserEntity user = new UserEntity();
            user.setUsername(registrationDto.getUsername());
            user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
            user.setRoles(Arrays.asList(registrationDto.getRole()));
            userRepo.save(user);

            System.out.println("User saved: " + user.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public UserEntity findByName(String username) {
        return userRepo.findByUsername(username);
    }
}