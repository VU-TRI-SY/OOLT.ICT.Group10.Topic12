package oop.group10.aio.objects;

import oop.group10.aio.operation.AntColonyOperation;
import oop.group10.aio.optimization.OptimizationForTSP;
<<<<<<< HEAD
import oop.group10.aio.optimization.aco.AntColonyOptimization;

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
		AntColonyOptimization o = (AntColonyOptimization) optimization;
		tour = operation.getNewTour(o.getAlpha(),o.getBeta());
		tourLength = optimization.getProblem().evaluate(tour);
	}
	@Override
	public String toString() {
		StringBuilder stringBuilder=new StringBuilder();
		stringBuilder.append(id).append(". {");
		int i;
		for(i=0;i<tour.length;i++) stringBuilder.append(tour[i]).append(",");
		stringBuilder.append("} Value: ").append(tourLength);
		String str=stringBuilder.toString();
		return str;
	}
	
}
=======

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
>>>>>>> branch 'master' of https://github.com/VU-TRI-SY/OOLT.ICT.Group10.Topic12.git
