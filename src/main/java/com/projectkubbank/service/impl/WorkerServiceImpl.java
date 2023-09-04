package com.projectkubbank.service.impl;

import com.projectkubbank.dao.WorkerRepository;
import com.projectkubbank.dto.WorkerDto;
import com.projectkubbank.dto.WorkerDtoInput;
import com.projectkubbank.dto.wrapped.DtoWrapper;
import com.projectkubbank.dto.wrapped.WorkerDtoWrapped;
import com.projectkubbank.dto.wrapped.WorkerListDtoWrapped;
import com.projectkubbank.exceptions.WorkerListNotFoundException;
import com.projectkubbank.exceptions.WorkerNotFoundException;
import com.projectkubbank.model.Worker;
import com.projectkubbank.service.WorkerService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service(value = "workerService")
@Slf4j
public class WorkerServiceImpl implements WorkerService {

    private final ModelMapper modelMapper;
    private final WorkerRepository workerRepository;

    @Autowired
    public WorkerServiceImpl(ModelMapper modelMapper, WorkerRepository workerRepository) {
        this.modelMapper = modelMapper;
        this.workerRepository = workerRepository;
    }

    @Override
    @Transactional
    public DtoWrapper addWorker(WorkerDtoInput workerDtoInput) {
        try {
            Worker worker = modelMapper.map(workerDtoInput, Worker.class);
            if (workerRepository.addWorker(worker)) {
                return DtoWrapper.builder().message("Работник добавлен в БД").snackbarType("Info").success(true).build();
            }
            return DtoWrapper.builder().message("Не удалось добавить работника в БД").snackbarType("Info")
                    .success(false).build();
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            throw new NullPointerException(e.getLocalizedMessage());
        }
    }

    @Override
    @Transactional
    public DtoWrapper updateWorker(WorkerDtoInput workerDtoInput) {
        try {
            Worker worker = modelMapper.map(workerDtoInput, Worker.class);
            if (workerRepository.updateWorker(worker)) {
                return DtoWrapper.builder().message("Работник обновлен в БД").snackbarType("Info").success(true).build();
            }
            return DtoWrapper.builder().message("Работник не обновлен в БД").snackbarType("error").success(false).build();
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            throw new NullPointerException(e.getLocalizedMessage());
        }
    }

    @Override
    @Transactional
    public DtoWrapper deleteWorkerWithTasks(UUID workerId) {
        try {
            if (workerRepository.deleteWorkerWithTasks(workerId)) {
                return DtoWrapper.builder().message("Работник удален из БД, вместе с задачами").snackbarType("info")
                        .success(true).build();
            }
            return DtoWrapper.builder().message("Работник не удален из БД").snackbarType("error").success(false).build();
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            throw new NullPointerException(e.getLocalizedMessage());
        }
    }

    @Override
    @Transactional
    public DtoWrapper deleteWorkerWithOutTasks(UUID workerId) {
        try {
            if (workerRepository.deleteWorkerWithOutTasks(workerId)) {
                return DtoWrapper.builder().message("Работник удален из БД, без задач").snackbarType("info")
                        .success(true).build();
            }
            return DtoWrapper.builder().message("Работник не удален из БД").snackbarType("error").success(false).build();
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            throw new NullPointerException(e.getLocalizedMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public WorkerDtoWrapped getWorkerById(UUID workerId) {
        try {
            Worker workerById = workerRepository.getWorkerById(workerId);
            if (workerById != null) {
                return new WorkerDtoWrapped(new WorkerDto(workerById));
            }
            throw new WorkerNotFoundException();
        } catch (WorkerNotFoundException workerNotFoundException) {
            log.error(workerNotFoundException.getLocalizedMessage());
            throw workerNotFoundException;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public WorkerListDtoWrapped getAllWorker() {
        try {
            List<Worker> workers = workerRepository.getAllWorker();
            if (workers != null) {
                List<WorkerDto> workersDto = new ArrayList<>();
                workers.forEach(worker -> workersDto.add(new WorkerDto(worker)));
                return new WorkerListDtoWrapped(workersDto);
            }
            throw new WorkerListNotFoundException();
        } catch (WorkerListNotFoundException workerListNotFoundException) {
            log.error(workerListNotFoundException.getLocalizedMessage());
            throw workerListNotFoundException;
        }
    }
}
