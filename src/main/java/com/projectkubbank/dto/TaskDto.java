package com.projectkubbank.dto;

import lombok.Data;
import com.projectkubbank.model.Task;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class TaskDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 3209061787630836529L;

    private UUID id;
    private String title;
    private String description;
    private LocalDateTime time;
    private String status;
    private UUID performer;

    public TaskDto() {
    }

    public TaskDto(Task task) {
        this.setId(task.getId());
        this.setTitle(task.getTitle());
        this.setDescription(task.getDescription());
        this.setTime(task.getTime());
        this.setStatus(task.getStatus());
        this.setPerformer(task.getPerformer());
    }

}
