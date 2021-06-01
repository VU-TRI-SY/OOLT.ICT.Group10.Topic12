package oop.group10.aio.objects;

import oop.group10.aio.operation.AntColonyOperation;
import oop.group10.aio.optimization.OptimizationForTSP;

public class Ant extends Individual {
	//Id of the ant
	private int id;
	//Operation for the ant
	private AntColonyOperation operation;
	
	public Ant(int id,OptimizationForTSP optimization,AntColonyOperation operation) {
		super(optimization);
		// TODO Auto-generated constructor stub
		this.id=id;
		this.operation=operation;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void constructSolution() {
		// TODO Auto-generated method stub
		
	}
	//Construct a tour use operation
	private void constructTour() {
		
	}
	
}
