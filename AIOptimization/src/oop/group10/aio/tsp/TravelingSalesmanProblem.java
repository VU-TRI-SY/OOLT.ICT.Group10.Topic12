package oop.group10.aio.tsp;


import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class TravelingSalesmanProblem {
	private final String path="map.txt";
	private float[][] map;
	private int numberOfCities;
	
	public TravelingSalesmanProblem() {
		// TODO Auto-generated constructor stub
		inputStatistics();
	}
	public float evaluate(int[] solution) {
		int i;
		float count=0.0f;
		for(i=0;i<solution.length-1;i++) {
			count+=map[solution[i]][solution[i+1]];
		}
		count+=map[solution[i]][solution[0]];
		return count;
	}
	public void inputStatistics() {
		ClassLoader loader=Thread.currentThread().getContextClassLoader();
		InputStream fi=loader.getResourceAsStream(path);
		Scanner sc= new Scanner(fi);
		numberOfCities=sc.nextInt();
		map=new float[numberOfCities][numberOfCities];
		int i,j;
		for(i=0;i<numberOfCities;i++) {
			for(j=0;j<numberOfCities;j++) {
				map[i][j]=sc.nextFloat();
			}
		}
		try {
			fi.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sc.close();
	}
	public void showStatistics() {
		// TODO Auto-generated method stub
		int i,j;
		for(i=0;i<numberOfCities;i++) {
			for(j=0;j<numberOfCities;j++) {
				System.out.print(map[i][j]+" ");
			}
			System.out.print("\n");
		}
	}
	
	public int getNumberOfCities() {
		return numberOfCities;
	}
}
