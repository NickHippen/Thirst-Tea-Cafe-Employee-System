package com.thirstteacafe.employees.schedule;

import java.util.List;

import org.jacop.constraints.LinearInt;
import org.jacop.constraints.SumBool;
import org.jacop.constraints.XeqC;
import org.jacop.constraints.XplusYlteqZ;
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
import java.util.ArrayList;
import org.jacop.constraints.Constraint;
import org.jacop.constraints.Or;
import org.jacop.constraints.PrimitiveConstraint;
import org.jacop.constraints.XplusYgtC;

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
		int[] minHours = new int[employees.size()];
		int[] maxHours = new int[employees.size()];
		for (int employeeNum = 0; employeeNum < employees.size(); employeeNum++) {
			Employee employee = employees.get(employeeNum);
			admin[employeeNum] = employee.isAdmin() ? 1 : 0;
			canLift[employeeNum] = employee.isCanLift() ? 1 : 0;
			food[employeeNum] = employee.isFoodMaker() ? 1 : 0;
			drink[employeeNum] = employee.isDrinkMaker() ? 1 : 0;
			minHours[employeeNum] = employee.getMinHours();
			maxHours[employeeNum] = employee.getMinHours();

			int[] shiftArr = new int[shifts.size()];
			for (int shiftNum = 0; shiftNum < shifts.size(); shiftNum++) {
				Shift shift = shifts.get(shiftNum);
				shiftArr[shiftNum] = employeeService.checkAvailabilityForShift(employee, shift) ? 1 : 0;
			}
			available[employeeNum] = shiftArr;
		}
		
		int[] minEmployees = new int[shifts.size()];
		int[] maxEmployees = new int[shifts.size()];
		int[] time = new int[shifts.size()];
		int[] adminOnly = new int[shifts.size()];
		for (int shiftNum = 0; shiftNum < shifts.size(); shiftNum++) {
			Shift shift = shifts.get(shiftNum);
			minEmployees[shiftNum] = shift.getMinEmployees();
			maxEmployees[shiftNum] = shift.getMaxEmployees();
			time[shiftNum] = shift.getTimeLength();
			time[shiftNum] = shift.isAdminOnly() ? 1 : 0;
		}
	
		return schedule(available,
				admin, canLift, food, drink, minHours, maxHours,
				minEmployees, maxEmployees, time, adminOnly);
	}
	
        
	@Override
	public ScheduleResult schedule(String available,
			String admin, String canLift, String food, String drink, String minHours, String maxHours,
			String minEmployees, String maxEmployees, String time, String adminOnly)

	{
		return schedule(matrixUtil.convertMatrix(available),
				matrixUtil.convert(admin), matrixUtil.convert(canLift), matrixUtil.convert(food), matrixUtil.convert(drink), matrixUtil.convert(minHours), matrixUtil.convert(maxHours),
				matrixUtil.convert(minEmployees), matrixUtil.convert(maxEmployees), matrixUtil.convert(time), matrixUtil.convert(adminOnly)
		);
	}

	private ScheduleResult schedule(int[][] available,
			int[] admin, int[] canLift, int[] food, int[] drink, int[] minHours, int[] maxHours,
			int[] minEmployees, int[] maxEmployees, int[] time, int[] adminOnly) {
        
        // assumes the input is rectangular
        final int numberOfEmployees = available.length;
        final int numberOfTimeSlots = available[0].length;
        final int C = 128; // sufficiently large constant for hour constraint
        
        Store store = new Store();
        IntVar ONE = new IntVar(store,String.format("ONE"),1,1);
     
        // work matrix
        IntVar[][] work  = new IntVar[numberOfEmployees][numberOfTimeSlots];
        
        // transposed version of the work matrix
        IntVar[][] workT = new IntVar[numberOfTimeSlots][numberOfEmployees];
        
        // work[i][j] is 1 if employee i works time slot j and 0 otherwise
        for (int i = 0; i < numberOfEmployees; i++)
            for (int j = 0; j < numberOfTimeSlots; j++)
                work[i][j] = workT[j][i] = new IntVar(store, String.format("work_%d,%d",i,j),0,1);

        // define problem constraints
        
        // There must be at least one viable pair that is working,
        // additionally constrains that there must be at least 1 food maker
        // and at least 1 drink maker
        for (int j = 0; j < numberOfTimeSlots; j++)
        {
            ArrayList<PrimitiveConstraint> viablePairs = new ArrayList<>();
            for (int i1 = 0 ; i1 < numberOfEmployees; i1++)
            {
                for (int i2 = i1 + 1; i2 < numberOfEmployees; i2++)
                {
                    // if it is possible for the pair to be viable
                    if (food[i1] + drink[i2] >= 2 || drink[i1] + food[i2] >= 2)
                    {
                        viablePairs.add(new XplusYgtC(work[i1][j],work[i2][j], 1));
                    }
                }
            }
            store.impose(new Or(viablePairs));
        }        
        // If the employee is not available for the time slot then the employee 
        // cannot work the time slot
        for (int i = 0; i < numberOfEmployees; i++)
            for (int j = 0; j < numberOfTimeSlots; j++)
                if (available[i][j] == 0)
                    store.impose(new XeqC(work[i][j],0));
        
        // Must have no less than minimum number of employees and no more than
        // maximum number of employees for slot.
        for (int j = 0; j < numberOfTimeSlots; j++)
        {
            // is there a way to use constants here instead of constant IntVars?
            store.impose(new SumBool(store,workT[j],">=",
                    new IntVar(store,String.format("min_%d",j),minEmployees[j],minEmployees[j])));
            store.impose(new SumBool(store,workT[j],"<=",
                    new IntVar(store,String.format("max_%d",j),maxEmployees[j],maxEmployees[j])));
        }
        
        // for each time slot must have 1 admin
        for (int j = 0; j < numberOfTimeSlots; j++)
            store.impose(new LinearInt(store,workT[j],admin,">=", 1));
        //for each time slot must have 1 lifter
        for (int j = 0; j < numberOfTimeSlots; j++)
            store.impose(new LinearInt(store,workT[j],canLift,">=", 1));
        
        // this runs real slow for some reason
        // Each employee must work between their minimum and maximum hours
//        for (int i = 0; i < numberOfEmployees; i++)
//            store.impose(new LinearInt(store,work[i],time,">=", minHours[i]));
//        for (int i = 0; i < numberOfEmployees; i++)
//            store.impose(new LinearInt(store,work[i],time,"<=", maxHours[i]));    
//        
        // for admin only shifts if employee is not an admin then they are
        // not available
        for (int j = 0; j < numberOfTimeSlots; j++)
            if (adminOnly[j] == 1)
                for (int i = 0; i < numberOfEmployees; i++)
                    if (admin[i] == 0)
                        store.impose(new XeqC(work[i][j],0));
                    
        // compute oldSchedule
        Search<IntVar> label = new DepthFirstSearch<IntVar>();
        //label.setPrintInfo(false);
        
        SelectChoicePoint<IntVar> select = 
                    new InputOrderSelect<>(store,matrixUtil.flatten(work), new IndomainMin<IntVar>());
        
        boolean feasible = label.labeling(store, select);
        if (!feasible) 
            System.err.print("not feasible");
           int[][] result = new int[numberOfEmployees][numberOfTimeSlots];
        //System.out.println(store);
        if (feasible)
        {
            for (int i = 0; i < numberOfEmployees; i++)
                for (int j = 0; j < numberOfTimeSlots; j++)
                    result[i][j] = work[i][j].value();
        }
        return new ScheduleResult(result, feasible);
	}
}
