package com.projectkubbank.service.impl;

import com.projectkubbank.dto.TaskDtoInput;
import com.projectkubbank.dto.wrapped.DtoWrapper;
import com.projectkubbank.dto.wrapped.TaskDtoWrapper;
import com.projectkubbank.dto.wrapped.TaskMiniListDtoWrapper;
import com.projectkubbank.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service(value = "taskService")
public class TaskServiceImpl implements TaskService {
    @Override
    public DtoWrapper addTaskInQueue(TaskDtoInput taskDtoInput) {
        return null;
    }

    @Override
    public DtoWrapper addThreeTasksToDB() {
        return null;
    }

    @Override
    public TaskMiniListDtoWrapper getAllTasks() {
        return null;
    }

    @Override
    public TaskDtoWrapper getTaskById(UUID id) {
        return null;
    }

    @Override
    public DtoWrapper updateTask(TaskDtoInput taskDtoInput) {
        return null;
    }
}
