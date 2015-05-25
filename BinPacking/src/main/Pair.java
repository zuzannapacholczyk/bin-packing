package main;

public class Pair<Box, Point> {


	    private Box box;
	    private Point point;

	    public Pair(Box box, Point point) {

	        this.setBox(box);
	        this.setPoint(point);
	    }

		public Box getBox() {
			return box;
		}

		public void setBox(Box box) {
			this.box = box;
		}

		public Point getPoint() {
			return point;
		}

		public void setPoint(Point point) {
			this.point = point;
		}
}
