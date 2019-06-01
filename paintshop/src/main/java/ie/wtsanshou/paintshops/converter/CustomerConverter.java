package ie.wtsanshou.paintshops.converter;

import ie.wtsanshou.paintshops.exception.ColorConverterException;
import ie.wtsanshou.paintshops.exception.CustomerConverterException;
import ie.wtsanshou.paintshops.model.CustomerPreference;

public interface CustomerConverter {
    CustomerPreference convert(String customerColors) throws CustomerConverterException, ColorConverterException;
}
