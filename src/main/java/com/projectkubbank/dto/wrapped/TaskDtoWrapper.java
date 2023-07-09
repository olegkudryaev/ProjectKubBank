package com.projectkubbank.dto.wrapped;

import com.projectkubbank.dto.TaskDto;

import java.io.Serial;
import java.io.Serializable;

public class TaskDtoWrapper extends WrapperOne<TaskDto> implements Serializable {
    @Serial
    private static final long serialVersionUID = -4113466395455781277L;

    public TaskDtoWrapper(TaskDto taskDto) {
        if (taskDto != null) {
            this.setSuccess(true);
            this.setMessage("Задача по id получена");
            this.setSnackbarType("info");
            this.setContent(taskDto);
        } else {
            this.setSuccess(false);
            this.setMessage("Задача по id не получена");
            this.setSnackbarType("error");
            this.setContent(null);
        }
    }

}
