package com.cog.jwt;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;

import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.util.ReflectionTestUtils;

import com.cog.dto.LoginDto;
import com.cog.service.UserDetailsImpl;

@ExtendWith(MockitoExtension.class)
class JwtUtilsTest {

	@InjectMocks
	JwtUtils jwtUtils;
	
	
	@BeforeEach
    public void setup() throws Exception {
        
        ReflectionTestUtils.setField(this.jwtUtils, "jwtSecret","books");
    }

	@Test
	void testGenerateJwtToken() {
		LoginDto loginDto = getLoginDto();
		GrantedAuthority authoritie = new SimpleGrantedAuthority("ROLE_AUTHOR");
		Set<GrantedAuthority> list = new HashSet<>();
		list.add(authoritie);
		UserDetailsImpl detailsImpl = new UserDetailsImpl(1L, "kammalli", loginDto.getEmailId(), loginDto.getPassword(),
				list);
		Authentication authentication = new UsernamePasswordAuthenticationToken(detailsImpl, list);
		assertNotEquals("", jwtUtils.generateJwtToken(authentication));
	}

	@Test
	void testGetEmailIdFromJwtToken() {
		assertNotEquals("", jwtUtils.getEmailIdFromJwtToken(
				"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzbmVoYUBnbWFpbC5jb20iLCJpYXQiOjE2NjMyMjc4NDYsImV4cCI6MTY2MzI4Nzg0Nn0.FNlv7KMIND7GUXtvViXK1UD8zYBET4-ydFFnguWoEcTDBFRiYq-NBcIN6HRNwz57IWTtJ5kEX-quQ9TkvZMNKg"));
	}

	@Test
	void testValidateJwtToken() {
		assertNotEquals("", jwtUtils.validateJwtToken(
				"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzbmVoYUBnbWFpbC5jb20iLCJpYXQiOjE2NjMyMjc4NDYsImV4cCI6MTY2MzI4Nzg0Nn0.FNlv7KMIND7GUXtvViXK1UD8zYBET4-ydFFnguWoEcTDBFRiYq-NBcIN6HRNwz57IWTtJ5kEX-quQ9TkvZMNKg"));
	}

	public static LoginDto getLoginDto() {
		LoginDto dto = new LoginDto();
		dto.setEmailId("kamma.mallika@gmail.com");
		dto.setPassword("pa");
		return dto;
	}

}
