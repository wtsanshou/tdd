package ie.wtsanshou.paintshops.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FilePathIllegalException extends Exception {

    public FilePathIllegalException(String format, Object... args) {
        String errorMessage = String.format(format, args);
        log.error(errorMessage);
    }
}
