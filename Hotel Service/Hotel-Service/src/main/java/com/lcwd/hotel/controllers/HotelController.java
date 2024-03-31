package com.lcwd.hotel.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lcwd.hotel.entities.Hotel;
import com.lcwd.hotel.services.HotelService;

@RestController
@RequestMapping("/hotel") 
public class HotelController {
	
	@Autowired
	HotelService hotelService;
	@PreAuthorize("hasAuthority('Admin')")
	@PostMapping()
	public ResponseEntity<Hotel> addHotel(@RequestBody Hotel hotel) {
		Hotel savedHotel = hotelService.addHotel(hotel);
		return new ResponseEntity<Hotel>(savedHotel,HttpStatus.CREATED);
	}
	@PreAuthorize("hasAuthority('SCOPE_internal') || hasAuthority('Admin')")
	@GetMapping()
	public ResponseEntity<List<Hotel>> getAllHotels(){
		List<Hotel> hotels = hotelService.getAll();
		return new ResponseEntity<List<Hotel>>(hotels,HttpStatus.OK);
	}
	@PreAuthorize("hasAuthority('SCOPE_internal')")
	@GetMapping("/{id}")
	public ResponseEntity<Hotel> getHotel(@PathVariable String id){
		Hotel hotel = hotelService.get(id);
		return new ResponseEntity<Hotel>(hotel,HttpStatus.OK);
		
	}
	@PutMapping("/{id}")
	public ResponseEntity<Hotel> updateHotel(@RequestBody Hotel hotel ,@PathVariable String id){
		hotel.setId(id);
		Hotel updatedHotel = hotelService.update(hotel);
		return new ResponseEntity<Hotel>(updatedHotel,HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Hotel> deleteHotel(@PathVariable String id){
		Hotel hotel = hotelService.delete(id);
		return new ResponseEntity<Hotel>(hotel,HttpStatus.OK);

		
	}
}
