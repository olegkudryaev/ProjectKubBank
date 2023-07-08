package com.projectkubbank.common;

import com.projectkubbank.model.Task;
import org.springframework.stereotype.Component;

import java.util.ArrayDeque;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class TaskQueue {
    private static TaskQueue instance;
    private LinkedBlockingQueue<Task> deque;

    private TaskQueue() {
        deque = new LinkedBlockingQueue<>();
    }

    public static synchronized TaskQueue getInstance() {
        if (instance == null) {
            instance = new TaskQueue();
        }
        return instance;
    }

    public void addItem(Task task) {
        synchronized (this) {
            deque.add(task);
        }
    }

    public Task removeItem() {
        synchronized (this) {
            return deque.poll();
        }
    }
}
