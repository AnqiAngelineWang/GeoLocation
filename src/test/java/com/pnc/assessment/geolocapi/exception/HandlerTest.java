package com.pnc.assessment.geolocapi.exception;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.pnc.assessment.geolocapi.model.GeneralError;

@SpringBootTest
public class HandlerTest {
    
	@Test
	void handleGeolocExceptionTest() throws Exception {
        Handler handler = new Handler();

        ResponseEntity<GeneralError> response = handler.handleGeolocException(new GeolocException("test"));

        Assert.notNull(response, "Response should not be null");
        Assert.notNull(response.getStatusCode(), "Response status should not be null");
		Assert.isTrue(response.getStatusCode() == HttpStatusCode.valueOf(403), "Response status should be FORBIDDEN");
        Assert.notNull(response.getBody(), "Response body should not be null");
    }
    
	@Test
	void handleMissingParameter() throws Exception {
        Handler handler = new Handler();

        MethodArgumentNotValidException exception = Mockito.mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = Mockito.mock(MethodArgumentNotValidException.class);
        FieldError fieldError = Mockito.mock(FieldError.class);
        Mockito.doReturn(bindingResult).when(exception).getBindingResult();
        Mockito.doReturn(Arrays.asList(fieldError)).when(bindingResult).getFieldErrors();
        Mockito.doReturn("Foo").when(fieldError).getField();

        ResponseEntity<GeneralError> response = handler.handleMissingParameter(exception);

        Assert.notNull(response, "Response should not be null");
        Assert.notNull(response.getStatusCode(), "Response status should not be null");
		Assert.isTrue(response.getStatusCode() == HttpStatusCode.valueOf(400), "Response status should be FORBIDDEN");
        Assert.notNull(response.getBody(), "Response body should not be null");
    }
}
