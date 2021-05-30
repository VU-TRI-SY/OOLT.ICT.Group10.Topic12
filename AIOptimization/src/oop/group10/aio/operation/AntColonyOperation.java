package oop.group10.aio.operation;

import java.util.ArrayList;

import oop.group10.aio.optimization.aco.PheromonesGraph;
import oop.group10.aio.tsp.TravelingSalesmanProblem;

public class AntColonyOperation extends AlgorithmsOperation {
	private PheromonesGraph graph;
	public AntColonyOperation(TravelingSalesmanProblem problem,PheromonesGraph graph) {
		super(problem);
		// TODO Auto-generated constructor stub
		this.graph=graph;
	}
	
	//Get the value of deposit value of ant on each road
	private float getDepositVlue(int[] tour) {
		return 0;
	}
	//Deposit pheromones on road map
	public void depositOnGraph() {
		
	}
	//Get new tour for ant
	public int[] getNewTour(float alpha,float beta) {
		int numCities=problem.getNumberOfCities();
		ArrayList<Integer> listNodes=new ArrayList<Integer>();
		int i;
		for(i=0;i<numCities;i++) listNodes.add(i);
		int[] list=new int[numCities];
		//Repeat process ... to get the tour
		
		return null;
	}
	
	//Probability for the road from city i to j
	private float getChooseProbabilty(int i,int j) {
		return 0;
	}
	private int nextCityToGo(ArrayList<Float> probabilty) {
		
		return 0;
	}
	
	
	
}
