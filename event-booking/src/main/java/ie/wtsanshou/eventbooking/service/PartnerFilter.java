package ie.wtsanshou.eventbooking.service;

import ie.wtsanshou.eventbooking.model.Partners;

import java.util.List;
import java.util.Map;

public interface PartnerFilter {
    Map<String, List<Partners.Partner>> filter(Partners partners);
}
