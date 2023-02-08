package com.ws.taskmanager.data.DTO.tasksdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.dozermapper.core.Mapping;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.hateoas.RepresentationModel;

@JsonPropertyOrder({"id", "task", "concluded", "deadline", "createdAt"})
public class TaskDTO extends RepresentationModel<TaskDTO> implements Serializable {

    @JsonProperty("id")
    @Mapping("id")
    private UUID key;

    @NotBlank(message = "A task é obrigatória!")
    private String task;

    @FutureOrPresent(message = "O prazo deve ser a data atual ou uma data futura.")
    private LocalDateTime deadline;

    @NotNull(message = "A situação da task é obrigatória!")
    private Boolean concluded;

    public UUID getKey() {
        return key;
    }

    public void setKey(UUID key) {
        this.key = key;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
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

        TaskDTO taskDTO = (TaskDTO) o;

        if (!getKey().equals(taskDTO.getKey())) {
            return false;
        }
        if (!getTask().equals(taskDTO.getTask())) {
            return false;
        }
        if (!getDeadline().equals(taskDTO.getDeadline())) {
            return false;
        }
        return getConcluded().equals(taskDTO.getConcluded());
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + getKey().hashCode();
        result = 31 * result + getTask().hashCode();
        result = 31 * result + getDeadline().hashCode();
        result = 31 * result + getConcluded().hashCode();
        return result;
    }
}
