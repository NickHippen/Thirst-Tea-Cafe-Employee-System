package com.thirstteacafe.employees.schedule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.jacop.constraints.LinearInt;
import org.jacop.constraints.Or;
import org.jacop.constraints.PrimitiveConstraint;
import org.jacop.constraints.SumBool;
import org.jacop.constraints.XeqC;
import org.jacop.constraints.XplusYgtC;
import org.jacop.core.IntVar;
import org.jacop.core.Store;
import org.jacop.search.DepthFirstSearch;
import org.jacop.search.IndomainMin;
import org.jacop.search.Search;
import org.jacop.search.SelectChoicePoint;
import org.jacop.search.SimpleMatrixSelect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thirstteacafe.employees.dto.Employee;
import com.thirstteacafe.employees.dto.ScheduleResult;
import com.thirstteacafe.employees.dto.Shift;
import com.thirstteacafe.employees.dto.WeeklySchedule;
import com.thirstteacafe.employees.employee.EmployeeService;
import com.thirstteacafe.employees.util.MatrixUtil;

/**
 *
 * @author Hayden
 */
@Service
public class ScheduleServiceImpl implements ScheduleService {
	
	@Autowired
	private MatrixUtil matrixUtil;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private ScheduleDao scheduleDao;

    @Override
	public ScheduleResult scheduleEmployees(List<Employee> employees, List<Shift> shifts) {
		int[][] available = new int[employees.size()][];
		int[] admin = new int[employees.size()];
		int[] canLift = new int[employees.size()];
		int[] food = new int[employees.size()];
		int[] drink = new int[employees.size()];
		int[] minHours = new int[employees.size()];
		int[] maxHours = new int[employees.size()];
                int[] days = new int[7];
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
                        // assumes shifts start on a monday
                        days[shift.getDayOfWeek().ordinal()]++;
		}
                for (int shiftNum = 1; shiftNum < shifts.size(); shiftNum++)
                    days[shiftNum] += days[shiftNum - 1];         
	
