package ie.wtsanshou.paintshops.Factory;

import ie.wtsanshou.paintshops.model.CustomerData;
import ie.wtsanshou.paintshops.model.CustomerPreference;

import java.util.List;

public class CustomerDataFactoryImpl implements CustomerDataFactory {
    @Override
    public CustomerData create(int color, List<CustomerPreference> customerPreferences) {
        return CustomerData.builder().color(color).customerPreferences(customerPreferences).build();
    }
}
