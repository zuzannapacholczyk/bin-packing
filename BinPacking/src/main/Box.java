package main;

public class Box extends Container{

	/**
	 * 	-1 oznacza, ze jest poza kontenerem
	 *  polozenie w kontenerze
	 */
	private int x = -1;
	private int y = -1;
	private int z = -1;
	
	public Box(int height, int width, int length) {
		super(height, width, length);
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
		return new Point(x,y,z);
	}
}
