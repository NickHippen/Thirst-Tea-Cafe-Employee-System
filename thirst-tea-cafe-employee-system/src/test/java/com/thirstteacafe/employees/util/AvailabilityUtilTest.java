package com.thirstteacafe.employees.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.thirstteacafe.employees.dto.Availability;
import com.thirstteacafe.employees.dto.DayOfWeek;
import com.thirstteacafe.employees.dto.DailyAvailability;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AvailabilityUtilTest {

	@Autowired
	private AvailabilityUtil availabilityUtil;
	
	private List<Availability> availabilities;
	
	@Before
	public void before() {
		availabilities = new ArrayList<>();
		Availability avail = new Availability();
		avail.put(DayOfWeek.MONDAY, Arrays.asList(new DailyAvailability(0, 24)));
		availabilities.add(avail);
		avail = new Availability();
		avail.put(DayOfWeek.MONDAY, Arrays.asList(new DailyAvailability(24, 32)));
		availabilities.add(avail);
		avail = new Availability();
		avail.put(DayOfWeek.WEDNESDAY, Arrays.asList(new DailyAvailability(0, 24)));
		availabilities.add(avail);
		avail = new Availability();
		avail.put(DayOfWeek.THURSDAY, Arrays.asList(new DailyAvailability(0, 24)));
		availabilities.add(avail);
		avail = new Availability();
		avail.put(DayOfWeek.FRIDAY, Arrays.asList(new DailyAvailability(0, 24)));
		availabilities.add(avail);
	}
	
	@Test
	public void testConsolidate() {
		Availability consolidatedAvail = availabilityUtil.consolidate(availabilities);
		System.out.println(consolidatedAvail.get(DayOfWeek.MONDAY));
		assertEquals(4, consolidatedAvail.keySet().size());
		assertEquals(2, consolidatedAvail.get(DayOfWeek.MONDAY).size());
		assertNull(consolidatedAvail.get(DayOfWeek.TUESDAY));
		assertEquals(1, consolidatedAvail.get(DayOfWeek.WEDNESDAY).size());
		assertEquals(1, consolidatedAvail.get(DayOfWeek.THURSDAY).size());
		assertEquals(1, consolidatedAvail.get(DayOfWeek.FRIDAY).size());
	}
	
}
