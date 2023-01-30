package com.ws.taskmanager.services;

import com.ws.taskmanager.data.DTO.TaskDTO;
import com.ws.taskmanager.models.TaskModel;
import com.ws.taskmanager.repositories.TaskRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class TaskService {

  final TaskRepository taskRepository;

  public TaskService(TaskRepository taskRepository) {
    this.taskRepository = taskRepository;
  }

  @Transactional
  public TaskModel createTask(TaskDTO taskDTO) {
    var task = new TaskModel();
    task.setCreatedAt(LocalDateTime.now(ZoneId.of("UTC")));

    ModelMapper modelMapper = new ModelMapper();
    modelMapper.map(taskDTO, task);

    return taskRepository.save(task);
  }

}