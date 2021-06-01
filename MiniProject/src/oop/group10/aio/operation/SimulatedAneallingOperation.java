package oop.group10.aio.operation;

import java.util.ArrayList;

import oop.group10.aio.tsp.TravelingSalesmanProblem;

public class SimulatedAneallingOperation extends AlgorithmsOperation {

	public SimulatedAneallingOperation(TravelingSalesmanProblem problem) {
		super(problem);
		// TODO Auto-generated constructor stub
	}
	
	//Use particle swarm operation code because of equivalence
	
	
	//Initialize state position
	public int[] initializeState() {
		int numCities=problem.getNumberOfCities();
		ArrayList<Integer> listNodes=new ArrayList<Integer>();
		int i;
		for(i=0;i<numCities;i++) listNodes.add(i);
		int[] list=new int[numCities];
		list[0]=0;
		listNodes.remove(0);
		i=1;
		while(!listNodes.isEmpty()) {
			int randNum=random.nextInt(listNodes.size());
			list[i]=listNodes.get(randNum);
			listNodes.remove(randNum);
			i++;
		}
 		return list;
	}
	//Get the neighbor of the current state
	public int[] getRandNeighborState(int[] tour) {
		int size=tour.length;
		int randNum=random.nextInt(size-3)+1;
		int temp=tour[randNum];
		tour[randNum]=tour[randNum+1];
		tour[randNum+1]=temp;
		return tour;
	}
	//Get the loss between current solution and neighbor solution
	public float getLoss(int[] current,int[] neighbor) {
		float currentSolution=problem.evaluate(current);
		float neighborSolution=problem.evaluate(neighbor);
		return currentSolution-neighborSolution;
	}
	//Probability to accept the neighbor solution
	public float calculateProbabity(float loss,float temperature) {
		float probability=(float) Math.exp(-loss/temperature);
		return probability;
	}
	//Accept the solution of neighbor or not
	public boolean isAceptableState(float probabilty) {
		float randProb=nextFloat(0.0f, 1.0f);
		if(randProb>probabilty) return false;
		return true;
	}
	
	
}
