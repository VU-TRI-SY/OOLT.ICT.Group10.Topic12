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
	private float alphaMax;
	//Importance of global best
	private float beta;
	private float betaMin;
	//Importance of temporary velocity
	private float w;
	private float maxW;
	//Operation of ParticleSwarmOptimization
	private ParticleSwarmOperation operation;
	//Particles
	private ArrayList<Particle> particles;
	
	public ParticleSwarmOptimization(TravelingSalesmanProblem problem,Controller controller) {
		super(problem,controller);
		//Default value
		operation=new ParticleSwarmOperation(problem);
		numberOfParticles=100;
		alphaMax=0.9f;
		betaMin=0.1f;
		maxW=0.9f;
	}
	
	@Override
	public void solve() {
		init();
		while(!terminatedCondition()) {
			constructSolution();
			printSolution();
			//Slow down the process
			try {
				Thread.sleep(threadDelay);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
			}
			render();
			updateDataAtEndLoop();
		}
		stop();
		System.out.println("Finish!");
	}
	
	
	//Initialize optimization
	private void init() {
		alpha=alphaMax;
		beta=betaMin;
		w=maxW;
		readySolution=0;
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
				readySolution=0;
		}else {
			if(i==numberOfParticles-1) readySolution++;
		}
	}
	//System out the solution
	public void printSolution() {
		System.out.println("Global best: "+globalBestValue);
		System.out.println("Ready: "+readySolution);
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
		alpha-=alphaMax/numberOfIteration;
		beta+=(1-betaMin)/numberOfIteration;
		w-=maxW/numberOfIteration;
		System.out.println("Alpha: "+alpha+" Beta: "+beta+" W: "+w);
	}
	
	@Override
	public synchronized void renderGraphics(Canvas canvas) {
		// TODO Auto-generated method stub
		float[][] map=getProblem().getXoyMap();
		Platform.runLater(()->{
			GraphicsContext graphicsContext=canvas.getGraphicsContext2D();
			graphicsContext.setFill(Color.WHITE);
			graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
			graphicsContext.setLineWidth(2.0);
			graphicsContext.setStroke(Color.BLUE);
			int i;
			for(i=0;i<problem.getNumberOfCities()-1;i++) {
				graphicsContext.strokeLine(map[0][globalBest[i]], map[1][globalBest[i]], map[0][globalBest[i+1]], map[1][globalBest[i+1]]);
			}
			graphicsContext.strokeLine(map[0][globalBest[i]], map[1][globalBest[i]],map[0][globalBest[0]] , map[1][globalBest[0]]);
			graphicsContext.closePath();
		});
	}

	public float getAlphaMax() {
		return alphaMax;
	}

	public void setAlphaMax(float alphaMax) {
		this.alphaMax = alphaMax;
	}

	public float getBetaMin() {
		return betaMin;
	}

	public void setBetaMin(float betaMin) {
		this.betaMin = betaMin;
	}

	public float getMaxW() {
		return maxW;
	}

	public void setMaxW(float maxW) {
		this.maxW = maxW;
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
	public float getW() {
		return w;
	}
	public int getNumberOfParticles() {
		return numberOfParticles;
	}

	public void setNumberOfParticles(int numberOfParticles) {
		this.numberOfParticles = numberOfParticles;
	}
}
