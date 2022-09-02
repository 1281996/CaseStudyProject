package com.cog.service;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.cog.dto.UserDto;
import com.cog.entity.Role;
import com.cog.entity.User;
import com.cog.entity.UserMapping;
import com.cog.enums.Event;
import com.cog.repository.UserRepository;
import com.cog.util.Constant;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private UserMappingService userMappingService;

	@Mock
	private RoleService roleService;

	@InjectMocks
	private UserDetailsServiceImpl detailsServiceImpl;

	@Test
	void testSaveUser1() {
		UserDto userDto = new UserDto();
		userDto.setEmailId("kamma.mallika@gmail.com");
		userDto.setFirstName("kamma");
		userDto.setLastName("mallika");
		userDto.setPassword("mkllll");
		userDto.setRole(Constant.ROLE_AUTHOR);

		User user1 = new User();
		user1.setEmailId("kamma.mallika@gmail.com");
		user1.setFirstName("kamma");
		user1.setLastName("mallika");
		user1.setPassword("mkllll");

		user1.setStatus(Event.ACTIVE);
		user1.setRegisteredDate(LocalDateTime.now());

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

		when(userRepository.findByEmailId(userDto.getEmailId())).thenReturn(null);
		Mockito.lenient().when(userRepository.save(user1)).thenReturn(user);
		when(roleService.findById(1)).thenReturn(role);
		assertEquals(Constant.USER_REGISTER_SUCCESS, detailsServiceImpl.saveUser(userDto).getResponse());
	}

	@Test
	void testSaveUser2() {
		UserDto userDto = new UserDto();
		userDto.setEmailId("kamma.mallika@gmail.com");
		userDto.setFirstName("kamma");
		userDto.setLastName("mallika");
		userDto.setPassword("mkllll");
		userDto.setRole(Constant.ROLE_AUTHOR);
		User user = new User();
		user.setEmailId("kamma.mallika@gmail.com");
		user.setFirstName("kamma");
		user.setLastName("mallika");
		user.setPassword("mkllll");
		user.setId(1);
		user.setStatus(Event.ACTIVE);
		user.setRegisteredDate(LocalDateTime.now());

		when(userRepository.findByEmailId(userDto.getEmailId())).thenReturn(user);

		assertEquals(Constant.USER_REGISTER_ERROR, detailsServiceImpl.saveUser(userDto).getResponse());
	}

	@Test
	void testLoadUserByUsername() {
		User user = new User();
		user.setEmailId("kamma.mallika@gmail.com");
		user.setFirstName("kamma");
		user.setLastName("mallika");
		user.setPassword("mkllll");
		user.setId(1);
		user.setStatus(Event.ACTIVE);
		user.setRegisteredDate(LocalDateTime.now());
		when(userRepository.findByEmailId("kamma.mallika@gmail.com")).thenReturn(user);

		Role role = new Role();
		role.setId(1);
		role.setRoleName(Constant.ROLE_AUTHOR);
		Set<UserMapping> usermappings = new HashSet<>();
		UserMapping mapping = new UserMapping();
		mapping.setRole(role);
		mapping.setUser(user);
		usermappings.add(mapping);

		when(userMappingService.findByUserId(user.getId())).thenReturn(usermappings);
		assertNotEquals(null, detailsServiceImpl.loadUserByUsername("kamma.mallika@gmail.com"));
	}

	@Test
	void testFindByUserId() {
		Optional<User> user = Optional.of(new User());
		user.get().setEmailId("kamma.mallika@gmail.com");
		user.get().setFirstName("kamma");
		user.get().setLastName("mallika");
		user.get().setPassword("mkllll");
		user.get().setId(1);
		user.get().setStatus(Event.ACTIVE);
		user.get().setRegisteredDate(LocalDateTime.now());
		when(userRepository.findById(2)).thenReturn(user);
		assertEquals(1, detailsServiceImpl.findByUserId(2).getId());
	}

}
