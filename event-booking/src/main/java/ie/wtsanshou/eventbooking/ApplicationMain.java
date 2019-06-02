package ie.wtsanshou.eventbooking;

import ie.wtsanshou.eventbooking.rest.RestRequest;
import ie.wtsanshou.eventbooking.rest.RestRequestImpl;
import ie.wtsanshou.eventbooking.model.Countries;
import ie.wtsanshou.eventbooking.model.Partners;
import ie.wtsanshou.eventbooking.presenter.CountriesPresenter;
import ie.wtsanshou.eventbooking.presenter.CountriesPresenterImpl;
import ie.wtsanshou.eventbooking.service.PartnerFilter;
import ie.wtsanshou.eventbooking.service.PartnerFilterImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Map;

@Slf4j
public class ApplicationMain {
    private static ApplicationContext applicationContext;
    private static PartnerFilter partnerFilter;
    private static RestRequest restRequest;
    private static CountriesPresenter countriesPresenter;
    private static final String configLocation = "eventBooking-applicationContext.xml";

    public static void main(String[] args) {

        init();

        try {
            log.info("Getting list of all people:");
            ResponseEntity<Partners> getResponse = restRequest.getPartners();
            if (getResponse.getStatusCodeValue() != 200) {
                log.error("Did not get date");
                return;
            }

            Partners partners = getResponse.getBody();
            print(partners);

            log.info("filtering all valid people");
            Map<String, List<Partners.Partner>> countryPartners = partnerFilter.filter(partners);

            log.info("Transfer to Countries which can be posted");
            Countries countries = countriesPresenter.generateCountriesFrom(countryPartners);
            print(countries);

            log.info("Sending all valid people");
            ResponseEntity<String> response = restRequest.postCountries(countries);
            int httpStatus = response.getStatusCodeValue();
            if (200 != httpStatus)
                log.error("Wrong result");
            log.info(String.format("response status: %s", httpStatus));
            log.info(String.format("response : %s", response));
        } catch (HttpClientErrorException e) {
            log.warn("Bad request response body: "+ e.getResponseBodyAsString());
        } catch(Exception e) {
            log.error("Wrong Https request", e);
        }

    }

    private static void init() {
        applicationContext = new ClassPathXmlApplicationContext(configLocation);
        restRequest = applicationContext.getBean(RestRequestImpl.class);
        partnerFilter = applicationContext.getBean(PartnerFilterImpl.class);
        countriesPresenter = applicationContext.getBean(CountriesPresenterImpl.class);
    }

    private static void print(Partners partners) {
        for (Partners.Partner p : partners.getPartners()) {
            System.out.println(p);
        }
    }

    private static void print(Countries countries) {
        for (Countries.Country p : countries.getCountries()) {
            System.out.println(p);
        }
    }
}
