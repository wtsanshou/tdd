package ie.wtsanshou.eventbooking.service

import ie.wtsanshou.eventbooking.model.Partners
import spock.lang.Specification

import static ie.wtsanshou.eventbooking.Util.*

class PartnerFilterImpl_Test extends Specification {
    PartnerFilter partnerFilter

    void setup() {
        partnerFilter = new PartnerFilterImpl()
    }

    def "given null partners, return empty country-partners map"() {
        given:
        def partners = null
        when:
        def country_Partners = partnerFilter.filter(partners)
        then:
        country_Partners.size() == 0
    }

    def "given partners with one partner who has two days period available, return an country-partners map with one country and one partner"() {
        given:
        Partners.Partner partner = getPartner("testCountry", [DATE1, DATE2], "test@email", "firstName", "lastName")
        def partnerList = [partner]
        def partners = getPartners(partnerList)
        when:
        def country_Partners = this.partnerFilter.filter(partners)
        then:
        country_Partners.size() == 1
    }

    def "given partners with null partnerList, return empty country-partners map"() {
        given:
        def partners = getPartners(null)
        when:
        def country_Partners = partnerFilter.filter(partners)
        then:
        country_Partners.size() == 0
    }

    def "given partners with empty partnerList, return empty country-partners map"() {
        given:
        def partners = getPartners(new ArrayList<>())
        when:
        def country_Partners = partnerFilter.filter(partners)
        then:
        country_Partners.size() == 0
    }

    def "given partners with one partner from one countries with null available dates, return one country and emtpy partners in the country"() {
        given:
        def testCountry1 = "testCountry1"
        Partners.Partner partner1 = getPartner(testCountry1, null, "test@email1", "firstName1", "lastName1")

        def partnerList = [partner1]
        def partners = getPartners(partnerList)
        when:
        def country_Partners = partnerFilter.filter(partners)
        then:
        country_Partners.size() == 1
        country_Partners.get(testCountry1) == []
    }

    def "given partners with one partner from one countries with null country name, return one country and emtpy partners in the country"() {
        given:
        Partners.Partner partner1 = getPartner(null, [DATE2, DATE3], "test@email1", "firstName1", "lastName1")

        def partnerList = [partner1]
        def partners = getPartners(partnerList)
        when:
        def country_Partners = partnerFilter.filter(partners)
        then:
        country_Partners.size() == 0
    }

    def "given partners with one partner from one countries with empty country name, return one country and emtpy partners in the country"() {
        given:
        Partners.Partner partner1 = getPartner("", [DATE2, DATE3], "test@email1", "firstName1", "lastName1")

        def partnerList = [partner1]
        def partners = getPartners(partnerList)
        when:
        def country_Partners = partnerFilter.filter(partners)
        then:
        country_Partners.size() == 0
    }

    def "given partners with one partner from one countries with blank country name, return one country and emtpy partners in the country"() {
        given:
        Partners.Partner partner1 = getPartner("    ", [DATE2, DATE3], "test@email1", "firstName1", "lastName1")

        def partnerList = [partner1]
        def partners = getPartners(partnerList)
        when:
        def country_Partners = partnerFilter.filter(partners)
        then:
        country_Partners.size() == 0
    }

    def "given partners with three partners from one countries with empty available dates, return one country and emtpy partners in the country"() {
        given:
        def testCountry1 = "testCountry1"
        Partners.Partner partner1 = getPartner(testCountry1, [], "test@email1", "firstName1", "lastName1")
        Partners.Partner partner2 = getPartner(testCountry1, [], "test@email2", "firstName2", "lastName2")
        Partners.Partner partner3 = getPartner(testCountry1, [], "test@email3", "firstName3", "lastName3")

        def partnerList = [partner1, partner2, partner3]
        def partners = getPartners(partnerList)
        when:
        def country_Partners = partnerFilter.filter(partners)
        then:
        country_Partners.size() == 1
        country_Partners.get(testCountry1) == []
    }

    def "given partners with two partners who has two days period available from two countries, return two country and one partner in each country"() {
        given:
        def testCountry1 = "testCountry1"
        Partners.Partner partner1 = getPartner(testCountry1, [DATE1, DATE2], "test@email1", "firstName1", "lastName1")
        def testCountry2 = "testCountry2"
        Partners.Partner partner2 = getPartner(testCountry2, [DATE1, DATE2], "test@email2", "firstName2", "lastName2")

        def partnerList = [partner1, partner2]
        def partners = getPartners(partnerList)
        when:
        def country_Partners = partnerFilter.filter(partners)
        then:
        country_Partners.size() == 2
        country_Partners.get(testCountry1) == [partner1]
        country_Partners.get(testCountry2) == [partner2]
    }

