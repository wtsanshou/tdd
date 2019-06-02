package ie.wtsanshou.eventbooking.presenter;

import ie.wtsanshou.eventbooking.factory.CountryFactory;
import ie.wtsanshou.eventbooking.model.Countries;
import ie.wtsanshou.eventbooking.model.Partners;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class CountriesPresenterImpl implements CountriesPresenter {

    private DateFormat dateFormatter;
    private CountryFactory countryFactory;

    public CountriesPresenterImpl(DateFormat dateFormatter, CountryFactory countryFactory){
        this.dateFormatter = dateFormatter;
        this.countryFactory = countryFactory;
    }

    @Override
    public Countries generateCountriesFrom(Map<String, List<Partners.Partner>> countryPartners) {

        Countries countries = Countries.builder().countries(new ArrayList<>()).build();

        if (countryPartners == null) return countries;

        List<Countries.Country> countryList = countries.getCountries();
        for (Map.Entry<String, List<Partners.Partner>> cp : countryPartners.entrySet()) {
            Countries.Country country = countryFactory.create(cp.getKey());
            countryList.add(country);

            putPartnersIntoTheCountry(cp.getValue(), country);
        }

        return countries;
    }

    private void putPartnersIntoTheCountry(List<Partners.Partner> partners, Countries.Country country) {
        if (partners == null || partners.isEmpty()) return;

        for (Partners.Partner partner : partners)
            country.getAttendees().add(partner.getEmail());

        country.setAttendeeCount(partners.size());
        Partners.Partner firstPartner = partners.get(0);
        Date startDate = firstPartner.getAvailableDates().get(0);
        country.setStartDate(dateFormatter.format(startDate));
    }
}
