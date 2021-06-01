package oop.group10.aio.optimization.aco;

public class PheromonesGraph {
	//Density of pheromones in the road
	private float density[][];
	
	//Initialize graph
<<<<<<< HEAD
	
	public PheromonesGraph(int numberOfCities) {
		super();
		this.density = new float[numberOfCities][numberOfCities];
	}

	public float getTau(int i,int j) {
		return density[i][j];
	}
	
	public void evaporation(float rho) {
		for(int i=0;i<density.length;i++) {
			for(int j=0;j<density[0].length&&j!=i;j++) {
				float x=density[i][j]-density[i][j]*rho;
				if(x>0) {
					density[i][j] = x;
				}else density[i][j]=0;
			}
		}
	}
	public void addDeposit(int i,int j, float value) {
		density[i][j]+=value;
	}
}
=======
	public void initGraph() {
		// TODO Auto-generated method stub
		
	}
	
	public float getTau(int i,int j) {
		i=0;j=0;
		return 0;
	}
	
	public void evaporation(float rho) {
		
	}
	public void addDeposit(int i,int j,float value) {
		density[i][j]+=value;
	}
}
>>>>>>> branch 'master' of https://github.com/VU-TRI-SY/OOLT.ICT.Group10.Topic12.git
