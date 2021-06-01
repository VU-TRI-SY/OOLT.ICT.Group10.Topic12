package oop.group10.aio.objects;

import oop.group10.aio.operation.SimulatedAneallingOperation;
import oop.group10.aio.optimization.OptimizationForTSP;
import oop.group10.aio.optimization.sao.SimulatedAneallingOptimization;

public class State extends Individual {
	
	//Store neighbor tour solution
	private int[] neighborTour;
	
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
		tour=operation.initializeState();
		SimulatedAneallingOptimization o=(SimulatedAneallingOptimization) optimization;
		tourLength=optimization.getProblem().evaluate(tour);
	}

	@Override
	public void constructSolution() {
		// TODO Auto-generated method stub
		getNeighborState();
		changeState();
		optimization.updateGlobalBest(0);
	}
	//Get the neighbor state
	private void getNeighborState() {
		neighborTour=operation.getRandNeighborState(tour);
		
	}
	//Change the state to neighbor or not
	private void changeState() {
		SimulatedAneallingOptimization o=(SimulatedAneallingOptimization) optimization;
		float temperature=o.getCurrentTemperature();
		float loss=operation.getLoss(tour,neighborTour);
		if(loss>0) tour=neighborTour;
		else {
			if(operation.isAceptableState(operation.calculateProbabity(loss, temperature))) tour=neighborTour;
		}
		tourLength=o.getProblem().evaluate(tour);
	}
}
