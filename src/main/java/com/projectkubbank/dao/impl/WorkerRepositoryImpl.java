package com.projectkubbank.dao.impl;

import com.projectkubbank.dao.WorkerRepository;
import com.projectkubbank.model.Task;
import com.projectkubbank.model.Worker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Repository(value = "workerRepository")
@RequiredArgsConstructor
@Slf4j
public class WorkerRepositoryImpl implements WorkerRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public boolean addWorker(Worker worker) {
        log.info("Начато добавление работника");
        try {
            String sql = "INSERT INTO public.\"Workers\" (\"id\", \"name\", \"position\", \"avatar\") VALUES (?, ?, ?, ?)";
            if (jdbcTemplate.update(sql, worker.getId(), worker.getName(), worker.getPosition(), worker.getAvatar()) > 0) {
                log.info("Работник добавлен в бд успешно");
                return true;
            } else {
                log.info("Работник не добавлен в бд");
                return false;
            }
        } catch (DataAccessException e) {
            log.error(e.getLocalizedMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public boolean updateWorker(Worker worker) {
        log.info("Начато обновление работника");
        try {
            String sql = "UPDATE public.\"Workers\" SET name = ?, position = ?, avatar = ? WHERE id = ?";
            if (jdbcTemplate.update(sql, worker.getName(), worker.getPosition(), worker.getAvatar(), worker.getId()) > 0) {
                log.info("Работник обновлен успешно");
                return true;
            } else {
                log.info("Работник не обновлен");
                return false;
            }
        } catch (DataAccessException e) {
            log.error(e.getLocalizedMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public boolean deleteWorkerWithTasks(UUID workerId) {
        log.info("Начато удаление работника вместе с задачами");
        try {
            List<Task> tasks = jdbcTemplate.query("SELECT * FROM public.\"Tasks\" WHERE performer = ?"
                    , new Object[]{workerId}, new BeanPropertyRowMapper<>(Task.class));
            for (Task task : tasks) {
                jdbcTemplate.update("DELETE FROM public.\"Tasks\" WHERE id = ?", task.getId());
            }
            if (jdbcTemplate.update("DELETE FROM public.\"Workers\" WHERE id = ?", workerId) > 0) {
                log.info("Работник и его задачи удалены успешно");
                return true;
            } else {
                log.info("Работник и его задачи не удален");
                return false;
            }
        } catch (DataAccessException e) {
            log.error(e.getLocalizedMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public boolean deleteWorkerWithOutTasks(UUID workerId) {
        log.info("Начато удаление работника без задачами");
        try {
            List<Task> tasks = jdbcTemplate.query("SELECT * FROM public.\"Tasks\" WHERE performer = ?"
                    , new Object[]{workerId}, new BeanPropertyRowMapper<>(Task.class));
            for (Task task : tasks) {
                task.setPerformer(null);
                jdbcTemplate.update("UPDATE public.\"Tasks\" SET performer = NULL WHERE id = ?", task.getId());
            }
            if (jdbcTemplate.update("DELETE FROM public.\"Workers\" WHERE id = ?", workerId) > 0) {
                log.info("Работник удален успешно");
                return true;
            } else {
                log.info("Работник не удален");
                return false;
            }
        } catch (DataAccessException e) {
            log.error(e.getLocalizedMessage());
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Worker getWorkerById(UUID workerId) {
        log.info("Старт обращения в БД за работником по Id");
        try {
            String sql = "SELECT w.id, w.name, w.position, w.avatar, t.id as task_id, t.title, t.description, t.time, t.status, t.performer " +
                    "FROM public.\"Workers\" w LEFT JOIN public.\"Tasks\" t ON w.id = t.performer WHERE w.id = ?";
            Worker result = jdbcTemplate.queryForObject(
                    sql,
                    new Object[]{workerId},
                    (rs, rowNum) -> {
                        Worker worker = new Worker();
                        worker.setId(UUID.fromString(rs.getString("id")));
                        worker.setName(rs.getString("name"));
                        worker.setPosition(rs.getString("position"));
                        worker.setAvatar(rs.getString("avatar"));
                        List<Task> tasks = new ArrayList<>();
                        if (rs.getString("task_id") != null) {
                            do {
                                Task task = new Task();
                                task.setId(UUID.fromString(rs.getString("task_id")));
                                task.setTitle(rs.getString("title"));
                                task.setDescription(rs.getString("description"));
                                task.setTime(rs.getTimestamp("time").toLocalDateTime());
                                task.setStatus(rs.getString("status"));
                                task.setPerformer(UUID.fromString(rs.getString("performer")));
                                tasks.add(task);
                            } while (rs.next() && workerId.equals(UUID.fromString(rs.getString("id"))));
                            worker.setTaskList(tasks);
                        }
                        return worker;
                    }
            );
            log.info("Работник и его задачи получены по Id");
            return result;
        } catch (DataAccessException e) {
            log.error(e.getLocalizedMessage());
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Worker> getAllWorker() {
        log.info("Старт обращения в БД за всеми работниками");
        try {
            String sql = "SELECT w.id, w.name, w.position, w.avatar, t.id as task_id, t.title, t.description, t.time, t.status, t.performer " +
                    "sk_id, t.title, t.description, t.time, t.status, t.performer FROM public.\"Workers\" w LEFT JOIN public.\"Tasks\" t ON w.id = t.performer";
            List<Worker> workerList = jdbcTemplate.query(sql,
                    rs -> {
                        Map<UUID, Worker> map = new HashMap<>();
                        while (rs.next()) {
                            UUID id = UUID.fromString(rs.getString("id"));
                            if (!map.containsKey(id)) {
                                Worker worker = new Worker();
                                worker.setId(id);
                                worker.setName(rs.getString("name"));
                                worker.setPosition(rs.getString("position"));
                                worker.setAvatar(rs.getString("avatar"));
                                map.put(id, worker);
                            }
                            if (rs.getString("task_id") != null) {
                                Task task = new Task();
                                task.setId(UUID.fromString(rs.getString("task_id")));
                                task.setTitle(rs.getString("title"));
                                task.setDescription(rs.getString("description"));
                                task.setTime(rs.getTimestamp("time").toLocalDateTime());
                                task.setStatus(rs.getString("status"));
                                task.setPerformer(UUID.fromString(rs.getString("performer")));
                                map.get(id).getTaskList().add(task);
                            }
                        }
                        return new ArrayList<>(map.values());
                    }
            );
            log.info("Все работники и их задачи получены");
            return workerList;
        } catch (DataAccessException e) {
            log.error(e.getLocalizedMessage());
            throw e;
        }
    }
}
