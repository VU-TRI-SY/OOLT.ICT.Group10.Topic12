package oop.group10.aio.objects;

import oop.group10.aio.operation.SimulatedAneallingOperation;
import oop.group10.aio.optimization.OptimizationForTSP;

public class State extends Individual {
	
	//Store neighbor tour solution
	private int[] neighborTour;
	private float neighborTourLength;
	//Operation of the state
	private SimulatedAneallingOperation operation;
	
	public State(OptimizationForTSP optimization,SimulatedAneallingOperation operation) {
		super(optimization);
		// TODO Auto-generated constructor stub
		this.operation=operation;
	}
	//Remember to initialize all the data for example neighborTour or neighborTourLength
	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void constructSolution() {
		// TODO Auto-generated method stub
	}
	
	private void getNeighborState() {
		
	}
	
	private void changeState() {
		
	}
	

}
