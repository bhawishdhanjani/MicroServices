package com.lcws.user.service.controllers;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lcws.user.service.entities.User;
import com.lcws.user.service.repositories.UserRepository;
import com.lcws.user.service.services.UserService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@PostMapping
	public ResponseEntity<User> saveUser(@RequestBody User  user){
		User savedUser = userService.saveUser(user);		
		return new ResponseEntity<User>(savedUser,HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<User> updateUser(@RequestBody User  user){
		User updatedUser = userService.updateUser(user);		
		return new ResponseEntity<User>(updatedUser,HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<User>> getAllUser(){
		List<User> users  = userService.getAllUser();
		return new ResponseEntity<List<User>>(users,HttpStatus.OK);
	}
	
	
	@GetMapping("/{id}")
//	@CircuitBreaker(name = "ratingHotelBreaker" , fallbackMethod = "ratingHotelFallback")
//	@Retry(name = "ratingHotelRetry" , fallbackMethod = "ratingHotelFallback")
	@RateLimiter(name = "ratingHotelLimiter" , fallbackMethod = "ratingHotelFallback")
	public ResponseEntity<User> getUser(@PathVariable String  id){
		User savedUser = userService.getUser(id);		
		return new ResponseEntity<User>(savedUser,HttpStatus.OK);
	}
	

	public ResponseEntity<User> ratingHotelFallback(String  id,Exception exception){
		logger.info("Fallback is excuted because sevice is down ",exception.getMessage());
		
		User user = User.builder().name("Dumy").email("dummy@mail.com").about("Nothing in about").userId("null").build();
		return new ResponseEntity<User>(user,HttpStatus.BAD_REQUEST);
	}
	
	
	
	
	
	
	
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<User> deleteUser(@PathVariable String  id){
		User deletedUser = userService.deleteUser(id);		
		return new ResponseEntity<User>(deletedUser,HttpStatus.OK);
	}
	
	
	
	

}