    def "given partners with two partners who has two days period available from one countries, return one country and two partners in the country"() {
        given:
        def testCountry1 = "testCountry1"
        Partners.Partner partner1 = getPartner(testCountry1, [DATE1, DATE2], "test@email1", "firstName1", "lastName1")
        Partners.Partner partner2 = getPartner(testCountry1, [DATE1, DATE2], "test@email2", "firstName2", "lastName2")

        def partnerList = [partner1, partner2]
        def partners = getPartners(partnerList)
        when:
        def country_Partners = partnerFilter.filter(partners)
        then:
        country_Partners.size() == 1
        country_Partners.get(testCountry1) == [partner1, partner2]
    }

    def "given partners with two partners from one countries but only one with available two days period, return one country and one partners in the country"() {
        given:
        def testCountry1 = "testCountry1"
        Partners.Partner partner1 = getPartner(testCountry1, [DATE1, DATE2], "test@email1", "firstName1", "lastName1")
        Partners.Partner partner2 = getPartner(testCountry1, [DATE1, DATE3], "test@email2", "firstName2", "lastName2")

        def partnerList = [partner1, partner2]
        def partners = getPartners(partnerList)
        when:
        def country_Partners = partnerFilter.filter(partners)
        then:
        country_Partners.size() == 1
        country_Partners.get(testCountry1) == [partner1]
    }

    def "given partners with two partners from one countries with not ordered dates, return one country and one partners in the country"() {
        given:
        def testCountry1 = "testCountry1"
        Partners.Partner partner1 = getPartner(testCountry1, [DATE2, DATE1], "test@email1", "firstName1", "lastName1")
        Partners.Partner partner2 = getPartner(testCountry1, [DATE3, DATE1], "test@email2", "firstName2", "lastName2")

        def partnerList = [partner1, partner2]
        def partners = getPartners(partnerList)
        when:
        def country_Partners = partnerFilter.filter(partners)
        then:
        country_Partners.size() == 1
        country_Partners.get(testCountry1) == [partner1]
    }

    def "given partners with three partners from one countries with two partners in the same 2 days period, return one country and the two partners in the country"() {
        given:
        def testCountry1 = "testCountry1"
        Partners.Partner partner1 = getPartner(testCountry1, [DATE1, DATE2], "test@email1", "firstName1", "lastName1")
        Partners.Partner partner2 = getPartner(testCountry1, [DATE2, DATE3], "test@email2", "firstName2", "lastName2")
        Partners.Partner partner3 = getPartner(testCountry1, [DATE1, DATE2], "test@email3", "firstName3", "lastName3")

        def partnerList = [partner1, partner2, partner3]
        def partners = getPartners(partnerList)
        when:
        def country_Partners = partnerFilter.filter(partners)
        then:
        country_Partners.size() == 1
        country_Partners.get(testCountry1) == [partner1, partner3]
        country_Partners.get(testCountry1).get(0).getAvailableDates().size() > 1
        country_Partners.get(testCountry1).get(0).getAvailableDates().get(0) == DATE1
    }

    def "given partners with three partners from one countries with one partner has no available dates, return one country and the two partners in the country"() {
        given:
        def testCountry1 = "testCountry1"
        Partners.Partner partner1 = getPartner(testCountry1, [DATE2, DATE3], "test@email1", "firstName1", "lastName1")
        Partners.Partner partner2 = getPartner(testCountry1, [], "test@email2", "firstName2", "lastName2")
        Partners.Partner partner3 = getPartner(testCountry1, [DATE2, DATE3], "test@email3", "firstName3", "lastName3")

        def partnerList = [partner1, partner2, partner3]
        def partners = getPartners(partnerList)
        when:
        def country_Partners = partnerFilter.filter(partners)
        then:
        country_Partners.size() == 1
        country_Partners.get(testCountry1) == [partner1, partner3]
        country_Partners.get(testCountry1).get(0).getAvailableDates().size() > 1
        country_Partners.get(testCountry1).get(0).getAvailableDates().get(0) == DATE2
    }

    def "given partners with three partners from one countries but no available dates, return one country and emtpy partners in the country"() {
        given:
        def testCountry1 = "testCountry1"
        Partners.Partner partner1 = getPartner(testCountry1, [DATE2, DATE5], "test@email1", "firstName1", "lastName1")
        Partners.Partner partner2 = getPartner(testCountry1, [DATE3, DATE5], "test@email2", "firstName2", "lastName2")
        Partners.Partner partner3 = getPartner(testCountry1, [DATE1, DATE3], "test@email3", "firstName3", "lastName3")

        def partnerList = [partner1, partner2, partner3]
        def partners = getPartners(partnerList)
        when:
        def country_Partners = partnerFilter.filter(partners)
        then:
        country_Partners.size() == 1
        country_Partners.get(testCountry1) == []
    }

    def "given partners with two partners from one countries , return one country and the early available partner in the country"() {
        given:
        def testCountry1 = "testCountry1"
        Partners.Partner partner1 = getPartner(testCountry1, [DATE4, DATE5], "test@email1", "firstName1", "lastName1")
        Partners.Partner partner2 = getPartner(testCountry1, [DATE2, DATE3], "test@email2", "firstName2", "lastName2")

        def partnerList = [partner1, partner2]
        def partners = getPartners(partnerList)
        when:
        def country_Partners = partnerFilter.filter(partners)
        then:
        country_Partners.size() == 1
        country_Partners.get(testCountry1) == [partner2]
        country_Partners.get(testCountry1).get(0).getAvailableDates().size() > 1
        country_Partners.get(testCountry1).get(0).getAvailableDates().get(0) == DATE2
    }

