package com.projectkubbank.dao.impl;

import com.projectkubbank.dao.TaskRepository;
import com.projectkubbank.dto.wrapped.DtoWrapper;
import com.projectkubbank.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Repository(value = "taskRepository")
public class TaskRepositoryImpl implements TaskRepository {
    private static final Logger logger = LoggerFactory.getLogger(TaskRepository.class);
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public TaskRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public DtoWrapper addTask(Task task) {
        logger.info("Начат процесс добавления задачи в БД");
        try {
            Object[] values = {task.getId(), task.getTitle(), task.getDescription(), task.getTime(), task.getStatus()};
            String sql = "INSERT INTO public.\"Tasks\" (\"id\", \"title\", \"description\", \"time\", \"status\") " +
                    "VALUES (?, ?, ?, ?, ?)";
            if (jdbcTemplate.update(sql, values) > 0) {
                logger.info("Задача добавлена в БД");
                return DtoWrapper.builder().message("Задача добавлена в БД").snackbarType("Info").success(true).build();
            } else {
                logger.info("Задача добавлена в БД");
                return DtoWrapper.builder().message("Задача добавлена в БД").snackbarType("Info").success(true).build();
            }
        } catch (DataAccessException e) {
            logger.error(e.getLocalizedMessage());
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    @Override
    public List<Task> getAllTask() {
        logger.info("Начат процес получения всех задач");
        try {
            List<Task> taskList = jdbcTemplate.query("SELECT * FROM public.\"Tasks\"",
                    new BeanPropertyRowMapper<>(Task.class));
            logger.info("Лист задач получен");
            return taskList;
        } catch (DataAccessException e) {
            logger.error(e.getLocalizedMessage());
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    @Override
    public Task getTaskById(UUID id) {
        logger.info("Начат процес получения задачи по id");
        try {
            Task task = jdbcTemplate.queryForObject(
                    "SELECT * FROM public.\"Tasks\" WHERE id = ?", new Object[]{id},
                    new BeanPropertyRowMapper<>(Task.class));
            if(task != null){
                logger.info("Задача по id получена");
                return task;
            } else {
                logger.info("Задача по id не получена");
                return null;
            }
        } catch (DataAccessException e) {
            logger.error(e.getLocalizedMessage());
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    @Override
    public boolean updateTask(Task task) {
        logger.info("Начат процес обновления задачи в БД");
        try {
            String sql = "UPDATE public.\"Tasks\" SET title = ?, description = ?, time = ?, status = ? WHERE id = ?";
            if(jdbcTemplate.update(sql, task.getTitle(), task.getDescription(), task.getTime(), task.getStatus()
                    , task.getId()) > 0){
                logger.info("Задача в БД обновлена");
                return true;
            } else {
                logger.info("Задача в БД не обновлена");
                return false;
            }
        } catch (DataAccessException e) {
            logger.error(e.getLocalizedMessage());
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    @Override
    public boolean addWorkerToTask(UUID workerId, UUID taskId) {
        String sql = "UPDATE public.\"Tasks\" SET performer = ? WHERE id = ?";
        if(jdbcTemplate.update(sql, workerId, taskId) > 0){
            return true;
        } else {
            return false;
        }
    }
}
