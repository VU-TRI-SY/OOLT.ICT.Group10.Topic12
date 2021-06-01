package oop.group10.aio.optimization.sao;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import oop.group10.aio.application.Controller;
import oop.group10.aio.objects.State;
import oop.group10.aio.operation.SimulatedAneallingOperation;
import oop.group10.aio.optimization.OptimizationForTSP;
import oop.group10.aio.tsp.TravelingSalesmanProblem;

public class SimulatedAneallingOptimization extends OptimizationForTSP {
	//First temperature
	private int startTemperature;
	//Current temperature
	private float currentTemperature;
	//State of this optimization
	private State state;
	//Operation of the optimization
	private SimulatedAneallingOperation operation;
	
	
	public SimulatedAneallingOptimization(TravelingSalesmanProblem problem, Controller controller) {
		super(problem, controller);
		// TODO Auto-generated constructor stub
		operation=new SimulatedAneallingOperation(problem);
		startTemperature=500;
	}	
	
	//Initialize optimization
	
	private void init() {
		currentTemperature=startTemperature;
		currentIteration=0;
		state=new State(this, operation);
		state.init();
		globalBest=state.cloneTour();
		globalBestValue=state.getTourLength();
		onActive=true;
	}
	
	@Override
	public void solve() {
		// TODO Auto-generated method stub
		init();
		while(!terminatedCondition()) {
			state.constructSolution();
			printSolution();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
			}
			render();
			updateDataAtEndLoop();
		}
		stop();
	}
	@Override
	public boolean terminatedCondition() {
		// TODO Auto-generated method stub
		return currentTemperature<0.1||onActive==false;
	}
	//Update temperature each loop and iteration++
	private void updateDataAtEndLoop() {
		currentIteration++;
		currentTemperature*=0.99;
		if(currentTemperature<0.2) currentTemperature=200;
	}
	
	
	//Update global best each loop for instance of Optimization
	//This will be call in side State instance
	//Since there are only one state so variable i is meaningless
	@Override
	public void updateGlobalBest(int i) {
		// TODO Auto-generated method stub
		if(globalBestValue>state.getTourLength()) {
			globalBest=state.cloneTour();
			globalBestValue=state.getTourLength();
		}
	}
	private void stop() {
		controller.stopProgressBarMotion();
	}
	
	private void printSolution() {
		// TODO Auto-generated method stub
		System.out.println("Current solution: "+state.getTourLength());
		System.out.println("Current temperature: "+currentTemperature);
	}
	
	public void render() {
		controller.changeProgress();
		renderGraphics(controller.getCanvas());
	}
	
	//Render the canvas (In VIEW)
	@Override
	public synchronized void renderGraphics(Canvas canvas) {
		// TODO Auto-generated method stub
		float[][] map=getProblem().getXoyMap();
		int[] currentTour=state.getTour();
		Platform.runLater(()->{
			GraphicsContext graphicsContext=canvas.getGraphicsContext2D();
			graphicsContext.setFill(Color.WHITE);
			graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
			graphicsContext.setStroke(Color.BLUE);
			int i;
			for(i=0;i<problem.getNumberOfCities()-1;i++) {
				graphicsContext.strokeLine(map[0][currentTour[i]], map[1][currentTour[i]], map[0][currentTour[i+1]], map[1][currentTour[i+1]]);
			}
			graphicsContext.strokeLine(map[0][currentTour[i]], map[1][currentTour[i]],map[0][currentTour[0]] , map[1][currentTour[0]]);
			graphicsContext.closePath();
			controller.setCurrentSolution(state.getTourLength());
		});
		
	}

	@Override
	public void setOptimizationData(ArrayList<Float> listOfData) {
		// TODO Auto-generated method stub
		
	}
	public float getCurrentTemperature() {
		return currentTemperature;
	}
}
