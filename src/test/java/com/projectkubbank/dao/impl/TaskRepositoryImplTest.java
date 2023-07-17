package com.projectkubbank.dao.impl;

import com.projectkubbank.containers.AbstractContainerBaseTest;
import com.projectkubbank.dto.wrapped.DtoWrapper;
import com.projectkubbank.model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
@SpringBootTest
public class TaskRepositoryImplTest extends AbstractContainerBaseTest {


    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    TaskRepositoryImpl taskRepositoryImpl;

    @Value("${countTaskInDBTest}")
    private int countTaskInDBTest;

    @Test
    public void whenWeAddTaskToDBAndGetSuccess(){
        // given
        Task task = createTask();
        task.setId(UUID.randomUUID());
        DtoWrapper expected = DtoWrapper.builder().message("Задача добавлена в БД").snackbarType("info")
                .success(true).build();
        // when
        DtoWrapper result = taskRepositoryImpl.addTask(task);
        // then
        Assertions.assertEquals(expected, result);
    }

    @Test
    @Disabled // TODO: не понятно как задействоавать код в блоке else
    public void whenWeAddTaskToDBAndGetFailure(){
        // given
        Task task = createTask();
        DtoWrapper expected = DtoWrapper.builder().message("Задача не добавлена в БД").snackbarType("error")
                .success(false).build();
        // when
        DtoWrapper result = taskRepositoryImpl.addTask(task);
        // then
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void whenWeAddTaskToDBAndGetException(){
        // given
        Task task = createTaskWithNullParams();
        // when
        // then
        Assertions.assertThrows(DataAccessException.class, () -> taskRepositoryImpl.addTask(task));
    }

    @Test
    public void whenWeGetAllTasksToDBAndGetSuccess(){
        // given
        List<Task> taskList = createTaskList();
        // when
        Optional<List<Task>> result = taskRepositoryImpl.getAllTasks();
        // then
        Assertions.assertEquals(taskList.size(), result.get().size());
    }

    @Test
    @Disabled // непонятно как задействоавать код в блоке
    public void whenWeGetAllTasksToDBAndGetFailure(){
    }

    @Test
    @Disabled // непонятно как задействоавать код в блоке
    public void whenWeGetAllTasksToDBAndGetException(){
    }

    @Test
    public void whenWeGetTaskByIdToDBAndGetSuccess(){
        // given
        Task task = createTask();
        // when
        Optional<Task> result = taskRepositoryImpl.getTaskById(task.getId());
        // then
        Assertions.assertEquals(task.getId(), result.get().getId());
    }

    @Test
    public void whenWeGetTaskByIdToDBAndGetFailure(){
        // given
        Task task = createTask();
        task.setId(UUID.randomUUID());
        // when
        Optional<Task> result = taskRepositoryImpl.getTaskById(task.getId());
        // then
        Assertions.assertEquals(Optional.empty(), result);
    }

    @Test
    @Disabled // непонятно как задействоавать код в блоке
    public void whenWeGetTaskByIdToDBAndGetException(){
        // given
        Task task = createTask();
        task.setId(null);
        // when
        // then
        Assertions.assertThrows(DataAccessException.class, () -> taskRepositoryImpl.getTaskById(task.getId()));
    }

    @Test
    public void whenWeUpdateTaskToDBAndGetSuccess(){
        // given
        Task task = createTask();
        // when
        boolean result = taskRepositoryImpl.updateTask(task);
        // then
        Assertions.assertTrue(result);
    }

    @Test
    public void whenWeUpdateTaskToDBAndGetFailure(){
        // given
        Task task = createTask();
        task.setId(UUID.randomUUID());
        // when
        boolean result = taskRepositoryImpl.updateTask(task);
        // then
        Assertions.assertFalse(result);
    }

    @Test
    public void whenWeUpdateTaskToDBAndGetException(){
        // given
        Task task = createTask();
        task.setTitle(null);
        // when
        // then
        Assertions.assertThrows(DataAccessException.class, () -> taskRepositoryImpl.updateTask(task));
    }

    @Test
    public void whenWeAddWorkerToTaskToDBAndGetSuccess(){
        // given
        //UUID рабочего и задачи существуют в БД
        UUID workerId = UUID.fromString("f4d3e8fc-1c81-11ee-be56-0242ac120002");
        UUID taskId = UUID.fromString("6f7907f0-2609-11ec-9621-0242ac130002");
        // when
        boolean result = taskRepositoryImpl.addWorkerToTask(workerId, taskId);
        // then
        Assertions.assertTrue(result);
        // then
    }

    @Test
    public void whenWeAddWorkerToTaskToDBAndGetFailure(){
        // given
        //UUID рабочего отсутствует в БД
        UUID workerId = UUID.fromString("f4d3e8fc-1c81-11ee-be56-0242ac120002");
        UUID taskId = UUID.fromString("6f7907f0-2609-11ec-9621-0242ac131111");
        // when
        boolean result = taskRepositoryImpl.addWorkerToTask(workerId, taskId);
        // then
        Assertions.assertFalse(result);
        // then
    }

    @Test
    @Disabled // непонятно как задействоавать код в блоке
    public void whenWeAddWorkerToTaskToDBAndGetException(){
        // given
        //UUID рабочего отсутствует в БД
        UUID workerId = null;
        UUID taskId = null;;
        // when
        boolean result = taskRepositoryImpl.addWorkerToTask(workerId, taskId);
        // then
        Assertions.assertTrue(result);
        // then
    }

    private Task createTask(){
        Task task = new Task();
        task.setId(UUID.fromString("6f7907f0-2609-11ec-9621-0242ac130002"));//данный id присутствует в бд.
        task.setTitle(" ");
        task.setDescription(" ");
        task.setTime(LocalDateTime.now());
        task.setStatus(" ");
        return task;
    }

    private Task createTaskWithNullParams(){
        Task task = new Task();
        task.setId(UUID.fromString("6f7907f0-2609-11ec-9621-0242ac130002"));
        task.setTitle(null);
        task.setDescription(" ");
        task.setTime(LocalDateTime.now());
        task.setStatus(" ");
        return task;
    }

    private List<Task> createTaskList(){
        List<Task> taskList = new ArrayList<>();
        for (int i = 0; i < countTaskInDBTest; i++) {
            taskList.add(createTask());
        }
        return taskList;
    }

}
