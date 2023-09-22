package com.pnc.assessment.geolocapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.pnc.assessment.geolocapi.exception.GeolocException;
import com.pnc.assessment.geolocapi.model.Geoloc;

@Component
public class GeolocService {

    private WebClient webClient;
    //private final List<String> VALID_COUNTRY_CODE_LIST = Arrays.asList("CA");
    private List<String> geolocCountryWhiteList;

    public GeolocService(WebClient geolocWebClient, @Value("${geoloc.country.whitelist}") List<String> geolocCountryWhiteList) {
        this.webClient = geolocWebClient;
        this.geolocCountryWhiteList = geolocCountryWhiteList;
    }
    
    public String getCityFromIP(String ipAddress) throws GeolocException {
        Geoloc geoloc = this.webClient.get()
                .uri(ipAddress)
                .retrieve()
                .bodyToMono(Geoloc.class)
                .block();
        if ("fail".equalsIgnoreCase(geoloc.getStatus())) {
            throw new GeolocException("Geoloc from IP address failed");
        }
        if (!this.geolocCountryWhiteList.contains(geoloc.getCountryCode())) {
            throw new GeolocException("IP address from invalid country");
        }
        return geoloc.getCity();
    }
}
