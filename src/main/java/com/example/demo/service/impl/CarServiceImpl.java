package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Booking;
import com.example.demo.entity.Operator;
import com.example.demo.pojo.TimeSlot;
import com.example.demo.repo.BookingRepo;
import com.example.demo.repo.OperatorRepo;
import com.example.demo.service.CarService;
import com.example.demoDTO.OperatorDetailsDTO;

import jakarta.annotation.PostConstruct;


@Service
public class CarServiceImpl implements CarService {
	
	@Autowired private BookingRepo bookingRepo;
	
	@Autowired private OperatorRepo operatorRepo;

	@Override
	public Map<String,String> bookit(Integer userId, Integer opid, Integer startTime) {
		
		Map<String,String> map=new HashMap<>();
		Booking bk=bookingRepo.checkslot(opid,startTime);
		if(bk == null) {
			bk=new Booking();
			bk.setUserId(userId);
			bk.setOpid(opid);
			bk.setStartTime(startTime);
			bk.setEndTime((startTime+1)%24);
			bk.setStatus(1);
			bookingRepo.save(bk);
			map.put("message", "booking successfully");
		}else {
			map.put("message", "this time slot is not available");
			
			System.out.println("this slot is already booked");
		}
		
		return map;
		
	}

	@Override
	public Object myBooking(Integer userid) {
		
		List<Booking> bookings=bookingRepo.findByUserId(userid);
		return bookings;
	}
	
	
	
	
	@PostConstruct
	private void createOperator() {
		for(int i=0;i<3;i++) {
			operatorRepo.save(new Operator("operator 1"));
		}
	}

	@Override
	public Object getOperators() {
		
		List<Operator> ops=operatorRepo.findAll();
		List<Booking> bks=bookingRepo.findByOperatorIdAndStatus(ops.stream().map(Operator::getId).collect(Collectors.toList()),1);
		
		
		List<OperatorDetailsDTO> dtos=new ArrayList<>();
		for(Operator op:ops) {
			OperatorDetailsDTO dto=new OperatorDetailsDTO();
			List<TimeSlot> bookedSlots = bks.stream().filter(booking -> booking.getOpid() == op.getId())
					.map(bk -> new TimeSlot(bk.getStartTime(),bk.getEndTime())).collect(Collectors.toList());
			
			Collections.sort(bookedSlots, new Comparator<TimeSlot>() {
	            @Override
	            public int compare(TimeSlot ts1, TimeSlot ts2) {
	                return Integer.compare(ts1.getStartTime(), ts2.getStartTime());
	            }
	        });
			
//			List<TimeSlot> mergedBooked = mergeSlots(bookedSlots);
			List<TimeSlot> mergedavailable = findAvailableSlots(bookedSlots);
			
			dto.setAvlSlots(mergedavailable);
			dto.setBookedslots(bookedSlots);
			dto.setId(op.getId());
			dto.setName(op.getName());
			dtos.add(dto);
		}
		
		
		return dtos;
	}

	
	public static List<TimeSlot> findAvailableSlots(List<TimeSlot> mergedSlots) {
		List<TimeSlot> availableSlots = new ArrayList<>();
        if(mergedSlots.isEmpty()) {
            availableSlots.add(new TimeSlot(0, 0));
            return availableSlots;
        }

        int lastEndTime = 0;
        boolean is23startavailable=false;
        for(TimeSlot slot : mergedSlots) {
            if(slot.getStartTime() > lastEndTime ) {
                availableSlots.add(new TimeSlot(lastEndTime, slot.getStartTime()));
                lastEndTime=slot.getEndTime();
            }
            if(slot.getStartTime() == 23) {
            	is23startavailable=true;
            }
        }
        
        lastEndTime=lastEndTime == -1 ?0:lastEndTime;
        if(lastEndTime <= 23) {
        	int etime=is23startavailable ? 23 :0;
        	if(etime !=lastEndTime && lastEndTime!= 0) {
        		availableSlots.add(new TimeSlot(lastEndTime, etime));
        	}
            
        }
        return availableSlots;
    }

	@Override
	public Object getMyBooking(Integer userid) {
		
		return bookingRepo.findByUserId(userid);
	}

	@Override
	public Map<String, String> updateBooking(Integer bookingid, Integer type, Integer startTime) {
		
		Optional<Booking> bh=bookingRepo.findById(bookingid);
		Map<String,String> map=new HashMap<>();
		if(bh.isPresent()) {
			Booking bk=bh.get();
//			
			if(type == 0) {
				bk.setStatus(0);
				bookingRepo.save(bk);
				map.put("message", "booking canceled successfully");
			}else {
				
				if(type != 1) {
					map.put("message", "invalid status. required 0 or 1");
				}
				if(startTime != null && startTime > 23) {
					map.put("message", "invalid start time");
					return map;
				}
				if(bookingRepo.checkslot(bk.getOpid(),startTime) == null) {
					bk.setStartTime(startTime);
					bk.setStatus(1);
					bk.setEndTime((startTime+1)%24);
					bookingRepo.save(bk);
					map.put("message", "booking updated successfully successfully");
				}else {
					map.put("message", "no slot available");
				}
				
			}
		}else {
			map.put("message", "no booking found with given id");
		}
		
		return map;
	}

}
