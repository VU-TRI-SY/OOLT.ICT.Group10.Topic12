package oop.group10.aio.tsp;


import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;


public class TravelingSalesmanProblem {
	private final String path="map1.txt";
	private float[][] map;
	private int numberOfCities;
	private float[][] xoyMap;
	public TravelingSalesmanProblem() {
		// TODO Auto-generated constructor stub
		inputStatistics();
		loadMap();
		resizeStatistics();
		System.out.println("Read complete!");
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
	public void loadMap() {
		map=new float[numberOfCities][numberOfCities];
		int i,j;
		for(i=0;i<numberOfCities;i++) {
			for(j=0;j<numberOfCities;j++) {
				if(i==j) {
					map[i][j]=0;
				}else {
					float a2=(float) Math.pow((xoyMap[0][i]-xoyMap[0][j]),2);
					float b2=(float) Math.pow((xoyMap[1][i]-xoyMap[1][j]), 2);
					map[i][j]= (float) Math.sqrt(a2+b2);
				}
			}
		}
	}
	public void resizeStatistics() {
		int i;
		for(i=0;i<numberOfCities;i++) {
//			xoyMap[0][i]=xoyMap[0][i]/14;
//			xoyMap[1][i]=xoyMap[1][i]/14+50;
			xoyMap[0][i]=xoyMap[0][i]*3+400;
			xoyMap[1][i]=xoyMap[1][i]*3+200;
		}
	}
	public void inputStatistics() {
		ClassLoader loader=Thread.currentThread().getContextClassLoader();
		InputStream fi=loader.getResourceAsStream(path);
		Scanner sc= new Scanner(fi);
		numberOfCities=sc.nextInt();
		xoyMap=new float[2][numberOfCities];
		int i;
		for(i=0;i<numberOfCities;i++) {
			xoyMap[0][i]=sc.nextFloat();
			xoyMap[1][i]=sc.nextFloat();
		}
		sc.close();
		try {
			fi.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	public float[][] getXoyMap() {
		return xoyMap;
	}
	public float getDistance(int i,int j) {
		return map[i][j];
	}
}