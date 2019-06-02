package ie.wtsanshou.eventbooking.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Partners {
    List<Partner> partners;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Partner {
        private String firstName;
        private String lastName;
        private String email;
        private String country;

        public List<Date> availableDates;
    }
}
