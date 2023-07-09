package com.projectkubbank.service.impl;

import com.projectkubbank.common.TaskQueue;
import com.projectkubbank.dao.TaskRepository;
import com.projectkubbank.dto.TaskDto;
import com.projectkubbank.dto.TaskDtoInput;
import com.projectkubbank.dto.TaskMiniDto;
import com.projectkubbank.dto.wrapped.DtoWrapper;
import com.projectkubbank.dto.wrapped.TaskDtoWrapper;
import com.projectkubbank.dto.wrapped.TaskMiniListDtoWrapper;
import com.projectkubbank.exceptions.TaskListNotFoundException;
import com.projectkubbank.exceptions.TaskNotFoundException;
import com.projectkubbank.model.Task;
import com.projectkubbank.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service(value = "taskService")
@Slf4j
public class TaskServiceImpl implements TaskService {

    private final TaskQueue taskQueue;
    private final ModelMapper modelMapper;
    private final TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskQueue taskQueue, ModelMapper modelMapper, TaskRepository taskRepository) {
        this.taskQueue = taskQueue;
        this.modelMapper = modelMapper;
        this.taskRepository = taskRepository;
    }

    @Value("${count.thread}")
    int countThreads;

    @Override
    public DtoWrapper addTaskInQueue(List<TaskDtoInput> taskDtoInputList) {
        try {
            for (TaskDtoInput taskDtoInput : taskDtoInputList) {
                Task task = modelMapper.map(taskDtoInput, Task.class);
                taskQueue.addItem(task);
            }
            return DtoWrapper.builder().message("Задача в очереди").snackbarType("Info").success(true).build();
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    @Override
    @Transactional
    public DtoWrapper addThreeTasksToDB() {
        try {
            ExecutorService executor = Executors.newFixedThreadPool(countThreads);
            Runnable run = () -> {
                log.info(Thread.currentThread().getName() + "start");
                Task task = taskQueue.removeItem();
                if (task != null) {
                    taskRepository.addTask(task);
                }
            };
            for (int i = 0; i < 3; i++) {
                executor.execute(run);
            }
            executor.execute(run);
            executor.shutdown();
            return DtoWrapper.builder().message("Задача в БД").snackbarType("Info").success(true).build();
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public TaskMiniListDtoWrapper getAllTasks() {
        try {
            Optional<List<Task>> taskList = taskRepository.getAllTasks();
            if (taskList != null) {
                List<TaskMiniDto> taskMiniDto = new ArrayList<>();
                taskList.get().forEach(task -> taskMiniDto.add(new TaskMiniDto(task)));
                return new TaskMiniListDtoWrapper(taskMiniDto);
            }
            throw new TaskListNotFoundException();
        } catch (TaskListNotFoundException taskListNotFoundException) {
            throw taskListNotFoundException;
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    @Override
    public TaskDtoWrapper getTaskById(UUID taskId) {
        try {
            Optional<Task> task = taskRepository.getTaskById(taskId);
            if (!task.isEmpty()) {
                TaskDto taskDto = modelMapper.map(task.get(), TaskDto.class);
                return new TaskDtoWrapper(taskDto);
            }
            throw new TaskNotFoundException();
        } catch (TaskNotFoundException taskNotFoundException) {
            throw taskNotFoundException;
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    @Override
    @Transactional
    public DtoWrapper updateTask(TaskDtoInput taskDtoInput) {
        try {
            Task task = modelMapper.map(taskDtoInput, Task.class);
            if (taskRepository.updateTask(task)) {
                return DtoWrapper.builder().message("Задача обновлена").snackbarType("Info").success(true).build();
            }
            return DtoWrapper.builder().message("Задача не обновлена").snackbarType("Info").success(true).build();
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    @Override
    @Transactional
    public DtoWrapper addWorkerToTask(UUID workerId, UUID taskId) {
        try {
            if (taskRepository.addWorkerToTask(workerId, taskId)) {
                return DtoWrapper.builder().message("Задаче назначен работник").snackbarType("Info").success(true).build();
            } else {
                return DtoWrapper.builder().message("Задаче не назначен работник").snackbarType("Info").success(true).build();
            }
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

}
