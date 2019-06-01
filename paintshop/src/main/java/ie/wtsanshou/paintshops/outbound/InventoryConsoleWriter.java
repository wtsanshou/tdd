package ie.wtsanshou.paintshops.outbound;

import java.util.List;

public class InventoryConsoleWriter implements InventoryWriter {
    @Override
    public void write(List<String> paints) {
        for(String paint : paints)
            System.out.print(paint+ " ");
        System.out.println();
    }
}
