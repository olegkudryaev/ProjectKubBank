package com.projectkubbank.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Data
public class WorkerDtoInput implements Serializable {
    @Serial
    private static final long serialVersionUID = -2069111562748051924L;
    private UUID id;
    private String name;
    private String position;
    private String avatar;

    public WorkerDtoInput(){}

}
