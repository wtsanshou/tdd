package ie.wtsanshou.eventbooking.rest;

import ie.wtsanshou.eventbooking.model.Countries;
import ie.wtsanshou.eventbooking.model.Partners;
import org.springframework.http.ResponseEntity;

public interface RestRequest {
    ResponseEntity<Partners> getPartners();
    ResponseEntity<String> postCountries(Countries countries);
}
