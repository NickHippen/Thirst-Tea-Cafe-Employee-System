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
	
	@Test
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
            
        }
}
