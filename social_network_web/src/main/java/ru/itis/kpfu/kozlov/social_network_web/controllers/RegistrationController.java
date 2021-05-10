package ru.itis.kpfu.kozlov.social_network_web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itis.kpfu.kozlov.social_network_api.dto.RegForm;
import ru.itis.kpfu.kozlov.social_network_api.dto.UserDto;
import ru.itis.kpfu.kozlov.social_network_api.services.UserService;
import ru.itis.kpfu.kozlov.social_network_impl.UserForm;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @PermitAll
    @GetMapping("/registration")
    public String registrationPage() {
        return "registrationPage";
    }

    @PermitAll
    @PostMapping("/registration")
    public String registration(@Valid UserForm userForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            Collector<FieldError, ?, Map<String, String>> collector = Collectors.toMap(
                    fieldError -> fieldError.getField() + "Error",
                    FieldError::getDefaultMessage
            );
            bindingResult.getAllErrors().stream().anyMatch(error -> {
                if (Objects.requireNonNull(error.getCodes())[0].equals("userForm.ValidNames")) {
                    model.addAttribute("passwordsErrorMessage", error.getDefaultMessage());
                }
                return true;
            });
            Map<String, String> errorsMap = bindingResult.getFieldErrors().stream().collect(collector);
            model.mergeAttributes(errorsMap);
            model.addAttribute("userForm", userForm);
            return "registrationPage";
        }
        else{
            UserDto userDto = userService.findByEmail(userForm.getEmail());
            if (userDto == null) {
                userService.register(modelMapper.map(userForm, RegForm.class));
                return "redirect:/auth";
            } else {
                model.addAttribute("existErr", "Email already exists");
            }
            return "registrationPage";
        }
    }
}
