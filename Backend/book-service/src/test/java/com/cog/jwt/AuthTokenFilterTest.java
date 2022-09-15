package com.cog.jwt;

import static org.mockito.Mockito.when;

import java.io.IOException;


import javax.servlet.FilterChain;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;

import com.cog.service.UserDetailsImpl;
import com.cog.service.UserService;

@ExtendWith(MockitoExtension.class)
class AuthTokenFilterTest {
	@Mock
	private JwtUtils jwtUtils;

	@Mock
	private UserService userDetailsService;

	@InjectMocks
	AuthTokenFilter authTokenFilter;

	@Test
	void testDoFilterInternalHttpServletRequestHttpServletResponseFilterChain() throws ServletException, IOException {
		 MockHttpServletRequest mockRequest = new MockHttpServletRequest();
		    mockRequest.addHeader("Content-Type", "text/html");
		    mockRequest.addHeader("if-none-match", "*");
		    mockRequest.addHeader("Authorization", "Bearer mmnskjjs");		
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		FilterChain filterChain = Mockito.mock(FilterChain.class);
		when(jwtUtils.validateJwtToken("mmnskjjs")).thenReturn(true);
		when(jwtUtils.getEmailIdFromJwtToken("mmnskjjs")).thenReturn("kamma@gmail.com");
		when(userDetailsService.loadUserByUsername("kamma@gmail.com")).thenReturn(new UserDetailsImpl(1L, null, "kamma@gmail.com", "ykunta123", null));
		authTokenFilter.doFilterInternal(mockRequest, response, filterChain);
	}

}
