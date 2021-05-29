package oop.group10.aio.optimization;


import javafx.scene.canvas.Canvas;
import oop.group10.aio.tsp.TravelingSalesmanProblem;

public abstract class OptimizationForTSP {
	protected int[] globalBest;
	protected float globalBestValue;
	protected int numberOfIteration;
	protected int currentIteration;
	protected TravelingSalesmanProblem problem;
	
	public OptimizationForTSP(TravelingSalesmanProblem problem) {
		// TODO Auto-generated constructor stub
		globalBest=null;
		currentIteration=0;
		numberOfIteration=100000;
		this.problem=problem;
	}
	//Terminated condition
	public boolean terminatedCondition() {
		return numberOfIteration==currentIteration;
	}
	public abstract void updateGlobalBest(int i);
	public abstract void solve();
	public abstract void renderGraphics(Canvas canvas);
	public TravelingSalesmanProblem getProblem() {
		return problem;
	}
	public int[] getGlobalBest() {
		return globalBest;
	}
	public float getGlobalBestValue() {
		return globalBestValue;
	}
}
