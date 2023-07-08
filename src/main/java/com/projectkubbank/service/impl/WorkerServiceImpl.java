package com.projectkubbank.service.impl;

import com.projectkubbank.dao.WorkerRepository;
import com.projectkubbank.dto.WorkerDto;
import com.projectkubbank.dto.WorkerDtoInput;
import com.projectkubbank.dto.wrapped.DtoWrapper;
import com.projectkubbank.dto.wrapped.WorkerDtoWrapped;
import com.projectkubbank.dto.wrapped.WorkerListDtoWrapped;
import com.projectkubbank.service.WorkerService;
import com.projectkubbank.model.Worker;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service(value = "workerService")
public class WorkerServiceImpl implements WorkerService {
    private static final Logger logger = LoggerFactory.getLogger(WorkerServiceImpl.class);
    private ModelMapper modelMapper;
    private WorkerRepository taskRepository;

    @Autowired
    public WorkerServiceImpl(ModelMapper modelMapper, WorkerRepository taskRepository) {
        this.modelMapper = modelMapper;
        this.taskRepository = taskRepository;
    }

    @Override
    public DtoWrapper addWorker(WorkerDtoInput workerDtoInput) {
        try {
            Worker worker = modelMapper.map(workerDtoInput, Worker.class);
            if (taskRepository.addWorker(worker)) {
                return DtoWrapper.builder().message("Работник добавлен в БД").snackbarType("Info").success(true).build();
            }
            return DtoWrapper.builder().message("Не удалось добавить работника в БД").snackbarType("Info")
                    .success(false).build();
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    @Override
    public DtoWrapper updateWorker(WorkerDtoInput workerDtoInput) {
        try {
            Worker worker = modelMapper.map(workerDtoInput, Worker.class);
            if (taskRepository.updateWorker(worker)) {
                return DtoWrapper.builder().message("Работник обновлен в БД").snackbarType("Info").success(true).build();
            }
            return DtoWrapper.builder().message("Работник не обновлен в БД").snackbarType("error").success(false).build();
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    @Override
    public DtoWrapper deleteWorkerWithTasks(UUID workerId) {
        try {
            if (taskRepository.deleteWorkerWithTasks(workerId)) {
                return DtoWrapper.builder().message("Работник удален из БД, вместе с задачами").snackbarType("info").success(true).build();
            }
            return DtoWrapper.builder().message("Работник не удален из БД").snackbarType("error").success(false).build();
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    @Override
    public DtoWrapper deleteWorkerWithOutTasks(UUID workerId) {
        try {
            if (taskRepository.deleteWorkerWithOutTasks(workerId)) {
                return DtoWrapper.builder().message("Работник удален из БД, без задач").snackbarType("info").success(true).build();
            }
            return DtoWrapper.builder().message("Работник не удален из БД").snackbarType("error").success(false).build();
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    @Override
    public WorkerDtoWrapped getWorkerById(UUID workerId) {
        try {
            Worker workerById = taskRepository.getWorkerById(workerId);
            if (workerById != null) {
                return new WorkerDtoWrapped(new WorkerDto(workerById));
            }
            return new WorkerDtoWrapped(null);
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            throw new RuntimeException(e.getLocalizedMessage());
        }

    }

    @Override
    public WorkerListDtoWrapped getAllWorker() {
        try {
            List<Worker> workers = taskRepository.getAllWorker();
            if (workers != null) {
                List<WorkerDto> workersDto = new ArrayList<>();
                workers.forEach(worker -> workersDto.add(new WorkerDto(worker)));
                return new WorkerListDtoWrapped(workersDto);
            }
            return new WorkerListDtoWrapped(null);
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }
}
