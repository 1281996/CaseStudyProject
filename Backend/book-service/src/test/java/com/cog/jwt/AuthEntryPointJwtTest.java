package com.cog.jwt;




import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.AuthenticationException;
@ExtendWith(MockitoExtension.class)
class AuthEntryPointJwtTest {
	@InjectMocks
	AuthEntryPointJwt authEntryPointJwt ;
	@Test
	void testCommence() throws IOException, ServletException {
		HttpServletRequest request=Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		AuthenticationException authenticate= Mockito.mock(AuthenticationException.class);
		authEntryPointJwt.commence(request, response, authenticate);
	}

}
