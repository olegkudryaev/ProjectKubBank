package com.projectkubbank.dto.wrapped;

import com.projectkubbank.dto.WorkerDto;

import java.io.Serial;
import java.io.Serializable;


public class WorkerDtoWrapped extends WrapperOne<WorkerDto> implements Serializable {
    @Serial
    private static final long serialVersionUID = -4481829047773384837L;

    public WorkerDtoWrapped(WorkerDto workerDtoDto) {
        if (workerDtoDto != null) {
            this.setSuccess(true);
            this.setMessage("Работник по id получен");
            this.setSnackbarType("info");
            this.setContent(workerDtoDto);
        } else {
            this.setSuccess(false);
            this.setMessage("Работник по id не получен");
            this.setSnackbarType("error");
            this.setContent(null);
        }
    }

}
