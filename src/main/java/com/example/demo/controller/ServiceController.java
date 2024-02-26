package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.repo.TestRepo;
import com.example.demo.service.CarService;

@RestController
public class ServiceController {
	
	@Autowired private TestRepo testRepo;
	
	@Autowired private CarService carService;
	
	@GetMapping("/test")
	public ResponseEntity<Object> testController() {
		
		System.out.println("demo is here");
		testRepo.findAll();
		return null;
	}
	
	@GetMapping("book")
	
	public ResponseEntity<Object> bookIt(@RequestParam Integer userId,@RequestParam Integer opid,@RequestParam Integer startTime ) {
		if(startTime <0 && startTime > 23 ) {
			return new ResponseEntity<Object>("invalid time slot",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<Object>(carService.bookit(userId,opid,startTime),HttpStatus.OK);
//		;
//		return null;
	}
	
	@GetMapping("update-booking")
	public ResponseEntity<Object> updateBooking(@RequestParam Integer bookingid,@RequestParam(required=false) Integer startTime,@RequestParam Integer type ) {
		return new ResponseEntity<Object>(carService.updateBooking(bookingid,type,startTime),HttpStatus.OK);
	}
	
	@GetMapping("my-booking")
	public ResponseEntity<Object> getMybooking(@RequestParam Integer userid) {
		
		return new ResponseEntity<Object>(carService.getMyBooking(userid),HttpStatus.OK);
//		return null;
	}
	
	@GetMapping("all-operator")
	public ResponseEntity<Object> allOperator(Model model) {
		return new ResponseEntity<Object>(carService.getOperators(),HttpStatus.OK);
		
//		return "index";
	}
	
	
	
	

}
