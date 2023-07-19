package com.projectkubbank.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projectkubbank.containers.AbstractContainerBaseTest;
import com.projectkubbank.dto.WorkerDto;
import com.projectkubbank.dto.WorkerDtoInput;
import com.projectkubbank.dto.wrapped.DtoWrapper;
import com.projectkubbank.dto.wrapped.WorkerDtoWrapped;
import com.projectkubbank.dto.wrapped.WorkerListDtoWrapped;
import com.projectkubbank.model.Worker;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class WorkerControllerTest  extends AbstractContainerBaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void whenWeAddWorkerInDbAndGetSuccess() throws Exception {
        WorkerDtoInput workerDtoInput = createWorkerDtoInput();
        DtoWrapper expected = DtoWrapper.builder().message("Работник добавлен в БД").snackbarType("Info")
                .success(true).build();
        mockMvc.perform(post("/api/AddWorker")
                        .content(objectMapper.writeValueAsString(workerDtoInput))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }

    @Test
    public void whenWeAddWorkerWithNullNameInDbAndGetFailure() throws Exception {
        WorkerDtoInput workerDtoInput = createWorkerDtoInput();
        workerDtoInput.setName(null);
        assertThrows(ServletException.class, () -> {
            mockMvc.perform(post("/api/AddWorker")
                            .content(objectMapper.writeValueAsString(workerDtoInput))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        });
    }

    @Test
    public void whenWeAddWorkerInDbAndGetFailure() throws Exception {
        WorkerDtoInput workerDtoInput = null;
        mockMvc.perform(post("/api/AddWorker")
                        .content(objectMapper.writeValueAsString(workerDtoInput))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenWeUpdateWorkerInDbAndGetSuccess() throws Exception {
        WorkerDtoInput workerDtoInput = createWorkerDtoInput();
        workerDtoInput.setId(UUID.fromString("ea89ae04-1c81-11ee-be56-0242ac120002"));
        DtoWrapper expected = DtoWrapper.builder().message("Работник обновлен в БД").snackbarType("Info")
                .success(true).build();
        mockMvc.perform(put("/api/UpdateWorker")
                        .content(objectMapper.writeValueAsString(workerDtoInput))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }

    @Test
    public void whenWeUpdateWorkerWithNullNameInDbAndGetFailure() throws Exception {
        WorkerDtoInput workerDtoInput = createWorkerDtoInput();
        workerDtoInput.setName(null);
        DtoWrapper expected = DtoWrapper.builder().message("Работник не обновлен в БД").snackbarType("error")
                .success(false).build();
        ResultActions resultRequest = mockMvc.perform(put("/api/UpdateWorker")
                        .content(objectMapper.writeValueAsString(workerDtoInput))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        MvcResult result = resultRequest.andReturn();
        String responseBody = result.getResponse().getContentAsString();
        DtoWrapper actual = objectMapper.readValue(responseBody, DtoWrapper.class);
        Assertions.assertEquals(expected.isSuccess(), actual.isSuccess());
    }

    @Test
    public void whenWeDeleteWorkerWithTasksInDbAndGetSuccess() throws Exception {
        //существующий в БД UUID
        UUID existUUID = UUID.fromString("ea89ae04-1c81-11ee-be56-0242ac120002");
        DtoWrapper expected = DtoWrapper.builder().message("Работник удален из БД, вместе с задачами")
                .snackbarType("info").success(true).build();
        mockMvc.perform(delete("/api/DeleteWorkerWithTasks/{workerId}", existUUID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }

    @Test
    public void whenWeDeleteWorkerWithTasksWithRandomUUIDInDbAndGetFailure() throws Exception {
        UUID randomUUID = UUID.randomUUID();
        DtoWrapper expected = DtoWrapper.builder().message("Работник не удален из БД").snackbarType("error")
                .success(false).build();
        mockMvc.perform(delete("/api/DeleteWorkerWithTasks/{workerId}", randomUUID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }

    @Test
    public void whenWeDeleteWorkerWithOutTasksInDbAndGetSuccess() throws Exception {
        //существующий в БД UUID
        UUID existUUID = UUID.fromString("ea89ae04-1c81-11ee-be56-0242ac120002");
        DtoWrapper expected = DtoWrapper.builder().message("Работник удален из БД, без задач").snackbarType("info")
                .success(true).build();
        mockMvc.perform(delete("/api/DeleteWorkerWithOutTasks/{workerId}", existUUID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }

    @Test
    public void whenWeDeleteWorkerWithOutTasksWithRandomUUIDInDbAndGetFailure() throws Exception {
        UUID randomUUID = UUID.randomUUID();
        DtoWrapper expected = DtoWrapper.builder().message("Работник не удален из БД").snackbarType("error")
                .success(false).build();
        mockMvc.perform(delete("/api/DeleteWorkerWithOutTasks/{workerId}", randomUUID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }

    @Test
    public void whenWeGetWorkerByIdInDbAndGetSuccess() throws Exception {
        //существующий в БД UUID
        UUID existUUID = UUID.fromString("f925e2e8-1c81-11ee-be56-0242ac120002");
        WorkerDto workerDto = createWorkerDto();
        WorkerDtoWrapped expected = new WorkerDtoWrapped(workerDto);
        mockMvc.perform(get("/api/GetWorkerById/{workerId}", existUUID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }

    @Test
    public void whenWeGetWorkerByIdInDbAndGetFailure() throws Exception {
        UUID randomUUID = UUID.randomUUID();
        assertThrows(ServletException.class, () -> {
            mockMvc.perform(get("/api/GetWorkerById/{workerId}", randomUUID)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        });
    }

    @Test
    public void whenWeGetAllWorkerInDbAndGetSuccess() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get("/api/GetAllWorker")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        WorkerListDtoWrapped actual = objectMapper.readValue(responseBody, WorkerListDtoWrapped.class);
        Assertions.assertEquals(true, actual.isSuccess());
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

    private WorkerDto createWorkerDto(){
        Worker worker = new Worker();
        worker.setId(UUID.fromString("f925e2e8-1c81-11ee-be56-0242ac120002"));
        worker.setName("John Kent");
        worker.setPosition("Boss");
        worker.setAvatar("http://example.com/avatar.png");
        worker.setTaskList(new ArrayList<>());
        return new WorkerDto(worker);
    }

    private List<Worker> createListWorkers(){
        List<Worker> workers = new ArrayList<>();
        workers.add(createWorker());
        workers.add(createWorker());
        return workers;
    }

}
