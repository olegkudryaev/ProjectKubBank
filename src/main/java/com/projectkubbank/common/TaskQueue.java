package com.projectkubbank.commonBeans;

import model.Task;
import org.springframework.stereotype.Component;

import java.util.ArrayDeque;

@Component
public class TaskQueue {
    private static TaskQueue instance;
    private ArrayDeque<Task> deque;

    private TaskQueue() {
        deque = new ArrayDeque<>();
    }

    public static synchronized TaskQueue getInstance() {
        if (instance == null) {
            instance = new TaskQueue();
        }
        return instance;
    }

    public void addItem(Task task) {
        deque.add(task);
    }

    public Task removeItem() {
        return deque.poll();
    }
}
