package com.projectkubbank.exceptions;

import com.projectkubbank.dto.wrapped.WorkerListDtoWrapped;
import lombok.Getter;

import java.io.Serial;

@Getter
public class WorkerListNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -2796679526394011515L;

    private final WorkerListDtoWrapped workerListDtoWrapped;

    public WorkerListNotFoundException() {
        this.workerListDtoWrapped = new WorkerListDtoWrapped(null);
    }

}
