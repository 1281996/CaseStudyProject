package com.cog.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import com.cog.entity.Role;
import com.cog.entity.User;
import com.cog.entity.UserMapping;

import com.cog.repository.UserMappingRepository;
import com.cog.util.Constant;

@ExtendWith(MockitoExtension.class)
class UserMappingServiceTest {

	@Mock
	UserMappingRepository userMappingRepository;
	@InjectMocks
	UserMappingService mappingService;

	@Test
	void testFindByUserId() {
		User user = new User();
		user.setEmailId("kamma.mallika@gmail.com");
		user.setFirstName("kamma");
		user.setLastName("mallika");
		user.setPassword("mkllll");
		user.setId(1);

		user.setRegisteredDate(LocalDate.now());

		Role role = new Role();
		role.setId(1);
		role.setRoleName(Constant.ROLE_AUTHOR);

		UserMapping mapping = new UserMapping();
		mapping.setRole(role);
		mapping.setUser(user);
		Set<UserMapping> usermappings = new HashSet<>();
		usermappings.add(mapping);
		when(userMappingRepository.findByUserId(1)).thenReturn(usermappings);
		assertEquals(1, mappingService.findByUserId(1).size());
	}

	public static UserMapping getUsergetUserMapping() {
		UserMapping mapping = new UserMapping();
		mapping.setRole(new Role());
		mapping.setUser(new User());

		return mapping;
	}

	@Test
	void testCreateMapping() {
		UserMapping mapping = getUsergetUserMapping();

		mappingService.createMapping(mapping.getUser(), mapping.getRole());
		verify(userMappingRepository, times(0)).save(mapping);

	}

	@Test
	void testFindAuthorRoleUsers() {
		List<UserMapping> userMappings = new ArrayList<>();
		userMappings.add(getUsergetUserMapping());
		when(userMappingRepository.findByRoleId(1)).thenReturn(userMappings);
		assertEquals(1, mappingService.findAuthorRoleUsers().size());

	}
}
