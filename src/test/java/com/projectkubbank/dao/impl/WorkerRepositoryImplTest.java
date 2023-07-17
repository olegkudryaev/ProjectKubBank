package com.projectkubbank.dao.impl;

import com.projectkubbank.containers.AbstractContainerBaseTest;
import com.projectkubbank.model.Task;
import com.projectkubbank.model.Worker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Transactional
@SpringBootTest
public class WorkerRepositoryImplTest extends AbstractContainerBaseTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    WorkerRepositoryImpl workerRepositoryImpl;

    @Test
    public void whenWeAddWorkerToDBAndGetSuccess(){
        // given
        Worker worker = createWorker();
        // when
        boolean result = workerRepositoryImpl.addWorker(worker);
        // then
        Assertions.assertTrue(result);
    }

    @Test
    @Disabled // TODO: не понятно как задействоавать код в блоке else
    public void whenWeAddWorkerToDBAndGetFailure(){
        // given
        Worker worker = createWorker();
        worker.setName(null);
        // when
        boolean result = workerRepositoryImpl.addWorker(worker);
        // then
        Assertions.assertFalse(result);
    }

    @Test
    public void whenWeAddWorkerToDBAndGetException(){
        // given
        Worker worker = createWorker();
        worker.setName(null);
        // when
        // then
        Assertions.assertThrows(DataAccessException.class, () -> workerRepositoryImpl.addWorker(worker));
    }

    @Test
    public void whenWeUpdateWorkerToDBAndGetSuccess(){
        // given
        Worker worker = createWorker();
        worker.setId(UUID.fromString("ea89ae04-1c81-11ee-be56-0242ac120002"));
        // when
        boolean result = workerRepositoryImpl.updateWorker(worker);
        // then
        Assertions.assertTrue(result);
    }

    @Test
    public void whenWeUpdateWorkerToDBAndGetFailure(){
        // given
        Worker worker = createWorker();
        // when
        boolean result = workerRepositoryImpl.updateWorker(worker);
        // then
        Assertions.assertFalse(result);
    }

    @Test
    public void whenWeUpdateWorkerToDBAndGetException(){
        // given
        Worker worker = createWorker();
        worker.setId(UUID.fromString("f4d3e8fc-1c81-11ee-be56-0242ac120002"));//UUID существующего работника
        worker.setName(null);
        // when
        // then
        Assertions.assertThrows(DataAccessException.class, () -> workerRepositoryImpl.updateWorker(worker));
    }

    @Test
    public void whenWeDeleteWorkerWithTasksFromDBAndGetSuccess(){
        // given
        UUID workerId = UUID.fromString("f4d3e8fc-1c81-11ee-be56-0242ac120002");
        // when
        boolean result = workerRepositoryImpl.deleteWorkerWithTasks(workerId);
        // then
        Assertions.assertTrue(result);
    }

    @Test
    public void whenWeDeleteWorkerWithTasksFromDBAndGetFailure(){
        // given
        UUID workerId = UUID.fromString("f4d3e8fc-1c81-11ee-be56-0242ac121111");
        // when
        boolean result = workerRepositoryImpl.deleteWorkerWithTasks(workerId);
        // then
        Assertions.assertFalse(result);
    }

    @Test
    @Disabled // TODO: не понятно как задействоавать код
    public void whenWeDeleteWorkerWithTasksFromDBAndGetException(){
        // given
        UUID workerId = null;
        // when
        // then
        Assertions.assertThrows(DataAccessException.class, () -> workerRepositoryImpl.deleteWorkerWithTasks(workerId));
    }

    @Test
    public void whenWeDeleteWorkerWithOutTasksFromDBAndGetSuccess(){
        // given
        UUID workerId = UUID.fromString("f4d3e8fc-1c81-11ee-be56-0242ac120002");
        // when
        boolean result = workerRepositoryImpl.deleteWorkerWithOutTasks(workerId);
        // then
        Assertions.assertTrue(result);
    }

    @Test
    public void whenWeDeleteWorkerWithOutTasksFromDBAndGetFailure(){
        // given
        UUID workerId = UUID.fromString("f4d3e8fc-1c81-11ee-be56-0242ac121111");
        // when
        boolean result = workerRepositoryImpl.deleteWorkerWithOutTasks(workerId);
        // then
        Assertions.assertFalse(result);
    }

    @Test
    @Disabled // TODO: не понятно как задействоавать код
    public void whenWeDeleteWorkerWithOutTasksFromDBAndGetException(){
        // given
        UUID workerId = null;
        // when
        // then
        Assertions.assertThrows(DataAccessException.class, () -> workerRepositoryImpl.deleteWorkerWithOutTasks(workerId));
    }

    @Test
    public void whenWeGetWorkerByIdFromDBAndGetSuccess(){
        // given
        //существующий UUID рабочего
        UUID workerId = UUID.fromString("f4d3e8fc-1c81-11ee-be56-0242ac120002");
        //существующий в БД рабочий
        Worker worker = createExistWorker();
        // when
        Worker result = workerRepositoryImpl.getWorkerById(workerId);
        // then
        Assertions.assertEquals(worker, result);
    }

    @Test
    @Disabled // TODO: не понятно как задействоавать код
    public void whenWeGetWorkerByIdFromDBAndGetFailure(){
        // given
        UUID workerId = UUID.fromString("f4d3e8fc-1c81-11ee-be56-0242ac121111");
        // when
        Worker result = workerRepositoryImpl.getWorkerById(workerId);
        // then
        Assertions.assertEquals(workerId, result);
    }

    @Test
    public void whenWeGetWorkerByIdFromDBAndGetException(){
        // given
        UUID workerId = null;
        // when
        // then
        Assertions.assertThrows(DataAccessException.class, () -> workerRepositoryImpl.getWorkerById(workerId));
    }

    @Test
    @Order(1)
    public void whenWeGetAllWorkerFromDBAndGetSuccess(){
        // given
        List<Worker> expected = createListWithAllWorker();
        // when
        List<Worker> result = workerRepositoryImpl.getAllWorker();
        // then
        Assertions.assertEquals(expected, result);
    }

    @Test
    @Disabled // TODO: не понятно как задействоавать код
    public void whenWeGetAllWorkerFromDBAndGetFailure(){
        // given
        // when
        // then
    }

    @Test
    @Disabled // TODO: не понятно как задействоавать код
    public void whenWeGetAllWorkerFromDBAndGetException(){
        // given
        // when
        // then
    }

    private Worker createWorker(){
        return Worker.builder()
                .id(UUID.randomUUID())
                .name("Ivan Ivanovich")
                .position("Manager")
                .avatar("avatar")
                .taskList(new ArrayList<>())
                .build();
    }

    private Worker createExistWorker(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Worker worker = Worker.builder()
                .id(UUID.fromString("f4d3e8fc-1c81-11ee-be56-0242ac120002"))
                .name("John Smith")
                .position("Manager")
                .avatar("http://example.com/avatar.png")
                .taskList(new ArrayList<>())
                .taskList(new ArrayList<>())
                .build();
        Task task = Task.builder()
                .id(UUID.fromString("cebf023c-83ef-49f4-a430-97d6de0cef7e"))
                .title("Task title2")
                .description("Task description2")
                .time(LocalDateTime.parse("2023-07-25 12:00:00", formatter))
                .status("In progress")
                .performer(UUID.fromString("f4d3e8fc-1c81-11ee-be56-0242ac120002"))
                .build();
        worker.getTaskList().add(task);
        return worker;
    }

    private List<Worker> createListWithAllWorker(){
        List<Worker> workerList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Worker worker1 = Worker.builder()
                .id(UUID.fromString("f4d3e8fc-1c81-11ee-be56-0242ac120002"))
                .name("John Smith")
                .position("Manager")
                .avatar("http://example.com/avatar.png")
                .taskList(new ArrayList<>())
                .build();
        Task task1 = Task.builder()
                .id(UUID.fromString("cebf023c-83ef-49f4-a430-97d6de0cef7e"))
                .title("Task title2")
                .description("Task description2")
                .time(LocalDateTime.parse("2023-07-25 12:00:00", formatter))
                .status("In progress")
                .performer(UUID.fromString("f4d3e8fc-1c81-11ee-be56-0242ac120002"))
                .build();
        worker1.getTaskList().add(task1);

        Worker worker2 = Worker.builder()
                .id(UUID.fromString("ea89ae04-1c81-11ee-be56-0242ac120002"))
                .name("John Doe")
                .position("Developer")
                .avatar("http://example.com/avatar.png")
                .taskList(new ArrayList<>())
                .build();
        Task task2 = Task.builder()
                .id(UUID.fromString("6f7907f0-2609-11ec-9621-0242ac130002"))
                .title("Task title1")
                .description("Task description1")
                .time(LocalDateTime.parse("2023-07-22 12:00:00", formatter))
                .status("In progress")
                .performer(UUID.fromString("ea89ae04-1c81-11ee-be56-0242ac120002"))
                .build();
        worker2.getTaskList().add(task2);

        Worker worker3 = Worker.builder()
                .id(UUID.fromString("f925e2e8-1c81-11ee-be56-0242ac120002"))
                .name("John Kent")
                .position("Boss")
                .avatar("http://example.com/avatar.png")
                .taskList(new ArrayList<>())
                .build();

        workerList.add(worker1);
        workerList.add(worker2);
        workerList.add(worker3);
        return workerList;
    }
}
