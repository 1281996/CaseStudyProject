package com.cog.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;


import com.cog.entity.UserMapping;

public interface UserMappingRepository extends JpaRepository<UserMapping, Integer> {

	Set<UserMapping> findByUserId(Integer userId);

	List<UserMapping> findByRoleId(Integer roleId);

}
