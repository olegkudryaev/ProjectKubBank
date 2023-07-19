package com.projectkubbank.dto.wrapped;

import com.projectkubbank.dto.WorkerDto;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class WorkerListDtoWrapped extends Wrapper<WorkerDto> implements Serializable {
    @Serial
    private static final long serialVersionUID = 6081448266055455951L;

    public WorkerListDtoWrapped(List<WorkerDto> workerDto) {
        if (workerDto != null) {
            this.setSuccess(true);
            this.setMessage("Все работники получены");
            this.setSnackbarType("info");
            this.setContent(workerDto);
        } else {
            this.setSuccess(false);
            this.setMessage("Все работники не получены");
            this.setSnackbarType("error");
            this.setContent(null);
        }
    }

    public WorkerListDtoWrapped() {
    }
}
