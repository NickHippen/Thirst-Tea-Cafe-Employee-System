package com.thirstteacafe.employees.dto;

public class ScheduleResult {

	private int[][] schedule; // TODO Change to proper schedule object
	private boolean feasible;

	public ScheduleResult() {
	}
	
	public ScheduleResult(int[][] schedule, boolean feasible) {
		this.schedule = schedule;
		this.feasible = feasible;
	}
	
	public int[][] getSchedule() {
		return schedule;
	}

	public void setSchedule(int[][] schedule) {
		this.schedule = schedule;
	}

	public boolean isFeasible() {
		return feasible;
	}

	public void setFeasible(boolean feasible) {
		this.feasible = feasible;
	}

}
