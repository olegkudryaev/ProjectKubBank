package com.projectkubbank.service;

import com.projectkubbank.dto.TaskDtoInput;
import com.projectkubbank.dto.wrapped.DtoWrapper;
import com.projectkubbank.dto.wrapped.TaskDtoWrapper;
import com.projectkubbank.dto.wrapped.TaskMiniListDtoWrapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface TaskService {
    DtoWrapper addTaskInQueue(TaskDtoInput taskDtoInput);

    DtoWrapper addThreeTasksToDB();

    TaskMiniListDtoWrapper getAllTasks();

    TaskDtoWrapper getTaskById(UUID id);

    DtoWrapper updateTask(TaskDtoInput taskDtoInput);
}
