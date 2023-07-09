package com.projectkubbank.dao;

import com.projectkubbank.dto.wrapped.DtoWrapper;
import com.projectkubbank.model.Task;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaskRepository {

    DtoWrapper addTask(Task task);

    Optional<List<Task>> getAllTasks();

    Optional<Task> getTaskById(UUID taskId);

    boolean updateTask(Task task);

    boolean addWorkerToTask(UUID workerId, UUID taskId);

}
