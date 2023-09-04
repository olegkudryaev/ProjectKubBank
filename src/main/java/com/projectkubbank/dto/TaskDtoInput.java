package com.projectkubbank.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class TaskDtoInput implements Serializable {
    @Serial
    private static final long serialVersionUID = -3462266196278777496L;

    @NotNull(message = "Invalid id")
    private UUID id;
    @NotNull(message = "Invalid title")
    private String title;
    @NotNull(message = "Invalid description")
    private String description;
    @NotNull(message = "Invalid time")
    private LocalDateTime time;
    @NotNull(message = "Invalid status")
    private String status;

    public TaskDtoInput() {
        //пустой конструктор для десериализации
    }

}
