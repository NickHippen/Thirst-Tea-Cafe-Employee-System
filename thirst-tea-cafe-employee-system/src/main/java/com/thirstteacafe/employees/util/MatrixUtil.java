package com.thirstteacafe.employees.util;

import java.util.ArrayList;

import org.jacop.core.IntVar;
import org.springframework.stereotype.Component;

@Component
public class MatrixUtil {

	// puts all the elements in a 2d array into a 1d array
	public IntVar[] flatten(IntVar[][] variables) {
		// assume input is a rectangular matrix of variables
		IntVar[] flatList = new IntVar[variables.length * variables[0].length];
		for (int i = 0; i < variables.length; i++)
			for (int j = 0; j < variables[i].length; j++)
				flatList[i * variables[0].length + j] = variables[i][j];
		return flatList;
	}

	// convert strings that represent 1d arrays into a 1d array of integers
	public int[] convert(String input) {
		return convert(getRow(input));
	}

	public int[] convert(ArrayList<Integer> list) {
		return list.stream().mapToInt(i -> i).toArray();
	}

	// convert strings that represent 2d arrays into a 2d array of integers
	public int[][] convertMatrix(String input) {
		return convertMatrix(getMatrix(input));
	}

	public int[][] convertMatrix(ArrayList<ArrayList<Integer>> list) {
		int[][] acc = new int[list.size()][list.get(0).size()];
		for (int i = 0; i < list.size(); i++)
			acc[i] = convert(list.get(i));
		return acc;
	}

	public ArrayList<Integer> getRow(String input) {
		ArrayList<Integer> row = new ArrayList<Integer>();
		for (String token : input.split("\\s+"))
			row.add(Integer.parseInt(token));
		return row;
	}

	public ArrayList<ArrayList<Integer>> getMatrix(String input) {
		ArrayList<ArrayList<Integer>> matrix = new ArrayList<>();
		for (String line : input.split("\n"))
			matrix.add(getRow(line));
		return matrix;
	}
	
}
