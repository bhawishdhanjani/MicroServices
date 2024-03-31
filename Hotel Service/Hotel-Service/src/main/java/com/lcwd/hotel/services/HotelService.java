package com.lcwd.hotel.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lcwd.hotel.entities.Hotel;


@Service
public interface HotelService {
	
	Hotel addHotel(Hotel hotel);
	List<Hotel> getAll();
	Hotel get(String id);
	Hotel delete(String id);
	Hotel update(Hotel hotel);

}
