package ie.wtsanshou.eventbooking.factory;

import ie.wtsanshou.eventbooking.model.Countries;

public interface CountryFactory {
    Countries.Country create(String name);
}
