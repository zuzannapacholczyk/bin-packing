package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TabuSolution {

	public Container container;
	public List<Box> boxes;

	private void generateData(int numberOfBoxes) {
		Random generator = new Random();
		container = new Container((generator.nextInt(100) + 1) * 10,
				(generator.nextInt(100) + 1) * 10,
				(generator.nextInt(100) + 1) * 10);
		boxes = new ArrayList<Box>();
		int id = 1;
		while (numberOfBoxes > 0) {
			boxes.add(new Box(id, generator.nextInt(50) + 1,
					generator.nextInt(50) + 1, generator.nextInt(50) + 1));
			numberOfBoxes--;
			id++;
		}
	}

	public void startProgram() {

		generateData(5);
		for (Box box : boxes) {
			System.out.println("h: " + Integer.toString(box.getHeight())
					+ " l: " + Integer.toString(box.getLength()) + "w: "
					+ Integer.toString(box.getWidth()));
		}
		
		Collections.sort(boxes, new BoxesComparator());
		
		System.out.println("after sort");
		for (Box box : boxes) {
			System.out.println("h: " + Integer.toString(box.getHeight())
					+ " l: " + Integer.toString(box.getLength()) + "w: "
					+ Integer.toString(box.getWidth()));
		}
	}
}
