package com.projectkubbank.exceptions;

import com.projectkubbank.dto.wrapped.TaskMiniListDtoWrapper;
import lombok.Getter;

import java.io.Serial;

@Getter
public class TaskListNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -6830495712383485209L;

    private final TaskMiniListDtoWrapper taskMiniListDtoWrapper;

    public TaskListNotFoundException() {
        this.taskMiniListDtoWrapper = new TaskMiniListDtoWrapper(null);
    }

}
