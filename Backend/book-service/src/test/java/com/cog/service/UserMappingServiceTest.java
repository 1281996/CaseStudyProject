package com.cog.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cog.entity.Role;
import com.cog.entity.User;
import com.cog.entity.UserMapping;
import com.cog.enums.Event;
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
		user.setStatus(Event.ACTIVE);
		user.setRegisteredDate(LocalDateTime.now());

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
		mapping.setStatus(Constant.ACTIVE);
		return mapping;
	}

	@Test
	void testCreateMapping() {
		UserMapping mapping = getUsergetUserMapping();
		// doNothing().when(userMappingRepository.save(getUsergetUserMapping()));
		mappingService.createMapping(mapping.getUser(), mapping.getRole());
		verify(userMappingRepository, times(0)).save(mapping);
		
	}

	/*
	 * MyList myList = mock(MyList.class);
	 * doNothing().when(myList).add(isA(Integer.class), isA(String.class));
	 * myList.add(0, "");
	 * 
	 * verify(myList, times(1)).add(0, "");
	 */
}
