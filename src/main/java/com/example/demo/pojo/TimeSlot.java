package com.example.demo.pojo;

public class TimeSlot {
    int startTime;
    int endTime;

    public TimeSlot(int startTime, int endTime) {
    	super();
        this.startTime = startTime;
        this.endTime = endTime;
    }
    
    public TimeSlot() {
    	
    }

    public String toString() {
        return "[" + startTime + ", " + endTime + "]";
    }

	public int getStartTime() {
		return startTime;
	}

	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}

	public int getEndTime() {
		return endTime;
	}

	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}
    
    
}
