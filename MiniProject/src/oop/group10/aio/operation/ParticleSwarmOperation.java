package oop.group10.aio.operation;


import java.util.ArrayList;
import java.util.Iterator;

import oop.group10.aio.swap.SwapOperator;
import oop.group10.aio.swap.SwapSeries;
import oop.group10.aio.tsp.TravelingSalesmanProblem;

public class ParticleSwarmOperation extends AlgorithmsOperation {
	//Reference
	
	public ParticleSwarmOperation(TravelingSalesmanProblem problem) {
		// TODO Auto-generated constructor stub
		super(problem);
	}
	//Get the distance between current pos to destination pos
	public static SwapSeries getDistance(int[] current,int[] destination) {
		// TODO Auto-generated method stub
		int[] currentClone=current.clone();
		SwapSeries swapSeries=new SwapSeries();
		int i,j;
		for(i=0;i<destination.length;i++) {
			for(j=0;j<currentClone.length;j++) {
				if(i==j) continue;
				if(currentClone[j]==destination[i]) {
					swapSeries.addSwapOperator(new SwapOperator(i, j));
					int temp=currentClone[i];
					current[i]=currentClone[j];
					currentClone[j]=temp;
					break;
				}
			}
		}
		
		if(swapSeries.getOperatorsList().isEmpty()) swapSeries.addSwapOperator(new SwapOperator(1, 1));
		return swapSeries;
	}
	//Next destination of particles
	public static int[] getDestination(int[] position,SwapSeries swapSeries) {
		try {
			Iterator<SwapOperator> iterator=swapSeries.getOperatorsList().iterator();
			while(iterator.hasNext()) {
				SwapOperator swapOperator=(SwapOperator) iterator.next();
				int temp=position[swapOperator.getIndex1()];
				position[swapOperator.getIndex1()]=position[swapOperator.getIndex2()];
				position[swapOperator.getIndex2()]=temp;
			}
		}catch(NullPointerException e) {
			
		}
		return position;
	}
	//Accept the importance of alpha or beta
	private boolean acceptImportance(float probability) {
		float num= nextFloat(0.0f, 1.0f);
		if(probability<num) return false;
		else return true;
	}
	//Return the new velocity for particle
	public SwapSeries getNewVelocity(SwapSeries velocity,SwapSeries toLocal,SwapSeries toGlobal,float alpha,float beta) {
		if(acceptImportance(alpha)) {
			velocity.addSwapSeries(toLocal);
		}
		if(acceptImportance(beta)) {
			velocity.addSwapSeries(toGlobal);
		}
		velocity.simplify();
		return velocity;
	}
	//Initialize the first position
	public int[] getRandomPosition() {
		int numCities=problem.getNumberOfCities();
		ArrayList<Integer> listNodes=new ArrayList<Integer>();
		int i;
		for(i=0;i<numCities;i++) listNodes.add(i);
		int[] list=new int[numCities];
		i=0;
		while(!listNodes.isEmpty()) {
			int randNum=random.nextInt(listNodes.size());
			list[i]=listNodes.get(randNum);
			listNodes.remove(randNum);
			i++;
		}
 		return list;
	}
	//Initialize first velocity
	public SwapSeries getRandomVelocity() {
		int i;
		int[] fpos=new int[problem.getNumberOfCities()];
		for(i=0;i<problem.getNumberOfCities();i++) fpos[i]=i;
		int[] npos=getRandomPosition();
		SwapSeries swapSeries=getDistance(fpos, npos);
		return swapSeries;
	}
	//Print velocity
	public String getVelocityString(SwapSeries velocity) {
		StringBuilder stringBuilder=new StringBuilder();
		Iterator<SwapOperator> iterator=velocity.getOperatorsList().iterator();
		while(iterator.hasNext()) {
			SwapOperator s=(SwapOperator)iterator.next();
			stringBuilder.append("S(").append(s.getIndex1()).append("-").append(s.getIndex2()).append(") ");
		}
		String str=stringBuilder.toString();
		return str;
	}
}
