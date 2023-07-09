package com.projectkubbank.dao.impl;

import com.projectkubbank.dao.TaskRepository;
import com.projectkubbank.dto.wrapped.DtoWrapper;
import com.projectkubbank.model.Task;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository(value = "taskRepository")
@Slf4j
@RequiredArgsConstructor
public class TaskRepositoryImpl implements TaskRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public DtoWrapper addTask(Task task) {
        log.info("Начат процесс добавления задачи в БД");
        try {
            Object[] values = {task.getId(), task.getTitle(), task.getDescription(), task.getTime(), task.getStatus()};
            String sql = "INSERT INTO public.\"Tasks\" (\"id\", \"title\", \"description\", \"time\", \"status\") " +
                    "VALUES (?, ?, ?, ?, ?)";
            if (jdbcTemplate.update(sql, values) > 0) {
                log.info("Задача добавлена в БД");
                return DtoWrapper.builder().message("Задача добавлена в БД").snackbarType("info").success(true).build();
            } else {
                log.info("Задача не добавлена в БД");
                return DtoWrapper.builder().message("Задача не добавлена в БД").snackbarType("error").success(false).build();
            }
        } catch (DataAccessException e) {
            log.error(e.getLocalizedMessage());
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<Task>> getAllTasks() {
        log.info("Начат процес получения всех задач");
        try {
            Optional<List<Task>> taskList = Optional.of(jdbcTemplate.query("SELECT * FROM public.\"Tasks\"",
                    new BeanPropertyRowMapper<>(Task.class)));
            if (taskList.isPresent()) {
                log.info("Лист задач получен");
                return taskList;
            } else {
                return Optional.empty();
            }
        } catch (DataAccessException e) {
            log.error(e.getLocalizedMessage());
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Task> getTaskById(UUID taskId) {
        log.info("Начат процес получения задачи по id");
        try {
            Optional<List<Task>> taskList = Optional.of(jdbcTemplate.query("SELECT * FROM public.\"Tasks\" WHERE id = ?", new Object[]{taskId},
                    new BeanPropertyRowMapper<>(Task.class)));
            if (taskList.get().size() > 0) {
                log.info("Задача по id получена");
                return taskList.get().stream().findFirst();
            } else {
                log.info("Задача по id не получена");
                return Optional.empty();
            }
        } catch (DataAccessException e) {
            log.error(e.getLocalizedMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public boolean updateTask(Task task) {
        log.info("Начат процес обновления задачи в БД");
        try {
            String sql = "UPDATE public.\"Tasks\" SET title = ?, description = ?, time = ?, status = ? WHERE id = ?";
            if (jdbcTemplate.update(sql, task.getTitle(), task.getDescription(), task.getTime(), task.getStatus()
                    , task.getId()) > 0) {
                log.info("Задача в БД обновлена");
                return true;
            } else {
                log.info("Задача в БД не обновлена");
                return false;
            }
        } catch (DataAccessException e) {
            log.error(e.getLocalizedMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public boolean addWorkerToTask(UUID workerId, UUID taskId) {
        log.info("Начат процес добавления работнику задачи");
        try {
            String sql = "UPDATE public.\"Tasks\" SET performer = ? WHERE id = ?";
            if (jdbcTemplate.update(sql, workerId, taskId) > 0) {
                log.info("Задача добавлена работнику");
                return true;
            } else {
                log.info("Задача не добавлена работнику");
                return false;
            }
        } catch (DataAccessException e) {
            log.error(e.getLocalizedMessage());
            throw e;
        }
    }
}
