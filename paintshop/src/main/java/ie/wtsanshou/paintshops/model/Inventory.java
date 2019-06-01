package ie.wtsanshou.paintshops.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@Builder
public class Inventory {

    int colorNum;
    Map<Integer, PaintType> paints;

    public static class NoSolution extends Inventory {
        public NoSolution() {
            super(-1, new HashMap<>());
        }
    }
}
