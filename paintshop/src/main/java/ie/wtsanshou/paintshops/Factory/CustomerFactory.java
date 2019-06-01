package ie.wtsanshou.paintshops.Factory;

import ie.wtsanshou.paintshops.model.CustomerPreference;
import ie.wtsanshou.paintshops.model.Paint;

import java.util.List;

public interface CustomerFactory {

    CustomerPreference create(List<Paint> ps);
}
