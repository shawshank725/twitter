package com.social.twitter.authentication.internal.controller;

import com.social.twitter.authentication.internal.dto.LoginRequest;
import com.social.twitter.authentication.internal.dto.RegistrationRequest;
import com.social.twitter.authentication.internal.entity.User;
import com.social.twitter.authentication.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Pattern;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {


    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegistrationRequest registrationRequest) {

        ResponseEntity<String> validationResponse = validateAccount(registrationRequest);
        if (validationResponse.getStatusCode() != HttpStatus.OK) {
            return validationResponse;
        }
        try {
            User savedUser = userService.addUser(registrationRequest);
            return ResponseEntity.ok("success");
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body("failure: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.username(),
                            loginRequest.password()
                    )
            );
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authentication);
            SecurityContextHolder.setContext(context);

            // Persist the security context into the session
            HttpSession session = request.getSession(true);
            session.setAttribute(
                    HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                    context
            );
            return "success";

        } catch (BadCredentialsException e) {
            System.out.println(e);
            return "failure: Invalid username or password";
        } catch (Exception e) {
            return "failure: " + e.getMessage();
        }
    }


    @PostMapping("/validate")
    public ResponseEntity<String> validateAccount(@RequestBody RegistrationRequest request) {
        boolean isEmailTaken = userService.getUserByEmail(request.email()) != null;
        boolean isUsernameTaken = userService.getUserByUsername(request.username()) != null;

        if (isEmailTaken) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Email is already taken.");
        }
        if (isUsernameTaken) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Username is already taken.");
        }
        if (!validatePassword(request.password())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Password does not meet requirements.");
        }

        return ResponseEntity.ok("Account details are valid.");
    }

    public static boolean validatePassword(String password) {
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\S+$).{8,20}$";
        return password != null && Pattern.matches(regex, password);
    }
}
