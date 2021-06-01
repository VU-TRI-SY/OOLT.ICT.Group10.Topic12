package oop.group10.aio.operation;

import oop.group10.aio.tsp.TravelingSalesmanProblem;

public class SimulatedAneallingOperation extends AlgorithmsOperation {

	public SimulatedAneallingOperation(TravelingSalesmanProblem problem) {
		super(problem);
		// TODO Auto-generated constructor stub
	}
	
	//Use particle swarm operation code because of equivalence
	
	
	//Initialize state position
	public int[] initializeState() {
		
		return null;
	}
	//Get the neighbor of the current state
	public int[] getRandNeighborState() {
		return null;
	}
	//Get the loss between current solution and neighbor solution
	public float getLoss() {
		return 0;
	}
	//Probability to accept the neighbor solution
	public float calculateProbabity() {
		return 0;
	}
	//Accept the solution of neighbor or not
	public boolean isAceptableState() {
		
		return false;
	}
	
	
}
