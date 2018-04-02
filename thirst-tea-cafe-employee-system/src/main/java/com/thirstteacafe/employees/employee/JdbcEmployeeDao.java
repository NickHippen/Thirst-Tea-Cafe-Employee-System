package com.thirstteacafe.employees.employee;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.thirstteacafe.employees.dto.Employee;

@Component
public class JdbcEmployeeDao implements EmployeeDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

    @Override
    public Employee getEmployee(Long employeeId) {
    	List<Employee> employees = jdbcTemplate.query(String.format(
    			"SELECT * FROM %s E"
    			+ " WHERE E.emp_id = ?",
    			TABLE),
    		new EmployeeMapper());
    	if (employees.isEmpty()) {
    		return null;
    	}
    	return employees.get(0);
    }

}
