package ie.wtsanshou.eventbooking.rest;

import ie.wtsanshou.eventbooking.model.Countries;
import ie.wtsanshou.eventbooking.model.Partners;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class RestRequestImpl implements RestRequest {

    private String get_url;
    private String post_url;
    private RestTemplate restTemplate;

    public RestRequestImpl(String get_url, String post_url, RestTemplate restTemplate) {
        this.get_url = get_url;
        this.post_url = post_url;
        this.restTemplate = restTemplate;
    }

    @Override
    public ResponseEntity<Partners> getPartners() {
        return restTemplate.exchange(get_url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Partners>() {
                });
    }

    @Override
    public ResponseEntity<String> postCountries(Countries countries) {
        HttpEntity<Countries> entity = getHttpEntityFrom(countries);
        return restTemplate.postForEntity(post_url, entity, String.class);
    }

    private HttpEntity<Countries> getHttpEntityFrom(Countries countries) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return new HttpEntity<>(countries, headers);
    }
}
