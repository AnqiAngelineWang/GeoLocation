package com.pnc.assessment.geolocapi.controller;

import java.util.UUID;

import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.pnc.assessment.geolocapi.model.LoginRequest;
import com.pnc.assessment.geolocapi.model.LoginResponse;
import com.pnc.assessment.geolocapi.service.GeolocService;

@Primary
@Component
public class LoginApiDelegateImpl implements LoginApiDelegate {

    private GeolocService geolocService;

    public LoginApiDelegateImpl(GeolocService geolocService) {

        this.geolocService = geolocService;
    }
    
    @Override
    public ResponseEntity<LoginResponse> login(LoginRequest loginRequest) throws Exception {
        LoginResponse response = new LoginResponse();
        response.setUuid(UUID.randomUUID().toString());
        response.setMessage(
            String.format(
                "Greeting %s from %s",
                loginRequest.getUsername(),
                geolocService.getCityFromIP(loginRequest.getIpAddress())
            )
        );
        return ResponseEntity.ok(response);
    }

}
