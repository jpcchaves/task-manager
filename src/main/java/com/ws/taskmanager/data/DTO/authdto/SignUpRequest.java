package com.ws.taskmanager.data.DTO.authdto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class SignUpRequest {

  @Schema(example = "user3")
  @NotBlank(message = "O nome de usuário é obrigatório!")
  private String username;

  @Schema(example = "user3")
  @NotBlank(message = "A senha é obrigatória!")
  @Length(min = 6, message = "A senha deve conter pelo menos 6 caracteres.")
  @Length(max = 12, message = "A senha deve conter menos de 12 caracteres.")
  private String password;

  @Schema(example = "User3")
  @NotBlank(message = "O nome é obrigatório!")
  private String name;

  @Schema(example = "user3@mycompany.com")
  @NotBlank(message = "O email é obrigatório!")
  @Email(message = "Insira um email válido!")
  private String email;
}