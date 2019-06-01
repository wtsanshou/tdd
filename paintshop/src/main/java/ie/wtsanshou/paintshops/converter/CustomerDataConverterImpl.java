package ie.wtsanshou.paintshops.converter;

import ie.wtsanshou.paintshops.Factory.CustomerDataFactory;
import ie.wtsanshou.paintshops.exception.ColorConverterException;
import ie.wtsanshou.paintshops.exception.CustomerConverterException;
import ie.wtsanshou.paintshops.exception.CustomerDataConverterException;
import ie.wtsanshou.paintshops.model.CustomerPreference;
import ie.wtsanshou.paintshops.model.CustomerData;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CustomerDataConverterImpl implements CustomerDataConverter {

    private final ColorConverter colorConverter;
    private final CustomerConverter customerConverter;
    private final CustomerDataFactory customerDataFactory;

    public CustomerDataConverterImpl(ColorConverter colorConverter, CustomerConverter customerConverter, CustomerDataFactory customerDataFactory) {

        this.colorConverter = colorConverter;
        this.customerConverter = customerConverter;
        this.customerDataFactory = customerDataFactory;
    }

    public CustomerData convert(List<String> input) throws CustomerDataConverterException {
        if (input == null) throw new CustomerDataConverterException("The customerData input is null");
        if (input.isEmpty())
            return new CustomerData.EmptyCustomerData();
        int color = 0;
        List<CustomerPreference> customers = new ArrayList<>();
        try {
            color = colorConverter.convert(input.get(0));
            for (int i = 1; i < input.size(); i++)
                customers.add(customerConverter.convert(input.get(i)));

        } catch (ColorConverterException e) {
            log.error("Color converter exception", e);
        } catch (CustomerConverterException e) {
            log.error("Customer converter exception", e);
        }

        log.info("Converter customerData: " + input);
        return customerDataFactory.create(color, customers);
    }
}
