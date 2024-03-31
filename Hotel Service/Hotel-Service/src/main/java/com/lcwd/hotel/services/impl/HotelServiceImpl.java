package com.lcwd.hotel.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lcwd.hotel.entities.Hotel;
import com.lcwd.hotel.repositories.HotelRepository;
import com.lcwd.hotel.services.HotelService;
import com.lcwd.hotel.services.exception.ResourceNotFoundException;

@Service
public class HotelServiceImpl implements HotelService{

	@Autowired
	HotelRepository hotelRepository;
	@Override
	public Hotel addHotel(Hotel hotel) {
		String id = UUID.randomUUID().toString();
		hotel.setId(id);
		return hotelRepository.save(hotel);
	}

	@Override
	public List<Hotel> getAll() {
		return hotelRepository.findAll();
	}

	@Override
	public Hotel get(String id) {
		return hotelRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Hotel not found with id "+id));
	}

	@Override
	public Hotel delete(String id) {
		Hotel hotel = get(id);
		hotelRepository.delete(hotel);
		return hotel;
	}

	@Override
	public Hotel update(Hotel hotel) {
		return hotelRepository.save(hotel);
	}

}
