package com.thirstteacafe.employees.shifts;

import java.time.LocalTime;
import java.time.DayOfWeek;

public class ShiftData {

	public static final int SUNDAY    = 1;
    public static final int MONDAY    = 2;
    public static final int TUESDAY   = 3;
    public static final int WEDNESDAY = 4;
    public static final int THURSDAY  = 5;
    public static final int FRIDAY    = 6;
    public static final int SATURDAY  = 7;
	
	private int id;
    private int dayOfWeek;
    private LocalTime start;
    private LocalTime end;
    private int numPeople;
    private int admin;

    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getDayOfWeek() {
		return dayOfWeek;
	}
	public void setDayOfWeek(int dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}
	
	public LocalTime getStart() {
		return start;
	}
	public void setStart(LocalTime start) {
		this.start = start;
	}
	
	public LocalTime getEnd() {
		return end;
	}
	public void setEnd(LocalTime end) {
		this.end = end;
	}
	
	public int getNumPeople() {
		return numPeople;
	}
	public void setNumPeople(int numPeople) {
		this.numPeople = numPeople;
	}
	
	public int getAdmin() {
		return admin;
	}
	public void setAdmin(int admin) {
		this.admin = admin;
	}
	
	@Override
	public String toString() {
		String s = "[ShiftData]\n";
		s += String.format("id        : %d\n", id);
		s += String.format("dayOfWeek : %d\n", dayOfWeek);
		s += String.format("start     : %s\n", start);
		s += String.format("end       : %s\n", end);
		s += String.format("numPeople : %d\n", numPeople);
		s += String.format("admin     : %d\n", admin);
		return s;
	}
	
}