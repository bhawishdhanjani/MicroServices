package com.lcws.user.service.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.lcws.user.service.entities.Hotel;
import com.lcws.user.service.entities.Rating;
import com.lcws.user.service.entities.User;
import com.lcws.user.service.exceptions.ResourceNotFoundException;
import com.lcws.user.service.external.services.HotelService;
import com.lcws.user.service.repositories.UserRepository;
import com.lcws.user.service.services.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	RestTemplate restTemplate;
	@Autowired
	HotelService hotelService;
	
	
	@Override
	public User saveUser(User user) {
		String userId = UUID.randomUUID().toString();
		user.setUserId(userId);
		return userRepository.save(user);
		
	}

	@Override
	public List<User> getAllUser() {
		List<User> users = userRepository.findAll();
		users.forEach(user->{
			Rating[] ratingsArray = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/"+user.getUserId(), Rating[].class);
			List<Rating> ratings = Arrays.stream(ratingsArray).toList();
			ratings.forEach(rating ->  rating.setHotel(hotelService.getHotel(rating.getHotelId())));
			user.setRatings(ratings);			
		});
		return users;
	}

	@Override
	public User getUser(String  id) {
		User user = userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException());
		Rating[] userRatingArray = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/"+id, Rating[].class);
		List<Rating> userRating = Arrays.stream(userRatingArray).toList();
		userRating.forEach(rating -> rating.setHotel(hotelService.getHotel(rating.getHotelId())));
		user.setRatings(userRating);
		return user;
		
	}

	@Override
	public User deleteUser(String  userId) {
		User user = getUser(userId);
		userRepository.delete(user);
		return user;
	}

	@Override
	public User updateUser(User  user) {
		return userRepository.save(user);
	}
	
	

}
