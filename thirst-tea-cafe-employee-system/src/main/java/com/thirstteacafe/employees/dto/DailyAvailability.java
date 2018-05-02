package com.thirstteacafe.employees.dto;

/**
 * DTO object to describe availability for a single day
 */
public class DailyAvailability {

	private Long availabilityId;
	private Integer fromTimeslot;
	private Integer toTimeslot;

	public DailyAvailability() {
	}

	public DailyAvailability(Integer fromTimeslot, Integer toTimeslot) {
		this.fromTimeslot = fromTimeslot;
		this.toTimeslot = toTimeslot;
	}

	public Long getAvailabilityId() {
		return availabilityId;
	}

	public void setAvailabilityId(Long availabilityId) {
		this.availabilityId = availabilityId;
	}

	public Integer getFromTimeslot() {
		return fromTimeslot;
	}

	public void setFromTimeslot(Integer fromTimeslot) {
		this.fromTimeslot = fromTimeslot;
	}

	public Integer getToTimeslot() {
		return toTimeslot;
	}

	public void setToTimeslot(Integer toTimeslot) {
		this.toTimeslot = toTimeslot;
	}

}
