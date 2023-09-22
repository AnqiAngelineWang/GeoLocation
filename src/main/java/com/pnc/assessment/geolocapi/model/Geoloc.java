package com.pnc.assessment.geolocapi.model;

import lombok.Data;

@Data
public class Geoloc {
    String status;
    String country;
    String countryCode;
    String region;
    String regionName;
    String city;
}
