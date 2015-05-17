package main;

public class Container {
	private int height;
	private int width;
	private int length;

	public Container(int height, int width, int length) {
		this.setHeight(height);
		this.setLength(length);
		this.setWidth(width);
	}

	public int getVolume() {
		return this.getHeight() * this.getWidth() * this.getLength();
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

}
