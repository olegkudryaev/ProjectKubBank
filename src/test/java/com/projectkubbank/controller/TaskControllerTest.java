package com.projectkubbank.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projectkubbank.containers.AbstractContainerBaseTest;
import com.projectkubbank.dto.TaskDtoInput;
import com.projectkubbank.dto.TaskMiniDto;
import com.projectkubbank.dto.wrapped.DtoWrapper;
import com.projectkubbank.dto.wrapped.TaskDtoWrapper;
import com.projectkubbank.dto.wrapped.TaskMiniListDtoWrapper;
import com.projectkubbank.model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerTest extends AbstractContainerBaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void whenWeAddExistedTaskListInQueue() throws Exception {
        List<TaskDtoInput> taskDtoInput = createListTasks(3);
        DtoWrapper expected = DtoWrapper.builder().message("Задачи в очереди").snackbarType("Info").success(true).build();
        mockMvc.perform(post("/api/AddTaskInQueue")
                        .content(objectMapper.writeValueAsString(taskDtoInput))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }

    @Test
    public void whenWeAddManyNotExistingTaskInQueue() throws Exception {
        mockMvc.perform(post("/api/AddTaskInQueue")
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenWeAddTasksToDBAndGetSuccess() throws Exception {
        DtoWrapper expected = DtoWrapper.builder().message("Задача в БД").snackbarType("Info").success(true).build();
        mockMvc.perform(get("/api/AddThreeTasksToDB"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }

    @Test
    public void whenWeGetAllTasksAndGetSuccess() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/GetAllTasks"))
                .andExpect(status().isOk())
                .andReturn();
        String responseBody = result.getResponse().getContentAsString();
        TaskMiniListDtoWrapper actual = objectMapper.readValue(responseBody, TaskMiniListDtoWrapper.class);
        Assertions.assertTrue(true, String.valueOf(actual.isSuccess()));
    }

    @Test
    public void whenWeGetTaskByIdAndGetSuccess() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/GetTaskById/{taskId}"
                        , "6f7907f0-2609-11ec-9621-0242ac130002")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        String responseBody = result.getResponse().getContentAsString();
        TaskDtoWrapper actual = objectMapper.readValue(responseBody, TaskDtoWrapper.class);
        Assertions.assertTrue(true, String.valueOf(actual.isSuccess()));
    }

    @Test
    public void whenWeGetTaskByIdAndGetFailure() throws Exception {
        mockMvc.perform(get("/api/GetTaskById/{taskId}", "0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenWeUpdateTaskAndGetSuccess() throws Exception {
        TaskDtoInput taskDtoInput = createTaskDtoInput();
        MvcResult result = mockMvc.perform(put("/api/UpdateTask")
                        .content(objectMapper.writeValueAsString(taskDtoInput))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        String responseBody = result.getResponse().getContentAsString();
        DtoWrapper actual = objectMapper.readValue(responseBody, DtoWrapper.class);
        Assertions.assertTrue(true, String.valueOf(actual.isSuccess()));
    }

    @Test
    public void whenWeUpdateTaskAndGetFailure() throws Exception {
        TaskDtoInput taskDtoInput = null;
        mockMvc.perform(put("/api/UpdateTask")
                        .content(objectMapper.writeValueAsString(taskDtoInput))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenWeAddWorkerToTaskAndGetSuccess() throws Exception {
        MvcResult result = mockMvc.perform(post("/api/AddWorkerToTask/{workerId}/{taskId}",
                        "f4d3e8fc-1c81-11ee-be56-0242ac120002", "e6ef0910-1c81-11ee-be56-0242ac120002")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        String responseBody = result.getResponse().getContentAsString();
        DtoWrapper actual = objectMapper.readValue(responseBody, DtoWrapper.class);
        Assertions.assertTrue(true, String.valueOf(actual.isSuccess()));
    }

    @Test
    public void whenWeAddWorkerToTaskAndGetFailure() throws Exception {
        mockMvc.perform(post("/api/AddWorkerToTask/{workerId}/{taskId}",
                        "0", "0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }

    private List<TaskDtoInput> createListTasks(int number) {
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

    private Task createTask() {
        Task task = new Task();
        task.setId(UUID.fromString("92b539c1-d1c6-43c4-b0b2-2354c56b5c9d"));
        task.setTitle(" ");
        task.setDescription(" ");
        task.setTime(LocalDateTime.now());
        task.setStatus(" ");
        return task;
    }

    private Optional<List<Task>> createTaskListOpt() {
        List<Task> taskListOpt = new ArrayList<>();
        taskListOpt.add(createTask());
        taskListOpt.add(createTask());
        taskListOpt.add(createTask());
        return Optional.of(taskListOpt);
    }

    private TaskMiniDto createTaskMini() {
        TaskMiniDto taskMiniDto = new TaskMiniDto();
        taskMiniDto.setId(UUID.fromString("92b539c1-d1c6-43c4-b0b2-2354c56b5c9d"));
        taskMiniDto.setTitle(" ");
        taskMiniDto.setStatus(" ");
        return taskMiniDto;
    }

    private Optional<List<TaskMiniDto>> createTaskMiniListOpt() {
        List<TaskMiniDto> taskListOpt = new ArrayList<>();
        taskListOpt.add(createTaskMini());
        taskListOpt.add(createTaskMini());
        taskListOpt.add(createTaskMini());
        return Optional.of(taskListOpt);
    }

    private TaskDtoInput createTaskDtoInput() {
        TaskDtoInput taskDtoInput = new TaskDtoInput();
        taskDtoInput.setId(UUID.fromString("92b539c1-d1c6-43c4-b0b2-2354c56b5c9d"));
        taskDtoInput.setTitle(" ");
        taskDtoInput.setDescription(" ");
        taskDtoInput.setTime(LocalDateTime.now());
        taskDtoInput.setStatus(" ");
        return taskDtoInput;
    }
}
