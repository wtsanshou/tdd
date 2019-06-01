package ie.wtsanshou.paintshops.inbound;

import ie.wtsanshou.paintshops.exception.FilePathIllegalException;

import java.util.List;

public interface CustomerDataReader {
    List<String> read() throws FilePathIllegalException;
}
