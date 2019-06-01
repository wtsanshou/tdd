package ie.wtsanshou.paintshops.Factory;

import ie.wtsanshou.paintshops.model.CustomerPreference;
import ie.wtsanshou.paintshops.model.Paint;

import java.util.ArrayDeque;
import java.util.List;

public class CustomerFactoryImpl implements CustomerFactory {
    @Override
    public CustomerPreference create(List<Paint> paints) {
        return CustomerPreference.builder().paints(paints).build();
    }
}
