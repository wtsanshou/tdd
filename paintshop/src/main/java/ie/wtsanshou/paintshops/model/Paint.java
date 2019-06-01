package ie.wtsanshou.paintshops.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Paint {
    int color;
    PaintType type;
}
