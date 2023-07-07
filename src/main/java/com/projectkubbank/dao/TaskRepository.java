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
public class TaskRepository {
    private static final Logger logger = LoggerFactory.getLogger(TaskRepository.class);
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public TaskRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public DtoWrapper addTask(Task task){
        try {
            Object[] values = {task.getId(), task.getTitle(), task.getDescription(), task.getTime(), task.getStatus(), task.getPerformer()};
            String query = "INSERT INTO public.\"Tasks\" (\"id\", \"title\", \"description\", \"time\", \"status\", \"performer\") " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(query, values);
            return DtoWrapper.builder().message("Задача добавлена в БД").snackbarType("Info").success(true).build();
        } catch (DataAccessException e) {
            logger.error(e.getLocalizedMessage());
        }
        return null;
    }

    public List<Task> getAllTask(){
        try {
            List<Task> taskList = jdbcTemplate.query("SELECT * FROM public.\"Tasks\"",
                    new BeanPropertyRowMapper<>(Task.class));
            return taskList;
        } catch (DataAccessException e) {
            logger.error(e.getLocalizedMessage());
        }
        return null;
    }

    public Task getTaskById(UUID id){
        try {
            Task task = jdbcTemplate.queryForObject(
                    "SELECT * FROM public.\"Tasks\" WHERE id = ?",new Object[]{id},
                    new BeanPropertyRowMapper<>(Task.class));
            return task;
        } catch (DataAccessException e) {
            logger.error(e.getLocalizedMessage());
        }
        return null;
    }

    public boolean updateTask(Task task){
        try {
            String sql = "UPDATE public.\"Tasks\" SET title = ?, description = ?, time = ?, status = ?, performer = ? WHERE id = ?";
            jdbcTemplate.update(sql, task.getTitle(), task.getDescription(), task.getTime(), task.getStatus()
                    , task.getPerformer(), task.getId());
            return true;

        } catch (DataAccessException e) {
            logger.error(e.getLocalizedMessage());
        }
        return false;
    }

    public boolean addWorkerToTask(UUID workerId, UUID taskId) {
        String sql = "UPDATE public.\"Tasks\" SET performer = ? WHERE id = ?";
        int rowsInserted = jdbcTemplate.update(
                sql,
                workerId,
                taskId
        );
        if(rowsInserted>0) {
            return true;
        }else{
            return false;
        }
    }

}
