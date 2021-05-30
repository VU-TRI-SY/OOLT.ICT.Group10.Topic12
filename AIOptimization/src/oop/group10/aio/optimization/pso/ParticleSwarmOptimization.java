package oop.group10.aio.optimization.pso;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import oop.group10.aio.application.Controller;
import oop.group10.aio.objects.Particle;
import oop.group10.aio.operation.ParticleSwarmOperation;
import oop.group10.aio.optimization.OptimizationForTSP;
import oop.group10.aio.swap.SwapSeries;
import oop.group10.aio.tsp.TravelingSalesmanProblem;

public class ParticleSwarmOptimization extends OptimizationForTSP {
	//Number of particles
	private int numberOfParticles;
	//Importance of local best
	private float alpha;
	//Importance of global best
	private float beta;
	//Operation of ParticleSwarmOptimization
	private ParticleSwarmOperation operation;
	//Particles
	private ArrayList<Particle> particles;
	
	public ParticleSwarmOptimization(TravelingSalesmanProblem problem,Controller controller) {
		super(problem,controller);
		//Default value
		operation=new ParticleSwarmOperation(problem);
		numberOfParticles=100;
		alpha=0.1f;
		beta=0.9f;
	}
	
	@Override
	public void solve() {
		init();
		while(!terminatedCondition()) {
			constructSolution();
			printSolution();
			//Slow down the process
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
			}
			render();
			updateDataAtEndLoop();
		}
		stop();
	}
	
	
	//Initialize optimization
	private void init() {
		currentIteration=0;
		particles=new ArrayList<Particle>();
		int i;
		for(i=0;i<numberOfParticles;i++) {
			Particle p=new Particle(i,this,operation);
			particles.add(p);
			p.init();
		}
		globalBest=operation.getRandomPosition();
		globalBestValue=problem.evaluate(globalBest);
		
		SwapSeries.maxIndex=getProblem().getNumberOfCities();
		
		onActive=true;
	}
	//Each particle construct solution
	private void constructSolution() {
		int i;
		for(i=0;i<numberOfParticles;i++) particles.get(i).constructSolution();
	}
	//Update the global best
	public void updateGlobalBest(int i) {
		if(globalBestValue > particles.get(i).getLocalbestvalue()) {
				globalBest=particles.get(i).cloneTour();
				globalBestValue=getProblem().evaluate(globalBest);
				controller.setGlobalBest();
		}
	}
	//System out the solution
	public void printSolution() {
		System.out.println("Global best: "+globalBestValue);
	}
	
	//Stop motion
	private void stop() {
		controller.stopProgressBarMotion();
	}
	//Render graphics each loop
	private void render() {
		controller.changeProgress();
		renderGraphics(controller.getCanvas());
	}
	//iteration++ each loop
	private void updateDataAtEndLoop() {
		currentIteration++;	
	}
	
	@Override
	public synchronized void renderGraphics(Canvas canvas) {
		// TODO Auto-generated method stub
		float[][] map=getProblem().getXoyMap();
		Platform.runLater(()->{
			GraphicsContext graphicsContext=canvas.getGraphicsContext2D();
			graphicsContext.setFill(Color.WHITE);
			graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
			graphicsContext.setStroke(Color.BLUE);
			int i;
			for(i=0;i<problem.getNumberOfCities()-1;i++) {
				graphicsContext.strokeLine(map[0][globalBest[i]], map[1][globalBest[i]], map[0][globalBest[i+1]], map[1][globalBest[i+1]]);
			}
			graphicsContext.strokeLine(map[0][globalBest[i]], map[1][globalBest[i]],map[0][globalBest[0]] , map[1][globalBest[0]]);
			graphicsContext.closePath();
		});
	}
	
	public ParticleSwarmOperation getOperation() {
		return operation;
	}
	public float getAlpha() {
		return alpha;
	}
	public float getBeta() {
		return beta;
	}
	
}
