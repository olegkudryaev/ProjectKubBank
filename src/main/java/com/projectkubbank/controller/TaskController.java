package com.projectkubbank.controller;

import com.projectkubbank.dto.TaskDtoInput;
import com.projectkubbank.dto.wrapped.DtoWrapper;
import com.projectkubbank.dto.wrapped.TaskDtoWrapper;
import com.projectkubbank.dto.wrapped.TaskMiniListDtoWrapper;
import com.projectkubbank.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class TaskController {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @Operation(summary = "Метод принимает задачу и складывает ее в очередь.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Задача в очереди",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoWrapper.class))),
            @ApiResponse(responseCode = "404", description = "Не удалось положить задачу в очередь",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoWrapper.class)))})
    @PostMapping("/AddTaskInQueue")
    public ResponseEntity<DtoWrapper> addTaskInQueue(
            @RequestBody List<TaskDtoInput> taskDtoInput) {
        DtoWrapper result = taskService.addTaskInQueue(taskDtoInput);
        return new ResponseEntity<>(result, result == null ? HttpStatus.BAD_REQUEST : HttpStatus.OK);
    }

    @Operation(summary = "Метод отправляет тремя разными потоками три задачи из очереди в БД.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Задачи успешно добавлены в БД",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoWrapper.class))),
            @ApiResponse(responseCode = "404", description = "Не удалось добавить задачи в БД",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoWrapper.class)))})
    @GetMapping("/AddThreeTasksToDB")
    public ResponseEntity<DtoWrapper> addThreeTasksToDB() {
        DtoWrapper result = taskService.addThreeTasksToDB();
        return new ResponseEntity<>(result, result == null ? HttpStatus.BAD_REQUEST : HttpStatus.OK);
    }

    @Operation(summary = "Метод возвращает все задачи из БД со следующими полями: id, title, status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Задачи успешно добавлены в БД",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TaskMiniListDtoWrapper.class))),
            @ApiResponse(responseCode = "404", description = "Не удалось добавить задачи в БД",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoWrapper.class)))})
    @GetMapping("/GetAllTasks")
    public ResponseEntity<TaskMiniListDtoWrapper> getAllTasks() {
        TaskMiniListDtoWrapper result = taskService.getAllTasks();
        return new ResponseEntity<>(result, result == null ? HttpStatus.BAD_REQUEST : HttpStatus.OK);
    }

    @Operation(summary = "Метод выдает задачу, с полным описанием, по переданному id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Задача получена по идентификатору",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TaskDtoWrapper.class))),
            @ApiResponse(responseCode = "404", description = "Не удалось получить задачу по идентификатору",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoWrapper.class)))})
    @GetMapping("/GetTaskById")
    public ResponseEntity<TaskDtoWrapper> getTaskById(
            @Parameter(description = "id") @RequestParam(value = "id", required = true) UUID id) {
        TaskDtoWrapper result = taskService.getTaskById(id);
        return new ResponseEntity<>(result, result == null ? HttpStatus.BAD_REQUEST : HttpStatus.OK);
    }

    @Operation(summary = "Метод изменяет задачу по id. Поля id и performer не изменяемы.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Задача изменена",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoWrapper.class))),
            @ApiResponse(responseCode = "404", description = "Не удалось изменить задачу",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoWrapper.class)))})
    @PutMapping("/UpdateTask")
    public ResponseEntity<DtoWrapper> updateTask(
            @RequestBody TaskDtoInput taskDtoInput) {
        DtoWrapper result = taskService.updateTask(taskDtoInput);
        return new ResponseEntity<>(result, result == null ? HttpStatus.BAD_REQUEST : HttpStatus.OK);
    }

    @Operation(summary = "Метод назначает работнику задачу")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Задача назначена работнику",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoWrapper.class))),
            @ApiResponse(responseCode = "404", description = "Не удалось назначить задачу работнику",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoWrapper.class)))})
    @GetMapping("/AddWorkerToTask")
    public ResponseEntity<DtoWrapper> addWorkerToTask(
            @Parameter(description = "workerId") @RequestParam(value = "workerId", required = true) UUID workerId,
            @Parameter(description = "taskId") @RequestParam(value = "taskId", required = true) UUID taskId) {
        DtoWrapper result = taskService.addWorkerToTask(workerId, taskId);
        return new ResponseEntity<>(result, result == null ? HttpStatus.BAD_REQUEST : HttpStatus.OK);
    }

}
