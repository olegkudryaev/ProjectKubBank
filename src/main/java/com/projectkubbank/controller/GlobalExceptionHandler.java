package com.projectkubbank.controller;

import com.projectkubbank.dto.wrapped.TaskDtoWrapper;
import com.projectkubbank.dto.wrapped.TaskMiniListDtoWrapper;
import com.projectkubbank.dto.wrapped.WorkerDtoWrapped;
import com.projectkubbank.dto.wrapped.WorkerListDtoWrapped;
import com.projectkubbank.exceptions.TaskListNotFoundException;
import com.projectkubbank.exceptions.TaskNotFoundException;
import com.projectkubbank.exceptions.WorkerListNotFoundException;
import com.projectkubbank.exceptions.WorkerNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TaskListNotFoundException.class)
    public ResponseEntity<TaskMiniListDtoWrapper> handleTaskListNotFoundException(TaskListNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getTaskMiniListDtoWrapper());
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<TaskDtoWrapper> handleTaskNotFoundException(TaskNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getTaskDtoWrapper());
    }


    @ExceptionHandler(WorkerNotFoundException.class)
    public ResponseEntity<WorkerDtoWrapped> handleTaskNotFoundException(WorkerNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getWorkerDtoWrapped());
    }

    @ExceptionHandler(WorkerListNotFoundException.class)
    public ResponseEntity<WorkerListDtoWrapped> handleTaskNotFoundException(WorkerListNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getWorkerListDtoWrapped());
    }

}


