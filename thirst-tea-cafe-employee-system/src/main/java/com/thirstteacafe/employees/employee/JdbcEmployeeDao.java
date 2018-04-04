package com.thirstteacafe.employees.employee;

import java.sql.Time;
import java.time.LocalTime;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.thirstteacafe.employees.dto.Availability;
import com.thirstteacafe.employees.dto.DayOfWeek;
import com.thirstteacafe.employees.dto.Employee;
import com.thirstteacafe.employees.util.AvailabilityUtil;

@Component
public class JdbcEmployeeDao implements EmployeeDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private AvailabilityUtil availabilityUtil;

    @Override
    public Employee getEmployee(Long employeeId) {
    	List<Employee> employees = jdbcTemplate.query(String.format(
    			"SELECT * FROM %s E"
    			+ " WHERE E.emp_id = ?",
    			TABLE),
    		new Object[] { employeeId },
    		new EmployeeMapper());
    	if (employees.isEmpty()) {
    		return null;
    	}
    	Employee emp = employees.get(0);
    	emp.setAvailability(getAvailability(employeeId));
    	return emp;
    }

	@Override
	public List<Employee> getAllEmployees() {
		List<Employee> employees = jdbcTemplate.query(String.format(
    			"SELECT * FROM %s E",
    			TABLE),
    		new EmployeeMapper());
		for (Employee emp : employees) {
			emp.setAvailability(getAvailability(emp.getEmployeeId()));
		}
    	return employees;
	}

	@Override
	public void createEmployee(Employee employee) {
		jdbcTemplate.update(String.format(
				"INSERT INTO %s"
				+ " (emp_username, emp_password, emp_firstname, emp_lastname, emp_minhours, emp_maxhours, emp_canlift, emp_food, emp_drinks, emp_admin)"
				+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
				TABLE),
			new Object[] { employee.getUsername(), employee.getPassword(), employee.getFirstName(), employee.getLastName(),
					employee.getMinHours(), employee.getMaxHours(), employee.isCanLift(),
					employee.isFoodMaker(), employee.isDrinkMaker(), employee.isAdmin() });
	}

	@Override
	public void updateEmployee(Long employeeId, Employee employee) {
		jdbcTemplate.update(String.format(
				"UPDATE %s"
				+ " SET emp_firstname=?, emp_lastname=?, emp_minhours=?, emp_maxhours=?, emp_canlift=?, emp_food=?, emp_drinks=?, emp_admin=?"
				+ " WHERE emp_id = ?",
				TABLE),
			new Object[] { employee.getFirstName(), employee.getLastName(),
					employee.getMinHours(), employee.getMaxHours(), employee.isCanLift(),
					employee.isFoodMaker(), employee.isDrinkMaker(), employee.isAdmin(),
					employeeId });
	}
	
	@Override
	public void deleteEmployee(Long employeeId) {
		jdbcTemplate.update(String.format(
				"DELETE FROM %s"
				+ " WHERE emp_id = ?",
				TABLE),
			new Object[] { employeeId });
	}

	@Override
	public void addAvailability(Long employeeId, Availability availability) {
		for (DayOfWeek dow : availability.keySet()) {
			List<Pair<LocalTime, LocalTime>> dayAvail = availability.get(dow);
			for (Pair<LocalTime, LocalTime> timeRange : dayAvail) {
				jdbcTemplate.update(String.format(
						"INSERT INTO %s"
						+ " (emp_id, avail_dayofweek, avail_start, avail_end)"
						+ " VALUES (?, ?, ?, ?)",
						AVAILABILITY_TABLE),
					new Object[] { employeeId, dow.getOffset(),
							Time.valueOf(timeRange.getLeft()), Time.valueOf(timeRange.getRight()) });
			}
		}
	}

	@Override
	public void deleteAvailability(Long employeeId, Long availabilityId) {
		jdbcTemplate.update(String.format(
				"DELETE FROM %s"
				+ " WHERE emp_id = ?"
				+ " AND avail_id = ?",
				AVAILABILITY_TABLE),
			new Object[] { employeeId, availabilityId });
	}

	@Override
	public Availability getAvailability(Long employeeId) {
		List<Availability> availabilities = jdbcTemplate.query(String.format(
				"SELECT * FROM %s A"
				+ " WHERE A.emp_id = ?",
				AVAILABILITY_TABLE),
			new Object[] { employeeId },
			new AvailabilityMapper());
		return availabilityUtil.consolidate(availabilities);
	}

	@Override
	public void deleteAllAvailability(Long employeeId) {
		jdbcTemplate.update(String.format(
				"DELETE FROM %s"
				+ " WHERE emp_id = ?",
				AVAILABILITY_TABLE),
			new Object[] { employeeId });
	}

}
