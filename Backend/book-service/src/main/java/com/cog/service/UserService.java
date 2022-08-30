package com.cog.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cog.dao.UserRepository;
import com.cog.dto.ResponseDto;
import com.cog.dto.UserDto;
import com.cog.entity.User;
import com.cog.enums.Event;
import com.cog.util.Constant;

@Service
public class UserService {
	@Autowired
	UserRepository userRepository;

	public ResponseDto saveUser(UserDto userDto) {
		ResponseDto res = new ResponseDto();
		//only Author role people required  sign up
		if (userDto.getRole().equals(Constant.ROLE_AUTHOR)) {
			User user = setUserData(userDto);

			res.setResponse(Constant.USER_REGISTER_ERROR);
			if (userRepository.save(user).getId() != null) {
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
		user.setPassword(userDto.getPassword());
		user.setRegisteredDate(LocalDateTime.now());
		user.setStatus(Event.ACTIVE);

		return user;
	}

	public ResponseDto checkCredentails(UserDto userDto) {
		User user = userRepository.findByEmailIdAndPassword(userDto.getEmailId(), userDto.getPassword());
		ResponseDto res = new ResponseDto();
		res.setResponse(Constant.USER_CHECK_CREDENTAILS_ERROR);
		if (user != null) {
			res.setResponse(Constant.USER_CHECK_CREDENTAILS_SUCESS);
		}
		return res;
	}

}
