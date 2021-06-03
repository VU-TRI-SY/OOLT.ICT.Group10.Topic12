package oop.group10.aio.optimization.aco;

public class PheromonesGraph {
	//Density of pheromones in the road
	private float density[][];
	
	//Initialize graph
	
	public PheromonesGraph(int numberOfCities) {
		this.density = new float[numberOfCities][numberOfCities];
	}

	public float getTau(int i,int j) {
		return density[i][j];
	}
	
	public void evaporation(int numOfCities,float rho) {
		for(int i=0;i<numOfCities;i++) {
			for(int j=0;j<numOfCities;j++) {
				float x=density[i][j]-density[i][j]*rho;
				density[i][j]=x;
			}
		}
	}
	public void addDeposit(int i,int j, float value) {
		density[i][j]+=value;
	}
	public void  reset(int numberOfCities) {
		this.density = new float[numberOfCities][numberOfCities];
	}
}