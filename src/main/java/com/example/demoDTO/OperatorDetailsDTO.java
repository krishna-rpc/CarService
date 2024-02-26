package com.example.demoDTO;

import java.util.List;

import com.example.demo.pojo.TimeSlot;

public class OperatorDetailsDTO {

	private Integer id;
	private String name;
	
	private List<TimeSlot> avlSlots;
	
	private List<TimeSlot> bookedslots;
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<TimeSlot> getAvlSlots() {
		return avlSlots;
	}

	public void setAvlSlots(List<TimeSlot> avlSlots) {
		this.avlSlots = avlSlots;
	}

	public List<TimeSlot> getBookedslots() {
		return bookedslots;
	}

	public void setBookedslots(List<TimeSlot> bookedslots) {
		this.bookedslots = bookedslots;
	}
	
} 
