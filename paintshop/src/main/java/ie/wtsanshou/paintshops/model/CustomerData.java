package ie.wtsanshou.paintshops.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class CustomerData {
    int color;
    List<CustomerPreference> customerPreferences;

    public List<CustomerPreference> getCustomerPreferences() {
        return new ArrayList<>(customerPreferences);
    }

    public static class EmptyCustomerData extends CustomerData {
        public EmptyCustomerData() {
            super(0, new ArrayList<>());
        }
    }
}
