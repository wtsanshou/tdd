package ie.wtsanshou.eventbooking.presenter;

import ie.wtsanshou.eventbooking.model.Countries;
import ie.wtsanshou.eventbooking.model.Partners;

import java.util.List;
import java.util.Map;

public interface CountriesPresenter {
    Countries generateCountriesFrom(Map<String, List<Partners.Partner>> countryPartners);
}
