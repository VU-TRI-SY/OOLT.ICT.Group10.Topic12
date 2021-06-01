package oop.group10.aio.objects;


import oop.group10.aio.operation.ParticleSwarmOperation;
import oop.group10.aio.optimization.OptimizationForTSP;
import oop.group10.aio.optimization.pso.ParticleSwarmOptimization;
import oop.group10.aio.swap.SwapSeries;

public class Particle extends Individual {
	private int id;
	private int[] localbest;
	private float localbestvalue;
	private SwapSeries velocity;
	private ParticleSwarmOperation operation;
	public Particle(int id,OptimizationForTSP optimization,ParticleSwarmOperation operation) {
		// TODO Auto-generated constructor stub
		super(optimization);
		this.id=id;
		this.operation=operation;
	}
	//Initialize data for particle
	@Override
	public void init() {
		// TODO Auto-generated method stub
		tour=operation.getRandomPosition();
		tourLength=optimization.getProblem().evaluate(tour);
		localbest=cloneTour();
		localbestvalue=tourLength;
		velocity=new SwapSeries();
		//velocity.addSwapOperator(new SwapOperator(1, 1));
		velocity=operation.getRandomVelocity();
	}
	//Particle moving
	@Override
	public void constructSolution() {
		// TODO Auto-generated method stub
		getNewVelociy();
		getToNewPosition();
		updateLocalBest();
		optimization.updateGlobalBest(id);
		System.out.println(this.toString());
		//Change this to change the individual velocity of particles(Initial velocity)
		velocity=operation.getRandomVelocity();
	}
	//Update the local best position
	public void updateLocalBest() {
		tourLength=optimization.getProblem().evaluate(tour);
		if(tourLength<localbestvalue) {
			localbestvalue=tourLength;
			localbest=cloneTour();
		}
	}
	//Go to the next destination
	private void getToNewPosition() {
		tour= ParticleSwarmOperation.getDestination(tour, velocity);
	}
	//Change velocity
	@SuppressWarnings("static-access")
	private void getNewVelociy() {
		int[] globalBest=getOptimization().getGlobalBest();
		//Casting
		ParticleSwarmOptimization optimization=(ParticleSwarmOptimization)getOptimization();
		//Get alpha and beta
		float alpha=optimization.getAlpha();
		float beta=optimization.getBeta();
		velocity=operation.getNewVelocity(velocity, operation.getDistance(tour, localbest), operation.getDistance(tour, globalBest),alpha , beta);
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuilder stringBuilder=new StringBuilder();
		stringBuilder.append(id).append(". {");
		int i;
		for(i=0;i<tour.length;i++) stringBuilder.append(tour[i]).append(",");
		stringBuilder.append("} Value: ").append(tourLength);
		String str=stringBuilder.toString();
		return str;
	}
	
	public int[] getLocalbest() {
		return localbest;
	}

	public float getLocalbestvalue() {
		return localbestvalue;
	}
}
