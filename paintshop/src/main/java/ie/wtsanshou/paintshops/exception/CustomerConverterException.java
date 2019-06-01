package ie.wtsanshou.paintshops.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomerConverterException extends Exception {

    public CustomerConverterException(String format, Object... args) {
        String errorMessage = String.format(format, args);
        log.error(errorMessage);
    }
}
