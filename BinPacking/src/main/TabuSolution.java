package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TabuSolution {

	public Container container;
	public List<Box> boxes;

	public void generateData(int numberOfBoxes) {
		Random generator = new Random();
		container = new Container((generator.nextInt(100) + 1) * 10,
				(generator.nextInt(100) + 1) * 10,
				(generator.nextInt(100) + 1) * 10);
		boxes = new ArrayList<Box>();
		while (numberOfBoxes > 0) {
			boxes.add(new Box(generator.nextInt(50) + 1,
					generator.nextInt(50) + 1, generator.nextInt(50) + 1));
			numberOfBoxes--;
		}
	}

	public void startProgram() {

		generateData(5);
		for (Box box : boxes) {
			System.out.println("h: " + Integer.toString(box.getHeight())
					+ " l: " + Integer.toString(box.getLength()) + "w: "
					+ Integer.toString(box.getWidth()));
		}
	}
}
