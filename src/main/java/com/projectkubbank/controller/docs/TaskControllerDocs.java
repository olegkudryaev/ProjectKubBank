package com.projectkubbank.controller.docs;

import com.projectkubbank.dto.TaskDtoInput;
import com.projectkubbank.dto.wrapped.DtoWrapper;
import com.projectkubbank.dto.wrapped.TaskDtoWrapper;
import com.projectkubbank.dto.wrapped.TaskMiniListDtoWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.UUID;

public interface TaskControllerDocs {

    @Operation(summary = "Метод принимает задачу и складывает ее в очередь.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Задачи в очереди",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoWrapper.class))),
            @ApiResponse(responseCode = "404", description = "Не удалось положить задачу в очередь",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoWrapper.class)))})
    ResponseEntity<DtoWrapper> addTaskInQueue(List<TaskDtoInput> taskDtoInput);

    @Operation(summary = "Метод добавляет три задачи из очереди в базу данных.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Задачи добавлены в базу данных",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoWrapper.class))),
            @ApiResponse(responseCode = "404", description = "Не удалось добавить задачи в базу данных",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoWrapper.class)))})
    @GetMapping("/AddThreeTasksToDB")
    ResponseEntity<DtoWrapper> addThreeTasksToDB();

    @Operation(summary = "Метод возвращает все задачи из БД со следующими полями: id, title, status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Задачи успешно добавлены в БД",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TaskMiniListDtoWrapper.class))),
            @ApiResponse(responseCode = "404", description = "Не удалось добавить задачи в БД",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoWrapper.class)))})
    ResponseEntity<TaskMiniListDtoWrapper> getAllTasks();

    @Operation(summary = "Метод выдает задачу, с полным описанием, по переданному id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Задача получена по идентификатору",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TaskDtoWrapper.class))),
            @ApiResponse(responseCode = "404", description = "Не удалось получить задачу по идентификатору",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoWrapper.class)))})
    ResponseEntity<TaskDtoWrapper> getTaskById(UUID taskId);

    @Operation(summary = "Метод изменяет задачу по id. Поля id и performer не изменяемы.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Задача изменена",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoWrapper.class))),
            @ApiResponse(responseCode = "404", description = "Не удалось изменить задачу",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoWrapper.class)))})
    ResponseEntity<DtoWrapper> updateTask(TaskDtoInput taskDtoInput);

    @Operation(summary = "Метод назначает работнику задачу")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Задача назначена работнику",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoWrapper.class))),
            @ApiResponse(responseCode = "404", description = "Не удалось назначить задачу работнику",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoWrapper.class)))})
    ResponseEntity<DtoWrapper> addWorkerToTask(UUID workerId, UUID taskId);

}
