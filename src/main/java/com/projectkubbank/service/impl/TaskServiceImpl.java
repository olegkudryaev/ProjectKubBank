package com.projectkubbank.service.impl;

import com.projectkubbank.common.TaskQueue;
import com.projectkubbank.dao.TaskRepository;
import com.projectkubbank.dto.TaskDto;
import com.projectkubbank.dto.TaskDtoInput;
import com.projectkubbank.dto.TaskMiniDto;
import com.projectkubbank.dto.wrapped.DtoWrapper;
import com.projectkubbank.dto.wrapped.TaskDtoWrapper;
import com.projectkubbank.dto.wrapped.TaskMiniListDtoWrapper;
import com.projectkubbank.service.TaskService;
import com.projectkubbank.model.Task;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service(value = "taskService")
public class TaskServiceImpl implements TaskService {
    private static final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

    private TaskQueue taskQueue;
    private ModelMapper modelMapper;
    private TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskQueue taskQueue, ModelMapper modelMapper, TaskRepository taskRepository) {
        this.taskQueue = taskQueue;
        this.modelMapper = modelMapper;
        this.taskRepository = taskRepository;
    }

    @Override
    public DtoWrapper addTaskInQueue(List<TaskDtoInput> taskDtoInputList) {
        try {
            for (TaskDtoInput taskDtoInput : taskDtoInputList) {
                Task task = modelMapper.map(taskDtoInput, Task.class);
                taskQueue.addItem(task);
            }
            return DtoWrapper.builder().message("Задача в очереди").snackbarType("Info").success(true).build();
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
        }
        return DtoWrapper.builder().message("Не удалось разметсить задачу в очередь").snackbarType("Error")
                .success(false).build();
    }

    @Override
    @Transactional
    public DtoWrapper addThreeTasksToDB() {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        Runnable task1 = () -> {
            System.out.println("Task 1 is running");
            Task task = taskQueue.removeItem();
            if(task!=null) {
                taskRepository.addTask(task);
            }
        };
        Runnable task2 = () -> {
            System.out.println("Task 2 is running");
            Task task = taskQueue.removeItem();
            if(task!=null) {
                taskRepository.addTask(task);
            }
        };
        Runnable task3 = () -> {
            System.out.println("Task 3 is running");
            Task task = taskQueue.removeItem();
            if(task!=null) {
                taskRepository.addTask(task);
            }
        };
        executor.execute(task1);
        executor.execute(task2);
        executor.execute(task3);
        executor.shutdown();
        return DtoWrapper.builder().message("Задача в очереди").snackbarType("Info").success(true).build();
    }

    @Override
    public TaskMiniListDtoWrapper getAllTasks() {
        List<Task> taskList = taskRepository.getAllTask();
        List<TaskMiniDto> taskMiniDto = new ArrayList<>();
        taskList.forEach(t-> taskMiniDto.add(modelMapper.map(t, TaskMiniDto.class)));
        return new TaskMiniListDtoWrapper(taskMiniDto);
    }

    @Override
    public TaskDtoWrapper getTaskById(UUID id) {
        Task task = taskRepository.getTaskById(id);
        if (task != null) {
            TaskDto taskDto = modelMapper.map(task, TaskDto.class);
            return new TaskDtoWrapper(taskDto);
        }
        return null;
    }

    @Override
    @Transactional
    public DtoWrapper updateTask(TaskDtoInput taskDtoInput) {
        try {
            Task task = modelMapper.map(taskDtoInput, Task.class);
            if(taskRepository.updateTask(task)){
                return DtoWrapper.builder().message("Задача обновлена").snackbarType("Info").success(true).build();
            }
            return DtoWrapper.builder().message("Задача не обновлена").snackbarType("Info").success(true).build();
        }catch (Exception e){
            logger.error(e.getLocalizedMessage());
        }
        return null;
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
        }catch (Exception e){
            logger.error(e.getLocalizedMessage());
        }
        return null;
    }

}
