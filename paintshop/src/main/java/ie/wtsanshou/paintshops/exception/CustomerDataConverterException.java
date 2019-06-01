package ie.wtsanshou.paintshops.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomerDataConverterException extends Exception {

    public CustomerDataConverterException(String format, Object... args) {
        String errorMessage = String.format(format, args);
        log.error(errorMessage);
    }
}
