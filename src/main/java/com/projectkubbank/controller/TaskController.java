package com.projectkubbank.controller;

import com.projectkubbank.controller.docs.TaskControllerDocs;
import com.projectkubbank.dto.TaskDtoInput;
import com.projectkubbank.dto.wrapped.DtoWrapper;
import com.projectkubbank.dto.wrapped.TaskDtoWrapper;
import com.projectkubbank.dto.wrapped.TaskMiniListDtoWrapper;
import com.projectkubbank.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class TaskController implements TaskControllerDocs {

    private final TaskService taskService;

    @Override
    @PostMapping("/AddTaskInQueue")
    public ResponseEntity<DtoWrapper> addTaskInQueue(
            @RequestBody List<TaskDtoInput> taskDtoInput) {
        DtoWrapper result = taskService.addTaskInQueue(taskDtoInput);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @Override
    @GetMapping("/AddThreeTasksToDB")
    public ResponseEntity<DtoWrapper> addThreeTasksToDB() {
        DtoWrapper result = taskService.addThreeTasksToDB();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    @GetMapping("/GetAllTasks")
    public ResponseEntity<TaskMiniListDtoWrapper> getAllTasks() {
        TaskMiniListDtoWrapper result = taskService.getAllTasks();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    @GetMapping("/GetTaskById/{taskId}")
    public ResponseEntity<TaskDtoWrapper> getTaskById(
            @PathVariable("taskId") UUID taskId) {
            TaskDtoWrapper result = taskService.getTaskById(taskId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    @PutMapping("/UpdateTask")
    public ResponseEntity<DtoWrapper> updateTask(
            @RequestBody TaskDtoInput taskDtoInput) {
        DtoWrapper result = taskService.updateTask(taskDtoInput);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    @PostMapping("/AddWorkerToTask/{workerId}/{taskId}")
    public ResponseEntity<DtoWrapper> addWorkerToTask(
            @PathVariable("workerId") UUID workerId,
            @PathVariable("taskId") UUID taskId) {
        DtoWrapper result = taskService.addWorkerToTask(workerId, taskId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
