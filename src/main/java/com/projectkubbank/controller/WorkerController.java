package com.projectkubbank.controller;

import com.projectkubbank.controller.docs.WorkerControllerDocs;
import com.projectkubbank.dto.WorkerDtoInput;
import com.projectkubbank.dto.wrapped.DtoWrapper;
import com.projectkubbank.dto.wrapped.WorkerDtoWrapped;
import com.projectkubbank.dto.wrapped.WorkerListDtoWrapped;
import com.projectkubbank.service.WorkerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class WorkerController implements WorkerControllerDocs {

    private final WorkerService workerService;

    @Override
    @PostMapping("/AddWorker")
    public ResponseEntity<DtoWrapper> addWorker(
            @RequestBody @Valid @NotNull WorkerDtoInput workerDtoInput) {
        DtoWrapper result = workerService.addWorker(workerDtoInput);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @Override
    @PutMapping("/UpdateWorker")
    public ResponseEntity<DtoWrapper> updateWorker(
            @RequestBody @Valid @NotNull WorkerDtoInput workerDtoInput) {
        DtoWrapper result = workerService.updateWorker(workerDtoInput);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/DeleteWorkerWithTasks/{workerId}")
    public ResponseEntity<DtoWrapper> deleteWorkerWithTasks(
            @PathVariable("workerId")
            @org.hibernate.validator.constraints.UUID(message = "Invalid UUID")
            @NotBlank
            UUID workerId) {
        DtoWrapper result = workerService.deleteWorkerWithTasks(workerId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/DeleteWorkerWithOutTasks/{workerId}")
    public ResponseEntity<DtoWrapper> deleteWorkerWithOutTasks(
            @PathVariable("workerId")
            @org.hibernate.validator.constraints.UUID(message = "Invalid UUID")
            @NotBlank
            UUID workerId) {
        DtoWrapper result = workerService.deleteWorkerWithOutTasks(workerId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    @GetMapping("/GetWorkerById/{workerId}")
    public ResponseEntity<WorkerDtoWrapped> getWorkerById(
            @PathVariable("workerId")
            @org.hibernate.validator.constraints.UUID(message = "Invalid UUID")
            @NotBlank
            UUID workerId) {
        WorkerDtoWrapped result = workerService.getWorkerById(workerId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    @GetMapping("/GetAllWorker")
    public ResponseEntity<WorkerListDtoWrapped> getAllWorker() {
        WorkerListDtoWrapped result = workerService.getAllWorker();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
