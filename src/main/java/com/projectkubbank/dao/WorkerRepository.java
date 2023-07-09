package com.projectkubbank.dao;

import com.projectkubbank.model.Worker;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface WorkerRepository {

    boolean addWorker(Worker worker);

    boolean updateWorker(Worker worker);

    boolean deleteWorkerWithTasks(UUID workerId);

    boolean deleteWorkerWithOutTasks(UUID workerId);

    Worker getWorkerById(UUID id);

    List<Worker> getAllWorker();

}
