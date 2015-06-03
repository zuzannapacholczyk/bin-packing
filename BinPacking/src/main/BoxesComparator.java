package main;

import java.util.Comparator;

public class BoxesComparator implements Comparator<Box>{
	
	    public int compare(Box o1, Box o2) {
	        return o2.getVolume() - o1.getVolume();
	    }
	
}
