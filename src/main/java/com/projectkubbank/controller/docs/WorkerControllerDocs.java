package com.projectkubbank.controller.docs;

import com.projectkubbank.dto.WorkerDtoInput;
import com.projectkubbank.dto.wrapped.DtoWrapper;
import com.projectkubbank.dto.wrapped.WorkerDtoWrapped;
import com.projectkubbank.dto.wrapped.WorkerListDtoWrapped;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface WorkerControllerDocs {

    @Operation(summary = "Метод добавляет работника в БД")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Работник добавлен в БД",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoWrapper.class))),
            @ApiResponse(responseCode = "404", description = "Не удалось добавить работника в БД",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoWrapper.class)))})
    ResponseEntity<DtoWrapper> addWorker(WorkerDtoInput workerDtoInput);

    @Operation(summary = "Метод обновляет работника в БД")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Информация о работнике обновлена",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoWrapper.class))),
            @ApiResponse(responseCode = "404", description = "Не удалось обновить информацию о работнике",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoWrapper.class)))})
    ResponseEntity<DtoWrapper> updateWorker(WorkerDtoInput workerDtoInput);

    @Operation(summary = "Метод удаляет работника из БД вместе с задачами")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Работник удален вместе с задачами",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoWrapper.class))),
            @ApiResponse(responseCode = "404", description = "Не удалось удалить работника и его задачи",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoWrapper.class)))})
    ResponseEntity<DtoWrapper> deleteWorkerWithTasks(UUID id);

    @Operation(summary = "Метод удаляет работника из БД без задач")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Работник удален",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoWrapper.class))),
            @ApiResponse(responseCode = "404", description = "Не удалось удалить работника",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoWrapper.class)))})
    ResponseEntity<DtoWrapper> deleteWorkerWithOutTasks(UUID workerId);

    @Operation(summary = "Метод получает информацию об одном работнике и его задачах из БД по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Информация получена",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = WorkerDtoWrapped.class))),
            @ApiResponse(responseCode = "404", description = "Не удалось получить информацию",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoWrapper.class)))})
    ResponseEntity<WorkerDtoWrapped> getWorkerById(UUID workerId);

    @Operation(summary = "Метод получает информацию о всех работниках и их задачах из БД")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Информация получена",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = WorkerListDtoWrapped.class))),
            @ApiResponse(responseCode = "404", description = "Не удалось получить информацию",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoWrapper.class)))})
    ResponseEntity<WorkerListDtoWrapped> getAllWorker();

}
