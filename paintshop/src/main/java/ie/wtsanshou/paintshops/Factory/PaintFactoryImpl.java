package ie.wtsanshou.paintshops.Factory;

import ie.wtsanshou.paintshops.model.Paint;
import ie.wtsanshou.paintshops.model.PaintType;

public class PaintFactoryImpl implements PaintFactory{
     public Paint create(int color, PaintType type){
         return Paint.builder().color(color).type(type).build();
     }
 }
