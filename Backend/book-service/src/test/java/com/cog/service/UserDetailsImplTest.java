package com.cog.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserDetailsImplTest {

	@InjectMocks
	UserDetailsImpl userDetailsImpl;

	@Test
	void testGetAuthorities() {
		assertEquals(null, userDetailsImpl.getAuthorities());
	}

	@Test
	void testGetPassword() {
		assertEquals(null, userDetailsImpl.getPassword());
	}

	@Test
	void testGetUsername() {
		assertEquals(null, userDetailsImpl.getUsername());
	}

	@Test
	void testIsAccountNonExpired() {
		assertEquals(true, userDetailsImpl.isAccountNonExpired());
	}

	@Test
	void testIsAccountNonLocked() {
		assertEquals(true, userDetailsImpl.isAccountNonLocked());
	}

	@Test
	void testIsEnabled() {
		assertEquals(true, userDetailsImpl.isEnabled());
	}
	@Test
	void testEquals() {
		assertNotEquals("",userDetailsImpl.equals(new Object()));
	}

}
