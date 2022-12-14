package com.cog.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cog.entity.Role;
import com.cog.repository.RoleRepository;
import com.cog.util.Constant;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

	@Mock
	RoleRepository repository;

	@InjectMocks
	RoleService roleService;

	@Test
	void testFindById() {
		Optional<Role> role = Optional.of(new Role());
		role.get().setId(1);
		role.get().setRoleName(Constant.ROLE_AUTHOR);
		when(repository.findById(1)).thenReturn(role);
		assertEquals(1, roleService.findById(1).getId());
	}

}
