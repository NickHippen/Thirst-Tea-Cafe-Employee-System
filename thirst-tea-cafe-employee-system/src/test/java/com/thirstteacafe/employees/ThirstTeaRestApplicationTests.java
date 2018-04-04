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

	@Test
	public void contextLoads() {
		assertTrue(true);
	}
	
}
