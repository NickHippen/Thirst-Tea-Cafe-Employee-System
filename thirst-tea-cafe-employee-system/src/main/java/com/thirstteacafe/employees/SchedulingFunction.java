package com.thirstteacafe.employees;

import java.util.ArrayList;
import java.util.Arrays;
import org.jacop.core.*; 
import org.jacop.constraints.*;
import org.jacop.search.*;
/**
 *
 * @author Hayden
 */
public class SchedulingFunction 
{
    public static void main(String[] args)
    {
        int[][] s = schedule(
                    "1 1 1 1 1 1\n" +
                    "1 1 1 1 1 1\n" +
                    "1 1 1 1 1 1\n",
                
                "1 1 1",
                "1 1 1",
                "0 1 0",
                "0 0 1",
                
                "2 1 2 1 1 2",
                "3 3 3 3 3 3",
                "1 1 1 1 1 1"
                
                );
        
        String scheduleMatrix =
                    "1 1 1\n" +
                    "1 1 1\n" +
                    "1 1 1\n";
		s = SchedulingFunction.schedule(
				scheduleMatrix,
                
                "1 1 1",
                "1 1 1",
                "1 1 1",
                "1 1 1",
                        
                "3 3 3",
                "3 3 3",
                "1 1 1");
        for (int[] e : s)
        {
            for (int x : e)
                System.out.printf("%d ", x);
            System.out.println();
        }
    }
    public static int[][] schedule(String available,
            
                                String admin    ,
                                String canLift  ,
                                String food     ,
                                String drink    ,
            
                                String min      ,
                                String max      ,
                                String time     )
            
    {
        return schedule(convertMatrix(available),

                    convert(admin  ), 
                    convert(canLift), 
                    convert(food   ), 
                    convert(drink  ),

                    convert(min    ), 
                    convert(max    ), 
                    convert(time   )
                    );
    }
    
    public static int[][] schedule(int[][] available,
                                     
                                    int[]   admin,
                                    int[]   canLift,
                                    int[]   food,
                                    int[]   drink,
                                    
                                    int[]   min,
                                    int[]   max,
                                    int[]   time)
    {
        int[] hoursMin;
        int[] adminOnly = min;
        
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
        
        // foodDuty_i,j is 1 if employee i is assigned to food duty on time slot j
        IntVar[][] foodDuty = new IntVar[numberOfEmployees][numberOfTimeSlots];
        IntVar[][] foodDutyT = new IntVar[numberOfTimeSlots][numberOfEmployees];
        // drinkDuty_i,j is 1 if employee i is assigned to drink duty on time slot j
        IntVar[][] drinkDuty = new IntVar[numberOfEmployees][numberOfTimeSlots];
        IntVar[][] drinkDutyT = new IntVar[numberOfTimeSlots][numberOfEmployees];
        for (int i = 0; i < numberOfEmployees; i++)
        {
            for (int j = 0; j < numberOfTimeSlots; j++)
            {
                foodDuty[i][j] = foodDutyT[j][i] = new IntVar(store,String.format("food_%d,%d", i,j),0,food[i]);
                drinkDuty[i][j] = drinkDutyT[j][i] = new IntVar(store,String.format("drink_%d,%d", i,j),0,drink[i]);
            }
        }
        // for each employee on each shift they can make food xor make drinks
        
        
        
        for (int i = 0; i < numberOfEmployees; i++)
            for (int j = 0; j < numberOfTimeSlots; j++)
                work[i][j] = workT[j][i] = new IntVar(store, String.format("work_%d,%d",i,j),0,1);
        
        for (int i = 0; i < numberOfEmployees; i++)
        {
            for (int j = 0; j < numberOfTimeSlots; j++)
            {
                // if the employee doesn't work then wether they make food/drink is irrelevant
                store.impose(new XplusYlteqZ(foodDuty[i][j],drinkDuty[i][j],work[i][j]));
            }
        }
        for (int j = 0; j < numberOfTimeSlots; j++)
        {
            store.impose(new SumBool(store,foodDutyT[j],">=",ONE));
            store.impose(new SumBool(store,drinkDutyT[j],">=",ONE));
        }
        
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
                    new IntVar(store,String.format("min_%d",j),min[j],min[j])));
            store.impose(new SumBool(store,workT[j],"<=",
                    new IntVar(store,String.format("max_%d",j),max[j],max[j])));
        }
        
        // for each time slot must have 1 admin
        for (int j = 0; j < numberOfTimeSlots; j++)
            store.impose(new LinearInt(store,workT[j],admin,">=", 1));
        //for each time slot must have 1 lifter
        for (int j = 0; j < numberOfTimeSlots; j++)
            store.impose(new LinearInt(store,workT[j],canLift,">=", 1));
        // for each time slot must have 1 food maker
        
        // not neccessary if the other constraints handle this
//        for (int j = 0; j < numberOfTimeSlots; j++)
//            store.impose(new LinearInt(store,workT[j],food,">=", 1));
//        // for each time slot must have 1 drink maker
//        for (int j = 0; j < numberOfTimeSlots; j++)
//            store.impose(new LinearInt(store,workT[j],drink,">=", 1));
        
        // for each employee they can work no more than 40 hours unless they
        // are an admin
        for (int i = 0; i < numberOfEmployees; i++)
            store.impose(new LinearInt(store,work[i],time,"<=", 40 + admin[i] * C));
        
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
                    new InputOrderSelect<>(store,flatten(work), new IndomainMin<IntVar>());
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
        return result;
    }
    
    // puts all the elements in a 2d array into a 1d array
    public static IntVar[] flatten(IntVar[][] variables)
    {
        // assume input is a rectangular matrix of variables
        IntVar[] flatList = new IntVar[variables.length * variables[0].length];
        for (int i = 0; i < variables.length; i++)
            for (int j = 0; j < variables[0].length; j++)
                flatList[i * variables[0].length + j] = variables[i][j];
        return flatList;
    }
    
    // convert strings that represent 1d arrays into a 1d array of integers
    public static int[] convert(String input)
    {
        return convert(getRow(input));
    }
    public static int[] convert(ArrayList<Integer> list)
    {
        return list.stream().mapToInt(i -> i).toArray();
    }
    
    // convert strings that represent 2d arrays into a 2d array of integers
    public static int[][] convertMatrix(String input)
    {
        return convertMatrix(getMatrix(input));
    }
    public static int[][] convertMatrix(ArrayList<ArrayList<Integer>> list)
    {
        int[][] acc = new int[list.size()][list.get(0).size()];
        for (int i = 0; i < list.size(); i++)
            acc[i] = convert(list.get(i));
        return acc;
    }
    public static ArrayList<Integer> getRow(String input)
    {
        ArrayList<Integer> row = new ArrayList<Integer>();
        for (String token : input.split("\\s+"))
            row.add(Integer.parseInt(token));
        return row;
    }
    public static ArrayList<ArrayList<Integer>> getMatrix(String input)
    {
        ArrayList<ArrayList<Integer>> matrix = new ArrayList<>();
        for (String line : input.split("\n"))
            matrix.add(getRow(line));
        return matrix;
    }
}
