package com.thirstteacafe.employees;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.thirstteacafe.employees.dto.ScheduleResult;
import com.thirstteacafe.employees.schedule.ScheduleService;
import com.thirstteacafe.employees.util.MatrixUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ThirstTeaRestApplicationTests {

	@Autowired
	private ScheduleService scheduleService;
	@Autowired
	private MatrixUtil matrixUtil;
	
	@Test
	public void contextLoads() {
		assertTrue(true);
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
               //M M M  T T T  W W W  R R R  F F F  S S S |S S S
          /*1*/ "0 0 1  0 0 0  0 0 1  0 0 0  0 0 0  0 1 0  0 1 1\n" +
          /*2*/ "0 0 1  0 1 1  0 1 1  0 1 1  0 1 1  0 1 1  0 1 1\n" +
          /*3*/ "0 1 1  0 0 1  0 0 0  0 0 0  0 1 1  0 1 1  0 1 1\n" +
          /*4*/ "0 1 0  0 1 0  0 1 1  0 1 1  0 1 0  0 0 0  0 0 0\n" +
          /*5*/ "0 0 1  0 1 1  0 0 1  0 1 1  0 0 0  0 1 1  0 0 0\n" +
          /*6*/ "0 0 0  0 0 0  0 0 0  0 0 0  0 0 0  0 1 0  0 1 0\n" +
          /*7*/ "1 1 1  1 1 1  1 1 1  1 1 1  1 1 1  1 1 1  1 1 1\n" + /*admins*/
          /*8*/ "1 1 1  1 1 1  1 1 1  1 1 1  1 1 1  1 1 1  1 1 1\n" + /*admins*/
          /*9*/ "1 1 1  1 1 1  1 1 1  1 1 1  1 1 1  1 1 1  1 1 1\n";  /*admins*/
		//Arrays.asList(SchedulingFunction.convertMatrix(scheduleMatrix)).forEach(arr -> System.out.println(Arrays.toString(arr)));
		ScheduleResult s = scheduleService.schedule(
				scheduleMatrix,
      //employee  1  2  3  4  5  6  7  8  9 
                " 0  0  0  0  0  0  1  1  1", // Admin
                " 1  1  1  1  1  1  1  1  1", // Lift
                " 1  1  1  1  1  1  1  1  1", // Food
                " 1  1  1  1  0  0  1  1  1", // Drinks
                " 0  0  0  0  0  0  0  0  0", // Min Hours
                "40 40 40 40 40 40 99 99 99", // Max Hours
                
                // M  M  M   T  T  T   W  W  W   R  R  R   F  F  F   S  S  S  |S  S  S 
/*Min Employees*/" 1  1  1   1  1  2   1  1  2   1  1  2   1  1  2   1  2  2   1  1  1", /* Min Employees */
/*Max Employees*/" 1  1  1   1  1  2   1  1  2   1  1  2   1  1  2   1  2  2   1  1  1", /* Max Employees */
/*Time         */"10  6  5  11  6  5  11  6  6  11  6  6  12  6  6  12  6  6  10 10  5", /* Time          */
/*Admin Only   */" 1  0  0   1  0  0   1  0  0   1  0  0   1  0  0   1  0  0   1  0  0", /* Admin Only    */
                        
                "3 6 9 12 15 18 21" // Days of week
                        );
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
