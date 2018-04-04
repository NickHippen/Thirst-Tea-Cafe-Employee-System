package com.thirstteacafe.employees.schedule;

import static org.junit.Assert.assertTrue;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
		Arrays.asList(DayOfWeek.values())
			.forEach((dow) -> emp1.addAvailability(dow, new ImmutablePair<LocalTime, LocalTime>(LocalTime.of(0, 0), LocalTime.of(12, 0))));
		Employee emp2 = new Employee("Mitch", "Blah", true, true, true, true, 0, 40);
		Arrays.asList(DayOfWeek.values())
			.forEach((dow) -> emp2.addAvailability(dow, new ImmutablePair<LocalTime, LocalTime>(LocalTime.of(0, 0), LocalTime.of(12, 0))));
		Employee emp3 = new Employee("Hayden", "Blah", true, true, true, true, 0, 40);
		Arrays.asList(DayOfWeek.values())
			.forEach((dow) -> emp3.addAvailability(dow, new ImmutablePair<LocalTime, LocalTime>(LocalTime.of(12, 0), LocalTime.of(23, 59, 59))));
		Employee emp4 = new Employee("Vincent", "Blah", true, true, true, true, 0, 40);
		Arrays.asList(DayOfWeek.values())
			.forEach((dow) -> emp4.addAvailability(dow, new ImmutablePair<LocalTime, LocalTime>(LocalTime.of(12, 0), LocalTime.of(23, 59, 59))));
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
		String scheduleMatrix =
                "1 1 1\n" +
                "1 1 1\n" +
                "1 1 1\n";
		ScheduleResult s = scheduleService.schedule(
				scheduleMatrix,
                // Employee Matrices
                "1 1 1",
                "1 1 1",
                "1 1 1",
                "1 1 1",
                "0 0 0",
                "40 40 40",
                // Shift Matrices
                "3 3 3",
                "3 3 3",
                "1 1 1",
                "0 0 0",
                
                "1 2 3"
        );
