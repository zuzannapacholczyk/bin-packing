package main;
import java.util.ArrayList;
import java.util.Random;

public class SimulatedAnnealingSolver  {
	double temperature = 100;
	double tempFactor = 0.85;
	int maxIter = 100000;
	final double stopTemp = 0.1;
	Solution bestSolution;
	
	Solution Solve(ArrayList<Box> boxes, int containerSz) {
		Solution sol = new Solution(boxes,containerSz);
		return Solve(sol);
	}
	
	
	Solution Solve(Solution sol) {
		Solution currentSolution = sol;
		Solution bestSolution = currentSolution;
		NeighbourGenerator ng = new NeighbourGenerator(currentSolution);
		Random randGen = new Random();
		int i = 0;
		
		while(temperature > stopTemp && i++ < maxIter) {
			ng.setSolution(currentSolution);
			Solution neighbourSolution = ng.getNeighbour();
			if(neighbourSolution.volume > bestSolution.volume)
				bestSolution = currentSolution;
			double costDifference = neighbourSolution.volume - currentSolution.volume;
			if(costDifference < 0) {
				currentSolution = neighbourSolution;
			}
			else {
				double x = randGen.nextDouble();
				double exponent = -costDifference/temperature;
				if(x < Math.pow(Math.E, exponent)) {
					currentSolution = neighbourSolution;
				}
			}
			temperature = tempFactor * temperature;
		}
		System.out.println("Ile iter " + Integer.toString(i));
		return bestSolution;
	}

}
