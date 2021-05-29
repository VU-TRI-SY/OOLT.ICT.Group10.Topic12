package oop.group10.aio.operation;

import java.util.Random;

public abstract class AlgorithmsOperation {
	protected Random random;
	protected float nextFloat(float lowerbound,float upperbound) {
		return lowerbound+ random.nextFloat() *(upperbound-lowerbound);
	}
	
	public AlgorithmsOperation() {
		// TODO Auto-generated constructor stub
		random=new Random();
	}
}
