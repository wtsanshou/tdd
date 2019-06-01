package ie.wtsanshou.paintshops.outbound;

import java.util.List;

public interface InventoryWriter {
    void write(List<String> paints);
}
