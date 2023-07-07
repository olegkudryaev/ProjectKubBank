package com.projectkubbank.dto.wrapped;

import com.projectkubbank.dto.TaskDto;

import java.io.Serial;
import java.io.Serializable;


public class TaskDtoWrapper extends WrapperOne<TaskDto> implements Serializable {
    @Serial
    private static final long serialVersionUID = -4113466395455781277L;

    public TaskDtoWrapper(TaskDto taskDto){
        this.setSuccess(true);
        this.setMessage("info");
        this.setSnackbarType("info");
        this.setContent(taskDto);
    }
}
