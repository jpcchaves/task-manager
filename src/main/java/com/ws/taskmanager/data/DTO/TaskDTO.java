package com.ws.taskmanager.data.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.dozermapper.core.Mapping;
import jakarta.persistence.Id;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.hateoas.RepresentationModel;

@JsonPropertyOrder({"id", "task", "concluded", "deadline", "createdAt"})
public class TaskDTO extends RepresentationModel<TaskDTO> implements Serializable {

    @Mapping("id")
    @JsonProperty("id")
    private UUID key;

    @NotBlank
    private String task;

    @FutureOrPresent
    private LocalDateTime deadline;

    @NotNull
    private Boolean concluded;

    private LocalDateTime createdAt;

    public UUID getKey() {
        return key;
    }

    public void setKey(UUID key) {
        this.key = key;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
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
        if (!getConcluded().equals(taskDTO.getConcluded())) {
            return false;
        }
        return getCreatedAt().equals(taskDTO.getCreatedAt());
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + getKey().hashCode();
        result = 31 * result + getTask().hashCode();
        result = 31 * result + getDeadline().hashCode();
        result = 31 * result + getConcluded().hashCode();
        result = 31 * result + getCreatedAt().hashCode();
        return result;
    }
}
