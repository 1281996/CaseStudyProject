package com.cog.service;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import java.util.Optional;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cog.dto.UserDto;
import com.cog.entity.Role;
import com.cog.entity.User;

import com.cog.enums.Event;
import com.cog.repository.UserRepository;
import com.cog.util.Constant;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private UserMappingService userMappingService;

	@Mock
	private RoleService roleService;

	@InjectMocks
	private UserDetailsServiceImpl detailsServiceImpl;

	@Test
	void testSaveUser1() {
		UserDto userDto = getUserDto();

		User user = getUser();

		Role role = new Role();
		role.setId(1);
		role.setRoleName(Constant.ROLE_AUTHOR);

		when(userRepository.findByEmailId(userDto.getEmailId())).thenReturn(null);
		Mockito.lenient().when(userRepository.save(user)).thenReturn(user);
		when(roleService.findById(1)).thenReturn(role);
		assertEquals(Constant.USER_REGISTER_SUCCESS, detailsServiceImpl.saveUser(userDto).getResponse());
	}

	@Test
	void testSaveUser2() {
		UserDto userDto = getUserDto();
		User user = getUser();

		when(userRepository.findByEmailId(userDto.getEmailId())).thenReturn(user);

		assertEquals(Constant.USER_REGISTER_ERROR, detailsServiceImpl.saveUser(userDto).getResponse());
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

	public static UserDto getUserDto() {
		UserDto userDto = new UserDto();
		userDto.setEmailId("kamma.mallika@gmail.com");
		userDto.setFirstName("kamma");
		userDto.setLastName("mallika");
		userDto.setPassword("mkllll");
		userDto.setRole(Constant.ROLE_AUTHOR);
		return userDto;
	}

	public static User getUser() {
		User user = new User();
		user.setEmailId("kamma.mallika@gmail.com");
		user.setFirstName("kamma");
		user.setLastName("mallika");
		user.setPassword("mkllll");
		user.setId(1);
		user.setStatus(Event.ACTIVE);
		user.setRegisteredDate(LocalDateTime.now());
		return user;
	}
}
