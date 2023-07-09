package com.projectkubbank.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class TaskDtoInput implements Serializable {
    @Serial
    private static final long serialVersionUID = -3462266196278777496L;

    private UUID id;
    private String title;
    private String description;
    private LocalDateTime time;
    private String status;

    public TaskDtoInput() {
    }

}
