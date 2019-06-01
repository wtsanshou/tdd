package ie.wtsanshou.paintshops.converter;

import ie.wtsanshou.paintshops.exception.ColorConverterException;

public interface ColorConverter {
    int convert(String color) throws ColorConverterException;
}
