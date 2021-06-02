package oop.group10.aio.swap;

import java.util.ArrayList;
import java.util.Iterator;

import oop.group10.aio.operation.ParticleSwarmOperation;


public class SwapSeries {
	public static int maxIndex;
	private ArrayList<SwapOperator> listOfSOperators;
	
	public SwapSeries() {
		// TODO Auto-generated constructor stub
		listOfSOperators=new ArrayList<SwapOperator>();
	}
	//Add a Swap Operator
	public void addSwapOperator(SwapOperator swapOperator) {
		listOfSOperators.add(swapOperator);
	}
	public void addSwapSeries(SwapSeries swapSeries) {
		Iterator<SwapOperator> iterator=swapSeries.listOfSOperators.iterator();
		while(iterator.hasNext()) {
			SwapOperator operator=(SwapOperator) iterator.next();
			addSwapOperator(operator);
		}
	}
	public void addPartialSwapSeries(SwapSeries swapSeries,float percent) {
		Iterator<SwapOperator> iterator=swapSeries.listOfSOperators.iterator();
		while(iterator.hasNext()) {
			SwapOperator operator=(SwapOperator) iterator.next();
			if(Math.random()<percent) addSwapOperator(operator);
		}
	}
	//Simplify the SwapSeries
	public void simplify() {
		
		int[] sampleTour=new int[maxIndex];
		int[] transformTour=new int[maxIndex];
		int i;
		for(i=0;i<maxIndex;i++) {
			sampleTour[i]=i;
			transformTour[i]=i;
		}
		transformTour=ParticleSwarmOperation.getDestination(transformTour, this);
		clear();
		addSwapSeries(ParticleSwarmOperation.getDistance(sampleTour, transformTour));
	}
	//Clear the list of operators
	public void clear() {
		listOfSOperators=new ArrayList<SwapOperator>();
	}
	public ArrayList<SwapOperator> getOperatorsList() {
		return listOfSOperators;
	}
}
