package ie.wtsanshou.paintshops.converter;

import ie.wtsanshou.paintshops.exception.ColorConverterException;

public class ColorConverterImpl implements ColorConverter {


    public int convert(String color) throws ColorConverterException {
        if (color == null) throw new ColorConverterException("color is null");
        return getColorNum(color);
    }

    private int getColorNum(String color) throws ColorConverterException {
        int colorNum;
        try {
            colorNum = Integer.valueOf(color.trim());
            if (colorNum <= 0) throw new ColorConverterException("color number {%s} is not positive integer", color);
        } catch (Exception e) {
            throw new ColorConverterException("{%s} is not able to transfer to Integer", color);
        }
        return colorNum;
    }
}
