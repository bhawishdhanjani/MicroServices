package com.lcws.user.service.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lcws.user.service.entities.User;



public interface UserService {
	
	User saveUser(User user);
	List<User> getAllUser();
	User getUser(String id);
	User deleteUser(String  userId);
	User updateUser(User user);
	

}
