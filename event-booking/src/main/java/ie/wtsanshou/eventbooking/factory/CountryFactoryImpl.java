package ie.wtsanshou.eventbooking.factory;

import ie.wtsanshou.eventbooking.model.Countries.Country;

import java.util.ArrayList;

public class CountryFactoryImpl implements CountryFactory {
    @Override
    public Country create(String countryName) {
        return Country.builder().name(countryName).attendeeCount(0).attendees(new ArrayList<>()).build();
    }
}
