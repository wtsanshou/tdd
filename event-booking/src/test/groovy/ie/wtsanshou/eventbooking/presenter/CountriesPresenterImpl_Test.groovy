package ie.wtsanshou.eventbooking.presenter

import ie.wtsanshou.eventbooking.factory.CountryFactory
import ie.wtsanshou.eventbooking.factory.CountryFactoryImpl

import ie.wtsanshou.eventbooking.model.Partners
import spock.lang.Specification

import static ie.wtsanshou.eventbooking.Util.*

class CountriesPresenterImpl_Test extends Specification {

    private CountriesPresenter countriesPresenter

    void setup() {
        CountryFactory countryFactory = new CountryFactoryImpl()
        countriesPresenter = new CountriesPresenterImpl(dateFormatter, countryFactory)
    }

    def "given null country partner map, return empty countries"() {
        when:
        def countries = this.countriesPresenter.generateCountriesFrom(null)
        then:
        countries.getCountries() == []
    }

    def "given a country partner map with two countries, return a Countries with two countries"() {
        given:
        String testCountry1 = "testCountry1"
        List<Date> availableDates1 = Arrays.asList(DATE1, DATE2)
        Partners.Partner partner1 = getPartner(testCountry1, availableDates1, "test@email1", "firstName1", "lastName1")
        List<Partners.Partner> partners1 = Arrays.asList(partner1)

        String testCountry2 = "testCountry2"
        List<Date> availableDates2 = Arrays.asList(DATE1, DATE2)
        Partners.Partner partner2 = getPartner(testCountry2, availableDates2, "test@email2", "firstName2", "lastName2")
        List<Partners.Partner> partners2 = Arrays.asList(partner2)

        def countryPartners = new HashMap<String, List<Partners.Partner>>()
        countryPartners.put(testCountry1, partners1)
        countryPartners.put(testCountry2, partners2)

        when:
        def countries = countriesPresenter.generateCountriesFrom(countryPartners)
        then:
        countries.getCountries().size() == 2
        countries.getCountries().get(0).name == testCountry2
        countries.getCountries().get(0).attendeeCount == 1
        countries.getCountries().get(0).attendees == ["test@email2"]
        countries.getCountries().get(0).startDate == "2019-04-08"
        countries.getCountries().get(1).name == testCountry1
        countries.getCountries().get(1).attendeeCount == 1
        countries.getCountries().get(1).attendees == ["test@email1"]
        countries.getCountries().get(1).startDate == "2019-04-08"
    }

    Partners.Partner getPartner(String testCountry, List<Date> availableDates, String email, String firstName, String lastName) {
        Partners.Partner.builder().country(testCountry).availableDates(availableDates).email(email).firstName(firstName).lastName(lastName).build()
    }

    def "given empty country partner map, return empty countries"() {
        given:
        when:
        def countries = countriesPresenter.generateCountriesFrom(new HashMap<>())
        then:
        countries.getCountries() == []
    }

    def "given one country partner map but null partner list, return a countries with one empty country"() {
        given:
        def countryPartners = new HashMap<>()
        countryPartners.put("testCountry", null)
        when:
        def countries = countriesPresenter.generateCountriesFrom(countryPartners)
        then:
        countries.getCountries().size() == 1
        countries.getCountries().get(0).name == "testCountry"
        countries.getCountries().get(0).attendeeCount == 0
        countries.getCountries().get(0).attendees == []
        countries.getCountries().get(0).startDate == null
    }

    def "given one country partner map but empty partner list, return a countries with null startDate"() {
        given:
        def countryPartners = new HashMap<>()
        countryPartners.put("testCountry", new ArrayList<>())
        when:
        def countries = countriesPresenter.generateCountriesFrom(countryPartners)
        then:
        countries.getCountries().size() == 1
        countries.getCountries().get(0).name == "testCountry"
        countries.getCountries().get(0).attendeeCount == 0
        countries.getCountries().get(0).attendees == []
        countries.getCountries().get(0).startDate == null
    }

    def "given country partner map with two countries, return Countries with two countries"() {
        given:
        String testCountry = "testCountry1"
        List<Date> availableDates = Arrays.asList(DATE1, DATE2)
        Partners.Partner partner = getPartner(testCountry, availableDates, "test@email", "firstName", "lastName")
        List<Partners.Partner> partners = Arrays.asList(partner)
        def countryPartners = new HashMap<String, List<Partners.Partner>>()
        countryPartners.put(testCountry, partners)
        when:
        def countries = countriesPresenter.generateCountriesFrom(countryPartners)
        then:
        countries.getCountries().size() == 1
        countries.getCountries().get(0).name == testCountry
        countries.getCountries().get(0).attendeeCount == 1
        countries.getCountries().get(0).attendees == ["test@email"]
        countries.getCountries().get(0).startDate == "2019-04-08"
    }

    def "given a country partner map with one of the two countries has no partners, return a Countries with one of the two countries has no attendees"() {
        given:
        String testCountry1 = "testCountry1"
        List<Date> availableDates1 = Arrays.asList(DATE1, DATE2)
        Partners.Partner partner1 = getPartner(testCountry1, availableDates1, "test@email1", "firstName1", "lastName1")
        List<Partners.Partner> partners1 = Arrays.asList(partner1)

        String testCountry2 = "testCountry2"

        def countryPartners = new HashMap<String, List<Partners.Partner>>()
        countryPartners.put(testCountry1, partners1)
        countryPartners.put(testCountry2, new ArrayList<>())

        when:
        def countries = countriesPresenter.generateCountriesFrom(countryPartners)
        then:
        countries.getCountries().size() == 2
        countries.getCountries().get(0).name == testCountry2
        countries.getCountries().get(0).attendeeCount == 0
        countries.getCountries().get(0).attendees == []
        countries.getCountries().get(0).startDate == null
        countries.getCountries().get(1).name == testCountry1
        countries.getCountries().get(1).attendeeCount == 1
        countries.getCountries().get(1).attendees == ["test@email1"]
        countries.getCountries().get(1).startDate == "2019-04-08"
    }

}
