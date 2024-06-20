package com.task.orders.constants;

import org.springframework.http.HttpStatus;

public class StatusCodes {
    //status code
    public static final int SUCCESS = HttpStatus.OK.value();
    public static final int CREATED = HttpStatus.CREATED.value();
    public static final int BAD_REQUEST = HttpStatus.BAD_REQUEST.value();
    public static final int UNAUTHORIZED = HttpStatus.UNAUTHORIZED.value();
    public static final int FORBIDDEN = HttpStatus.FORBIDDEN.value();
    public static final int UNKNOWN = HttpStatus.INTERNAL_SERVER_ERROR.value();
    public static final int NOT_FOUND = HttpStatus.NOT_FOUND.value();
    public static final int CONFLICT = HttpStatus.CONFLICT.value();
    public static final int EMPTY = HttpStatus.NO_CONTENT.value();
}
