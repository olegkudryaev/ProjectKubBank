package com.projects7.dto.wrapped;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DtoWrapper implements Serializable {

    @Serial
    private static final long serialVersionUID = -1611929146412107565L;

    private boolean success;
    private String message;
    private String snackbarType;
}
