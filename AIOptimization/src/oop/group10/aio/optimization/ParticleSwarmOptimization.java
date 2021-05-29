package oop.group10.aio.optimization;

import java.util.ArrayList;

import javafx.scene.canvas.Canvas;
import oop.group10.aio.objects.Particle;
import oop.group10.aio.operation.ParticleSwarmOperation;
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
	
	public ParticleSwarmOptimization(TravelingSalesmanProblem problem) {
		super(problem);
		//Default value
		operation=new ParticleSwarmOperation(problem);
		numberOfParticles=100;
		alpha=0.5f;
		beta=0.5f;
	}
	//Initialize optimization
	private void init() {
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
		}
	}
	//System out the solution
	public void printSolution() {
		System.out.println("Global best: "+globalBestValue);
	}
	@Override
	public void solve() {
		init();
		while(!terminatedCondition()) {
			constructSolution();
			printSolution();
			currentIteration++;
		}
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
	@Override
	public void renderGraphics(Canvas canvas) {
		// TODO Auto-generated method stub
		
	}
}
