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
	private float defautAlpha;
	private float alpha;
	//Importance of heuristics information(length of the tour)
	private float defautBeta;
	private float beta;
	//Evaporation rate
	private float defautRho;
	private float rho;
	//Operation of optimization
	private AntColonyOperation operation;
	//Pheromones map
	private PheromonesGraph graph;
	//List of ants
	private ArrayList<Ant> ants;
	//Best of current tour
	private float bestOfCurrentTour;
	
	
	public AntColonyOptimization(TravelingSalesmanProblem problem, Controller controller) {
		super(problem, controller);
		// TODO Auto-generated constructor stub
		numberOfAnts=20;
		defautAlpha=1.0f;
		defautBeta=2.0f;
		defautRho=0.4f;
		graph= new PheromonesGraph(problem.getNumberOfCities());
		operation = new AntColonyOperation (problem,graph);
	}
	//Initialize optimization
	
	private void init() {
		// TODO Auto-generated method stub
		alpha=defautAlpha;
		beta=defautBeta;
		rho=defautRho;
		graph.reset(problem.getNumberOfCities());
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
		graph.evaporation(problem.getNumberOfCities(),rho);
		for(Ant ant:ants) {
			operation.depositOnGraph(ant.getTour());
		}
	}
	//Update data at end loop
	private void updateDataAtEndLoop() {
		// TODO Auto-generated method stub
		updatePheromonesMap();
		currentIteration++;
		if(currentIteration==2*getMaximumIteration()/3) {
			alpha=4.5f;
			beta=0.5f;
		}
	}
	//Print solution
	private void printSolution() {
		// TODO Auto-generated method stub
		System.out.println("Global best: "+globalBestValue);
	}
	
	//Update global best (Will be call inside Ant instance) with i is id of the ant
	@Override
	public void updateGlobalBest(int i) {
		// TODO Auto-generated method stub
		if(globalBestValue > ants.get(i).getTourLength()) {
			globalBest=ants.get(i).cloneTour();
			globalBestValue=getProblem().evaluate(globalBest);
		}
		if(i==0) bestOfCurrentTour=ants.get(0).getTourLength();
		else {
			bestOfCurrentTour= (bestOfCurrentTour)>(ants.get(i).getTourLength())? (bestOfCurrentTour):(ants.get(i).getTourLength());
		}
		controller.setCurrentSolution(bestOfCurrentTour);
	}
	private void render() {
		controller.changeProgress();
		renderGraphics(controller.getCanvas());
	}
	private void stop() {
		controller.stopProgressBarMotion();
	}
	//Render graphics for VIEW
	public void renderGraphics(Canvas canvas) {
		// TODO Auto-generated method stub
		float[][] map=getProblem().getXoyMap();
		Platform.runLater(()->{
			GraphicsContext graphicsContext=canvas.getGraphicsContext2D();
			graphicsContext.setFill(Color.WHITE);
			graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
			graphicsContext.setStroke(Color.BLUE);
			int i;
			for(i=0;i<problem.getNumberOfCities();i++) {
				for(int j=0;j<problem.getNumberOfCities();j++) {
					if(i<j) {
						graphicsContext.setLineWidth(graph.getTau(i, j)*100);
						graphicsContext.strokeLine(map[0][i], map[1][i], map[0][j], map[1][j]);
					}
				}
			}
			graphicsContext.closePath();
		});
		
	}

	public float getAlpha() {
		return alpha;
	}

	public int getNumberOfAnts() {
		return numberOfAnts;
	}

	public void setNumberOfAnts(int numberOfAnts) {
		this.numberOfAnts = numberOfAnts;
	}

	public float getBeta() {
		return beta;
	}

	public float getRho() {
		return rho;
	}
	
	public float getDefautAlpha() {
		return defautAlpha;
	}

	public void setDefautAlpha(float defautAlpha) {
		this.defautAlpha = defautAlpha;
	}

	public float getDefautBeta() {
		return defautBeta;
	}

	public void setDefautBeta(float defautBeta) {
		this.defautBeta = defautBeta;
	}

	public float getDefautRho() {
		return defautRho;
	}

	public void setDefautRho(float defautRho) {
		this.defautRho = defautRho;
	}


}