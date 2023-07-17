package com.projectkubbank.service.impl;

import com.projectkubbank.dao.TaskRepository;
import com.projectkubbank.dao.WorkerRepository;
import com.projectkubbank.dto.WorkerDto;
import com.projectkubbank.dto.WorkerDtoInput;
import com.projectkubbank.dto.wrapped.DtoWrapper;
import com.projectkubbank.dto.wrapped.WorkerDtoWrapped;
import com.projectkubbank.dto.wrapped.WorkerListDtoWrapped;
import com.projectkubbank.exceptions.WorkerListNotFoundException;
import com.projectkubbank.exceptions.WorkerNotFoundException;
import com.projectkubbank.model.Worker;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringJUnitConfig(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WorkerServiceImplTest {
    @Spy
    ModelMapper modelMapper = Mockito.spy(ModelMapper.class);
    @Mock
    WorkerRepository workerRepository = Mockito.mock(WorkerRepository.class);
    @Mock
    TaskRepository taskRepository = Mockito.mock(TaskRepository.class);

    @InjectMocks
    WorkerServiceImpl workerServiceImpl;

    @Test
    void whenWeAddWorkerToDBAndGetSuccess(){
        //given
        Worker worker = createWorker();
        WorkerDtoInput workerDtoInput = createWorkerDtoInput();
        DtoWrapper expected = DtoWrapper.builder().message("Работник добавлен в БД").snackbarType("Info")
                .success(true).build();
        //when
        Mockito.when(workerRepository.addWorker(worker)).thenReturn(true);
        DtoWrapper result = workerServiceImpl.addWorker(workerDtoInput);
        //then
        Assertions.assertEquals(expected, result);
    }

    @Test
    void whenWeAddWorkerToDBAndGetFailure(){
        //given
        Worker worker = createWorker();
        WorkerDtoInput workerDtoInput = createWorkerDtoInput();
        DtoWrapper expected = DtoWrapper.builder().message("Не удалось добавить работника в БД").snackbarType("Info")
                .success(false).build();
        //when
        Mockito.when(workerRepository.addWorker(worker)).thenReturn(false);
        DtoWrapper result = workerServiceImpl.addWorker(workerDtoInput);
        //then
        Assertions.assertEquals(expected, result);
    }

    @Test
    void whenWeAddWorkerToDBAndGetException(){
        //given
        WorkerDtoInput workerDtoInput = createWorkerDtoInput();
        //when
        Mockito.when(workerRepository.addWorker(createWorker())).thenThrow(RuntimeException.class);
        //then
        Assertions.assertThrows(RuntimeException.class, () -> workerServiceImpl.addWorker(workerDtoInput));
    }

    @Test
    void whenWeUpdateWorkerToDBAndGetSuccess(){
        //given
        Worker worker = createWorker();
        WorkerDtoInput workerDtoInput = createWorkerDtoInput();
        DtoWrapper expected = DtoWrapper.builder().message("Работник обновлен в БД").snackbarType("Info")
                .success(true).build();
        //when
        Mockito.when(workerRepository.updateWorker(worker)).thenReturn(true);
        DtoWrapper result = workerServiceImpl.updateWorker(workerDtoInput);
        //then
        Assertions.assertEquals(expected, result);
    }

    @Test
    void whenWeUpdateWorkerToDBAndGetFailure(){
        //given
        Worker worker = createWorker();
        WorkerDtoInput workerDtoInput = createWorkerDtoInput();
        DtoWrapper expected = DtoWrapper.builder().message("Работник не обновлен в БД").snackbarType("error")
                .success(false).build();
        //when
        Mockito.when(workerRepository.updateWorker(worker)).thenReturn(false);
        DtoWrapper result = workerServiceImpl.updateWorker(workerDtoInput);
        //then
        Assertions.assertEquals(expected, result);
    }

    @Test
    void whenWeUpdateWorkerToDBAndGetException(){
        //given
        WorkerDtoInput workerDtoInput = createWorkerDtoInput();
        //when
        Mockito.when(workerRepository.updateWorker(createWorker())).thenThrow(RuntimeException.class);
        //then
        Assertions.assertThrows(RuntimeException.class, () -> workerServiceImpl.updateWorker(workerDtoInput));
    }

    @Test
    void whenWeDeleteWorkerWithTaskFromDBAndGetSuccess(){
        //given
        UUID workerId = UUID.randomUUID();
        DtoWrapper expected = DtoWrapper.builder().message("Работник удален из БД, вместе с задачами").snackbarType("info")
                .success(true).build();
        //when
        Mockito.when(workerRepository.deleteWorkerWithTasks(workerId)).thenReturn(true);
        DtoWrapper result = workerServiceImpl.deleteWorkerWithTasks(workerId);
        //then
        Assertions.assertEquals(expected, result);
    }

    @Test
    void whenWeDeleteWorkerWithTaskFromDBAndGetFailure(){
        //given
        UUID workerId = UUID.randomUUID();
        DtoWrapper expected = DtoWrapper.builder().message("Работник не удален из БД").snackbarType("error")
                .success(false).build();
        //when
        Mockito.when(workerRepository.deleteWorkerWithTasks(workerId)).thenReturn(false);
        DtoWrapper result = workerServiceImpl.deleteWorkerWithTasks(workerId);
        //then
        Assertions.assertEquals(expected, result);
    }

    @Test
    void whenWeDeleteWorkerWithTaskFromDBAndGetException(){
        //given
        UUID workerId = UUID.randomUUID();
        //when
        Mockito.when(workerRepository.deleteWorkerWithTasks(workerId)).thenThrow(RuntimeException.class);
        //then
        Assertions.assertThrows(RuntimeException.class, () -> workerServiceImpl.deleteWorkerWithTasks(workerId));
    }

    @Test
    void whenWeDeleteWorkerWithOutTaskFromDBAndGetSuccess(){
        //given
        UUID workerId = UUID.randomUUID();
        DtoWrapper expected = DtoWrapper.builder().message("Работник удален из БД, без задач").snackbarType("info")
                .success(true).build();
        //when
        Mockito.when(workerRepository.deleteWorkerWithOutTasks(workerId)).thenReturn(true);
        DtoWrapper result = workerServiceImpl.deleteWorkerWithOutTasks(workerId);
        //then
        Assertions.assertEquals(expected, result);
    }

    @Test
    void whenWeDeleteWorkerWithOutTaskFromDBAndGetFailure(){
        //given
        UUID workerId = UUID.randomUUID();
        DtoWrapper expected = DtoWrapper.builder().message("Работник не удален из БД").snackbarType("error")
                .success(false).build();
        //when
        Mockito.when(workerRepository.deleteWorkerWithOutTasks(workerId)).thenReturn(false);
        DtoWrapper result = workerServiceImpl.deleteWorkerWithOutTasks(workerId);
        //then
        Assertions.assertEquals(expected, result);
    }

    @Test
    void whenWeDeleteWorkerWithOutTaskFromDBAndGetException(){
        //given
        UUID workerId = UUID.randomUUID();
        //when
        Mockito.when(workerRepository.deleteWorkerWithOutTasks(workerId)).thenThrow(RuntimeException.class);
        //then
        Assertions.assertThrows(RuntimeException.class, () -> workerServiceImpl.deleteWorkerWithOutTasks(workerId));
    }

    @Test
    void whenWeGetWorkerByIdFromDBAndGetSuccess(){
        //given
        UUID workerId = UUID.randomUUID();
        Worker worker = createWorker();
        WorkerDtoWrapped expected = new WorkerDtoWrapped(new WorkerDto(worker));
        //when
        Mockito.when(workerRepository.getWorkerById(workerId)).thenReturn(worker);
        WorkerDtoWrapped result = workerServiceImpl.getWorkerById(workerId);
        //then
        Assertions.assertEquals(expected, result);
        //then
    }

    @Test
    void whenWeGetWorkerByIdFromDBAndGetFailure(){
        //given
        UUID workerId = UUID.randomUUID();
        //when
        Mockito.when(workerRepository.getWorkerById(workerId)).thenReturn(null);
        //then
        Assertions.assertThrows(WorkerNotFoundException.class, () -> workerServiceImpl.getWorkerById(workerId));
    }

    @Test
    void whenWeGetWorkerByIdFromDBAndGetException(){
        //given
        UUID workerId = UUID.randomUUID();
        //when
        Mockito.when(workerRepository.getWorkerById(workerId)).thenThrow(RuntimeException.class);
        //then
        Assertions.assertThrows(RuntimeException.class, () -> workerServiceImpl.getWorkerById(workerId));
    }

    @Test
    void whenWeGetAllWorkerFromDBAndGetSuccess(){
        //given
        List<Worker> workersList = createListWorkers();
        List<WorkerDto> workersListDto = createListWorkersDto();
        WorkerListDtoWrapped expected = new WorkerListDtoWrapped(workersListDto);
        //when
        Mockito.when(workerRepository.getAllWorker()).thenReturn(workersList);
        WorkerListDtoWrapped result = workerServiceImpl.getAllWorker();
        //then
        Assertions.assertEquals(expected, result);
    }

    @Test
    void whenWeGetAllWorkerFromDBAndGetFailure(){
        //given
        //when
        Mockito.when(workerRepository.getAllWorker()).thenReturn(null);
        //then
        Assertions.assertThrows(WorkerListNotFoundException.class, () -> workerServiceImpl.getAllWorker());
    }

    @Test
    void whenWeGetAllWorkerFromDBAndGetException(){
        //given
        //when
        Mockito.when(workerRepository.getAllWorker()).thenThrow(RuntimeException.class);
        //then
        Assertions.assertThrows(RuntimeException.class, () -> workerServiceImpl.getAllWorker());
    }

    private WorkerDtoInput createWorkerDtoInput(){
        WorkerDtoInput workerDtoInput = new WorkerDtoInput();
        workerDtoInput.setId(UUID.fromString("8a8666a1-244d-4121-bb05-b27314de29f0"));
        workerDtoInput.setName("firstName");
        workerDtoInput.setPosition("lastName");
        workerDtoInput.setAvatar("email");
        return workerDtoInput;
    }

    private Worker createWorker(){
        Worker worker = new Worker();
        worker.setId(UUID.fromString("8a8666a1-244d-4121-bb05-b27314de29f0"));
        worker.setName("firstName");
        worker.setPosition("lastName");
        worker.setAvatar("email");
        return worker;
    }

    private List<Worker> createListWorkers(){
        List<Worker> workers = new ArrayList<>();
        workers.add(createWorker());
        workers.add(createWorker());
        return workers;
    }

    private List<WorkerDto> createListWorkersDto(){
        List<Worker> workerList = createListWorkers();
        List<WorkerDto> workerListDto = new ArrayList<>();
        workerList.forEach(worker -> workerListDto.add(new WorkerDto(worker)));
        return workerListDto;
    }
}
