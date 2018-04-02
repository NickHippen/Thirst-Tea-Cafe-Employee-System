package com.thirstteacafe.employees.employee;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.thirstteacafe.employees.dto.Employee;

public class EmployeeMapper implements RowMapper<Employee> {

	@Override
	public Employee mapRow(ResultSet rs, int arg1) throws SQLException {
		Employee employee = new Employee();
		employee.setName(rs.getString("emp_firstname") + " " + rs.getString("emp_lastname"));
		employee.setAdmin(rs.getBoolean("emp_admin"));
		employee.setCanLift(rs.getBoolean("emp_canlift"));
		employee.setDrinkMaker(rs.getBoolean("emp_drinks"));
		employee.setFoodMaker(rs.getBoolean("emp_food"));
		employee.setMaxHours(rs.getInt("emp_maxhours"));
		employee.setMinHours(rs.getInt("emp_minhours"));
		return employee;
	}

}
