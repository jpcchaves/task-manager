package com.ws.taskmanager.controller;

import com.ws.taskmanager.data.DTO.authdto.AuthResponse;
import com.ws.taskmanager.data.DTO.authdto.LoginRequest;
import com.ws.taskmanager.data.DTO.authdto.SignUpRequest;
import com.ws.taskmanager.models.UserModel;
import com.ws.taskmanager.security.WebSecurityConfig;
import com.ws.taskmanager.services.UserService;
import jakarta.validation.Valid;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

  private final UserService userService;

  @PostMapping("/authenticate")
  public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
    Optional<UserModel> userOptional = userService.validUsernameAndPassword(loginRequest.getUsername(), loginRequest.getPassword());
    if (userOptional.isPresent()) {
      UserModel user = userOptional.get();
      return ResponseEntity.ok(new AuthResponse(user.getId(), user.getName(), user.getRole()));
    }
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/signup")
  public AuthResponse signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
    if (userService.hasUserWithUsername(signUpRequest.getUsername())) {
      throw new DuplicatedUserInfoException(String.format("Username %s is already been used", signUpRequest.getUsername()));
    }
    if (userService.hasUserWithEmail(signUpRequest.getEmail())) {
      throw new DuplicatedUserInfoException(String.format("Email %s is already been used", signUpRequest.getEmail()));
    }

    UserModel user = userService.saveUser(createUser(signUpRequest));
    return new AuthResponse(user.getId(), user.getName(), user.getRole());
  }

  private UserModel createUser(SignUpRequest signUpRequest) {
    UserModel user = new UserModel();
    user.setUsername(signUpRequest.getUsername());
    user.setPassword(signUpRequest.getPassword());
    user.setName(signUpRequest.getName());
    user.setEmail(signUpRequest.getEmail());
    user.setRole(WebSecurityConfig.USER);
    return user;
  }

}
