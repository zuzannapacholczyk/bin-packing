package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TabuSolution {

	public Container container;
	public List<Box> boxesOutsideContainer;
	public List<Box> boxesInsideContainer;

	private void generateData(int numberOfBoxes) {
		Random generator = new Random();
		// container = new Container((generator.nextInt(100) + 1) * 10,
		// (generator.nextInt(100) + 1) * 10,
		// (generator.nextInt(100) + 1) * 10);
		container = new Container(4, 4, 4);
		boxesOutsideContainer = new ArrayList<Box>();
		int id = 1;
		int height = generator.nextInt(50) + 1;
		while (numberOfBoxes > 1) {
			boxesOutsideContainer.add(new Box(id, 2, 2, 2));
			// boxesOutsideContainer.add(new Box(id, height,
			// generator.nextInt(50) + 1, generator.nextInt(50) + 1));
			numberOfBoxes--;
			id++;
		}
		boxesOutsideContainer.add(new Box(id, 3, 4, 3));
	}

	public void startProgram() {

		generateData(5);

		Collections.sort(boxesOutsideContainer, new BoxesComparator());

		printData();
		
		boxesInsideContainer = new ArrayList<Box>();
		createSolution();
		
		printResult();
		System.out.println("\nVolume of solution: " + Double.toString(countValueOfVolumeFactor()));
		
	}

	private double countValueOfVolumeFactor() {
		int volumeOfBoxes = 0;
		double result = 0;
		for(Box boxInside: boxesInsideContainer) {
			volumeOfBoxes+=boxInside.getVolume();
		}
		result = (double) volumeOfBoxes/ (double) container.getVolume();
		return result;
	}

	private void printData() {
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

	private void printResult() {
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
					while (checkBox.getRightId() != -1) {
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
