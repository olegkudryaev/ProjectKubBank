package com.projectkubbank.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Data
public class WorkerDtoInput implements Serializable {
    @Serial
    private static final long serialVersionUID = -2069111562748051924L;

    @NotNull(message = "Invalid id")
    private UUID id;
    @NotNull(message = "Invalid name")
    private String name;
    @NotNull(message = "Invalid position")
    private String position;
    @NotNull(message = "Invalid avatar")
    private String avatar;

    public WorkerDtoInput() {
        //пустой конструктор для десериализации
    }



}
