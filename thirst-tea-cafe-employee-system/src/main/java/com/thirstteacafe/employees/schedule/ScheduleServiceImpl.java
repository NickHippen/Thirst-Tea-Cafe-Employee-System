package com.thirstteacafe.employees.schedule;

import java.util.List;

import org.jacop.constraints.LinearInt;
import org.jacop.constraints.SumBool;
import org.jacop.constraints.XeqC;
import org.jacop.core.IntVar;
import org.jacop.core.Store;
import org.jacop.search.DepthFirstSearch;
import org.jacop.search.IndomainMin;
import org.jacop.search.InputOrderSelect;
import org.jacop.search.Search;
import org.jacop.search.SelectChoicePoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thirstteacafe.employees.dto.Employee;
import com.thirstteacafe.employees.dto.ScheduleResult;
import com.thirstteacafe.employees.dto.Shift;
import com.thirstteacafe.employees.util.MatrixUtil;

@Service
public class ScheduleServiceImpl implements ScheduleService {
	
	@Autowired
	private MatrixUtil matrixUtil;
	@Autowired
	private EmployeeService employeeService;

	@Override
	public ScheduleResult scheduleEmployees(List<Employee> employees, List<Shift> shifts) {
		int[][] available = new int[employees.size()][];
		int[] admin = new int[employees.size()];
		int[] canLift = new int[employees.size()];
		int[] food = new int[employees.size()];
		int[] drink = new int[employees.size()];
		for (int employeeNum = 0; employeeNum < employees.size(); employeeNum++) {
			Employee employee = employees.get(employeeNum);
			admin[employeeNum] = employee.isAdmin() ? 1 : 0;
			canLift[employeeNum] = employee.isCanLift() ? 1 : 0;
			food[employeeNum] = employee.isFood() ? 1 : 0;
			drink[employeeNum] = employee.isDrinks() ? 1 : 0;
			
			int[] shiftArr = new int[shifts.size()];
			for (int shiftNum = 0; shiftNum < shifts.size(); shiftNum++) {
				Shift shift = shifts.get(shiftNum);
				shiftArr[shiftNum] = employeeService.checkAvailabilityForShift(employee, shift) ? 1 : 0;
			}
			available[employeeNum] = shiftArr;
		}
		
		int[] min = new int[shifts.size()];
		int[] max = new int[shifts.size()];
		int[] time = new int[shifts.size()];
		for (int shiftNum = 0; shiftNum < shifts.size(); shiftNum++) {
			Shift shift = shifts.get(shiftNum);
			min[shiftNum] = shift.getMinEmployees();
			max[shiftNum] = shift.getMaxEmployees();
			time[shiftNum] = shift.getTimeLength();
		}
		
		return schedule(available, admin, canLift, food, drink, min, max, time);
	}
	
	@Override
	public ScheduleResult schedule(String available,
			String admin, String canLift, String food, String drink,
			String min, String max, String time)

	{
		return schedule(matrixUtil.convertMatrix(available),
				matrixUtil.convert(admin), matrixUtil.convert(canLift), matrixUtil.convert(food), matrixUtil.convert(drink),
				matrixUtil.convert(min), matrixUtil.convert(max), matrixUtil.convert(time)
		);
	}

	private ScheduleResult schedule(int[][] available,
			int[] admin, int[] canLift, int[] food, int[] drink,
			int[] min, int[] max, int[] time) {
		int[] hoursMin;
		int[] adminOnly = min;

		// assumes the input is rectangular
		final int numberOfEmployees = available.length;
		final int numberOfTimeSlots = available[0].length;
		final int C = 128; // sufficiently large constant for hour constraint

		Store store = new Store();

		// work matrix
		IntVar[][] work = new IntVar[numberOfEmployees][numberOfTimeSlots];

		// transposed version of the work matrix
		IntVar[][] workT = new IntVar[numberOfTimeSlots][numberOfEmployees];

		for (int i = 0; i < numberOfEmployees; i++)
			for (int j = 0; j < numberOfTimeSlots; j++)
				work[i][j] = workT[j][i] = new IntVar(store, String.format("work_%d,%d", i, j), 0, 1);

		// define problem constraints

		// If the employee is not available for the time slot then the employee
		// cannot work the time slot
		for (int i = 0; i < numberOfEmployees; i++)
			for (int j = 0; j < numberOfTimeSlots; j++)
				if (available[i][j] == 0)
					store.impose(new XeqC(work[i][j], 0));

		// Must have no less than minimum number of employees and no more than
		// maximum number of employees for slot.
		for (int j = 0; j < numberOfTimeSlots; j++) {
			// is there a way to use constants here instead of constant intVars?
			store.impose(
					new SumBool(store, workT[j], ">=", new IntVar(store, String.format("min_%d", j), min[j], min[j])));
			store.impose(
					new SumBool(store, workT[j], "<=", new IntVar(store, String.format("max_%d", j), max[j], max[j])));
		}

		// for each time slot must have 1 admin
		for (int j = 0; j < numberOfTimeSlots; j++)
			store.impose(new LinearInt(store, workT[j], admin, ">=", 1));
		// for each time slot must have 1 lifter
		for (int j = 0; j < numberOfTimeSlots; j++)
			store.impose(new LinearInt(store, workT[j], canLift, ">=", 1));
		// for each time slot must have 1 food maker
		for (int j = 0; j < numberOfTimeSlots; j++)
			store.impose(new LinearInt(store, workT[j], food, ">=", 1));
		// for each time slot must have 1 drink maker
		for (int j = 0; j < numberOfTimeSlots; j++)
			store.impose(new LinearInt(store, workT[j], drink, ">=", 1));

		// for each employee they can work no more than 40 hours unless they
		// are an admin
		for (int i = 0; i < numberOfEmployees; i++)
			store.impose(new LinearInt(store, work[i], time, "<=", 40 + admin[i] * C));

		// for admin only shifts if employee is not an admin then they are
		// not available
		for (int j = 0; j < numberOfTimeSlots; j++)
			if (adminOnly[j] == 1)
				for (int i = 0; i < numberOfEmployees; i++)
					if (admin[i] == 0)
						store.impose(new XeqC(work[i][j], 0));

		// compute oldSchedule
		Search<IntVar> label = new DepthFirstSearch<IntVar>();
		label.setPrintInfo(false);

		SelectChoicePoint<IntVar> select = new InputOrderSelect<>(store, matrixUtil.flatten(work), new IndomainMin<IntVar>());
		boolean feasible = label.labeling(store, select);

		int[][] result = new int[numberOfEmployees][numberOfTimeSlots];
		for (int i = 0; i < numberOfEmployees; i++)
			for (int j = 0; j < numberOfTimeSlots; j++)
				result[i][j] = work[i][j].value();
		return new ScheduleResult(result, feasible);
	}

}
