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
import org.jacop.constraints.Constraint;
import org.jacop.constraints.Or;
import org.jacop.constraints.PrimitiveConstraint;

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
        
//        // foodDuty_i,j is 1 if employee i is assigned to food duty on time slot j
//        IntVar[][] foodDuty = new IntVar[numberOfEmployees][numberOfTimeSlots];
//        IntVar[][] foodDutyT = new IntVar[numberOfTimeSlots][numberOfEmployees];
//        // drinkDuty_i,j is 1 if employee i is assigned to drink duty on time slot j
//        IntVar[][] drinkDuty = new IntVar[numberOfEmployees][numberOfTimeSlots];
//        IntVar[][] drinkDutyT = new IntVar[numberOfTimeSlots][numberOfEmployees];
//        for (int i = 0; i < numberOfEmployees; i++)
//        {
//            for (int j = 0; j < numberOfTimeSlots; j++)
//            {
//                foodDuty[i][j] = foodDutyT[j][i] = new IntVar(store,String.format("food_%d,%d", i,j),0,food[i]);
//                drinkDuty[i][j] = drinkDutyT[j][i] = new IntVar(store,String.format("drink_%d,%d", i,j),0,drink[i]);
//            }
//        }
        
        for (int i = 0; i < numberOfEmployees; i++)
            for (int j = 0; j < numberOfTimeSlots; j++)
                work[i][j] = workT[j][i] = new IntVar(store, String.format("work_%d,%d",i,j),0,1);
        
        // define problem constraints
        
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
        // for each time slot must have 1 food maker (possibly redundant)
        for (int j = 0; j < numberOfTimeSlots; j++)
            store.impose(new LinearInt(store,workT[j],food,">=", 1));
        //for each time slot must have 1 drink maker (possibly redundant)
        for (int j = 0; j < numberOfTimeSlots; j++)
            store.impose(new LinearInt(store,workT[j],drink,">=", 1));
        
        
        
//        //for each time slot must have 1 food maker
//        for (int j = 0; j < numberOfTimeSlots; j++)
//            store.impose(new SumBool(store,foodDutyT[j],">=",ONE));
//        //for each time slot must have 1 drink maker
//        for (int j = 0; j < numberOfTimeSlots; j++)
//            store.impose(new SumBool(store,drinkDutyT[j],">=",ONE));    
        
//        // for each employee on each shift if they are working they can make 
//        // food xor make drinks
//        for (int i = 0; i < numberOfEmployees; i++)
//            for (int j = 0; j < numberOfTimeSlots; j++)
//                store.impose(new XplusYlteqZ(foodDuty[i][j],drinkDuty[i][j],work[i][j]));
//  
        
        for (int j = 0; j < numberOfTimeSlots; j++)
        {
            PrimitiveConstraint[] pairs = new PrimitiveConstraint[numberOfEmployees * (numberOfEmployees - 1) / 2];
            int c = 0;
            for (int i1 = 0; i1 < numberOfEmployees; i1++)
            {
                for (int i2 = i1 + 1; i2 < numberOfEmployees; i2++)
                {
                    pairs[c++] = new LinearInt(store,
                            new IntVar[]{work[i1][j],work[i2][j]},new int[]{1,1},
                            ">=", 
                            4 - food[i1] - food[i2]);
                }
            }
            store.impose(new Or(pairs));
        }
        for (int j = 0; j < numberOfTimeSlots; j++)
        {
            PrimitiveConstraint[] pairs = new PrimitiveConstraint[numberOfEmployees * (numberOfEmployees - 1) / 2];
            int c = 0;
            for (int i1 = 0; i1 < numberOfEmployees; i1++)
            {
                for (int i2 = i1 + 1; i2 < numberOfEmployees; i2++)
                {
                    pairs[c++] = new LinearInt(store,
                            new IntVar[]{work[i1][j],work[i2][j]},new int[]{1,1},
                            ">=", 
                            4 - drink[i1] - drink[i2]);
                }
            }
            store.impose(new Or(pairs));
        }
        // Each employee must work between their minimum and maximum hours
        for (int i = 0; i < numberOfEmployees; i++)
            store.impose(new LinearInt(store,work[i],time,">=", minHours[i]));
        for (int i = 0; i < numberOfEmployees; i++)
            store.impose(new LinearInt(store,work[i],time,"<=", maxHours[i]));    
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
        // TODO: actually do something with this feasbility value
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
