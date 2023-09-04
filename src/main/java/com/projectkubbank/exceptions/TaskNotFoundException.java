package com.projectkubbank.exceptions;

import com.projectkubbank.dto.wrapped.TaskDtoWrapper;
import lombok.Getter;

import java.io.Serial;

@Getter
public class TaskNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -6830495712383485209L;

    private final TaskDtoWrapper taskDtoWrapper;

    public TaskNotFoundException() {
        this.taskDtoWrapper = new TaskDtoWrapper(null);
    }

}
