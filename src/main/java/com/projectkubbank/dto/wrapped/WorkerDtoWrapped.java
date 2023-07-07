package com.projectkubbank.dto.wrapped;

import com.projectkubbank.dto.WorkerDto;

import java.io.Serial;
import java.io.Serializable;


public class WorkerDtoWrapped extends WrapperOne<WorkerDto> implements Serializable {
    @Serial
    private static final long serialVersionUID = -4481829047773384837L;

    public WorkerDtoWrapped(WorkerDto workerDtoDto){
        this.setSuccess(true);
        this.setMessage("info");
        this.setSnackbarType("info");
        this.setContent(workerDtoDto);
    }

}
