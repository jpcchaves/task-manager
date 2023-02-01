package com.ws.taskmanager.services;

import com.ws.taskmanager.controller.TaskController;
import com.ws.taskmanager.data.DTO.TaskDTO;
import com.ws.taskmanager.exceptions.ResourceNotFoundException;
import com.ws.taskmanager.mapper.DozerMapper;
import com.ws.taskmanager.models.TaskModel;
import com.ws.taskmanager.repositories.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

@Service
public class TaskService {

    final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Transactional
    public TaskDTO createTask(TaskDTO taskDTO) throws Exception {

        taskDTO.setCreatedAt(LocalDateTime.now(ZoneId.of("UTC")));

        var entity = DozerMapper.parseObject(taskDTO, TaskModel.class);

        var dto = DozerMapper.parseObject(taskRepository.save(entity), TaskDTO.class);

        dto.add(linkTo(methodOn(TaskController.class).listTaskById(dto.getKey())).withSelfRel());

        return dto;
    }

    public List<TaskDTO> listAllTasks() {

        var tasks = DozerMapper.parseListObjects(taskRepository.findAll(), TaskDTO.class);

        tasks
            .forEach((task) -> {
                try {
                    task.add(linkTo(methodOn(TaskController.class).listTaskById(task.getKey())).withSelfRel());
                } catch (Exception e) {
                    throw new RuntimeException(e.getMessage());
                }
            });

        return tasks;

    }

    public TaskDTO listTaskById(UUID id) throws Exception {

        var entity = taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado uma task com o ID informado!"));

        TaskDTO taskDTO = DozerMapper.parseObject(entity, TaskDTO.class);

        taskDTO.add(linkTo(methodOn(TaskController.class).listTaskById(id)).withSelfRel());

        return taskDTO;
    }

    @Transactional
    public TaskDTO updateTask(UUID id, TaskDTO taskDTO) throws Exception {

        var task = taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado uma task com o ID informado!"));

        task.setTask(taskDTO.getTask());
        task.setConcluded(taskDTO.getConcluded());
        task.setDeadline(taskDTO.getDeadline());

        var dto = DozerMapper.parseObject(taskRepository.save(task), TaskDTO.class);

        dto.add(linkTo(methodOn(TaskController.class).listTaskById(dto.getKey())).withSelfRel());

        return dto;
    }

    @Transactional
    public void deleteTask(UUID id) {
        var task = taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Não é possível deletar essa task pois ela não existe!"));
        taskRepository.deleteById(id);
    }


}
