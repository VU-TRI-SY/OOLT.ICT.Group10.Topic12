package oop.group10.aio.optimization.aco;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
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
		numberOfAnts=30;
		alpha=0.1f;
		beta=0.9f;
		rho=0.4f;
		graph= new PheromonesGraph(numberOfAnts);
		operation = new AntColonyOperation (problem,graph);
	}
	//Initialize optimization
	
	private void init() {
		// TODO Auto-generated method stub
		currentIteration = 0;
		ants = new ArrayList<Ant>();
		for(int i=0;i<numberOfAnts;i++) {
			Ant ant = new Ant(i,this,operation);
			ants.add(ant);
		}
		globalBest = operation.getNewTour(alpha, beta);
		globalBestValue = problem.evaluate(globalBest);
		onActive = true;
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
			try {
				Thread.sleep(threadDelay);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			render();
			updateDataAtEndLoop();
		}
		stop();
	}
	//Each ant construct solution
	private void constructSolution() {
		for(Ant ant:ants) ant.constructSolution();
	}
	//Update pheromones map
	private void updatePheromonesMap() {
		graph.evaporation(rho);
		for(Ant ant:ants) {
			
			operation.depositOnGraph(ant.getTour());
		}
	}
	//Update data at end loop
	private void updateDataAtEndLoop() {
		// TODO Auto-generated method stub
		updatePheromonesMap();
		currentIteration++;
	}
	//Print solution
	private void printSolution() {
		// TODO Auto-generated method stub
		System.out.println("Global best: "+globalBestValue);
		System.out.println("Ready: "+readySolution);
	}
	
	//Update global best (Will be call inside Ant instance) with i is id of the ant
	@Override
	public void updateGlobalBest(int i) {
		// TODO Auto-generated method stub
		if(globalBestValue > ants.get(i).getTourLength()) {
			globalBest=ants.get(i).cloneTour();
			globalBestValue=getProblem().evaluate(globalBest);
			controller.setGlobalBest();
			readySolution=0;
		}else {
			if(i==numberOfAnts-1) readySolution++;
		}
	}
	private void render() {
		renderGraphics(controller.getCanvas());
	}
	private void stop() {
		
	}
	//Render graphics for VIEW
	@Override
	public void renderGraphics(Canvas canvas) {
		// TODO Auto-generated method stub
		float[][] map=getProblem().getXoyMap();
		Platform.runLater(()->{
			GraphicsContext graphicsContext=canvas.getGraphicsContext2D();
			graphicsContext.setFill(Color.WHITE);
			//graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
			graphicsContext.setStroke(Color.RED);
			int i;
			
			for(i=0;i<problem.getNumberOfCities();i++) {
				for(int j=0;j<problem.getNumberOfCities()&&j!=i;j++) {
					graphicsContext.setLineWidth(graph.getTau(i, j)*10);
					graphicsContext.strokeLine(map[0][i], map[1][i], map[0][j], map[1][j]);
				}
			}
			graphicsContext.closePath();
		});
		
	}

	public float getAlpha() {
		return alpha;
	}

	public float getBeta() {
		return beta;
	}

	@Override
	public void setOptimizationData(ArrayList<Float> listOfData) {
		// TODO Auto-generated method stub
		
	}

}