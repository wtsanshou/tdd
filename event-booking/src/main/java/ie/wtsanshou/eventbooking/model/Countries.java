package ie.wtsanshou.eventbooking.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class Countries {
    List<Country> countries;

    @Data
    @Builder
    @AllArgsConstructor
    public static class Country {
        private int attendeeCount;
        private List<String> attendees;
        private String name;
        private String startDate;

    }
}
