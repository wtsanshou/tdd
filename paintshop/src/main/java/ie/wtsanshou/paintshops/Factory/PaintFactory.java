package ie.wtsanshou.paintshops.Factory;

import ie.wtsanshou.paintshops.model.Paint;
import ie.wtsanshou.paintshops.model.PaintType;

//TODO: delete it
public interface PaintFactory {
    Paint create(int color, PaintType type);
}
