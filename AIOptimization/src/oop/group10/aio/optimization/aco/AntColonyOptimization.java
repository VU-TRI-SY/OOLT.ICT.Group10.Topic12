package oop.group10.aio.optimization.aco;

import java.util.ArrayList;

import javafx.scene.canvas.Canvas;
import oop.group10.aio.application.Controller;
import oop.group10.aio.objects.Ant;
import oop.group10.aio.operation.AntColonyOperation;
import oop.group10.aio.optimization.OptimizationForTSP;
import oop.group10.aio.tsp.TravelingSalesmanProblem;

public class AntColonyOptimization extends OptimizationForTSP {
	//Number of Ants 
	private int numberOfAnts;
	//Importance of pheromonesvalue
	private float alpha;
	//Importance of heuristics information(length of the tour)
	private float beta;
	//Evaporation rate
	private float rho;
	//Operation of optimization
	private AntColonyOperation operation;
	//Pheromones map
	private PheromonesGraph graph;
	//List of ants
	private ArrayList<Ant> ants;
	
	
	public AntColonyOptimization(TravelingSalesmanProblem problem, Controller controller) {
		super(problem, controller);
		// TODO Auto-generated constructor stub
	}
	//Initialize optimization
	
	
	private void init() {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void solve() {
		// TODO Auto-generated method stub
		init();
		while(!terminatedCondition()) {
			constructSolution();
			updatePheromonesMap();
			printSolution();
			//Optional slow down the process
			render();
			updateDataAtEndLoop();
		}
		stop();
	}
	//Each ant construct solution
	private void constructSolution() {
		
	}
	//Update pheromones map
	private void updatePheromonesMap() {
		
	}
	//Update data at end loop
	private void updateDataAtEndLoop() {
		// TODO Auto-generated method stub

	}
	//Print solution
	private void printSolution() {
		// TODO Auto-generated method stub

	}
	
	//Update global best (Will be call inside Ant instance) with i is id of the ant
	@Override
	public void updateGlobalBest(int i) {
		// TODO Auto-generated method stub
		
	}
	private void render() {
		
	}
	private void stop() {
		
	}
	//Render graphics for VIEW
	@Override
	public void renderGraphics(Canvas canvas) {
		// TODO Auto-generated method stub
		
	}

}
