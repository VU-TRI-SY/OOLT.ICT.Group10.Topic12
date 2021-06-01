package oop.group10.aio.optimization.sao;

import javafx.scene.canvas.Canvas;
import oop.group10.aio.application.Controller;
import oop.group10.aio.objects.State;
import oop.group10.aio.operation.SimulatedAneallingOperation;
import oop.group10.aio.optimization.OptimizationForTSP;
import oop.group10.aio.tsp.TravelingSalesmanProblem;

public class SimulatedAneallingOptimization extends OptimizationForTSP {
	//First temperature
	private int startTemperature;
	//Current temperature
	private int currentTemperature;
	//State of this optimization
	private State state;
	//Operation of the optimization
	private SimulatedAneallingOperation operation;
	
	
	public SimulatedAneallingOptimization(TravelingSalesmanProblem problem, Controller controller) {
		super(problem, controller);
		// TODO Auto-generated constructor stub
	}	
	
	//Initialize optimization
	
	private void init() {
		
	}
	
	@Override
	public void solve() {
		// TODO Auto-generated method stub
		init();
		while(!terminatedCondition()) {
			state.constructSolution();
			printSolution();
			updateDataAtEndLoop();
		}
	}
	
	//Update temperature each loop and iteration++
	private void updateDataAtEndLoop() {
		
	}
	
	
	//Update global best each loop for instance of Optimization
	//This will be call in side State instance
	//Since there are only one state so variable i is meaningless
	@Override
	public void updateGlobalBest(int i) {
		// TODO Auto-generated method stub
		
	}
	
	private void printSolution() {
		// TODO Auto-generated method stub

	}
	//Render the canvas (In VIEW)
	@Override
	public void renderGraphics(Canvas canvas) {
		// TODO Auto-generated method stub
		
	}

}
