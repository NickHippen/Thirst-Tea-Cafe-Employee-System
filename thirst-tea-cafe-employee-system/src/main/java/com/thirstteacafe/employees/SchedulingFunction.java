package com.thirstteacafe.employees;

import java.util.ArrayList;
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
                    "1 0 1\n" +
                    "0 1 1\n" +
                    "1 0 1\n",
                "2 1 3",
                "3 3 3",
                "1 1 1",
                "1 1 1",
                "1 1 1",
                "1 1 1",
                "1 1 1"
                );
        for (int[] e : s)
        {
            for (int x : e)
                System.out.printf("%d ", x);
            System.out.println();
        }
    }
    public static int[][] schedule(
                                String available,
                                String min      ,
                                String max      ,
                                String time     ,
                                String admin    ,
                                String canLift  ,
                                String food     ,
                                String drink    )
    {
        return schedule(convertMatrix(available),
        
        convert(min      ),        
        convert(max      ),        
        convert(time     ),        
        convert(admin    ),        
        convert(canLift  ),        
        convert(food     ),        
        convert(drink    )        
                
                
        );
    }
    
    public static int[][] schedule(int[][] available,
                                    int[]   min,
                                    int[]   max,
                                    int[]   time,
                                    int[]   admin,
                                    int[]   canLift,
                                    int[]   food,
                                    int[]   drink)
    {
        // assumes the input is rectangular
        final int numberOfEmployees = available.length;
        final int numberOfTimeSlots = available[0].length;
        final int C = 128; // sufficiently large constants for hour constraint
        
        Store store = new Store();
        
     
        // work matrix
        IntVar[][] work = new IntVar[numberOfEmployees][numberOfTimeSlots];
        
        // transposed version of the work matrix
        IntVar[][] workT = new IntVar[numberOfTimeSlots][numberOfEmployees];
        
        for (int i = 0; i < numberOfEmployees; i++)
            for (int j = 0; j < numberOfEmployees; j++)
            {
                work[i][j] = workT[j][i]= new IntVar(store, String.format("work_%d,%d",i,j),0,1);
                 
            }
        
        
        // define problem constraints
        
        // If the employee is not available for the time slot then the employee 
        // cannot work the time slot
        for (int i = 0; i < numberOfEmployees; i++)
            for (int j = 0; j < numberOfTimeSlots; j++)
                if (available[i][j] == 0)
                    store.impose(new XeqC(work[i][j],0));
        
        // Must have no less than minimum number of employees and no more than
        // maximum number of employees' for slot.
        for (int j = 0; j < numberOfEmployees; j++)
        {
            // is there a way to use constants here instead of constant intVars?
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
        for (int j = 0; j < numberOfTimeSlots; j++)
            store.impose(new LinearInt(store,workT[j],food,">=", 1));
        // for each time slot must have 1 drink maker
        for (int j = 0; j < numberOfTimeSlots; j++)
            store.impose(new LinearInt(store,workT[j],drink,">=", 1));
        
        // for each employee they can work no more than 40 hours unless they
        // are an admin
        for (int i = 0; i < numberOfEmployees; i++)
            store.impose(new LinearInt(store,work[i],time,"<=", 40 + admin[i] * C));
        
        
        // compute oldSchedule
        Search<IntVar> label = new DepthFirstSearch<IntVar>();
        label.setPrintInfo(false);
        
        SelectChoicePoint<IntVar> select = 
                    new InputOrderSelect<>(store,flatten(work), new IndomainMin<IntVar>());
        // TODO: actually do something with this feasbility value
        boolean feasible = label.labeling(store, select);
        
        int[][] result = new int[numberOfEmployees][numberOfTimeSlots];
        for (int i = 0; i < numberOfEmployees; i++)
            for (int j = 0; j < numberOfTimeSlots; j++)
                result[i][j] = work[i][j].value();
        return result;
    }
    
    public static IntVar[] flatten(IntVar[][] variables)
    {
        // assume input is a rectangular matrix of variables
        IntVar[] flatList = new IntVar[variables.length * variables[0].length];
        for (int i = 0; i < variables.length; i++)
            for (int j = 0; j < variables.length; j++)
                flatList[i * variables[0].length + j] = variables[i][j];
        return flatList;
    }
    public static int[] convert(String input)
    {
        return convert(getRow(input));
    }
    public static int[] convert(ArrayList<Integer> list)
    {
        // since java is weird we:
        // convert the list to a Stream
        // the stream to a IntStream with a lambda to unbox the Integer Objects
        // IntStream has a toArray function that converts it to a int[]
        return list.stream().mapToInt(i -> i).toArray();
    }
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
