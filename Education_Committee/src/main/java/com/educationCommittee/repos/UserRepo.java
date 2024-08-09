package com.educationCommittee.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.educationCommittee.entityes.User;

public interface UserRepo extends JpaRepository<User, Integer>{

public User findByEmail(String email);
	
	@Query("select u from User u where u.email= :email")
	public User getUserByUserName(@Param("email") String email);
	
	@Query("select u from User u where u.id= :id")
	public User getUserByUserId(@Param("id") int id);

}
