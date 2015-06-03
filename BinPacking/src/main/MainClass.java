package main;

import java.util.ArrayList;


public class MainClass {
	public static void main(String[] args) {
	//	Solution solution = new Solution();
	//	solution.startProgram();
	   // DataGenerator dg = new DataGenerator();
	    //dg.CreateTestsContainers("1", 5, 125);
	//	SimulatedAnnealingSolver sas = new SimulatedAnnealingSolver();
		//Solution sol = sas.Solve(solution);

	   /*
		sol.printResult();
		System.out.println("Box num " + sol.boxesInsideContainer.size());
		System.out.println("\nVolume of solution: " + Double.toString(sol.countValueOfVolumeFactor()));
		*/
		
		//Tester t = new Tester();
		//t.runTests();
		
		ArrayList<Box> b = new ArrayList<Box>();
		b.add(new Box(1,5,5,2));
		b.add(new Box(5,5,2,2));
		b.add(new Box(9,5,3,5));
		int cont = 5;
		Solution sol = new Solution(b,cont);
		sol.printResult();
		System.out.println(sol.volume);
	}
}