//        Assert.assertArrayEquals(matrixUtil.convertMatrix(scheduleMatrix), s.getSchedule());
	}
	
	@Test
	public void testSchedule2() {
		String scheduleMatrix =
               //M M T T W W T T F F F S S S|S S
                "1 0 1 0 0 1 1 0 1 0 0 1 1 0 0 1\n" +
                "1 0 1 0 0 1 1 0 1 0 1 0 1 0 1 0\n" +
                "0 1 0 1 1 0 0 1 0 1 0 1 0 1 1 0\n" +
                "0 1 0 1 1 0 0 1 0 1 1 1 0 1 0 1\n";
		//Arrays.asList(SchedulingFunction.convertMatrix(scheduleMatrix)).forEach(arr -> System.out.println(Arrays.toString(arr)));
		ScheduleResult s = scheduleService.schedule(
				scheduleMatrix,
      //employee 0 1 2 3
                "1 1 1 1",
                "1 1 1 1",
                "1 1 1 1",
                "1 1 1 1",
                "0 0 0 0",
                "40 40 40 40",
                
               //M M T T W W T T F F F S S S|S S 
                "1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1",
                "2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2",
                "1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1",
                "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0",
                
                "2 4 6 8 10 12 14"
        );
		Arrays.asList(s.getSchedule()).forEach(arr -> System.out.println(Arrays.toString(arr)));
        // every column should sum to 2
        boolean result = true;
        for (int j = 0; j < s.getSchedule()[0].length; j++)
        {
            int sum = 0;
            for (int i = 0; i < s.getSchedule().length; i++)
            {
                sum += s.getSchedule()[i][j];
            }
            result &= sum == 2;
        }

        assertTrue(result);
        //Assert.assertArrayEquals(SchedulingFunction.convertMatrix(scheduleMatrix), s);
	}
	
        // disable in favor of real data
	//@Test
	public void testSchedule3() {
		String scheduleMatrix =
               //M M T T W W T T F F F S S S|S S
                "1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1\n" +
                "1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1\n" +
                "1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1\n" +
                "1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1\n" +
                "1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1\n" +
                "1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1\n" +
                "0 0 0 0 0 0 0 0 0 0 0 1 1 1 1 1\n";
		//Arrays.asList(SchedulingFunction.convertMatrix(scheduleMatrix)).forEach(arr -> System.out.println(Arrays.toString(arr)));
		ScheduleResult s = scheduleService.schedule(
				scheduleMatrix,
      //employee 0 1 2 3 4 5 6
                "0 0 0 0 0 0 0", // Admin
                "1 1 1 1 1 1 1", // Lift
                "1 1 1 1 1 1 1", // Food
                "1 1 1 1 1 1 1", // Drinks
                "20 20 20 20 20 20 20", // Min Hours
                "40 40 40 40 40 40 40", // Max Hours
                
               //M M T T W W T T F F F S S S|S S 
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
	public void realDataSchedule() 
        {
            String scheduleMatrix =     
               //M M  T T  W W  R R  F F  S S |S S
          /*1*/ "0 1  0 0  0 1  0 0  0 0  1 0  1 1\n" +
          /*2*/ "0 1  1 1  1 1  1 1  1 1  1 1  1 1\n" +
          /*3*/ "1 1  0 1  0 0  0 0  1 1  1 1  1 1\n" +
          /*4*/ "1 0  1 0  1 1  1 1  1 0  0 0  0 0\n" +
          /*5*/ "0 1  1 1  0 1  1 1  0 0  1 1  0 0\n" +
          /*6*/ "0 0  0 0  0 0  0 0  0 0  1 0  1 0\n" +
          /*7*/ "1 1  1 1  1 1  1 1  1 1  1 1  1 1\n" + /*admins*/
          /*8*/ "1 1  1 1  1 1  1 1  1 1  1 1  1 1\n" + /*admins*/
          /*9*/ "1 1  1 1  1 1  1 1  1 1  1 1  1 1\n";  /*admins*/
		//Arrays.asList(SchedulingFunction.convertMatrix(scheduleMatrix)).forEach(arr -> System.out.println(Arrays.toString(arr)));
		ScheduleResult s = scheduleService.schedule(
				scheduleMatrix,
      //employee  1  2  3  4  5  6  7  8  9 
                " 0  0  0  0  0  0  1  1  1", // Admin
                " 1  1  1  1  1  1  1  1  1", // Lift
                " 1  1  1  1  1  1  1  1  1", // Food
                " 1  1  1  1  0  0  1  1  1", // Drinks
                "10 10 10 10 10  5  0  0  0", // Min Hours
                "40 40 40 40 40 40 60 60 60", // Max Hours
                
                // M  M   T  T   W  W   R  R   F  F   S  S  |S  S 
/*Min Employees*/" 2  2   2  3   2  3   2  3   2  3   3  3   2  2", /* Min Employees */
/*Max Employees*/" 2  2   2  3   2  3   2  3   2  3   3  3   2  2", /* Max Employees */
/*Time         */" 6  5   6  5   6  6   6  6   6  6   6  6   5  5", /* Time          */
/*Admin Only   */" 0  0   0  0   0  0   0  0   0  0   0  0   0  0", /* Admin Only    */
                        
                "2 4 6 8 10 12 14" // Days of week
                        );    
				Arrays.asList(s.getSchedule()).forEach(arr -> System.out.println(Arrays.toString(arr)));
                assertTrue(s.isFeasible());
        }
        
        
//            // M) Monday    (1100-1230-1400-1700-1900-2000) 5 
//            
//            // T) Tuesday   (1100-1230-1700-1730-2000)      4
//            //
//            // W) Wednesday (1100-1400-1630-1700-2000)      4
//            //
//            // T) Thursday  (1100-1230-1630-2000)           3
//            //
//            // F) Friday    (1100-1630-1730-2300)           3
//            //
//            // S) Saturday  (1100-1700-1900-2300)           3
//            //
//            // S) Sunday    (1100-1600-1900-2100)           3
//            String scheduleMatrix =     
//               //M M M M M  T T T T  W W W W  T T T  F F F  S S S |S S S
//          /*1*/ "0 0 0 1 1  0 0 0 0  0 0 1 1  0 0 0  0 0 0  1 0 0  1 1 1\n" +
//          /*2*/ "1 1 1 1 1  1 1 1 1  1 1 1 1  0 1 1  1 1 1  1 1 1  1 1 1\n" +
//          /*3*/ "0 1 1 1 1  0 1 1 1  0 0 0 0  0 1 0  1 1 1  1 1 1  1 1 1\n" +
//          /*4*/ "1 1 1 0 0  1 1 1 0  1 1 1 1  1 1 1  1 1 0  0 0 0  1 1 1\n" +
//          /*5*/ "1 1 0 0 0  1 1 1 1  0 1 1 1  1 1 1  1 1 1  1 1 1  1 1 1\n" +
//          /*6*/ "0 0 0 0 0  0 0 0 0  0 0 0 0  0 0 0  0 0 0  1 1 0  1 1 0\n" +
//          /*7*/ "1 1 1 1 1  1 1 1 1  1 1 1 1  1 1 1  1 1 1  1 1 1  1 1 1\n" + /*admins*/
//          /*8*/ "1 1 1 1 1  1 1 1 1  1 1 1 1  1 1 1  1 1 1  1 1 1  1 1 1\n" + /*admins*/
//          /*9*/ "1 1 1 1 1  1 1 1 1  1 1 1 1  1 1 1  1 1 1  1 1 1  1 1 1\n";  /*admins*/
//		//Arrays.asList(SchedulingFunction.convertMatrix(scheduleMatrix)).forEach(arr -> System.out.println(Arrays.toString(arr)));
//		ScheduleResult s = scheduleService.schedule(
//				scheduleMatrix,
//      //employee  0  1  2  3  4  5  6  7  8  9 
//                " 0  0  0  0  0  0  0  1  1  1", // Admin
//                " 1  1  1  1  1  1  1  1  1  1", // Lift
//                " 1  1  1  1  1  1  1  1  1  1", // Food
//                " 1  1  1  1  1  1  1  1  1  1", // Drinks
//                "20 20 20 20 20 20 20  0  0  0", // Min Hours
//                "40 40 40 40 40 40 40 90 90 90", // Max Hours
//                
//               //M M T T W W T T F F F S S S|S S 
//                "2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2", // Min Employees
//                "2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2", // Max Employees
//                "8 8 8 8 8 8 8 8 8 8 8 8 8 8 8 8", // Time
//                "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0", // Admin Only
//                        
//                "2 4 6 8 10 12 14" // Days of week)
//                        );
	
}
