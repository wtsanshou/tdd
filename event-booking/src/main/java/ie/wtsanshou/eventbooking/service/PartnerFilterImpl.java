package ie.wtsanshou.eventbooking.service;

import ie.wtsanshou.eventbooking.model.Partners;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PartnerFilterImpl implements PartnerFilter {

    private final long ONE_DAY = TimeUnit.DAYS.toMillis(1);

    public Map<String, List<Partners.Partner>> filter(Partners partners) {
        HashMap<String, List<Partners.Partner>> availablePartnersPerCountry = new HashMap<>();

        if (isInvalidPartners(partners))
            return availablePartnersPerCountry;

        List<Partners.Partner> partnerList = filterOutPartnersWithInvalidCountryName(partners);

        putPartnersIntoCountryMap(availablePartnersPerCountry, partnerList);

        filterMaxAvailablePartnersInEachCountry(availablePartnersPerCountry);

        return availablePartnersPerCountry;
    }

    private boolean isInvalidPartners(Partners partners) {
        return partners == null || partners.getPartners() == null || partners.getPartners().isEmpty();
    }

    private List<Partners.Partner> filterOutPartnersWithInvalidCountryName(Partners partners) {
        return partners.getPartners().stream().filter(validCountryName()).collect(Collectors.toList());
    }

    private Predicate<Partners.Partner> validCountryName() {
        return p -> p.getCountry() != null && !p.getCountry().trim().isEmpty();
    }

    private void putPartnersIntoCountryMap(HashMap<String, List<Partners.Partner>> availPartnersPerCountry, List<Partners.Partner> partnerList) {
        for (Partners.Partner partner : partnerList)
            putThePartnerIntoTheCountryMap(partner, availPartnersPerCountry);
    }

    private void putThePartnerIntoTheCountryMap(Partners.Partner partner, HashMap<String, List<Partners.Partner>> availPartnersPerCountry) {
        String countryName = partner.getCountry();
        if (!availPartnersPerCountry.containsKey(countryName))
            availPartnersPerCountry.put(countryName, new ArrayList<>());

        availPartnersPerCountry.get(countryName).add(partner);
    }

    private void filterMaxAvailablePartnersInEachCountry(HashMap<String, List<Partners.Partner>> availPartnersPerCountry) {
        for (Map.Entry<String, List<Partners.Partner>> countryPartners : availPartnersPerCountry.entrySet())
            filterMaxAvailablePartnersInTheCountry(availPartnersPerCountry, countryPartners);
    }

    private void filterMaxAvailablePartnersInTheCountry(HashMap<String, List<Partners.Partner>> availPartnersPerCountry, Map.Entry<String, List<Partners.Partner>> countryPartners) {
        List<Partners.Partner> partnersInTheCountry = countryPartners.getValue();
        List<Partners.Partner> availPartners = findMostAvailablePartners(partnersInTheCountry);
        availPartnersPerCountry.put(countryPartners.getKey(), availPartners);
    }

    private List<Partners.Partner> findMostAvailablePartners(List<Partners.Partner> partnersInTheCountry) {

        List<Date> sortedUniqueValidDays = getSortedUniqueValidDaysFrom(partnersInTheCountry);
        List<Partners.Partner> maxAvailablePartners = new ArrayList<>();

        for (Date today : sortedUniqueValidDays) {
            Date tomorrow = new Date(today.getTime() + ONE_DAY);
            List<Partners.Partner> filteredPartners = partnersInTheCountry.stream()
                    .filter(p -> available(p, today, tomorrow))
                    .collect(Collectors.toList());
            if (filteredPartners.size() > maxAvailablePartners.size()) {
                maxAvailablePartners = filteredPartners;
                maxAvailablePartners.forEach(p -> p.getAvailableDates().set(0, today));
            }
        }

        return maxAvailablePartners;
    }

    private List<Date> getSortedUniqueValidDaysFrom(List<Partners.Partner> partnersInTheCountry) {
        List<List<Date>> allValidDaysOfAllPartners = partnersInTheCountry.stream()
                .filter(p -> p.getAvailableDates() != null)
                .map(Partners.Partner::getAvailableDates)
                .collect(Collectors.toList());
        return allValidDaysOfAllPartners.stream()
                .flatMap(Collection::stream)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    private boolean available(Partners.Partner partner, Date date1, Date date2) {
        boolean date1IsAvailable = partner.getAvailableDates().stream().anyMatch(d -> d.equals(date1));
        boolean date2IsAvailable = partner.getAvailableDates().stream().anyMatch(d -> d.equals(date2));
        return date1IsAvailable && date2IsAvailable;
    }

}
