package com.ws.taskmanager.data.DTO.tasksdto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.dozermapper.core.Mapping;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;
import org.springframework.hateoas.RepresentationModel;

public class TaskPatchDTO extends RepresentationModel<TaskResponseDTO> implements Serializable {

  @JsonIgnore()
  @Mapping("id")
  private UUID key;

  @NotNull(message = "A situação da task é obrigatória!")
  private Boolean concluded;

  public UUID getKey() {
    return key;
  }

  public void setKey(UUID key) {
    this.key = key;
  }

  public Boolean getConcluded() {
    return concluded;
  }

  public void setConcluded(Boolean concluded) {
    this.concluded = concluded;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }

    TaskPatchDTO that = (TaskPatchDTO) o;

    if (!getKey().equals(that.getKey())) {
      return false;
    }
    return getConcluded().equals(that.getConcluded());
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + getKey().hashCode();
    result = 31 * result + getConcluded().hashCode();
    return result;
  }
}
