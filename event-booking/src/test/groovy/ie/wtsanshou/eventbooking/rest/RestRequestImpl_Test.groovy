package ie.wtsanshou.eventbooking.rest

import ie.wtsanshou.eventbooking.model.Countries
import ie.wtsanshou.eventbooking.model.Partners
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

import static ie.wtsanshou.eventbooking.Util.dateFormatter
import static ie.wtsanshou.eventbooking.Util.getTestPartners

class RestRequestImpl_Test extends Specification {

    String GET_URL = "https://test:8443/testGet"
    String POST_URL = "https://test:8443/testPost"

    def "RestRequest can get Partners"() {
        given:
        Partners testPartners = getTestPartners()

        RestTemplate restTemplate = Mock {
            exchange(GET_URL, HttpMethod.GET, null, new ParameterizedTypeReference<Partners>() {
            }) >> new ResponseEntity(testPartners, HttpStatus.OK)
        }

        RestRequest restRequest = new RestRequestImpl(GET_URL, POST_URL, restTemplate)
        when:
        ResponseEntity<Partners> requestPartners = restRequest.getPartners()
        then:
        requestPartners.getStatusCodeValue() == 200
        requestPartners.getBody() == testPartners
    }

    def "RestRequest can post Countries"() {
        given:
        RestTemplate restTemplate = Mock {
            postForEntity(POST_URL, _, String.class) >> new ResponseEntity(_, HttpStatus.OK)
        }
        RestRequest restRequest = new RestRequestImpl(GET_URL, POST_URL, restTemplate);

        List<Countries.Country> countryList = new ArrayList<>()
        Countries.Country country = Countries.Country.builder()
                .attendeeCount(1)
                .name("testCountry")
                .attendees(Arrays.asList("testEmail"))
                .startDate(dateFormatter.format(new Date()))
                .build();
        countryList.add(country)
        Countries countries = Countries.builder().countries(countryList).build()
        when:
        ResponseEntity<String> response = restRequest.postCountries(countries)
        then:
        response.getStatusCodeValue() == 200
    }
}
