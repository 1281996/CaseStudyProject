package com.cog.service;

import java.time.LocalDate;
import java.util.Set;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import com.cog.dto.ResponseDto;
import com.cog.dto.UserDto;
import com.cog.entity.Role;
import com.cog.entity.User;
import com.cog.entity.UserMapping;
import com.cog.repository.UserRepository;
import com.cog.util.Constant;

@Service
public class UserService implements UserDetailsService{
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserMappingService userMappingService;

	@Autowired
	private RoleService roleService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

	public ResponseDto saveUser(UserDto userDto) {
		LOGGER.info("saveUser");
		ResponseDto res = new ResponseDto();
		// only Author role people required sign up
		if (userDto.getRole().equals(Constant.ROLE_AUTHOR)) {
			res.setResponse(Constant.USER_REGISTER_ERROR);
			User user = setUserData(userDto);
			// save User
			User userRes = userRepository.save(user);
			if (userRes.getId() != null) {
				Role role = roleService.findById(Constant.ROLE_AUTHOR_ID);
				// user mapping..mapping user to role
				userMappingService.createMapping(userRes, role);
				res.setResponse(Constant.USER_REGISTER_SUCCESS);
			}
		}
		return res;
	}

	private User setUserData(UserDto userDto) {
		User user = new User();
		user.setEmailId(userDto.getEmailId());
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		user.setRegisteredDate(LocalDate.now());
		return user;
	}

	public User findByUserId(Integer id) {
		return userRepository.findById(id).get();
	}

	

	@Override

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		LOGGER.info("loadUserByUsername");
		User user = userRepository.findByEmailId(username);

		Set<UserMapping> userMappings = userMappingService.findByUserId(user.getId());
		return UserDetailsImpl.build(user, userMappings);
	}
}
