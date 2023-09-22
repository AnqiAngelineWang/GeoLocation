package com.pnc.assessment.geolocapi.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersUriSpec;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;

import com.pnc.assessment.geolocapi.exception.GeolocException;
import com.pnc.assessment.geolocapi.model.Geoloc;

import reactor.core.publisher.Mono;

@SpringBootTest
public class GeolocServiceTest {
    
	@Test
	void getCityFromIPTest() throws Exception {
        WebClient mockedWebClient = Mockito.mock(WebClient.class);
        RequestHeadersUriSpec<?> mockedRequestHeadersUriSpec = Mockito.mock(RequestHeadersUriSpec.class);
        RequestHeadersSpec<?> mockedRequestHeadersSpec = Mockito.mock(RequestHeadersSpec.class);
        ResponseSpec mockedResponseSpec = Mockito.mock(ResponseSpec.class);
        Mono<?> mockedMono = Mockito.mock(Mono.class);
        Geoloc geoloc = new Geoloc();
        geoloc.setStatus("success");
        geoloc.setCountryCode("CA");
        geoloc.setCity("Foo");
        Mockito.doReturn(mockedRequestHeadersUriSpec).when(mockedWebClient).get();
        Mockito.doReturn(mockedRequestHeadersSpec).when(mockedRequestHeadersUriSpec).uri(Mockito.eq("IP.FOO.0.0"));
        Mockito.doReturn(mockedResponseSpec).when(mockedRequestHeadersSpec).retrieve();
        Mockito.doReturn(mockedMono).when(mockedResponseSpec).bodyToMono(Mockito.eq(Geoloc.class));
        Mockito.doReturn(geoloc).when(mockedMono).block();
		GeolocService service = new GeolocService(mockedWebClient, Arrays.asList("CA"));

        String city = service.getCityFromIP("IP.FOO.0.0");
        Assert.notNull(city, "City should not be null");
        Assert.isTrue("Foo".equals(city), "City should be 'Foo'");
	}
    
	@Test
	void getCityFromIPTest_ReqFail() throws Exception {
        WebClient mockedWebClient = Mockito.mock(WebClient.class);
        RequestHeadersUriSpec<?> mockedRequestHeadersUriSpec = Mockito.mock(RequestHeadersUriSpec.class);
        RequestHeadersSpec<?> mockedRequestHeadersSpec = Mockito.mock(RequestHeadersSpec.class);
        ResponseSpec mockedResponseSpec = Mockito.mock(ResponseSpec.class);
        Mono<?> mockedMono = Mockito.mock(Mono.class);
        Geoloc geoloc = new Geoloc();
        geoloc.setStatus("fail");
        geoloc.setCountryCode("CA");
        geoloc.setCity("Foo");
        Mockito.doReturn(mockedRequestHeadersUriSpec).when(mockedWebClient).get();
        Mockito.doReturn(mockedRequestHeadersSpec).when(mockedRequestHeadersUriSpec).uri(Mockito.eq("IP.FOO.0.0"));
        Mockito.doReturn(mockedResponseSpec).when(mockedRequestHeadersSpec).retrieve();
        Mockito.doReturn(mockedMono).when(mockedResponseSpec).bodyToMono(Mockito.eq(Geoloc.class));
        Mockito.doReturn(geoloc).when(mockedMono).block();
		GeolocService service = new GeolocService(mockedWebClient, Arrays.asList("CA"));


		Exception exception = assertThrows(GeolocException.class, () -> {
			service.getCityFromIP("IP.FOO.0.0");
		});

		Assert.notNull(exception, "Exception should not be null");
	}
    
	@Test
	void getCityFromIPTest_ReqInvalidCountry() throws Exception {
        WebClient mockedWebClient = Mockito.mock(WebClient.class);
        RequestHeadersUriSpec<?> mockedRequestHeadersUriSpec = Mockito.mock(RequestHeadersUriSpec.class);
        RequestHeadersSpec<?> mockedRequestHeadersSpec = Mockito.mock(RequestHeadersSpec.class);
        ResponseSpec mockedResponseSpec = Mockito.mock(ResponseSpec.class);
        Mono<?> mockedMono = Mockito.mock(Mono.class);
        Geoloc geoloc = new Geoloc();
        geoloc.setStatus("success");
        geoloc.setCountryCode("US");
        geoloc.setCity("Foo");
        Mockito.doReturn(mockedRequestHeadersUriSpec).when(mockedWebClient).get();
        Mockito.doReturn(mockedRequestHeadersSpec).when(mockedRequestHeadersUriSpec).uri(Mockito.eq("IP.FOO.0.0"));
        Mockito.doReturn(mockedResponseSpec).when(mockedRequestHeadersSpec).retrieve();
        Mockito.doReturn(mockedMono).when(mockedResponseSpec).bodyToMono(Mockito.eq(Geoloc.class));
        Mockito.doReturn(geoloc).when(mockedMono).block();
		GeolocService service = new GeolocService(mockedWebClient, Arrays.asList("CA"));


		Exception exception = assertThrows(GeolocException.class, () -> {
			service.getCityFromIP("IP.FOO.0.0");
		});

		Assert.notNull(exception, "Exception should not be null");
	}
}
