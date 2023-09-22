package com.pnc.assessment.geolocapi.controller;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;

import com.pnc.assessment.geolocapi.exception.GeolocException;
import com.pnc.assessment.geolocapi.model.LoginRequest;
import com.pnc.assessment.geolocapi.service.GeolocService;

@SpringBootTest
class LoginApiDelegateImplTest {

	@Test
	@SuppressWarnings("null")
	void loginTest() throws Exception {
		GeolocService mockedGeolocService = Mockito.mock(GeolocService.class);
		Mockito.doReturn("City").when(mockedGeolocService).getCityFromIP(Mockito.eq("IP.FOO.0.0"));
    	LoginApiDelegateImpl delegate = new LoginApiDelegateImpl(mockedGeolocService);

		LoginRequest request = new LoginRequest();
		request.setUsername("Foo");
		request.setIpAddress("IP.FOO.0.0");
		ResponseEntity<?> response = delegate.login(request);

		Assert.notNull(response, "Response should not be null");
		Assert.notNull(response.getStatusCode(), "Response status should not be null");
		Assert.isTrue(response.getStatusCode() == HttpStatusCode.valueOf(200), "Response status should be OK");
		Assert.notNull(response.getBody(), "Response body should not be null");
		if (response.getBody() != null) {
			Assert.isTrue(response.getBody().toString().contains("Foo"), "Response body should contains the username");
			Assert.isTrue(response.getBody().toString().contains("City"), "Response body should contains the city");
		}
	}

	@Test
	void loginErrorTest() throws Exception {
		GeolocService mockedGeolocService = Mockito.mock(GeolocService.class);
		Mockito.doThrow(new GeolocException("Test")).when(mockedGeolocService).getCityFromIP(Mockito.eq("IP.FOO.0.0"));
    	LoginApiDelegateImpl delegate = new LoginApiDelegateImpl(mockedGeolocService);

		LoginRequest request = new LoginRequest();
		request.setUsername("Foo");
		request.setIpAddress("IP.FOO.0.0");

		Exception exception = assertThrows(GeolocException.class, () -> {
			delegate.login(request);
		});

		Assert.notNull(exception, "Exception should not be null");
	}

}
