package com.thirstteacafe.employees.timeslot;

import static org.junit.Assert.assertEquals;

import java.time.LocalTime;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.thirstteacafe.employees.timeslot.TimeslotService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TimeslotServiceTest {

	@Autowired
	private TimeslotService timeslotService;
	
	@Before
	public void before() {
	}
	
	@Test
	public void testConvertLocalTime() {
		assertEquals(0, timeslotService.convertLocalTime(LocalTime.of(0, 0)));
		assertEquals(47, timeslotService.convertLocalTime(LocalTime.of(23, 59, 59)));
		assertEquals(24, timeslotService.convertLocalTime(LocalTime.of(12, 0)));
		assertEquals(13, timeslotService.convertLocalTime(LocalTime.of(6, 30)));
		assertEquals(13, timeslotService.convertLocalTime(LocalTime.of(6, 59)));
	}
	
}
