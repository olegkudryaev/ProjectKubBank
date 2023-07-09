package com.projectkubbank.controller;

import com.projectkubbank.controller.docs.WorkerControllerDocs;
import com.projectkubbank.dto.WorkerDtoInput;
import com.projectkubbank.dto.wrapped.DtoWrapper;
import com.projectkubbank.dto.wrapped.WorkerDtoWrapped;
import com.projectkubbank.dto.wrapped.WorkerListDtoWrapped;
import com.projectkubbank.service.WorkerService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class WorkerController implements WorkerControllerDocs {

    private final WorkerService workerService;

    @Autowired
    public WorkerController(WorkerService workerService) {
        this.workerService = workerService;
    }

    @Override
    @PostMapping("/AddWorker")
    public ResponseEntity<DtoWrapper> addWorker(
            @RequestBody WorkerDtoInput workerDtoInput) {
        DtoWrapper result = workerService.addWorker(workerDtoInput);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @Override
    @PutMapping("/UpdateWorker")
    public ResponseEntity<DtoWrapper> updateWorker(
            @RequestBody WorkerDtoInput workerDtoInput) {
        DtoWrapper result = workerService.updateWorker(workerDtoInput);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/DeleteWorkerWithTasks")
    public ResponseEntity<DtoWrapper> deleteWorkerWithTasks(
            @Parameter(description = "workerId") @RequestParam(value = "id", required = true) UUID id) {
        DtoWrapper result = workerService.deleteWorkerWithTasks(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/DeleteWorkerWithOutTasks")
    public ResponseEntity<DtoWrapper> deleteWorkerWithOutTasks(
            @Parameter(description = "workerId") @RequestParam(value = "workerId", required = true) UUID workerId) {
        DtoWrapper result = workerService.deleteWorkerWithOutTasks(workerId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    @GetMapping("/GetWorkerById/{workerId}")
    public ResponseEntity<WorkerDtoWrapped> getWorkerById(
            @PathVariable("workerId") UUID workerId) {
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
