package com.projectkubbank.dto.wrapped;

import com.projectkubbank.dto.WorkerDto;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class WorkerListDtoWrapped extends Wrapper<WorkerDto> implements Serializable {
    @Serial
    private static final long serialVersionUID = 6081448266055455951L;

    public WorkerListDtoWrapped(List<WorkerDto> workerDto) {
        this.setSuccess(true);
        this.setMessage("info");
        this.setSnackbarType("info");
        this.setContent(workerDto);
    }
}
