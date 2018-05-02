package com.thirstteacafe.employees.dto;

/**
 * An enum representing the 7 days in a week
 */
public enum DayOfWeek {

	MONDAY(0),
	TUESDAY(1),
	WEDNESDAY(2),
	THURSDAY(3),
	FRIDAY(4),
	SATURDAY(5),
	SUNDAY(6);
	
	private int offset;
	
	private DayOfWeek(int offset) {
		this.offset = offset;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}
	
	public static DayOfWeek fromOffset(int offset) {
		for (DayOfWeek dow : values()) {
			if (dow.getOffset() == offset) {
				return dow;
			}
		}
		throw new IndexOutOfBoundsException("No day of week for offset " + offset);
	}
	
}
