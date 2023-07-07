package com.projectkubbank.service;

import com.projectkubbank.dto.WorkerDtoInput;
import com.projectkubbank.dto.wrapped.DtoWrapper;
import com.projectkubbank.dto.wrapped.WorkerDtoWrapped;
import com.projectkubbank.dto.wrapped.WorkerListDtoWrapped;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface WorkerService {

    DtoWrapper addWorker(WorkerDtoInput workerDtoInput);

    DtoWrapper updateWorker(WorkerDtoInput workerDtoInput);

    DtoWrapper deleteWorker(UUID workerId);

    WorkerDtoWrapped getWorkerById(UUID workerId);

    WorkerListDtoWrapped getAllWorker();
}
