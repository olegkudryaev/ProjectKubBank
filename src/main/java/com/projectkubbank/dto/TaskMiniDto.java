package com.projectkubbank.dto;

import lombok.Data;
import model.Task;

import java.io.Serial;
import java.io.Serializable;
import java.security.PublicKey;
import java.util.UUID;

@Data
public class TaskMiniListDto implements Serializable {
    @Serial
    private static final long serialVersionUID = -5881629469096132745L;

    private UUID id;
    private String title;
    private String status;
    
    public TaskMiniListDto(){}

    public TaskMiniListDto(Task task){
        this.id = task.getId();
        this.title = task.getTitle();
        this.status = task.getStatus();
    }
    
}
