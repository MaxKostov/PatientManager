package patientmanager.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import patientmanager.dto.RegistrationDto;
import patientmanager.entities.Role;
import patientmanager.entities.UserEntity;
import patientmanager.services.RoleService;
import patientmanager.services.UserService;

import java.util.List;

@Controller
public class AuthController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AuthController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        return "login";
    }

    @GetMapping("/userReg")
    public String getRegisterForm(Model model) {
        RegistrationDto user = new RegistrationDto();
        List<Role> roleList = roleService.getAllRoles();
        model.addAttribute("user", user);
        model.addAttribute("roles", roleList);
        return "userRegistration";
    }

    @PostMapping("/userReg/save")
    public String saveUser(@Valid @ModelAttribute("user") RegistrationDto user,
                           BindingResult bindingResult, Model model) {
        UserEntity existingUser = userService.findByName(user.getUsername());
        if (existingUser != null && existingUser.getUsername() != null && !existingUser.getUsername().isEmpty()) {
            bindingResult.rejectValue("username", "error.user", "There is already a user with the same username");
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            return "userRegistration";
        }
        userService.saveUser(user);
        return "redirect:/login";
    }
}