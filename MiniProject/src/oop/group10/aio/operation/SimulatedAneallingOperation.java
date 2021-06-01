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
		int[] cloneTour=tour.clone();
		int size=tour.length;
		int randNum1=random.nextInt(size-1)+1;
		int randNum2=random.nextInt(size-1)+1;
		while(randNum2==randNum1) randNum2=random.nextInt(size-2)+1;
		int temp=cloneTour[randNum1];
		cloneTour[randNum1]=cloneTour[randNum2];
		cloneTour[randNum2]=temp;
		return cloneTour;
	}
	//Get the loss between current solution and neighbor solution
	public float getLoss(int[] current,int[] neighbor) {
		float currentSolution=problem.evaluate(current);
		float neighborSolution=problem.evaluate(neighbor);
		float loss=currentSolution-neighborSolution;
		return loss;
	}
	//Probability to accept the neighbor solution
	public float calculateProbabity(float loss,float temperature) {
		float probability=(float) Math.exp(loss/temperature);
		System.out.println("Accept probability: "+probability);
		return probability;
	}
	//Accept the solution of neighbor or not
	public boolean isAceptableState(float probabilty) {
		float randProb=nextFloat(0.0f, 1.0f);
		if(randProb>probabilty) return false;
		return true;
	}
	
	
}
