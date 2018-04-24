package com.thirstteacafe.employees.schedule;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.thirstteacafe.employees.dto.DailyAvailability;
import com.thirstteacafe.employees.dto.DayOfWeek;
import com.thirstteacafe.employees.dto.Employee;
import com.thirstteacafe.employees.dto.Shift;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ScheduleGeneratorTest {

	@Autowired
	private ScheduleGenerator scheduleGenerator;

	@Test
	public void testSchedule() throws Exception {
		Employee jp = new Employee();
		jp.setFirstName("jp");
		jp.addAvailability(DayOfWeek.MONDAY, new DailyAvailability(34, 44));
		jp.addAvailability(DayOfWeek.WEDNESDAY, new DailyAvailability(34, 44));
		jp.addAvailability(DayOfWeek.SATURDAY, new DailyAvailability(22, 34));
		jp.addAvailability(DayOfWeek.SUNDAY, new DailyAvailability(22, 42));
		jp.setMinHours(5);
		jp.setMaxHours(11);
		jp.setDrinkMaker(true);
		jp.setCanLift(true);
		
		Employee ef = new Employee();
		ef.setFirstName("ef");
		ef.addAvailability(DayOfWeek.MONDAY, new DailyAvailability(22, 44));
		ef.addAvailability(DayOfWeek.TUESDAY, new DailyAvailability(22, 44));
		ef.addAvailability(DayOfWeek.WEDNESDAY, new DailyAvailability(22, 44));
		ef.addAvailability(DayOfWeek.THURSDAY, new DailyAvailability(22, 44));
		ef.addAvailability(DayOfWeek.FRIDAY, new DailyAvailability(22, 46));
		ef.addAvailability(DayOfWeek.SATURDAY, new DailyAvailability(22, 46));
		ef.addAvailability(DayOfWeek.SUNDAY, new DailyAvailability(22, 42));
		ef.setMinHours(50);
		ef.setMaxHours(60);
		ef.setDrinkMaker(true);
		
		Employee bl = new Employee();
		bl.setFirstName("bl");
		bl.addAvailability(DayOfWeek.MONDAY, new DailyAvailability(25, 44));
		bl.addAvailability(DayOfWeek.TUESDAY, new DailyAvailability(25, 44));
		bl.addAvailability(DayOfWeek.THURSDAY, new DailyAvailability(25, 33));
		bl.addAvailability(DayOfWeek.FRIDAY, new DailyAvailability(22, 46));
		bl.addAvailability(DayOfWeek.SATURDAY, new DailyAvailability(22, 46));
		bl.addAvailability(DayOfWeek.SUNDAY, new DailyAvailability(22, 42));
		bl.setMinHours(5);
		bl.setMaxHours(22);
		bl.setDrinkMaker(true);
		bl.setCanLift(true);
		
		Employee js = new Employee();
		js.setFirstName("js");
		js.addAvailability(DayOfWeek.MONDAY, new DailyAvailability(22, 34));
		js.addAvailability(DayOfWeek.TUESDAY, new DailyAvailability(22, 35));
		js.addAvailability(DayOfWeek.WEDNESDAY, new DailyAvailability(22, 44));
		js.addAvailability(DayOfWeek.THURSDAY, new DailyAvailability(22, 44));
		js.addAvailability(DayOfWeek.THURSDAY, new DailyAvailability(22, 35));
		js.setMinHours(5);
		js.setMaxHours(30);
		js.setDrinkMaker(true);
		js.setCanLift(true);
		
		Employee rs = new Employee();
		rs.setFirstName("rs");
		rs.addAvailability(DayOfWeek.MONDAY, new DailyAvailability(28, 44));
		rs.addAvailability(DayOfWeek.TUESDAY, new DailyAvailability(22, 44));
		rs.addAvailability(DayOfWeek.WEDNESDAY, new DailyAvailability(28, 44));
		rs.addAvailability(DayOfWeek.THURSDAY, new DailyAvailability(22, 44));
		rs.addAvailability(DayOfWeek.SATURDAY, new DailyAvailability(22, 46));
		rs.setMinHours(0);
		rs.setMaxHours(15);
		
		Employee tp = new Employee();
		tp.setFirstName("tp");
		tp.addAvailability(DayOfWeek.MONDAY, new DailyAvailability(22, 44));
		tp.addAvailability(DayOfWeek.TUESDAY, new DailyAvailability(22, 44));
		tp.addAvailability(DayOfWeek.WEDNESDAY, new DailyAvailability(22, 44));
		tp.addAvailability(DayOfWeek.THURSDAY, new DailyAvailability(22, 44));
		tp.addAvailability(DayOfWeek.FRIDAY, new DailyAvailability(22, 46));
		tp.addAvailability(DayOfWeek.SATURDAY, new DailyAvailability(22, 46));
		tp.addAvailability(DayOfWeek.SUNDAY, new DailyAvailability(22, 42));
		tp.setMinHours(0);
		tp.setMaxHours(999);
		tp.setFoodMaker(true);
		tp.setDrinkMaker(true);
		tp.setAdmin(true);
		tp.setCanLift(true);
		
		Employee ww = new Employee();
		ww.setFirstName("ww");
		ww.addAvailability(DayOfWeek.MONDAY, new DailyAvailability(22, 44));
		ww.addAvailability(DayOfWeek.TUESDAY, new DailyAvailability(22, 44));
		ww.addAvailability(DayOfWeek.WEDNESDAY, new DailyAvailability(22, 44));
		ww.addAvailability(DayOfWeek.THURSDAY, new DailyAvailability(22, 44));
		ww.addAvailability(DayOfWeek.FRIDAY, new DailyAvailability(22, 46));
		ww.addAvailability(DayOfWeek.SATURDAY, new DailyAvailability(22, 46));
		ww.addAvailability(DayOfWeek.SUNDAY, new DailyAvailability(22, 42));
		ww.setMinHours(0);
		ww.setMaxHours(999);
		ww.setFoodMaker(true);
		ww.setDrinkMaker(true);
		ww.setAdmin(true);
		
		Employee id = new Employee();
		id.setFirstName("id");
		id.addAvailability(DayOfWeek.MONDAY, new DailyAvailability(22, 44));
		id.addAvailability(DayOfWeek.TUESDAY, new DailyAvailability(22, 44));
		id.addAvailability(DayOfWeek.WEDNESDAY, new DailyAvailability(22, 44));
		id.addAvailability(DayOfWeek.THURSDAY, new DailyAvailability(22, 44));
		id.addAvailability(DayOfWeek.FRIDAY, new DailyAvailability(22, 46));
		id.addAvailability(DayOfWeek.SATURDAY, new DailyAvailability(22, 46));
		id.addAvailability(DayOfWeek.SUNDAY, new DailyAvailability(22, 42));
		id.setMinHours(0);
		id.setMaxHours(999);
		id.setFoodMaker(true);
		id.setDrinkMaker(true);
		id.setAdmin(true);
		
		List<Employee> employees = Arrays.asList(jp, ef, bl, js, rs, tp, ww, id);
		
		Shift m1 = new Shift(DayOfWeek.MONDAY, 22, 44, 1, 1);
		Shift m2 = new Shift(DayOfWeek.MONDAY, 22, 34, 1, 0);
		Shift m3 = new Shift(DayOfWeek.MONDAY, 34, 44, 2, 0);
		
		Shift t1 = new Shift(DayOfWeek.TUESDAY, 22, 44, 1, 1);
		Shift t2 = new Shift(DayOfWeek.TUESDAY, 22, 44, 1, 0);
		Shift t3 = new Shift(DayOfWeek.TUESDAY, 22, 34, 1, 0);
		Shift t4 = new Shift(DayOfWeek.TUESDAY, 34, 44, 1, 0);
		
		Shift w1 = new Shift(DayOfWeek.WEDNESDAY, 22, 44, 2, 1);
		Shift w2 = new Shift(DayOfWeek.WEDNESDAY, 22, 44, 1, 0);
		
		Shift th1 = new Shift(DayOfWeek.THURSDAY, 22, 44, 1, 1);
		Shift th2 = new Shift(DayOfWeek.THURSDAY, 22, 44, 1, 0);
		
		Shift f1 = new Shift(DayOfWeek.FRIDAY, 22, 46, 1, 1);
		Shift f2 = new Shift(DayOfWeek.FRIDAY, 22, 46, 1, 0);
		Shift f3 = new Shift(DayOfWeek.FRIDAY, 34, 46, 1, 0);
		
		Shift sa1 = new Shift(DayOfWeek.SATURDAY, 22, 46, 1, 1);
		Shift sa2 = new Shift(DayOfWeek.SATURDAY, 22, 34, 1, 0);
		Shift sa3 = new Shift(DayOfWeek.SATURDAY, 34, 46, 2, 0);
		
		Shift su1 = new Shift(DayOfWeek.SUNDAY, 22, 42, 1, 1);
		Shift su2 = new Shift(DayOfWeek.SUNDAY, 22, 42, 1, 0);
		Shift su3 = new Shift(DayOfWeek.SUNDAY, 32, 42, 1, 0);
		
		List<Shift> shifts = Arrays.asList(m1, m2, m3, t1, t2, t3, t4, w1, w2, th1, th2, f1, f2, f3, sa1, sa2, sa3, su1, su2, su3);
		scheduleGenerator.scheduleEmployees(employees, shifts);
	}

}
