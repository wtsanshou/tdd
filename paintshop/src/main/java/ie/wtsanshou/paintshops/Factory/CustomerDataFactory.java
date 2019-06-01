package ie.wtsanshou.paintshops.Factory;

import ie.wtsanshou.paintshops.model.CustomerPreference;
import ie.wtsanshou.paintshops.model.CustomerData;

import java.util.List;

public interface CustomerDataFactory {
    CustomerData create(int color, List<CustomerPreference> customers);
}
