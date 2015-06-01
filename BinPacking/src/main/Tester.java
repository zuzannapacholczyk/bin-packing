package main;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;



public class Tester {
	private String folderName = "TestData";
	
	public void printResult(Solution sol) {
		sol.printResult();
		System.out.println("Box num " + sol.boxesInsideContainer.size());
		System.out.println("\nVolume of solution: " + Double.toString(sol.countValueOfVolumeFactor()));
	}
	
	public void runTests() {
		ArrayList<Box> boxes;
		File folder = new File(folderName);
		for(File file : folder.listFiles()) {
			try {
				Scanner scanner = new Scanner(file);
			//	int boxesNum = scanner.nextInt();
				int containerSz = scanner.nextInt();
				boxes = readBoxesData(scanner);
				SimulatedAnnealingSolver sas = new SimulatedAnnealingSolver();
				printResult(sas.Solve(boxes,containerSz));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		
	}

	private ArrayList<Box> readBoxesData(Scanner scanner) {
		ArrayList<Box> boxes = new ArrayList<Box>();
		while(scanner.hasNextInt()) {
			int id = scanner.nextInt();
			int height = scanner.nextInt();
			int width = scanner.nextInt();
			int length = scanner.nextInt();
			boxes.add(new Box(id,height,width,length));
			System.out.println(Integer.toString(id) + " " + Integer.toString(height) + " "
					+ Integer.toString(width) + " " + Integer.toString(length));
		}
		return boxes;
	}
}
