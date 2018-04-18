package com.thirstteacafe.employees.schedule;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.thirstteacafe.employees.dto.DailyAvailability;
import com.thirstteacafe.employees.dto.DayOfWeek;
import com.thirstteacafe.employees.dto.Employee;
import com.thirstteacafe.employees.dto.ScheduleResult;
import com.thirstteacafe.employees.dto.Shift;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ScheduleServiceTest {

	@Autowired
	private ScheduleService scheduleService;

	private List<Employee> employees;
	private List<Shift> shifts;

	@Before
	public void before() {
		Employee emp1 = new Employee("Nick", "Blah", true, true, true, true, 0, 40);
		Arrays.asList(DayOfWeek.values()).forEach((dow) -> emp1.addAvailability(dow, new DailyAvailability(0, 24)));
		Employee emp2 = new Employee("Mitch", "Blah", true, true, true, true, 0, 40);
		Arrays.asList(DayOfWeek.values()).forEach((dow) -> emp2.addAvailability(dow, new DailyAvailability(0, 24)));
		Employee emp3 = new Employee("Hayden", "Blah", true, true, true, true, 0, 40);
		Arrays.asList(DayOfWeek.values()).forEach((dow) -> emp3.addAvailability(dow, new DailyAvailability(24, 47)));
		Employee emp4 = new Employee("Vincent", "Blah", true, true, true, true, 0, 40);
		Arrays.asList(DayOfWeek.values()).forEach((dow) -> emp4.addAvailability(dow, new DailyAvailability(24, 47)));
		employees = Arrays.asList(emp1, emp2, emp3, emp4);

		shifts = new ArrayList<Shift>();
		Arrays.asList(DayOfWeek.values()).forEach((dow) -> {
			shifts.add(new Shift(dow, 2, 8));
			shifts.add(new Shift(dow, 26, 32));
		});
	}

	@Test
	@Ignore
	public void testSchedule() {
		ScheduleResult result = scheduleService.scheduleEmployees(employees, shifts);
		Arrays.asList(result.getSchedule()).forEach(arr -> System.out.println(Arrays.toString(arr)));
		assertTrue(result.isFeasible());
	}

	@Test
	public void testSchedule1() {
		String scheduleMatrix = "1 1 1\n" + "1 1 1\n" + "1 1 1\n";
		ScheduleResult s = scheduleService.schedule(scheduleMatrix,
				// Employee Matrices
				"1 1 1", "1 1 1", "1 1 1", "1 1 1", "0 0 0", "40 40 40",
				// Shift Matrices
				"3 3 3", "3 3 3", "1 1 1", "0 0 0",

				"1 2 3");
		// Assert.assertArrayEquals(matrixUtil.convertMatrix(scheduleMatrix),
		// s.getSchedule());
	}

	@Test
	public void testSchedule2() {
		String scheduleMatrix =
				// M M T T W W T T F F F S S S|S S
				"1 0 1 0 0 1 1 0 1 0 0 1 1 0 0 1\n" + "1 0 1 0 0 1 1 0 1 0 1 0 1 0 1 0\n"
						+ "0 1 0 1 1 0 0 1 0 1 0 1 0 1 1 0\n" + "0 1 0 1 1 0 0 1 0 1 1 1 0 1 0 1\n";
		// Arrays.asList(SchedulingFunction.convertMatrix(scheduleMatrix)).forEach(arr
		// -> System.out.println(Arrays.toString(arr)));
		ScheduleResult s = scheduleService.schedule(scheduleMatrix,
				// employee 0 1 2 3
				"1 1 1 1", "1 1 1 1", "1 1 1 1", "1 1 1 1", "0 0 0 0", "40 40 40 40",

				// M M T T W W T T F F F S S S|S S
				"1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1", "2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2", "1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1",
				"0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0",

				"2 4 6 8 10 12 14");
		Arrays.asList(s.getSchedule()).forEach(arr -> System.out.println(Arrays.toString(arr)));
		// every column should sum to 2
		boolean result = true;
		for (int j = 0; j < s.getSchedule()[0].length; j++) {
			int sum = 0;
			for (int i = 0; i < s.getSchedule().length; i++) {
				sum += s.getSchedule()[i][j];
			}
			result &= sum == 2;
		}

		assertTrue(result);
		// Assert.assertArrayEquals(SchedulingFunction.convertMatrix(scheduleMatrix),
		// s);
	}

	// disable in favor of real data
	// @Test
	public void testSchedule3() {
		String scheduleMatrix =
				// M M T T W W T T F F F S S S|S S
				"1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1\n" + "1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1\n"
						+ "1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1\n" + "1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1\n"
						+ "1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1\n" + "1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1\n"
						+ "0 0 0 0 0 0 0 0 0 0 0 1 1 1 1 1\n";
		// Arrays.asList(SchedulingFunction.convertMatrix(scheduleMatrix)).forEach(arr
		// -> System.out.println(Arrays.toString(arr)));
		ScheduleResult s = scheduleService.schedule(scheduleMatrix,
				// employee 0 1 2 3 4 5 6
				"0 0 0 0 0 0 0", // Admin
				"1 1 1 1 1 1 1", // Lift
				"1 1 1 1 1 1 1", // Food
				"1 1 1 1 1 1 1", // Drinks
				"20 20 20 20 20 20 20", // Min Hours
				"40 40 40 40 40 40 40", // Max Hours

				// M M T T W W T T F F F S S S|S S
				"2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2", // Min Employees
				"2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2", // Max Employees
				"8 8 8 8 8 8 8 8 8 8 8 8 8 8 8 8", // Time
				"0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0", // Admin Only

				"2 4 6 8 10 12 14" // Days of week
		);
		Arrays.asList(s.getSchedule()).forEach(arr -> System.out.println(Arrays.toString(arr)));
		assertTrue(s.isFeasible());
	}

	@Test
	public void realDataSchedule() {
		String scheduleMatrix =
				// M M T T W W R R F F S S |S S
				/* 1 */ "0 1  0 0  0 1  0 0  0 0  1 0  1 1\n" +
				/* 2 */ "0 1  1 1  1 1  1 1  1 1  1 1  1 1\n" +
				/* 3 */ "1 1  0 1  0 0  0 0  1 1  1 1  1 1\n" +
				/* 4 */ "1 0  1 0  1 1  1 1  1 0  0 0  0 0\n" +
				/* 5 */ "0 1  1 1  0 1  1 1  0 0  1 1  0 0\n" +
				/* 6 */ "0 0  0 0  0 0  0 0  0 0  1 0  1 0\n" +
				/* 7 */ "1 1  1 1  1 1  1 1  1 1  1 1  1 1\n" + /* admins */
				/* 8 */ "1 1  1 1  1 1  1 1  1 1  1 1  1 1\n" + /* admins */
				/* 9 */ "1 1  1 1  1 1  1 1  1 1  1 1  1 1\n"; /* admins */
		// Arrays.asList(SchedulingFunction.convertMatrix(scheduleMatrix)).forEach(arr
		// -> System.out.println(Arrays.toString(arr)));
		ScheduleResult s = scheduleService.schedule(scheduleMatrix,
				// employee 1 2 3 4 5 6 7 8 9
				" 0  0  0  0  0  0  1  1  1", // Admin
				" 1  1  1  1  1  1  1  1  1", // Lift
				" 1  1  1  1  1  1  1  1  1", // Food
				" 1  1  1  1  0  0  1  1  1", // Drinks
				"10 10 10 10 10  5  0  0  0", // Min Hours
				"40 40 40 40 40 40 60 60 60", // Max Hours

				// M M T T W W R R F F S S |S S
				/* Min Employees */" 2  2   2  3   2  3   2  3   2  3   3  3   2  2", /*
																						 * Min
																						 * Employees
																						 */
				/* Max Employees */" 2  2   2  3   2  3   2  3   2  3   3  3   2  2", /*
																						 * Max
																						 * Employees
																						 */
				/* Time */" 6  5   6  5   6  6   6  6   6  6   6  6   5  5", /* Time */
				/* Admin Only */" 0  0   0  0   0  0   0  0   0  0   0  0   0  0", /*
																					 * Admin
																					 * Only
																					 */

				"2 4 6 8 10 12 14" // Days of week
		);
		Arrays.asList(s.getSchedule()).forEach(arr -> System.out.println(Arrays.toString(arr)));
		assertTrue(s.isFeasible());
	}
	
	@Test
	public void testRealDataSplit() {
		Employee jp = new Employee();
		jp.addAvailability(DayOfWeek.MONDAY, new DailyAvailability(34, 44));
		jp.addAvailability(DayOfWeek.WEDNESDAY, new DailyAvailability(34, 44));
		jp.addAvailability(DayOfWeek.SATURDAY, new DailyAvailability(22, 34));
		jp.addAvailability(DayOfWeek.SUNDAY, new DailyAvailability(22, 42));
		jp.setMinHours(5);
		jp.setMaxHours(11);
		jp.setDrinkMaker(true);
		jp.setCanLift(true);
		
		Employee ef = new Employee();
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
		rs.addAvailability(DayOfWeek.MONDAY, new DailyAvailability(28, 44));
		rs.addAvailability(DayOfWeek.TUESDAY, new DailyAvailability(22, 44));
		rs.addAvailability(DayOfWeek.WEDNESDAY, new DailyAvailability(28, 44));
		rs.addAvailability(DayOfWeek.THURSDAY, new DailyAvailability(22, 44));
		rs.addAvailability(DayOfWeek.SATURDAY, new DailyAvailability(22, 46));
		rs.setMinHours(0);
		rs.setMaxHours(15);
		
		Employee tp = new Employee();
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
		
		Employee ww = new Employee();
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
		
		Employee id = new Employee();
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
		
		List<Employee> employees = Arrays.asList(jp, ef, bl, js, rs, tp, ww, id);
		
		Shift m1 = new Shift(DayOfWeek.MONDAY, 22, 44, 1, 1, true);
		Shift m2 = new Shift(DayOfWeek.MONDAY, 22, 34, 1, 1, false);
		Shift m3 = new Shift(DayOfWeek.MONDAY, 34, 44, 2, 2, false);
		
		Shift t1 = new Shift(DayOfWeek.TUESDAY, 22, 44, 1, 1, true);
		Shift t2 = new Shift(DayOfWeek.TUESDAY, 22, 44, 1, 1, false);
		Shift t3 = new Shift(DayOfWeek.TUESDAY, 22, 34, 1, 1, false);
		Shift t4 = new Shift(DayOfWeek.TUESDAY, 34, 44, 1, 1, false);
		
		Shift w1 = new Shift(DayOfWeek.WEDNESDAY, 22, 44, 2, 2, true);
		Shift w2 = new Shift(DayOfWeek.WEDNESDAY, 22, 44, 1, 1, false);
		
		Shift th1 = new Shift(DayOfWeek.THURSDAY, 22, 44, 1, 1, true);
		Shift th2 = new Shift(DayOfWeek.THURSDAY, 22, 44, 1, 1, false);
		
		Shift f1 = new Shift(DayOfWeek.FRIDAY, 22, 46, 1, 1, true);
		Shift f2 = new Shift(DayOfWeek.FRIDAY, 22, 46, 1, 1, false);
		Shift f3 = new Shift(DayOfWeek.FRIDAY, 34, 46, 1, 1, false);
		
		Shift sa1 = new Shift(DayOfWeek.SATURDAY, 22, 46, 1, 1, true);
		Shift sa2 = new Shift(DayOfWeek.SATURDAY, 22, 34, 1, 1, false);
		Shift sa3 = new Shift(DayOfWeek.SATURDAY, 34, 46, 2, 2, false);
		
		Shift su1 = new Shift(DayOfWeek.SUNDAY, 22, 42, 1, 1, true);
		Shift su2 = new Shift(DayOfWeek.SUNDAY, 22, 42, 1, 1, false);
		Shift su3 = new Shift(DayOfWeek.SUNDAY, 32, 42, 1, 1, false);
		
		List<Shift> shifts = Arrays.asList(m1, m2, m3, t1, t2, t3, t4, w1, w2, th1, th2, f1, f2, f3, sa1, sa2, sa3, su1, su2, su3);
		
		scheduleService.scheduleEmployees(employees, shifts);
	}

	// // M) Monday (1100-1230-1400-1700-1900-2000) 5
	//
	// // T) Tuesday (1100-1230-1700-1730-2000) 4
	// //
	// // W) Wednesday (1100-1400-1630-1700-2000) 4
	// //
	// // T) Thursday (1100-1230-1630-2000) 3
	// //
	// // F) Friday (1100-1630-1730-2300) 3
	// //
	// // S) Saturday (1100-1700-1900-2300) 3
	// //
	// // S) Sunday (1100-1600-1900-2100) 3
	// String scheduleMatrix =
	// //M M M M M T T T T W W W W T T T F F F S S S |S S S
	// /*1*/ "0 0 0 1 1 0 0 0 0 0 0 1 1 0 0 0 0 0 0 1 0 0 1 1 1\n" +
	// /*2*/ "1 1 1 1 1 1 1 1 1 1 1 1 1 0 1 1 1 1 1 1 1 1 1 1 1\n" +
	// /*3*/ "0 1 1 1 1 0 1 1 1 0 0 0 0 0 1 0 1 1 1 1 1 1 1 1 1\n" +
	// /*4*/ "1 1 1 0 0 1 1 1 0 1 1 1 1 1 1 1 1 1 0 0 0 0 1 1 1\n" +
	// /*5*/ "1 1 0 0 0 1 1 1 1 0 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1\n" +
	// /*6*/ "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 1 0 1 1 0\n" +
	// /*7*/ "1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1\n" + /*admins*/
	// /*8*/ "1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1\n" + /*admins*/
	// /*9*/ "1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1\n"; /*admins*/
	// //Arrays.asList(SchedulingFunction.convertMatrix(scheduleMatrix)).forEach(arr
	// -> System.out.println(Arrays.toString(arr)));
	// ScheduleResult s = scheduleService.schedule(
	// scheduleMatrix,
	// //employee 0 1 2 3 4 5 6 7 8 9
	// " 0 0 0 0 0 0 0 1 1 1", // Admin
	// " 1 1 1 1 1 1 1 1 1 1", // Lift
	// " 1 1 1 1 1 1 1 1 1 1", // Food
	// " 1 1 1 1 1 1 1 1 1 1", // Drinks
	// "20 20 20 20 20 20 20 0 0 0", // Min Hours
	// "40 40 40 40 40 40 40 90 90 90", // Max Hours
	//
	// //M M T T W W T T F F F S S S|S S
	// "2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2", // Min Employees
	// "2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2", // Max Employees
	// "8 8 8 8 8 8 8 8 8 8 8 8 8 8 8 8", // Time
	// "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0", // Admin Only
	//
	// "2 4 6 8 10 12 14" // Days of week)
	// );

}
