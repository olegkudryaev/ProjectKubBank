package com.projectkubbank.dto.wrapped;

import com.projectkubbank.dto.TaskMiniDto;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class TaskMiniListDtoWrapper extends Wrapper<TaskMiniDto> implements Serializable {
    @Serial
    private static final long serialVersionUID = 6081448266055455951L;

    public TaskMiniListDtoWrapper(List<TaskMiniDto> taskMiniDto) {
        if(taskMiniDto != null){
            this.setSuccess(true);
            this.setMessage("Все задачи получены");
            this.setSnackbarType("info");
            this.setContent(taskMiniDto);
        } else {
            this.setSuccess(false);
            this.setMessage("Задачи не получены");
            this.setSnackbarType("error");
            this.setContent(null);
        }
    }
}
