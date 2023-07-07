package com.projectkubbank.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class WorkerDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 6287236421348329409L;
    private UUID id;
    private String name;
    private String position;
    private String avatar;
    private List<TaskDto> taskDtoList = new ArrayList<>();

    public WorkerDto(){}

    public WorkerDto(Worker worker){
        this.setId(worker.getId());
        this.setName(worker.getName());
        this.setPosition(worker.getPosition());
        this.setAvatar(worker.getAvatar());
        if (worker.getTask() != null) {
            for (Task task: worker.getTask()) {
                TaskDto taskDto = new TaskDto(task);
                taskDtoList.add(taskDto);
            }
        }
    }


}
