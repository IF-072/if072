package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.common.model.Role;
import com.softserve.if072.common.model.User;
import com.softserve.if072.mvcapp.dto.UserRegistrationForm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@RequestMapping("/register")
@PropertySource({"classpath:application.properties", "classpath:message.properties"})
public class RegistrationController extends BaseController {

    private static final Logger LOGGER = LogManager.getLogger(RegistrationController.class);

    @Value("${service.url.getRoleById}")
    private String getRoleByIdUrl;

    @Value("${service.url.roles}")
    private String rolesUrl;

    @Value("${service.url.register}")
    private String registerUrl;

    @Value("${registration.alreadyExists}")
    private String alreadyExist;

    @Value("${registration.generalError}")
    private String generalError;

    @Value("${registration.successful}")
    private String registrationSuccessful;

    @Value("${registration.incorrectAccountType}")
    private String incorrectAccountType;


    @GetMapping
    public String getRegisterPage(Model model) {
        RestTemplate template = getRestTemplate();
        ResponseEntity<Role[]> responseEntity = template.getForEntity(rolesUrl, Role[].class);
        Map<Integer, String> rolesMap = new LinkedHashMap<>();
        Role[] roles = responseEntity.getBody();
        for (Role role : roles)
            rolesMap.put(role.getId(), role.getDescription());

        model.addAttribute("roleMap", rolesMap);
        model.addAttribute("registrationForm", new UserRegistrationForm());

        return "register";
    }

    @PostMapping
    public String postRegisterPage(@Validated @ModelAttribute("registrationForm") UserRegistrationForm registrationForm,
                                   BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("validationErrors", result.getFieldErrors());
            return "redirect:/register";
        }

        Role role = getRoleByID(registrationForm.getRoleId());
        if (role == null) {
            redirectAttributes.addFlashAttribute("errorMessage", incorrectAccountType);
            return "redirect:/register";
        }

        User user = new User();
        user.setEmail(registrationForm.getEmail());
        user.setName(registrationForm.getName());
        user.setPassword(registrationForm.getPassword());
        user.setRole(role);
        user.setEnabled(true);

        RestTemplate template = getRestTemplate();
        try {
            ResponseEntity<String> responseEntity = template.postForEntity(registerUrl, user, String.class);
            if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
                redirectAttributes.addFlashAttribute("successMessage", registrationSuccessful);
                return "redirect:/login";
            }
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().equals(HttpStatus.UNPROCESSABLE_ENTITY)) {
                LOGGER.warn("User tried to register under already registered username");
                redirectAttributes.addFlashAttribute("errorMessage", alreadyExist);
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", generalError);
            }
            return "redirect:/register";
        }

        return "redirect:/register";
    }

    private Role getRoleByID(int roleId) {
        RestTemplate template = new RestTemplate();
        ResponseEntity<Role> responseEntity = template.getForEntity(String.format(getRoleByIdUrl, roleId), Role.class);
        if (responseEntity == null || !responseEntity.hasBody()) {
            return null;
        }
        return responseEntity.getBody();
    }

}
