package main;

import java.util.ArrayList;
import java.util.List;

public class ResultBehind{


    private List<Box> boxes;
    private Point point;

    public ResultBehind(Box box, Point point) {
    	this.boxes = new ArrayList<Box>();
        this.setBox(box);
        this.setPoint(point);
    }
    
    public ResultBehind(List<Box> boxes, Point point) {
    	this.boxes = new ArrayList<Box>();
        this.setBoxes(boxes);
        this.setPoint(point);
    }

	private void setBoxes(List<Box> boxes2) {
			this.boxes.addAll(boxes2);
		
	}

	public List<Box> getBoxes() {
		return boxes;
	}

	public void setBox(Box box) {
		this.boxes.add(box);
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}
}