		return schedule(available,
				admin, canLift, food, drink, minHours, maxHours,
				minEmployees, maxEmployees, time, adminOnly,
                                days);
	}
	
        /** 
        *  <script type="text/javascript" src="http://cdn.mathjax.org/mathjax/latest/MathJax.js?config=TeX-AMS-MML_HTMLorMML"></script>
*	<br><b>Definitions:</b><br>
*	\(numberOfEmployees\) employees with iterating variable \(i\)<br>
*	\(numberOfTimeSlots\) time slots with iterating variable \(j\)<br>
*	\(work_{i,j}\) is 1 if employee \(i\) works slot \(j\) else 0<br>
*
*	\(available_{i,j}\) is 1 if employee \(i\) is available on slot \(j\) else 0.<br>
*	
*	\(admin_i\) is 1 if employee \(i\) is an admin else 0.<br>
*	\(canLift_i\) is 1 if employee \(i\) can lift heavy tea container else 0.<br>
*	\(food_i\) is 1 if employee \(i\) can make food else 0.<br>
*	\(drink_i\) is 1 if employee \(i\) can make drinks else 0.<br>
*	\(minHours_i\) is the minimum number of hours that employee \(i\) will work.<br>
*	\(maxHours_i\) is the maximum number of hours that employee \(i\) will work.<br>
*	
*	\(min_j\) is minimum number of employees that can work slot \(j\).<br>
*	\(max_j\) is minimum number of employees that can work slot \(j\).<br>
*
*	\(time_j\) is the time worked if scheduled on time slot \(j\).<br>
*	
*	
*	\(days_k\) is the number of sub sections that we are splitting our schedule in<br>
*	
*	
*	
*	
*	<br><b>Inputs:</b><br>
*	\(available_{i,j}\)<br>
*	
*	\(admin_i\)<br>
*	\(canLift_i\)<br>
*	\(food_i\)<br>
*	\(drink_i\)<br>
*	\(minHours_i\)<br>
*	\(maxHours_i\)<br>
*	
*	\(min_j\)<br>
*	\(max_j\)<br>
*	\(adminOnly_j\)<br>
*	\(time_j\)<br>
*	
*	
*	\(days_k\)<br>
*	
*	
*	<br><b>Variables:</b><br>
*	\(work_{i,j}\)<br>
*	
*	
*	<br><b>Constraints:</b><br>
*	If the employee is not available for the time slot then the employee cannot work the time slot.<br>
*	\(\forall_{i}\forall_{j}(\neg available_{i,j} \implies work_{i,j} = 0)\)
*	<br>
*	
*	
*	Must have no less than minimum number of employees and no more than maximum number of employees for slot.<br>
*	\(\forall_{j}(min_j \le \sum\limits_{i}work_{i,j} \le max_j)\)<br>
*	
*	
*	For each time slot must have at least 1 admin.<br>
*	\(\forall_j(\sum\limits_{i}work_{i,j}\cdot admin_i \ge 1)\)<br>
*	
*	For each time slot must have at least 1 lifter.<br>
*	\(\forall_j(\sum\limits_{i}work_{i,j}\cdot canLift_i \ge 1)\)<br>
*	
*	There must be at least one viable pair that is working, additionally constrains that there must be at least 1 food maker and at least 1 drink maker.<br>
*	\(\forall_j\forall_{(i_1,i_2)}\)<br>\((i_2 &gt; i_1 \wedge (food_{i_1} + drink_{i_2}\ge 2\vee food_{i_2} + drink_{i_1}\ge 2)\implies work_{i_1,j}\cdot work_{i_2,j}\ge 2)\)<br>
*	
*	
*	
*	
*	For each employee they can work no less than their minimum and no more than their maximum number of hours.<br>
*	\(\forall_{i}(minHours_i \le \sum\limits_{j}time_j\cdot work_{i,j}\le maxHours_i)\)<br>
*	
*	If the time slot is admin only then if an employee is not an admin then they are not available for that shift.<br>
*	\(\forall_{j}(adminOnly_j \implies \forall_{i}(\neg admin_i \implies available_{i,j} = 0))\)<br>
*	
*	for every employee they can only work once a day.<br>
*	\(\forall_{i}\forall_{k}(\sum\limits_{days[k-1] \le j &lt; days[k]}\le 1)\)
*	
*
*	
*	<br><b>Output:</b><br> \(\forall_i\forall_j(work_{i,j}\text{ is assigned})\)<br>
        *    @param available is 1 if employee \(i\) is available on slot \(j\) else 0.
        *    @param admin is 1 if employee \(i\) is an admin else 0.
        *    @param canLift is 1 if employee \(i\) can lift heavy tea container else 0.
        *    @param food is 1 if employee \(i\) can make food else 0.
        *    @param drink is 1 if employee \(i\) can make drinks else 0.
        *    @param minHours is the minimum number of hours that employee \(i\) will work
        *    @param maxHours  is the maximum number of hours that employee \(i\) will work.
        *    @param minEmployees is minimum number of employees that can work slot \(j\).
        *    @param maxEmployees is minimum number of employees that can work slot \(j\).
        *    @param time time_j is the time worked if scheduled on time slot \(j\)
        *    @param adminOnly is the time worked if scheduled on time slot \(j\).
        *    @return the schedule 
        */
        @Override
	public ScheduleResult schedule(String available,
			String admin, String canLift, String food, String drink, String minHours, String maxHours,
			String minEmployees, String maxEmployees, String time, String adminOnly, 
                        String days)

	{
		return schedule(matrixUtil.convertMatrix(available),
				matrixUtil.convert(admin), matrixUtil.convert(canLift), matrixUtil.convert(food), matrixUtil.convert(drink), matrixUtil.convert(minHours), matrixUtil.convert(maxHours),
				matrixUtil.convert(minEmployees), matrixUtil.convert(maxEmployees), matrixUtil.convert(time), matrixUtil.convert(adminOnly),
                                matrixUtil.convert(days)
		);
	}

        /** 
        *    @param available is 1 if employee \(i\) is available on slot \(j\) else 0.
        *    @param admin \\(admin_i\\) is 1 if employee \(i\) is an admin else 0.
        *    @param canLift is 1 if employee \(i\) can lift heavy tea container else 0.
        *    @param food is 1 if employee \(i\) can make food else 0.
        *    @param drink is 1 if employee \(i\) can make drinks else 0.
        *    @param minHours is the minimum number of hours that employee \(i\) will work
        *    @param maxHours  is the maximum number of hours that employee \(i\) will work.
        *    @param minEmployees is minimum number of employees that can work slot \(j\).
        *    @param maxEmployees is minimum number of employees that can work slot \(j\).
        *    @param time time_j is the time worked if scheduled on time slot \(j\)
        *    @param adminOnly is the time worked if scheduled on time slot \(j\).
        *    @return the schedule
        */
	private ScheduleResult schedule(int[][] available,
			int[] admin, int[] canLift, int[] food, int[] drink, int[] minHours, int[] maxHours,
			int[] minEmployees, int[] maxEmployees, int[] time, int[] adminOnly,
                        int[] days) {

        // assumes the input is rectangular
        final int numberOfEmployees = available.length;
        final int numberOfTimeSlots = available[0].length;
        
        
        Store store = new Store();
        IntVar ONE = new IntVar(store,"ONE",1,1);
        // work matrix
        IntVar[][] work  = new IntVar[numberOfEmployees][numberOfTimeSlots];
        
        // transposed version of the work matrix
        IntVar[][] workT = new IntVar[numberOfTimeSlots][numberOfEmployees];
        
        // work[i][j] is 1 if employee i works time slot j and 0 otherwise.
        for (int i = 0; i < numberOfEmployees; i++)
            for (int j = 0; j < numberOfTimeSlots; j++)
                work[i][j] = workT[j][i] = new IntVar(store, String.format("work_%d,%d",i,j),0,1);

        
        // define problem constraints
        
        // If the employee is not available for the time slot then the employee 
        // cannot work the time slot.
        for (int i = 0; i < numberOfEmployees; i++)
            for (int j = 0; j < numberOfTimeSlots; j++)
                if (available[i][j] == 0)
                    store.impose(new XeqC(work[i][j],0));
        
        // Must have no less than minimum number of employees and no more than
        // maximum number of employees for slot.
        for (int j = 0; j < numberOfTimeSlots; j++)
        {
            store.impose(new SumBool(store,workT[j],">=",
                    new IntVar(store,String.format("min_%d",j),minEmployees[j],minEmployees[j])));
            store.impose(new SumBool(store,workT[j],"<=",
                    new IntVar(store,String.format("max_%d",j),maxEmployees[j],maxEmployees[j])));
        }
        
        // disable since the first shift of each day is admin only
//        // for each time slot must have 1 admin.
        for (int j = 0; j < numberOfTimeSlots; j++)
            store.impose(new LinearInt(store,workT[j],admin,">=", 1));
        
        //for each time slot must have 1 lifter.
        for (int j = 0; j < numberOfTimeSlots; j++)
            store.impose(new LinearInt(store,workT[j],canLift,">=", 1));
        
        
        // There must be at least one viable pair that is working,
        // additionally constrains that there must be at least 1 food maker
        // and at least 1 drink maker.
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
        
        // this runs real slow for some reason
//         Each employee must work between their minimum and maximum hours.
        for (int i = 0; i < numberOfEmployees; i++)
            store.impose(new LinearInt(store,work[i],time,">=", minHours[i]));
        for (int i = 0; i < numberOfEmployees; i++)
            store.impose(new LinearInt(store,work[i],time,"<=", maxHours[i]));    
        
        // If the time slot is admin only then if an employee is not an admin 
        // then they are not available for that shift.
        for (int j = 0; j < numberOfTimeSlots; j++)
            if (adminOnly[j] == 1)
                for (int i = 0; i < numberOfEmployees; i++)
                    if (admin[i] == 0)
                        store.impose(new XeqC(work[i][j],0));
        
        // not sure if we want this but it distributes the hours more evenly
        // can maybe replace it with soft constraints later
        // disabled to see if it makes schedule feasible
        // for every employee they can only work once a day.
        for (int i = 0; i < numberOfEmployees; i++)
            for (int k = 0; k < days.length; k++)
                store.impose(new SumBool(store,
                        Arrays.copyOfRange(work[i],k==0?0:days[k-1],days[k]),
                        "<=",ONE));
        
        // compute oldSchedule
        Search<IntVar> label = new DepthFirstSearch<IntVar>();
        //label.setPrintInfo(false);
        
        SelectChoicePoint<IntVar> select = 
                    new SimpleMatrixSelect<>(work, new IndomainMin<IntVar>());
        
        boolean feasible = label.labeling(store, select);
        if (!feasible) 
            System.err.print("not feasible");
           int[][] result = new int[numberOfEmployees][numberOfTimeSlots];
           
        if (feasible)
        {
            for (int i = 0; i < numberOfEmployees; i++)
                for (int j = 0; j < numberOfTimeSlots; j++)
                    result[i][j] = work[i][j].value();
        }
        return new ScheduleResult(result, feasible);
	}

	@Override
	public WeeklySchedule getSchedule(Date date) {
		return scheduleDao.getSchedule(getStartOfWeek(date));
	}
	
	private Date getStartOfWeek(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.clear(Calendar.MINUTE);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);
		cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
		return cal.getTime();
	}

}
