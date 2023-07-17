package com.projectkubbank.service.impl;

import com.projectkubbank.common.TaskQueue;
import com.projectkubbank.dao.TaskRepository;
import com.projectkubbank.dto.TaskDto;
import com.projectkubbank.dto.TaskDtoInput;
import com.projectkubbank.dto.TaskMiniDto;
import com.projectkubbank.dto.wrapped.DtoWrapper;
import com.projectkubbank.dto.wrapped.TaskDtoWrapper;
import com.projectkubbank.dto.wrapped.TaskMiniListDtoWrapper;
import com.projectkubbank.exceptions.TaskListNotFoundException;
import com.projectkubbank.exceptions.TaskNotFoundException;
import com.projectkubbank.model.Task;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.mock;

@SpringJUnitConfig(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
@TestPropertySource("/application.properties")
class TaskServiceImplTest {

    @Value("${count.pool.threads}")
    private int countPoolThreads;

    @Mock
    TaskQueue taskQueue = mock(TaskQueue.class);
    @Spy
    ModelMapper modelMapper = Mockito.spy(ModelMapper.class);
    @Mock
    TaskRepository taskRepository = mock(TaskRepository.class);
    @InjectMocks
    TaskServiceImpl taskServiceImpl;


    @Test
    void whenWeAddOneExistedTaskInQueue() {
        // Given
        List<TaskDtoInput> taskDtoInputList = createListTasks(1);
        // When
        DtoWrapper result = taskServiceImpl.addTaskInQueue(taskDtoInputList);
        // Then
        Assertions.assertTrue(result.isSuccess());
        Assertions.assertEquals("Задачи в очереди", result.getMessage());
        Assertions.assertEquals("Info", result.getSnackbarType());
    }

    @Test
    void whenWeAddManyExistedTaskInQueue() {
        // Given
        List<TaskDtoInput> taskDtoInputList = createListTasks(3);
        // When
        DtoWrapper result = taskServiceImpl.addTaskInQueue(taskDtoInputList);
        // Then
        Assertions.assertTrue(result.isSuccess());
        Assertions.assertEquals("Задачи в очереди", result.getMessage());
        Assertions.assertEquals("Info", result.getSnackbarType());
    }

    @Test
    void whenWeAddManyExistedTaskInQueueAndGetRuntimeException() {
        // Given
        List<TaskDtoInput> taskDtoInputList = createListTasks(3);
        // When
        Mockito.when(taskServiceImpl.addTaskInQueue(taskDtoInputList)).thenThrow(RuntimeException.class);
        // Then
        Assertions.assertThrows(RuntimeException.class, () -> taskServiceImpl.addTaskInQueue(taskDtoInputList));
    }

    @Test
    void whenWeAddTasksToDBAndGetSuccess() {
        // Given
        Task task = createTask();
        // When
        ReflectionTestUtils.setField(taskServiceImpl, "countPoolThreads", countPoolThreads);
        Mockito.when(taskQueue.removeItem()).thenReturn(task);
        Mockito.when(taskRepository.addTask(task)).thenReturn(new DtoWrapper());
        DtoWrapper result = taskServiceImpl.addThreeTasksToDB();
        // Then
        Assertions.assertTrue(result.isSuccess());
        Assertions.assertEquals("Задача в БД", result.getMessage());
        Assertions.assertEquals("Info", result.getSnackbarType());
    }

    @Test
    void whenWeAddTasksToDBAndGetException() {
        // Given
        // When
        ReflectionTestUtils.setField(taskServiceImpl, "countPoolThreads", 0);
        // Then
        Assertions.assertThrows(RuntimeException.class, () -> taskServiceImpl.addThreeTasksToDB());
    }

    @Test
    void whenWeGetAllTasks(){
        // Given
        Optional<List<Task>> taskListOpt = createTaskListOpt();
        Optional<List<TaskMiniDto>> taskMiniListOpt = createTaskMiniListOpt();
        TaskMiniListDtoWrapper expected = new TaskMiniListDtoWrapper(taskMiniListOpt.get());
        // When
        Mockito.when(taskRepository.getAllTasks()).thenReturn(taskListOpt);
        TaskMiniListDtoWrapper result = taskServiceImpl.getAllTasks();
        // Then
        Assertions.assertEquals(expected, result);
    }

    @Test
    void whenWeExpectedAllTasksButGotNull(){
        // Given
        // When
        Mockito.when(taskRepository.getAllTasks()).thenReturn(null);
        // Then
        Assertions.assertThrows(TaskListNotFoundException.class, () -> taskServiceImpl.getAllTasks());
    }

    @Test
    void whenWeExpectedAllTasksButGotOptEmpty(){
        // Given
        // When
        Mockito.when(taskRepository.getAllTasks()).thenReturn(Optional.empty());
        // Then
        Assertions.assertThrows(RuntimeException.class, () -> taskServiceImpl.getAllTasks());
    }
    
    @Test
    void whenWeGetTaskById(){
        // Given
        Optional<Task> task = Optional.of(createTask());
        TaskDto taskDto = modelMapper.map(task.get(), TaskDto.class);
        TaskDtoWrapper expected = new TaskDtoWrapper(taskDto);
        UUID uuid = UUID.randomUUID();
        // When
        Mockito.when(taskRepository.getTaskById(uuid)).thenReturn(task);
        // Then
        Assertions.assertEquals(expected, taskServiceImpl.getTaskById(uuid));
    }

    @Test
    void whenWeGetTaskByIdButGotOptEmpty(){
        // Given
        UUID uuid = UUID.randomUUID();
        // When
        Mockito.when(taskRepository.getTaskById(uuid)).thenReturn(Optional.empty());
        // Then
        Assertions.assertThrows(TaskNotFoundException.class, () -> taskServiceImpl.getTaskById(uuid));
    }

    @Test
    void whenWeGetTaskByIdButGotRuntimeException(){
        // Given
        UUID uuid = UUID.randomUUID();
        // When
        Mockito.when(taskRepository.getTaskById(uuid)).thenThrow(RuntimeException.class);
        // Then
        Assertions.assertThrows(RuntimeException.class, () -> taskServiceImpl.getTaskById(uuid));
    }

    @Test
    void whenWeUpdateTask(){
        // Given
        TaskDtoInput taskDtoInput = createTaskDtoInput();
        Task task = createTask();
        DtoWrapper expected = DtoWrapper.builder().message("Задача обновлена").snackbarType("Info").success(true).build();
        // When
        Mockito.when(taskRepository.updateTask(task)).thenReturn(true);
        // Then
        Assertions.assertEquals(expected, taskServiceImpl.updateTask(taskDtoInput));
    }

    @Test
    void whenWeUpdateTaskButGotFalse(){
        // Given
        TaskDtoInput taskDtoInput = createTaskDtoInput();
        Task task = createTask();
        DtoWrapper expected = DtoWrapper.builder().message("Задача не обновлена").snackbarType("Info").success(false).build();
        // When
        Mockito.when(taskRepository.updateTask(task)).thenReturn(false);
        // Then
        Assertions.assertEquals(expected, taskServiceImpl.updateTask(taskDtoInput));
    }

    @Test
    void whenWeUpdateTaskButGotException(){
        // Given
        TaskDtoInput taskDtoInput = null;
        // When
        // Then
        Assertions.assertThrows(RuntimeException.class, () -> taskServiceImpl.updateTask(taskDtoInput));

    }

    @Test
    void whenWeAddWorkerToTaskTrue(){
        // Given
        UUID workerId = UUID.randomUUID();
        UUID taskId = UUID.randomUUID();
        DtoWrapper expected = DtoWrapper.builder().message("Задаче назначен работник").snackbarType("Info").success(true).build();
        // When
        Mockito.when(taskRepository.addWorkerToTask(workerId, taskId)).thenReturn(true);
        // Then
        Assertions.assertEquals(expected, taskServiceImpl.addWorkerToTask(workerId, taskId));
    }

    @Test
    void whenWeAddWorkerToTaskFalse(){
        // Given
        UUID workerId = UUID.randomUUID();
        UUID taskId = UUID.randomUUID();
        DtoWrapper expected = DtoWrapper.builder().message("Задаче не назначен работник").snackbarType("Info").success(false).build();
        // When
        Mockito.when(taskRepository.addWorkerToTask(workerId, taskId)).thenReturn(false);
        // Then
        Assertions.assertEquals(expected, taskServiceImpl.addWorkerToTask(workerId, taskId));
    }

    @Test
    void whenWeAddWorkerToTaskException(){
        // Given
        UUID workerId = null;
        UUID taskId = null;
        // When
        Mockito.when(taskRepository.addWorkerToTask(workerId, taskId)).thenThrow(new RuntimeException());
        // Then
        Assertions.assertThrows(RuntimeException.class, () -> taskServiceImpl.addWorkerToTask(workerId, taskId));
    }

    private List<TaskDtoInput> createListTasks(int number){
        List<TaskDtoInput> taskDtoInputList = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            TaskDtoInput task = new TaskDtoInput();
            task.setId(UUID.randomUUID());
            task.setTitle("Задача номер " + i);
            task.setDescription("Это задача номер: " + i);
            task.setStatus("В процессе");
            //task.setTime(LocalDateTime.("2023-07-12T09:07:30.924Z"));
            taskDtoInputList.add(task);
        }
        return taskDtoInputList;
    }

    private Task createTask(){
        Task task = new Task();
        task.setId(UUID.fromString("92b539c1-d1c6-43c4-b0b2-2354c56b5c9d"));
        task.setTitle(" ");
        task.setDescription(" ");
        task.setTime(LocalDateTime.now());
        task.setStatus(" ");
        return task;
    }
    
    private Optional<List<Task>> createTaskListOpt(){
        List<Task> taskListOpt = new ArrayList<>();
        taskListOpt.add(createTask());
        taskListOpt.add(createTask());
        taskListOpt.add(createTask());
        return Optional.of(taskListOpt);
    }

    private TaskMiniDto createTaskMini(){
        TaskMiniDto taskMiniDto = new TaskMiniDto();
        taskMiniDto.setId(UUID.fromString("92b539c1-d1c6-43c4-b0b2-2354c56b5c9d"));
        taskMiniDto.setTitle(" ");
        taskMiniDto.setStatus(" ");
        return taskMiniDto;
    }

    private Optional<List<TaskMiniDto>> createTaskMiniListOpt(){
        List<TaskMiniDto> taskListOpt = new ArrayList<>();
        taskListOpt.add(createTaskMini());
        taskListOpt.add(createTaskMini());
        taskListOpt.add(createTaskMini());
        return Optional.of(taskListOpt);
    }

    private TaskDtoInput createTaskDtoInput(){
        TaskDtoInput taskDtoInput = new TaskDtoInput();
        taskDtoInput.setId(UUID.fromString("92b539c1-d1c6-43c4-b0b2-2354c56b5c9d"));
        taskDtoInput.setTitle(" ");
        taskDtoInput.setDescription(" ");
        taskDtoInput.setTime(LocalDateTime.now());
        taskDtoInput.setStatus(" ");
        return taskDtoInput;
    }
}
