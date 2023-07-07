package com.projectkubbank.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Worker implements Serializable {
    @Serial
    private static final long serialVersionUID = -4510849922040676107L;
    private UUID id;
    private String name;
    private String position;
    private String avatar;
    private List<Task> taskList = new ArrayList<>();

}
