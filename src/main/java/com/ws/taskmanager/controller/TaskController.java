package com.ws.taskmanager.controller;


import com.ws.taskmanager.data.DTO.tasksdto.TaskCreateDTO;
import com.ws.taskmanager.data.DTO.tasksdto.TaskDTO;
import com.ws.taskmanager.data.DTO.tasksdto.TaskPatchDTO;
import com.ws.taskmanager.data.DTO.tasksdto.TaskResponseDTO;
import com.ws.taskmanager.services.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/v1/task")
public class TaskController {

    final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @Operation(summary = "Creates a new task",
            description = "Adds a new task by passing in a JSON representation of the task!",
            tags = {"Tasks"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = TaskResponseDTO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    @PostMapping(value = "/new", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskResponseDTO> createTask(@RequestBody @Valid TaskCreateDTO taskDTO)
            throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.createTask(taskDTO));
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Finds all tasks", description = "Finds all tasks",
            tags = {"Tasks"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = TaskResponseDTO.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<Page<TaskResponseDTO>> listAllTasks(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                              @RequestParam(value = "size", defaultValue = "10") Integer size,
                                                              @RequestParam(value = "direction", defaultValue = "asc") String direction) {

        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "createdAt"));

        return ResponseEntity.status(HttpStatus.OK).body(taskService.listAllTasks(pageable));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Finds a tasks", description = "Finds a tasks",
            tags = {"Tasks"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = TaskResponseDTO.class))
                    ),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<TaskResponseDTO> listTaskById(@PathVariable(value = "id") UUID id) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.listTaskById(id));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Updates a task",
            description = "Updates a task by passing in a JSON representation of the task!",
            tags = {"Tasks"},
            responses = {
                    @ApiResponse(description = "Updated", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = TaskResponseDTO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<TaskResponseDTO> updateTask(@PathVariable(value = "id") UUID id, @Valid @RequestBody TaskDTO taskDTO)
            throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.updateTask(id, taskDTO));
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update the situation of a task (if it's concluded or not)",
            description = "Updates a task by passing in a boolean that represents if the task is concluded or not",
            tags = {"Tasks"},
            responses = {
                    @ApiResponse(description = "Updated", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = TaskResponseDTO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<TaskPatchDTO> updateTaskSituation(@PathVariable(value = "id") UUID id, @Valid @RequestBody TaskPatchDTO taskPatchDTO)
            throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.updateTaskSituation(id, taskPatchDTO));
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Deletes a task",
            description = "Deletes a task by passing in a JSON representation of the task!",
            tags = {"Tasks"},
            responses = {
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<?> deleteTask(@PathVariable(value = "id") UUID id) throws Exception {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }


}