    def "given partners with two partners in two countries , return two country and the early available partner in the country"() {
        given:
        def testCountry1 = "testCountry1"
        Partners.Partner partner1 = getPartner(testCountry1, [DATE4, DATE5], "test@email1", "firstName1", "lastName1")
        Partners.Partner partner2 = getPartner(testCountry1, [DATE2, DATE3], "test@email2", "firstName2", "lastName2")

        def testCountry2 = "testCountry2"
        Partners.Partner partner3 = getPartner(testCountry2, [DATE4, DATE5], "test@email3", "firstName3", "lastName3")
        Partners.Partner partner4 = getPartner(testCountry2, [DATE4, DATE5], "test@email4", "firstName4", "lastName4")

        def partnerList = [partner1, partner2, partner3, partner4]
        def partners = getPartners(partnerList)
        when:
        def country_Partners = partnerFilter.filter(partners)
        then:
        country_Partners.size() == 2
        country_Partners.get(testCountry1) == [partner2]
        country_Partners.get(testCountry2) == [partner3, partner4]
        country_Partners.get(testCountry1).get(0).getAvailableDates().get(0) == DATE2
        country_Partners.get(testCountry2).get(0).getAvailableDates().get(0) == DATE4
    }

    def "given partners with two partners in two countries no available partners, return two country and the early available partner in one country"() {
        given:
        def testCountry1 = "testCountry1"
        Partners.Partner partner1 = getPartner(testCountry1, [DATE4, DATE5], "test@email1", "firstName1", "lastName1")
        Partners.Partner partner2 = getPartner(testCountry1, [DATE2, DATE3, DATE4], "test@email2", "firstName2", "lastName2")

        def testCountry2 = "testCountry2"
        Partners.Partner partner3 = getPartner(testCountry2, [DATE2, DATE5], "test@email3", "firstName3", "lastName3")
        Partners.Partner partner4 = getPartner(testCountry2, [DATE3, DATE5], "test@email4", "firstName4", "lastName4")

        def partnerList = [partner1, partner2, partner3, partner4]
        def partners = getPartners(partnerList)
        when:
        def country_Partners = partnerFilter.filter(partners)
        then:
        country_Partners.size() == 2
        country_Partners.get(testCountry1) == [partner2]
        country_Partners.get(testCountry2) == []
        country_Partners.get(testCountry1).get(0).getAvailableDates().get(0) == DATE2
    }


    def "given 225 partners in 9 countries, return map with 9 country-partners mapping"() {
        given:
        def partners = getTestPartners()
        def Year2017 = 2017 - 1900
        when:
        def country_Partners = partnerFilter.filter(partners)
        def partnerList = country_Partners.values().toList()
        then:
        country_Partners.size() == 9
        partnerList.get(0).size() == 15
        partnerList.get(0).get(0).getAvailableDates().get(0).getTime() == new Date(Year2017, 05, 27).getTime()
        partnerList.get(1).size() == 15
        partnerList.get(1).get(0).getAvailableDates().get(0).getTime() == new Date(Year2017, 04, 19).getTime()
        partnerList.get(2).size() == 16
        partnerList.get(2).get(0).getAvailableDates().get(0).getTime() == new Date(Year2017, 05, 07).getTime()
        partnerList.get(3).size() == 15
        partnerList.get(3).get(0).getAvailableDates().get(0).getTime() == new Date(Year2017, 04, 02).getTime()
        partnerList.get(4).size() == 14
        partnerList.get(4).get(0).getAvailableDates().get(0).getTime() == new Date(Year2017, 04, 27).getTime()
        partnerList.get(5).size() == 15
        partnerList.get(5).get(0).getAvailableDates().get(0).getTime() == new Date(Year2017, 04, 11).getTime()
        partnerList.get(6).size() == 16
        partnerList.get(6).get(0).getAvailableDates().get(0).getTime() == new Date(Year2017, 04, 12).getTime()
        partnerList.get(7).size() == 15
        partnerList.get(7).get(0).getAvailableDates().get(0).getTime() == new Date(Year2017, 03, 29).getTime()
        partnerList.get(8).size() == 14
        partnerList.get(8).get(0).getAvailableDates().get(0).getTime() == new Date(Year2017, 04, 19).getTime()
    }


    private Partners.Partner getPartner(String testCountry, List<Date> dateList, String testEmail, String testFirstName, String testLastName) {
        def partner = Partners.Partner.builder()
                .country(testCountry)
                .availableDates(dateList)
                .email(testEmail)
                .firstName(testFirstName)
                .lastName(testLastName)
                .build()
        partner
    }

    private Partners getPartners(List<Partners.Partner> partnerList) {
        Partners.builder().partners(partnerList).build()
    }
}
