package com.itechart.devbooks.util;

import com.itechart.devbooks.exception.NotFoundException;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ExceptionUtil {

    public static NotFoundException notFoundException(String message){
        return new NotFoundException(message);
    }

    public static IllegalArgumentException illegalArgumentException(String message) {
        return new IllegalArgumentException(message);
    }

}
