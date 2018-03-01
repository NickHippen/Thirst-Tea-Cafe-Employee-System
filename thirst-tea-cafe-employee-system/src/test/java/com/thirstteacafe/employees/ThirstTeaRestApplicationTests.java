package com.thirstteacafe.employees;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ThirstTeaRestApplicationTests {

	@Before
	public void before() {
	}
	
	@Test
	public void contextLoads() {
		assertTrue(true);
	}
	
	@Test
	public void testSchedule1() {
		String scheduleMatrix =
				"1 0 1\n" +
                "0 1 1\n" +
                "1 0 1\n";
		int[][] s = SchedulingFunction.schedule(
				scheduleMatrix,
                
                "1 1 1",
                "1 1 1",
                "1 1 1",
                "1 1 1",
                        
                "2 1 3",
                "3 3 3",
                "1 1 1"
        );
        Assert.assertArrayEquals(SchedulingFunction.convertMatrix(scheduleMatrix), s);
	}
	
	@Test
	public void testSchedule2() {
		String scheduleMatrix =
               //M M T T W W T T F F F S S S|S S
                "1 0 1 0 1 0 1 0 1 0 0 1 1 0 1 1\n" +
                "0 0 0 0 0 1 0 0 0 0 0 1 1 0 1 1\n" +
                "0 1 0 1 0 0 0 1 0 1 1 0 0 0 0 0\n" +
                "0 0 0 0 0 0 0 0 0 0 1 0 0 1 0 0\n";
		//Arrays.asList(SchedulingFunction.convertMatrix(scheduleMatrix)).forEach(arr -> System.out.println(Arrays.toString(arr)));
		int[][] s = SchedulingFunction.schedule(
				scheduleMatrix,
      //employee 0 1 2 3
                "1 1 1 1",
                "1 1 1 1",
                "1 1 1 1",
                "1 1 1 1",
                
               //M M T T W W T T F F F S S S|S S 
                "1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1",
                "1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1",
                "1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1"
        );
                // every column should sum to 1
                boolean result = true;
                for (int j = 0; j < s[0].length; j++)
                {
                    int sum = 0;
                    for (int i = 0; i < s.length; i++)
                    {
                        sum += s[i][j];
                    }
                    result &= sum == 1;
                }
		
                assertTrue(result);
                //Assert.assertArrayEquals(SchedulingFunction.convertMatrix(scheduleMatrix), s);
	}
}
