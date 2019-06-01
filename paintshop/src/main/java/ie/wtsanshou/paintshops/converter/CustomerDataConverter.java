package ie.wtsanshou.paintshops.converter;

import ie.wtsanshou.paintshops.exception.CustomerDataConverterException;
import ie.wtsanshou.paintshops.model.CustomerData;

import java.util.List;

public interface CustomerDataConverter {
    CustomerData convert(List<String> input) throws CustomerDataConverterException;
}
