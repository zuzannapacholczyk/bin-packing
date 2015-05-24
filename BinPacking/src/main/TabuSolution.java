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
		container = new Container(120, 20, 120);
		boxesOutsideContainer = new ArrayList<Box>();
		int id = 1;
		int height = generator.nextInt(50) + 1;
		while (numberOfBoxes > 0) {
			boxesOutsideContainer.add(new Box(id, height,
					generator.nextInt(50) + 1, generator.nextInt(50) + 1));
			numberOfBoxes--;
			id++;
		}
	}

	public void startProgram() {

		generateData(5);
		// for (Box box : boxesOutsideContainer) {
		// System.out.println("h: " + Integer.toString(box.getHeight())
		// + " l: " + Integer.toString(box.getLength()) + "w: "
		// + Integer.toString(box.getWidth()));
		// }

		Collections.sort(boxesOutsideContainer, new BoxesComparator());

		System.out.println("container");
		System.out.println("h: " + Integer.toString(container.getHeight())
				+ " l: " + Integer.toString(container.getLength()) + " w: "
				+ Integer.toString(container.getWidth()));

		System.out.println("after sort");
		for (Box box : boxesOutsideContainer) {
			System.out.println("h: " + Integer.toString(box.getHeight())
					+ " l: " + Integer.toString(box.getLength()) + " w: "
					+ Integer.toString(box.getWidth()));
		}
		boxesInsideContainer = new ArrayList<Box>();
		createSolution();
		System.out.println("outside after solution");
		if (!boxesOutsideContainer.isEmpty())
			for (Box box : boxesOutsideContainer) {
				System.out.println("h: " + Integer.toString(box.getHeight())
						+ " l: " + Integer.toString(box.getLength()) + " w: "
						+ Integer.toString(box.getWidth()));
			}

		System.out.println("\n inside after solution");
		if (!boxesInsideContainer.isEmpty())
			for (Box box : boxesInsideContainer) {
				System.out.println("h: " + Integer.toString(box.getHeight())
						+ " l: " + Integer.toString(box.getLength()) + " w: "
						+ Integer.toString(box.getWidth()));
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
					Box earlierCheckBox = new Box();
					Point iterator = new Point(0, 0, 0);

					while (iterator.getZ() <= container.getWidth()
							&& checkBox.getId() != earlierCheckBox.getId()) {
						while (iterator.getY() <= container.getHeight()
								&& checkBox.getId() != earlierCheckBox.getId()) {
							while (iterator.getX() <= container.getLength()
									&& checkBox.getId() != earlierCheckBox
											.getId()) {
								earlierCheckBox = checkBox;
								if (checkBox.getRightId() == -1
										&& checkBox.getAboveId() == -1
										&& checkBox.getInFrontId() == -1) {
									Point newPlace = new Point(checkBox
											.getPlaceInContainer().getX()
											+ checkBox.getLength(), checkBox
											.getPlaceInContainer().getY(),
											checkBox.getPlaceInContainer()
													.getZ());

									newPlace = checkIfFitsToOtherBoxes(
											outsideBox, newPlace);

									if (checkIfBoxFitsInsideContainer(newPlace,
											outsideBox.getId())) {
										// boxesOutsideContainer.remove(outsideBox);
										keepToRemove.add(outsideBox);
										outsideBox.setInContainer(newPlace);
										outsideBox.setLeftId(checkBox.getId());
										Box currentBox = new Box();
										// getBoxBehind
										Point currentPoint = newPlace;
										currentPoint.setZ(newPlace.getZ() - 1);
										currentBox = getInsideBoxWithXYZ(currentPoint);
										if (currentBox != null) {
											currentBox.setInFrontId(outsideBox
													.getId());
											outsideBox.setBehindId(currentBox
													.getId());
										}

										// getBoxUnder
										currentPoint = newPlace;
										currentPoint.setY(newPlace.getY() - 1);
										currentBox = getInsideBoxWithXYZ(currentPoint);
										if (currentBox != null) {
											currentBox.setAboveId(outsideBox
													.getId());
											outsideBox.setUnderId(currentBox
													.getId());
										}

										checkBox.setRightId(outsideBox.getId());
										boxesInsideContainer.add(outsideBox);
									}

								} else {
									checkBox = getInsideBoxWithId(checkBox
											.getRightId());
								}

							}
							iterator.setX(0);
							iterator.setY(checkBox.getPlaceInContainer().getY()
									+ checkBox.getHeight());
						}
						iterator.setX(0);
						iterator.setY(0);
						iterator.setZ(checkBox.getPlaceInContainer().getZ()
								+ checkBox.getWidth());
					}
				}
			}
		}
		boxesOutsideContainer.removeAll(keepToRemove);
	}

	private Point checkIfFitsToOtherBoxes(Box outsideBox, Point newPlace) {

		int i = 0;
		int j = 0;
		Point resultOfCheck = newPlace;
		while (i < 3) {
			if (i == 0)
				resultOfCheck.setY(resultOfCheck.getY());
			if (i == 1)
				resultOfCheck.setY(resultOfCheck.getY()
						+ (outsideBox.getHeight() / 2));
			if (i == 2)
				resultOfCheck.setY(resultOfCheck.getY()
						+ (outsideBox.getHeight()));
			while (j < 3) {
				resultOfCheck = newPlace;
				if (j == 0)
					resultOfCheck.setX(resultOfCheck.getX());
				if (j == 1)
					resultOfCheck.setX(resultOfCheck.getX()
							+ (outsideBox.getLength() / 2));
				if (j == 2)
					resultOfCheck.setX(resultOfCheck.getX()
							+ (outsideBox.getLength()));
				resultOfCheck = checkIfBoxesBehindFit(resultOfCheck);

				if (resultOfCheck != new Point(-1, -1, -1)) {
					newPlace = resultOfCheck;
				}
				j++;
			}
			i++;
		}

		return newPlace;
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

	public Point checkIfBoxesBehindFit(Point corner) {
		Box boxBehind = getInsideBoxWithXYZ(corner);
		Point newCorner = new Point(-1, -1, -1);
		int newCornerZ;
		if (boxBehind != null) {
			newCornerZ = boxBehind.getPlaceInContainer().getZ()
					+ boxBehind.getWidth();
			newCorner.setZ(newCornerZ);
		}
		return newCorner;
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
