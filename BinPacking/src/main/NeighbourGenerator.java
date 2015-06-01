package main;

public class NeighbourGenerator {
	
	int neighbourhoodLength = 20;
	double minRemoveFactor = 0.05;
	double maxRemoveFactor = 0.15;
	Solution solution;
	
	NeighbourGenerator(Solution solution) {
		this.solution = solution;
	}
	
	public Solution getNeighbour() {
	//	int solLength = solution.getNumberOfboxesInsideContainer();
	//	int minBoxesToRemove = (int)Math.floor(solLength * minRemoveFactor);
	//	int maxBoxesToRemove = (int)Math.floor(solLength * maxRemoveFactor);
	//	Random rand = new Random();
		int numberOfBoxesToRemove = 2;
	
		return new Solution(solution,numberOfBoxesToRemove);
	}
	
	public Solution getBestNeigbourFrom(int n) {
		Solution bestNeighbour = getNeighbour();
		for(int i = 0; i < n - 1; i++) {
			Solution newNeighbour = getNeighbour();
			if(newNeighbour.volume > bestNeighbour.volume)
				bestNeighbour = newNeighbour;
		}
		return bestNeighbour;
	}
	
	public void setSolution(Solution solution) {
		this.solution = solution;
	}

}
