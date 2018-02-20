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
        // determined that maxPerSlot is entirely useless
        ArrayList<ArrayList<Integer>> s = schedule(
                                                    "1 0 1\n" +
                                                    "0 1 1\n" +
                                                    "1 0 1\n",
                                                        "2 1 3",
                                                        "3 3 3"
                                            );
        for (ArrayList<Integer> row : s)
        {
            for (Integer element : row)
            {
                System.out.printf("%d ", element);
            }
            System.out.println();
        }
    }
    
    public static ArrayList<ArrayList<Integer>> schedule(String available, 
                                                         String minPerSlot, 
                                                         String maxPerSlot)
    {
        return schedule(getMatrix(available), getRow(minPerSlot), getRow(maxPerSlot));
    }
    
    // takes a 2d matrix where each value is 1 if person i is available for
    // time slot j,
    // the minimum and maximum number of people required for shift j
    public static ArrayList<ArrayList<Integer>> schedule(
                                    ArrayList<ArrayList<Integer>> available, 
                                    ArrayList<Integer> minPerSlot, 
                                    ArrayList<Integer> maxPerSlot)
    {
        // will hold the resulting scheduling at the end
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        
        // store for all variables and constraints
        Store store = new Store();
        
        final int numberOfEmployees = available.size();
        final int numberOfTimeSlots = available.get(0).size();

        // a schedule simply indicates when person i works on time slot j
        BooleanVar[][] schedule = new BooleanVar[numberOfEmployees]
                                                [numberOfTimeSlots];
        for (int i = 0; i < numberOfEmployees; i++)
        {
            for (int j = 0; j < numberOfTimeSlots; j++)
            {   
                schedule[i][j] = 
                        new BooleanVar(store, String.format("P_%d,%d", i,j));
            }
        }
        
        // impose that employees cannot work when they are not available
        for (int i = 0; i < numberOfEmployees; i++)
        {
            for (int j = 0 ; j < numberOfTimeSlots; j++)
            {
                if (available.get(i).get(j) == 0)
                {
                    store.impose(new XeqC(schedule[i][j],0));
                }
            }
        }
        
        // s_j tracks how many employees are working shift j
        for (int j = 0; j < numberOfTimeSlots; j++)
        {
            BooleanVar[] f = new BooleanVar[numberOfEmployees];
            for (int i = 0; i < numberOfEmployees; i++)
                f[i] = schedule[i][j];
            // the sum of everyone working shift j is how many people on shift j
            store.impose(new SumBool(store,
                    f,
                    "==",
                    new IntVar(store,String.format("S_%d",j),
                               minPerSlot.get(j),maxPerSlot.get(j))));
        }
        
        // TODO: restructure this so it makes sense
        
        // looks like all variables that are to be optimized must be passed
        // in as the second parameter to select choice point
        IntVar[] f = new IntVar[numberOfEmployees * 2 * numberOfTimeSlots];
        for (int i = 0; i < numberOfEmployees; i++)
        {
            for (int j = 0; j < numberOfTimeSlots; j++)
            {
                f[i * numberOfEmployees + j] = schedule[i][j];
            }
        }
        for (int j = 0; j < numberOfEmployees; j++)
        {
            
            {
                f[numberOfEmployees * numberOfTimeSlots + j] = 
                        (IntVar)store.variablesHashMap.get(String.format("S_%d",j));
            }
        }
        System.out.println(store);// TODO: remove
        Search<IntVar> label = new DepthFirstSearch<IntVar>();
        
        // stop search from printing to standard out
        label.setPrintInfo(false);
        SelectChoicePoint<IntVar> select = 
                    new InputOrderSelect<>(store,f, new IndomainMin<IntVar>());
        
        // computes the schedule
        label.labeling(store, select);
        System.out.println(store);// TODO: remove
        
        
        for (int i = 0; i < numberOfEmployees; i++)
        {
            result.add(new ArrayList<>());
            for (int j = 0; j < numberOfTimeSlots; j++)
            {
                result.get(i).add(schedule[i][j].value());
            }
        }
        
        return result;
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
