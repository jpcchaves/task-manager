package com.ws.taskmanager.services;

import com.ws.taskmanager.models.UserModel;
import java.util.List;
import java.util.Optional;

public interface UserService {
  List<UserModel> getUsers();

  Optional<UserModel> getUserByUsername(String username);

  boolean hasUserWithUsername(String username);

  boolean hasUserWithEmail(String email);

  UserModel validateAndGetUserByUsername(String username);

  UserModel saveUser(UserModel user);

  void deleteUser(UserModel user);

  Optional<UserModel> validUsernameAndPassword(String username, String password);
}
