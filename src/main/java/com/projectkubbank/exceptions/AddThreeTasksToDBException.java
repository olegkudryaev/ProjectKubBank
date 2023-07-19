package com.projectkubbank.exceptions;

import lombok.Getter;

import java.io.Serial;

@Getter
public class AddThreeTasksToDBException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 6721989213433446089L;

    public AddThreeTasksToDBException(String message) {
        super(message);
    }

}
