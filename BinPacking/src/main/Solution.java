package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Solution {

	public Container container;
	public List<Box> boxesOutsideContainer;
	public List<Box> boxesInsideContainer;
	double volume;
	
	int getNumberOfboxesInsideContainer() {
		return boxesInsideContainer.size();
	}
	
	Solution(List<Box> boxesToRearrangement, List<Box> boxesToPut) {
		boxesOutsideContainer = boxesToRearrangement;
		createSolution();
		boxesOutsideContainer = boxesToPut;
		createSolution();
		volume = countValueOfVolumeFactor();
	}
	
	Solution(List<Box> boxes, int containerSz) {
		boxesInsideContainer = new ArrayList<Box>();
		boxesOutsideContainer = boxes;
		container = new Container(containerSz, containerSz, containerSz);
		createSolution();
		volume = countValueOfVolumeFactor();
	}
	
	//Konstruktor tworz¹cy rozwi¹zanie s¹siednie
	Solution(Solution baseSolution, int boxesNumberToRemove) {
		container = new Container(baseSolution.container);
		boxesOutsideContainer = new ArrayList<Box>();
		boxesInsideContainer = new ArrayList<Box>();
		Random rand = new Random();
		
		boxesInsideContainer = new ArrayList<Box>();
		for(Box b : baseSolution.boxesInsideContainer)
			boxesOutsideContainer.add(new Box(b));

		
		ArrayList<Box> boxesToInsert = new ArrayList<Box>();
		for(Box box : baseSolution.boxesOutsideContainer)
			boxesToInsert.add(new Box(box));

		for(int i = 0; i < Math.min(boxesNumberToRemove,boxesInsideContainer.size()); i++) {
			int indexToRemove = rand.nextInt(boxesInsideContainer.size());
			Box removedBox = new Box(boxesOutsideContainer.get(indexToRemove));
			removedBox.resetIds();
			boxesToInsert.add(removedBox);
			boxesOutsideContainer.remove(indexToRemove);
		}
		
		for(Box removedBox : boxesOutsideContainer)
			removedBox.resetIds();
		Collections.sort(boxesOutsideContainer, new BoxesComparator());
		createSolution();
		boxesOutsideContainer = boxesToInsert;
		Collections.sort(boxesOutsideContainer, new BoxesComparator());
		createSolution();
		volume = countValueOfVolumeFactor();
	}
	
	Solution() {}
	
	private void generateData(int numberOfBoxes) {
		Random generator = new Random();
		int containerSz = 50;
		container = new Container(containerSz, containerSz, containerSz);
		int boxSz = (int)(0.3 * containerSz);
		boxesOutsideContainer = new ArrayList<Box>();
		int id = 1;
		int height = generator.nextInt(boxSz) + 2;
		while (numberOfBoxes > 1) {
			//boxesOutsideContainer.add(new Box(id, 2, generator.nextInt(8)+1, 2));
			 boxesOutsideContainer.add(new Box(id, height,
		     generator.nextInt(boxSz) + 2, generator.nextInt(boxSz) + 2));
			numberOfBoxes--;
			id++;
		}
	}

	public void startProgram() {

		generateData(125);

		Collections.sort(boxesOutsideContainer, new BoxesComparator());
		int sum = 0;
		for(Box b : boxesOutsideContainer)
			sum += (b.getHeight() * b.getWidth() * b.getLength());
		printData();
		
		boxesInsideContainer = new ArrayList<Box>();
		createSolution();
		
		printResult();
		System.out.println("\nVolume of solution: " + Double.toString(countValueOfVolumeFactor()));
		System.out.println("\nBoxes volume: " + Integer.toString(sum));
	}

	public double countValueOfVolumeFactor() {
		int volumeOfBoxes = 0;
		double result = 0;
		for(Box boxInside: boxesInsideContainer) {
			volumeOfBoxes+=boxInside.getVolume();
		}
		result = (double) volumeOfBoxes/ (double) container.getVolume();
		return result;
	}

	public void printData() {
		System.out.println("container");
		System.out.println("h: " + Integer.toString(container.getHeight())
				+ " l: " + Integer.toString(container.getLength()) + " w: "
				+ Integer.toString(container.getWidth()));

		System.out.println("\nafter sort");
		for (Box box : boxesOutsideContainer) {
			System.out.println("id: " + Integer.toString(box.getId()) + " h: " + Integer.toString(box.getHeight())
					+ " l: " + Integer.toString(box.getLength()) + " w: "
					+ Integer.toString(box.getWidth()));
		}
	}

	public void printResult() {
		System.out.println("\noutside after solution");
		if (!boxesOutsideContainer.isEmpty())
			for (Box box : boxesOutsideContainer) {
				System.out.println("id: " + Integer.toString(box.getId()) + " h: " + Integer.toString(box.getHeight())
						+ " l: " + Integer.toString(box.getLength()) + " w: "
						+ Integer.toString(box.getWidth()));
			}

		System.out.println("\ninside after solution");
		if (!boxesInsideContainer.isEmpty())
			for (Box box : boxesInsideContainer) {
				System.out.println("id: " + Integer.toString(box.getId()) + " h: " + Integer.toString(box.getHeight())
						+ " l: " + Integer.toString(box.getLength()) + " w: "
						+ Integer.toString(box.getWidth()) + " x:"
						+ Integer.toString(box.getPlaceInContainer().getX())
						+ " y:"
						+ Integer.toString(box.getPlaceInContainer().getY())
						+ " z:"
						+ Integer.toString(box.getPlaceInContainer().getZ()));
				System.out.println("aboveId: "+Integer.toString(box.getAboveId()) + " " + 
						"behindId: "+Integer.toString(box.getBehindId()) + " " +
						"frontId: "+Integer.toString(box.getInFrontId()) + " " +
						"leftId: "+Integer.toString(box.getLeftId()) + " " +
						"rightId: "+Integer.toString(box.getRightId()) + "\n\n"
						);
			}
	}

	public void createSolution() {
		List<Box> keepToRemove = new ArrayList<Box>();
		for (Box outsideBox : boxesOutsideContainer) {
			if (getInsideBoxWithId(outsideBox.getId()) == null) {
				if (boxesInsideContainer.isEmpty()) {
					if (checkIfBoxFitsInsideContainer(new Point(0, 0, 0),
							outsideBox.getId())) {
						// boxesOutsideContainer.remove(outsideBox);
						keepToRemove.add(outsideBox);
						outsideBox.setInContainer(0, 0, 0);
						boxesInsideContainer.add(outsideBox);
					}
				} else {

					Box checkBox = getInsideBoxWithXYZ(new Point(0, 0, 0));

					while (checkBox.getInFrontId() != -1) {
						checkBox = getInsideBoxWithId(checkBox.getInFrontId());
					}
					Box downLeftCheckBox = checkBox;
					while (checkBox.getAboveId() != -1) {
						checkBox = getInsideBoxWithId(checkBox.getAboveId());
					}
					Box aboveLeftBox = checkBox;
					while ( checkBox.getRightId() != -1) { 
						checkBox = getInsideBoxWithId(checkBox.getRightId());
					}

					Point newPlace = new Point(checkBox.getPlaceInContainer()
							.getX() + checkBox.getLength(), checkBox
							.getPlaceInContainer().getY(), checkBox
							.getPlaceInContainer().getZ());

					//wstaw najbardziej na prawo
					if (!checkIfFitsOnPlace(keepToRemove, outsideBox, checkBox,
							newPlace)) {
						newPlace = new Point(aboveLeftBox.getPlaceInContainer()
								.getX(), aboveLeftBox.getPlaceInContainer()
								.getY() + aboveLeftBox.getHeight(),
								aboveLeftBox.getPlaceInContainer().getZ());
						
						//jesli sie nie udalo, wstaw rzad wyzej
						if (!checkIfFitsOnPlace(keepToRemove, outsideBox, null,
								newPlace)) {
							newPlace = new Point(downLeftCheckBox
									.getPlaceInContainer().getX(),
									downLeftCheckBox.getPlaceInContainer()
											.getY(), downLeftCheckBox
											.getPlaceInContainer().getZ()
											+ downLeftCheckBox.getWidth());
							
							//jesli sie nie udalo, wstaw rzad z przodu
							if(checkIfFitsOnPlace(keepToRemove, outsideBox, null,
									newPlace)) {
								downLeftCheckBox.setInFrontId(outsideBox.getId());
								outsideBox.setBehindId(downLeftCheckBox.getId());
							}
						}
						else {
							aboveLeftBox.setAboveId(outsideBox.getId());
							outsideBox.setUnderId(aboveLeftBox.getId());
						}
					}
				}
			}
		}
		boxesOutsideContainer.removeAll(keepToRemove);

	}

	private boolean checkIfFitsOnPlace(List<Box> keepToRemove, Box outsideBox,
			Box checkBox, Point newPlace) {
		ResultBehind result = checkIfFitsToOtherBoxes(outsideBox, newPlace);
		Point defaultValue = new Point(-1, -1, -1);
		if (result.getPoint().getX() != defaultValue.getX()
				&& result.getPoint().getY() != defaultValue.getY()
				&& result.getPoint().getY() != defaultValue.getY())
			newPlace = new Point(result.getPoint().getX(), result.getPoint()
					.getY(), result.getPoint().getZ());
		if (checkIfBoxFitsInsideContainer(newPlace, outsideBox.getId())) {
			// boxesOutsideContainer
			// .remove(outsideBox);
			keepToRemove.add(outsideBox);
			outsideBox.setInContainer(newPlace);

			if (checkBox != null) {
				outsideBox.setLeftId(checkBox.getId());
				// getBoxOnTheRight
				checkBox.setRightId(outsideBox.getId());
				outsideBox.setLeftId(checkBox.getId());
			}
			// getBoxBehind
			for (Box behindBox : result.getBoxes()) {
				if (behindBox != null) {
					behindBox.setInFrontId(outsideBox.getId());
					outsideBox.setBehindId(behindBox.getId());
				}
			}

			// getBoxUnder
			setPointersToBoxesUnder(outsideBox, newPlace);
			boxesInsideContainer.add(outsideBox);
			return true;
		}
		return false;
	}

	private void setPointersToBoxesUnder(Box outsideBox, Point newPlace) {
		Box currentBox;
		Point currentPoint = new Point(newPlace.getX(), newPlace.getY(),
				newPlace.getZ());
		int i = 0;
		int j = 0;
		while (i <= outsideBox.getLength()) {
			while (j <= outsideBox.getWidth()) {
				currentPoint.setY(newPlace.getY() - 1);
				currentPoint.setX(newPlace.getX() + i);
				currentPoint.setZ(newPlace.getZ() + j);
				currentBox = getInsideBoxWithXYZ(currentPoint);
				if (currentBox != null) {
					currentBox.setAboveId(outsideBox.getId());
					outsideBox.setUnderId(currentBox.getId());
				}
				j++;
			}
			i++;
		}
	}

	private ResultBehind checkIfFitsToOtherBoxes(Box outsideBox, Point corner) {
		int i = 0;
		int j = 0;
		Point resultOfCheck = new Point(corner.getX(), corner.getY(),
				corner.getZ());
		Point result = new Point(corner.getX(), corner.getY(), corner.getZ());
		Point newPlaceCurrent = new Point(-1, -1, -1);
		List<Box> boxesBehind = new ArrayList<Box>();
		Pair<Box, Point> boxPointBehind = new Pair<Box, Point>(null, corner);
		while (i <= outsideBox.getHeight()) {

			resultOfCheck.setY(resultOfCheck.getY() + i);

			while (j <= outsideBox.getLength()) {
				resultOfCheck.setX(resultOfCheck.getX() + j);
				Pair<Box, Point> checkIfBoxesBehindFit = checkIfBoxesBehindFit(resultOfCheck);
				if (checkIfBoxesBehindFit.getBox() != null) {
					boxPointBehind.setPoint(checkIfBoxesBehindFit.getPoint());
					boxPointBehind.setBox(checkIfBoxesBehindFit.getBox());
					if (boxPointBehind.getPoint().getZ() < corner.getZ()
							&& newPlaceCurrent.getZ() <= boxPointBehind
									.getPoint().getZ()) {
						newPlaceCurrent = boxPointBehind.getPoint();
						resultOfCheck = newPlaceCurrent;
						boxesBehind.add(boxPointBehind.getBox());
					}
					if (boxPointBehind.getPoint().getZ() > newPlaceCurrent
							.getZ()) {
						newPlaceCurrent = boxPointBehind.getPoint();
						resultOfCheck = newPlaceCurrent;
						boxesBehind.add(boxPointBehind.getBox());
					}
				}
				j++;
			}
			i++;
		}
		if (newPlaceCurrent.getX() != -1 && newPlaceCurrent.getY() != -1
				&& newPlaceCurrent.getZ() != -1) {
			result.setZ(newPlaceCurrent.getZ());
			return new ResultBehind(boxesBehind, result);
		} else
			return new ResultBehind(boxesBehind, newPlaceCurrent);
	}

	public boolean checkIfBoxFitsInsideContainer(Point corner, int boxId) {
		if (corner.getX() + getOutsideBoxWithId(boxId).getLength() <= container
				.getLength())
			if (corner.getY() + getOutsideBoxWithId(boxId).getHeight() <= container
					.getHeight())
				if (corner.getZ() + getOutsideBoxWithId(boxId).getWidth() <= container
						.getWidth())
					return true;
		return false;
	}

	public Pair<Box, Point> checkIfBoxesBehindFit(Point corner) {
		Box boxBehind = null;
		int newCornerZ = corner.getZ();
		Point newCorner = new Point(corner.getX(), corner.getY(), corner.getZ());
		while (boxBehind == null && newCornerZ >= 0) {
			newCornerZ--;
			newCorner.setZ(newCornerZ);
			boxBehind = getInsideBoxWithXYZ(newCorner);

		}
		newCorner = corner;

		if (boxBehind != null) {
			newCornerZ = boxBehind.getPlaceInContainer().getZ()
					+ boxBehind.getWidth();
			newCorner.setZ(newCornerZ);
		}

		return new Pair<Box, Point>(boxBehind, newCorner);
	}

	public Box getOutsideBoxWithId(int id) {
		for (Box box : boxesOutsideContainer) {
			if (box.getId() == id)
				return box;
		}
		return null;
	}

	public Box getInsideBoxWithId(int id) {
		for (Box box : boxesInsideContainer) {
			if (box.getId() == id)
				return box;
		}
		return null;
	}

	public Box getInsideBoxWithXYZ(Point point) {
		for (Box box : boxesInsideContainer) {
			if (box.getPlaceInContainer() == point)
				return box;
			else {
				if (box.getPlaceInContainer().getX() <= point.getX()
						&& box.getPlaceInContainer().getX() + box.getLength() >= point
								.getX())

					if (box.getPlaceInContainer().getY() <= point.getY()
							&& box.getPlaceInContainer().getY()
									+ box.getHeight() >= point.getY())

						if (box.getPlaceInContainer().getZ() <= point.getZ()
								&& box.getPlaceInContainer().getZ()
										+ box.getWidth() >= point.getZ())
							return box;
			}
		}
		return null;
	}
}
