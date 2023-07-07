package com.projectkubbank.dto;

import lombok.Data;
import model.Task;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class WorkerDtoInput implements Serializable {
    @Serial
    private static final long serialVersionUID = -3462266196278777496L;

    private UUID id;
    private String name;
    private String position;
    private String avatar;

    public WorkerDtoInput(){}


}
