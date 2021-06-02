package oop.group10.aio.optimization;


import java.util.ArrayList;

import javafx.scene.canvas.Canvas;
import oop.group10.aio.application.Controller;
import oop.group10.aio.tsp.TravelingSalesmanProblem;

public abstract class OptimizationForTSP implements Runnable {
	protected int[] globalBest;
	protected float globalBestValue;
	protected int numberOfIteration;
	protected final int maxThreadDelay=500;
	protected int threadDelay;
	protected boolean onActive;
	protected final int maxReadySolution=100;
	protected int readySolution;
	protected int currentIteration;
	protected TravelingSalesmanProblem problem;
	protected Controller controller;
	public OptimizationForTSP(TravelingSalesmanProblem problem,Controller controller) {
		// TODO Auto-generated constructor stub
		threadDelay=500;
		globalBest=null;
		numberOfIteration=100;
		this.problem=problem;
		this.controller=controller;
		onActive=false;
		readySolution=0;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		solve();
	}
	//Terminated condition
	public boolean terminatedCondition() {
		return (numberOfIteration==currentIteration)||(onActive==false)||(readySolution==maxReadySolution);
	}
	public abstract void updateGlobalBest(int i);
	public abstract void solve();
	public abstract void renderGraphics(Canvas canvas);
	public abstract void setOptimizationData(ArrayList<Float> listOfData);
	public TravelingSalesmanProblem getProblem() {
		return problem;
	}
	public int[] getGlobalBest() {
		return globalBest;
	}
	public float getGlobalBestValue() {
		return globalBestValue;
	}
	public void stopSolving() {
		this.onActive=false;
	}
	public int getCurrentIteration() {
		return currentIteration;
	}
	public int getMaximumIteration() {
		return numberOfIteration;
	}
	public void setSpeed(int speed) {
		threadDelay=maxThreadDelay/speed;
	}
}
