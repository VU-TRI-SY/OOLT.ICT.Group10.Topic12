package oop.group10.aio.operation;

import java.util.Random;

import oop.group10.aio.tsp.TravelingSalesmanProblem;

public abstract class AlgorithmsOperation {
	protected Random random;
	protected TravelingSalesmanProblem problem;
	protected float nextFloat(float lowerbound,float upperbound) {
		return lowerbound+ random.nextFloat() *(upperbound-lowerbound);
	}
	
	public AlgorithmsOperation(TravelingSalesmanProblem problem) {
		// TODO Auto-generated constructor stub
		random=new Random();
		this.problem=problem;
	}
}
