package com.thirstteacafe.employees;

import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.SchedulingException;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

import java.util.ArrayList;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ThirstTeaRestApplicationTests {

	public String scheduleString;
	
	@Before
	public void before() {
		scheduleString = "1 0 1\n" +
                "0 1 1\n" +
                "1 0 1\n";
	}
	
	@Test
	public void contextLoads() {
		assertTrue(true);
	}
	
	@Test
	public void testSchedule() {
		ArrayList<ArrayList<Integer>> s = SchedulingFunction.schedule(
				scheduleString,
                "2 1 3",
                "3 3 3"
        );
		assertEquals(SchedulingFunction.getMatrix(scheduleString), s);
	}

}
