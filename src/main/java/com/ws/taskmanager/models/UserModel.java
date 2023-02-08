package com.ws.taskmanager.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.scheduling.config.Task;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users", uniqueConstraints = {
    @UniqueConstraint(columnNames = "username"),
    @UniqueConstraint(columnNames = "email")
})
public class UserModel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String username;
  private String password;
  private String name;
  private String email;
  private String role;

  @OneToMany
  private List<TaskModel> tasks;

  public UserModel(Long id, String username, String password, String name, String email,
      String role,
      List<TaskModel> tasks) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.name = name;
    this.email = email;
    this.role = role;
    this.tasks = tasks;
  }
}
