package ie.wtsanshou.paintshops.model;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.LinkedList;
import java.util.List;

@Builder
@AllArgsConstructor
public class CustomerPreference {
    List<Paint> paints;

    public List<Paint> getPaints() {
        return paints;
    }

    public static class EmptyCustomerPreference extends CustomerPreference {

        public EmptyCustomerPreference() {
            super(new LinkedList<>());
        }
    }
}
