package ie.wtsanshou.paintshops.inbound;

import ie.wtsanshou.paintshops.exception.FilePathIllegalException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class CustomerDataFileReader implements CustomerDataReader {

    private String filePath;

    public CustomerDataFileReader(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<String> read() throws FilePathIllegalException {
        if (filePath == null || filePath.trim().isEmpty()) throw new FilePathIllegalException("file path is null or empty");

        return getOrder();
    }

    private List<String> getOrder() {
        List<String> order = new ArrayList<>();
        try {
            final URL resource = getClass().getClassLoader()
                                            .getResource(filePath.trim());
            Path path = Paths.get(resource.toURI());

            order = Files.lines(path).collect(Collectors.toList());
        } catch (IOException | URISyntaxException | NullPointerException e) {
            log.error("File reading exception");
        }
        return order;
    }
}
