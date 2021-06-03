package oop.group10.aio.operation;

import java.util.ArrayList;

import oop.group10.aio.optimization.aco.PheromonesGraph;
import oop.group10.aio.tsp.TravelingSalesmanProblem;

public class AntColonyOperation extends AlgorithmsOperation {
	private PheromonesGraph graph;
	public AntColonyOperation(TravelingSalesmanProblem problem,PheromonesGraph graph) {
		super(problem);
		// TODO Auto-generated constructor stub
		this.graph=graph;
	}
	
	//Get the value of deposit value of ant on each road
	private float getDepositValue(int[] tour) {
		float depositValue = problem.evaluate(tour);
		return 1/depositValue;
	}
	//Deposit pheromones on road map
	/*public void depositOnGraph(int[] tour,float rho) {
		int numberOfCities = problem.getNumberOfCities();
		for(int i = 0; i < numberOfCities; i++) {
			for(int j = 0; j < numberOfCities && j != i; j++) {
				graph.addDeposit(i,j,getDepositValue(tour));
			}
		}
	}*/
	public void depositOnGraph(int[] tour) {
		for(int i=0;i<tour.length;i++) {
			if(i==tour.length-1) {
				graph.addDeposit(tour[i],tour[0],getDepositValue(tour));
				graph.addDeposit(tour[0],tour[i],getDepositValue(tour));
			}else {
				graph.addDeposit(tour[i],tour[i+1],getDepositValue(tour));
				graph.addDeposit(tour[i+1],tour[i],getDepositValue(tour));
			}
		}
	}
	//Get new tour for ant
	public int[] getNewTour(float alpha,float beta) {
		int numCities = problem.getNumberOfCities();
		ArrayList<Integer> listNodes = new ArrayList<Integer>();
		int i;
		for(i = 0; i < numCities; i++) listNodes.add(i);
		int[] tour=new int[numCities];// tour of ant
		//Repeat process ... to get the tour
		listNodes.remove(0);
		for(i = 1; i < numCities; i++) {
			ArrayList<Float> probabilty = new ArrayList<Float>();
			for(int j : listNodes) {
				probabilty.add(getChooseProbabilty(tour[i-1],j,alpha,beta));
			}
			int next = nextCityToGo(probabilty);
			tour[i] = listNodes.get(next);
			listNodes.remove(next);
			
		}
		return tour;
	}
	//Probability for the road from city i to j
	private float getChooseProbabilty(int i,int j,float alpha,float beta) {
		float t = graph.getTau(i,j);
		float n = 1/(problem.getDistance(i, j));
		return (float) ((Math.pow(t,alpha))+Math.pow(n, beta));// return tu so cua P
	}
	private int nextCityToGo(ArrayList<Float> probabilty) {
		float sumProbabilty=0;
		for(float x : probabilty) {
			sumProbabilty += x;
		}
		int i = 0;
		float p = probabilty.get(i);
		float r = nextFloat(0.0f,sumProbabilty);
		while(p < r) {
			i = i+1;
			p = p + probabilty.get(i);
		}
		return i;
	}

}