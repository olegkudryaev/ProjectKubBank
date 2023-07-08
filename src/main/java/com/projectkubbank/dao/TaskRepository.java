package com.projectkubbank.dao;

import com.projectkubbank.dto.wrapped.DtoWrapper;
import com.projectkubbank.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository {

    DtoWrapper addTask(Task task);

    List<Task> getAllTask();

    Task getTaskById(UUID id);

    boolean updateTask(Task task);

    boolean addWorkerToTask(UUID workerId, UUID taskId);

}
