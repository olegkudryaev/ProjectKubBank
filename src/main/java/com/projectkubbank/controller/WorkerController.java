package com.projectkubbank.controller;

import com.projectkubbank.dto.WorkerDtoInput;
import com.projectkubbank.dto.wrapped.DtoWrapper;
import com.projectkubbank.dto.wrapped.WorkerDtoWrapped;
import com.projectkubbank.dto.wrapped.WorkerListDtoWrapped;
import com.projectkubbank.service.WorkerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class WorkerController {

    private static final Logger logger = LoggerFactory.getLogger(WorkerController.class);

    private final WorkerService workerService;

    @Autowired
    public WorkerController(WorkerService workerService) {
        this.workerService = workerService;
    }


    @Operation(summary = "Метод добавляет работника в БД")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Работник добавлен в БД",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoWrapper.class))),
            @ApiResponse(responseCode = "404", description = "Не удалось добавить работника в БД",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoWrapper.class)))})
    @PostMapping("/AddWorker")
    public ResponseEntity<DtoWrapper> addWorker(
            @RequestBody WorkerDtoInput workerDtoInput) {
        DtoWrapper result = null;
        result = workerService.addWorker(workerDtoInput);
        return new ResponseEntity<>(result, result == null ? HttpStatus.BAD_REQUEST : HttpStatus.CREATED);
    }

    @Operation(summary = "Метод обновляет работника в БД")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Информация о работнике обновлена",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoWrapper.class))),
            @ApiResponse(responseCode = "404", description = "Не удалось обновить информацию о работнике",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoWrapper.class)))})
    @PutMapping("/UpdateWorker")
    public ResponseEntity<DtoWrapper> updateWorker(
            @RequestBody WorkerDtoInput workerDtoInput) {
        DtoWrapper result = null;
        result = workerService.updateWorker(workerDtoInput);
        return new ResponseEntity<>(result, result == null ? HttpStatus.BAD_REQUEST : HttpStatus.OK);
    }

    @Operation(summary = "Метод удаляет работника из БД вместе с задачами")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Работник удален вместе с задачами",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoWrapper.class))),
            @ApiResponse(responseCode = "404", description = "Не удалось удалить работника и его задачи",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoWrapper.class)))})
    @DeleteMapping("/DeleteWorkerWithTasks")
    public ResponseEntity<DtoWrapper> deleteWorkerWithTasks(
            @Parameter(description = "workerId") @RequestParam(value = "id", required = true) UUID id) {
        DtoWrapper result = null;
        result = workerService.deleteWorkerWithTasks(id);
        return new ResponseEntity<>(result, result == null ? HttpStatus.BAD_REQUEST : HttpStatus.OK);
    }

    @Operation(summary = "Метод удаляет работника из БД без задач")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Работник удален",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoWrapper.class))),
            @ApiResponse(responseCode = "404", description = "Не удалось удалить работника",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoWrapper.class)))})
    @DeleteMapping("/DeleteWorkerWithOutTasks")
    public ResponseEntity<DtoWrapper> deleteWorkerWithOutTasks(
            @Parameter(description = "workerId") @RequestParam(value = "workerId", required = true) UUID workerId) {
        DtoWrapper result = null;
        result = workerService.deleteWorkerWithOutTasks(workerId);
        return new ResponseEntity<>(result, result == null ? HttpStatus.BAD_REQUEST : HttpStatus.OK);
    }

    @Operation(summary = "Метод получает информацию об одном работнике и его задачах из БД по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Информация получена",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = WorkerDtoWrapped.class))),
            @ApiResponse(responseCode = "404", description = "Не удалось получить информацию",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoWrapper.class)))})
    @GetMapping("/GetWorkerById")
    public ResponseEntity<WorkerDtoWrapped> getWorkerById(
            @Parameter(description = "workerId") @RequestParam(value = "workerId", required = true) UUID workerId) {
        WorkerDtoWrapped result = null;
        result = workerService.getWorkerById(workerId);
        return new ResponseEntity<>(result, result == null ? HttpStatus.BAD_REQUEST : HttpStatus.OK);
    }

    @Operation(summary = "Метод получает информацию о всех работниках и их задачах из БД")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Информация получена",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = WorkerListDtoWrapped.class))),
            @ApiResponse(responseCode = "404", description = "Не удалось получить информацию",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoWrapper.class)))})
    @GetMapping("/GetAllWorker")
    public ResponseEntity<WorkerListDtoWrapped> getAllWorker() {
        WorkerListDtoWrapped result = null;
        result = workerService.getAllWorker();
        return new ResponseEntity<>(result, result == null ? HttpStatus.BAD_REQUEST : HttpStatus.OK);
    }

}
