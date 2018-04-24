package com.thirstteacafe.employees.schedule;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thirstteacafe.employees.dto.DailySchedule;
import com.thirstteacafe.employees.dto.DayOfWeek;
import com.thirstteacafe.employees.dto.Employee;
import com.thirstteacafe.employees.dto.Shift;
import com.thirstteacafe.employees.dto.WeeklySchedule;
import com.thirstteacafe.employees.employee.EmployeeService;
import com.thirstteacafe.employees.exceptions.ScheduleException;

@Service
public class ASPScheduleGenerator implements ScheduleGenerator {

	@Autowired
	private EmployeeService employeeService;
	
	@SuppressWarnings("unchecked")
	@Override
	public WeeklySchedule scheduleEmployees(List<Employee> employees, List<Shift> shifts) throws ScheduleException {
		File tmpInstanceFile;
		FileWriter tmpWriter = null;
		try {
			tmpInstanceFile = File.createTempFile("tt-instance-", ".lp");
			tmpInstanceFile.deleteOnExit();
			tmpWriter = new FileWriter(tmpInstanceFile);
			Map<DayOfWeek, List<Shift>> shiftMap = shifts.stream().collect(Collectors.groupingBy(Shift::getDayOfWeek));
			for (DayOfWeek dow : shiftMap.keySet()) {
				List<Shift> shiftsForDow = shiftMap.get(dow);
				int minSlot = Integer.MAX_VALUE;
				int maxSlot = Integer.MIN_VALUE;
				for (int i = 0; i < shiftsForDow.size(); i++) {
					Shift shift = shiftsForDow.get(i);
					tmpWriter.write(String.format("shift(%s,%d,%d,%d,%d,%d,%d).\n",
							dow.name().toLowerCase(), i, shift.getMinEmployees(),
							shift.getMaxEmployees(), shift.getStartTimeslot(), shift.getEndTimeslot(), (shift.getEndTimeslot() - shift.getStartTimeslot()) / 2));
					minSlot = Math.min(minSlot, shift.getStartTimeslot());
					maxSlot = Math.max(maxSlot, shift.getEndTimeslot());
					if (shift.isAdminOnly()) {
						tmpWriter.write(String.format("admin_only_shift(%s,%d).\n", dow.name().toLowerCase(), i));
					}
				}
				tmpWriter.write(String.format("day_range(%s,%d,%d).\n", dow.name().toLowerCase(), minSlot, maxSlot));
			}
			for (Employee employee : employees) {
				for (DayOfWeek dow : shiftMap.keySet()) {
					List<Shift> shiftsForDow = shiftMap.get(dow);
					for (int i = 0; i < shiftsForDow.size(); i++) {
						Shift shift = shiftsForDow.get(i);
						if (employeeService.checkAvailabilityForShift(employee, shift)) {
							tmpWriter.write(String.format("available(%d,%s,%d).\n", employee.getEmployeeId(), dow.name().toLowerCase(), i));
						}
					}
				}
			}
			tmpWriter.write(String.format("employee(%s).\n",
					String.join(";", employees.stream()
							.map(Employee::getEmployeeId)
							.map(String::valueOf)
							.collect(Collectors.toList()))));
			tmpWriter.write(String.format("is_admin(%s).\n",
					String.join(";", employees.stream()
							.filter(Employee::isAdmin)
							.map(Employee::getEmployeeId)
							.map(String::valueOf)
							.collect(Collectors.toList()))));
			tmpWriter.write(String.format("can_lift(%s).\n",
					String.join(";", employees.stream()
							.filter(Employee::isCanLift)
							.map(Employee::getEmployeeId)
							.map(String::valueOf)
							.collect(Collectors.toList()))));
			tmpWriter.write(String.format("makes_food(%s).\n",
					String.join(";", employees.stream()
							.filter(Employee::isFoodMaker)
							.map(Employee::getEmployeeId)
							.map(String::valueOf)
							.collect(Collectors.toList()))));
			tmpWriter.write(String.format("makes_drinks(%s).\n",
					String.join(";", employees.stream()
							.filter(Employee::isDrinkMaker)
							.map(Employee::getEmployeeId)
							.map(String::valueOf)
							.collect(Collectors.toList()))));
		} catch (IOException e1) {
			e1.printStackTrace();
			throw new ScheduleException("Unable to write temporary file");
		} finally {
			if (tmpWriter != null) {
				try {
					tmpWriter.close();
				} catch (IOException e) {
					throw new ScheduleException("Unable to close temporary file");
				}
			}
		}
		
		Process process;
		try {
			process = Runtime.getRuntime().exec(new String[] {"cmd.exe", "/c", "clingo", "src/main/resources/schedule.2.lp", tmpInstanceFile.getAbsolutePath(), "--warn=none", "--outf=2"});
		} catch (IOException e) {
			e.printStackTrace();
			throw new ScheduleException("Unable to start clingo process");
		}
		try {
			process.waitFor();
		} catch (InterruptedException e) {
		}
		tmpInstanceFile.delete();
		BufferedReader procStdin = new BufferedReader(new InputStreamReader(process.getInputStream()));
		String jsonString = "";
		String s = null;
		try {
			while ((s = procStdin.readLine()) != null) {
			    jsonString += s + "\n";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		Map<String, Object> result;
		try {
			result = new ObjectMapper().readValue(jsonString, HashMap.class);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		List<Map<String,Object>> callList = (List<Map<String, Object>>) result.get("Call");
		List<Map<String,Object>> witnessList = (List<Map<String, Object>>) callList.get(0).get("Witnesses");
		List<String> answerSet = (List<String>) witnessList.get(0).get("Value");
		WeeklySchedule weeklySchedule = new WeeklySchedule();
		Map<DayOfWeek, DailySchedule> dailySchedules = new HashMap<>();
		for (String atom : answerSet) {
			if (!atom.startsWith("schedule(")) {
				continue;
			}
			String[] atomArgs = atom.substring(9, atom.length()-1).split(",");
			DayOfWeek dow = DayOfWeek.valueOf(atomArgs[0].toUpperCase());
			DailySchedule dailySchedule = dailySchedules.getOrDefault(dow, new DailySchedule());
			int timeslotMin = Integer.parseInt(atomArgs[3]);
			int timeslotMax = Integer.parseInt(atomArgs[4]);
			Employee employee = employees.stream().filter(e -> String.valueOf(e.getEmployeeId()).equals(atomArgs[2])).findFirst().orElse(null);
			if (employee == null) {
				throw new ScheduleException("Unable to find employee for id " + atomArgs[2]);
			}
			dailySchedule.scheduleEmployee(timeslotMin, timeslotMax, employee);
			dailySchedules.put(dow, dailySchedule);
		}
		weeklySchedule.setDays(dailySchedules);
		System.out.println(weeklySchedule);
		return weeklySchedule;
	}
	
}
