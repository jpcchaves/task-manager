package com.ws.taskmanager.repositories;

import com.ws.taskmanager.models.UserModel;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
  Optional<UserModel> findByUsername(String username);

  boolean existsByUsername(String username);

  boolean existsByEmail(String email);
}
