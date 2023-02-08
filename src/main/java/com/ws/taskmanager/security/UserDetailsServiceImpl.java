package com.ws.taskmanager.security;

import com.ws.taskmanager.exceptions.ResourceNotFoundException;
import com.ws.taskmanager.models.UserModel;
import com.ws.taskmanager.services.UserService;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

private final UserService userService;

  @Override
  public UserDetails loadUserByUsername(String username) {
    UserModel user = userService.getUserByUsername(username).orElseThrow(() -> new ResourceNotFoundException(String.format("Username %s not found", username)));
    List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(user.getRole()));
    return mapUserToCustomUserDetails(user, authorities);
  }

  private CustomUserDetails mapUserToCustomUserDetails(UserModel user, List<SimpleGrantedAuthority> authorities) {
    CustomUserDetails customUserDetails = new CustomUserDetails();
    customUserDetails.setId(user.getId());
    customUserDetails.setUsername(user.getUsername());
    customUserDetails.setPassword(user.getPassword());
    customUserDetails.setName(user.getName());
    customUserDetails.setEmail(user.getEmail());
    customUserDetails.setAuthorities(authorities);
    return customUserDetails;
  }

}
