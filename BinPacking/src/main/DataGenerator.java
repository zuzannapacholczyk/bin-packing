package main;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Random;

public class DataGenerator {
	final String path = "TestData\\";
	final int instancesNum = 3;
	final double boxSizeFactor = 0.8;
			
	public void CreateTestsContainers(String fileName, int containerSz, int numberOfBoxes) {
		PrintWriter writer;
		try {
			writer = new PrintWriter(path+"\\"+fileName+".txt", "UTF-8");
			Random generator = new Random();
			int id = 1;
			int height = Math.min(generator.nextInt(containerSz) + 2,containerSz);
			int boxSz = (int)(boxSizeFactor * containerSz);
			writer.println(Integer.toString(numberOfBoxes) + " " + Integer.toString(containerSz));
			
			while (numberOfBoxes > 1) {
				int width = Math.min(generator.nextInt(boxSz) + 2,containerSz);
				int length =  Math.min(generator.nextInt(boxSz) + 2,containerSz);
				
				 writer.println(Integer.toString(id) + " " + Integer.toString(height) +
			     " " + Integer.toString(width)
			     + " " + Integer.toString(length));
				numberOfBoxes--;
				id++;
			}
			writer.close();
			
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	

}
