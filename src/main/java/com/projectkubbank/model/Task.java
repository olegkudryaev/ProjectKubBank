package com.projectkubbank.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Task implements Serializable {
    @Serial
    private static final long serialVersionUID = -5521467769307252473L;

    private UUID id;
    private String title;
    private String description;
    private LocalDateTime time;
    private String status;
    private UUID performer;

}
