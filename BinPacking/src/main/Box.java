package main;

public class Box extends Container {

	/**
	 * -1 oznacza, ze jest poza kontenerem polozenie w kontenerze
	 */
	public int id;
	private int x = -1;
	private int y = -1;
	private int z = -1;

	private int aboveId = -1;
	private int underId = -1;
	private int rightId = -1;
	private int leftId = -1;
	private int behindId = -1;
	private int inFrontId = -1;
	
	public Box() {
		this.id = -1;
	}
	
	public Box(int id, int height, int width, int length) {
		super(height, width, length);
		this.id = id;
	}

	public void setInContainer(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void setInContainer(Point point) {
		this.x = point.getX();
		this.y = point.getY();
		this.z = point.getZ();
	}

	public Point getPlaceInContainer() {
		return new Point(x, y, z);
	}

	public int getId() {
		return id;
	}

	public int getAboveId() {
		return aboveId;
	}

	public void setAboveId(int aboveId) {
		this.aboveId = aboveId;
	}

	public int getUnderId() {
		return underId;
	}

	public void setUnderId(int underId) {
		this.underId = underId;
	}

	public int getRightId() {
		return rightId;
	}

	public void setRightId(int rightId) {
		this.rightId = rightId;
	}

	public int getLeftId() {
		return leftId;
	}

	public void setLeftId(int leftId) {
		this.leftId = leftId;
	}

	public int getBehindId() {
		return behindId;
	}

	public void setBehindId(int behindId) {
		this.behindId = behindId;
	}

	public int getInFrontId() {
		return inFrontId;
	}

	public void setInFrontId(int inFrontId) {
		this.inFrontId = inFrontId;
	}
}
