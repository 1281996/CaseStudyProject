package com.cog.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.cog.entity.User;


public interface UserRepository extends JpaRepository<User, Integer> {

	User findByEmailId(String emailId);

	User findByEmailIdAndPassword(String emailId, String password);

	

	

}
