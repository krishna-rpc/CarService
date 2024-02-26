package com.example.demo.service;

import java.util.Map;

public interface CarService {

	Map<String, String> bookit(Integer userId, Integer opid, Integer startTime);

	Object myBooking(Integer userid);

	Object getOperators();
	Object getMyBooking(Integer userid);

	Map<String, String> updateBooking(Integer bookingid, Integer type, Integer startTime);
}
