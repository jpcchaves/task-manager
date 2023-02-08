package com.ws.taskmanager.services;

import com.ws.taskmanager.exceptions.ResourceNotFoundException;
import com.ws.taskmanager.models.UserModel;
import com.ws.taskmanager.repositories.UserRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public List<UserModel> getUsers() {
    return userRepository.findAll();
  }

  @Override
  public Optional<UserModel> getUserByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  @Override
  public boolean hasUserWithUsername(String username) {
    return userRepository.existsByUsername(username);
  }

  @Override
  public boolean hasUserWithEmail(String email) {
    return userRepository.existsByEmail(email);
  }

  @Override
  public UserModel validateAndGetUserByUsername(String username) {
    return getUserByUsername(username)
        .orElseThrow(() -> new ResourceNotFoundException(String.format("User with username %s not found", username)));
  }

  @Override
  public UserModel saveUser(UserModel user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    return userRepository.save(user);
  }

  @Override
  public void deleteUser(UserModel user) {
    userRepository.delete(user);
  }

  @Override
  public Optional<UserModel> validUsernameAndPassword(String username, String password) {
    return getUserByUsername(username)
        .filter(user -> passwordEncoder.matches(password, user.getPassword()));
  }
}
