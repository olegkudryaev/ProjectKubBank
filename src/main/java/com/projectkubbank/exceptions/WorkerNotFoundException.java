package com.projectkubbank.exceptions;

import com.projectkubbank.dto.wrapped.WorkerDtoWrapped;
import lombok.Getter;

import java.io.Serial;

@Getter
public class WorkerNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -2796679526394011515L;

    private WorkerDtoWrapped workerDtoWrapped;

    public WorkerNotFoundException() {
        this.workerDtoWrapped = new WorkerDtoWrapped(null);
    }

}
