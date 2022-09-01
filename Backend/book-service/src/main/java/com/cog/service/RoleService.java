package com.cog.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cog.entity.Role;
import com.cog.repository.RoleRepository;

@Service
public class RoleService {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserMappingService.class);

	@Autowired
	RoleRepository repository;

	Role findById(int id) {
		LOGGER.info("findById");
		return repository.findById(id).get();

	}
}
