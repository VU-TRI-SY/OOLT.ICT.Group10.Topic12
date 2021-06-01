package oop.group10.aio.objects;

import oop.group10.aio.optimization.OptimizationForTSP;

public abstract class Individual {
	protected int[] tour;
	protected float tourLength;
	protected OptimizationForTSP optimization;
	public Individual(OptimizationForTSP optimization) {
		// TODO Auto-generated constructor stub
		this.optimization=optimization;
	}
	
	public int[] getTour() {
		return tour;
	}

	public float getTourLength() {
		return tourLength;
	}

	public abstract void init();
	
	public abstract void constructSolution();
	
	public int[] cloneTour() {
		int[] clone=new int[tour.length];
		int i;
		for(i=0;i<clone.length;i++) clone[i]=tour[i];
		return clone;
	}
	public OptimizationForTSP getOptimization() {
		return optimization;
	}
}
