package com.projectkubbank.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projectkubbank.containers.AbstractContainerBaseTest;
import com.projectkubbank.dto.WorkerDto;
import com.projectkubbank.dto.WorkerDtoInput;
import com.projectkubbank.dto.wrapped.DtoWrapper;
import com.projectkubbank.model.Worker;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
        Assertions.assertThrows(ServletException.class, () -> {
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
