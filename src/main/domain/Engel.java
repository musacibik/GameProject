package main.domain;

import java.util.ArrayList;
import java.util.List;

import main.domain.strategy.IMovementStrategy;
import main.domain.strategy.NoMovementStrategy;
import main.physics.Circle;
import main.physics.LineSegment;
import main.physics.Vect;
import main.ui.GameWindow;

/**
 *  OVERVIEW: This class represents an Engel object that divides the 
 *  movement areas of the Cezmis. It is similar to a volleyball net.
 *  @since 0.1
 *  @version 0.1
 *  @author JusticeLeague
 */
public class Engel extends Paintable{
		
	private static Engel instance;
	public static final int ENGEL_HEIGHT=3;
	private int x;
	private int y;
	private List<LineSegment> lineSegmentList;
	private List<Integer> xCoordinates;
	private List<Integer> yCoordinates;
	private int[] xArray;
	private int[] yArray;
	private List<Circle> cornerCircleList;
	private IMovementStrategy movementStrategy;
	
	/**
	 * The primary constructor for the Engel object. Sets the X and Y coordinates 
	 * of the Engel object to a static variable that cannot be changed
	 */
	private Engel() {
		this.x = (int) ((12.375)*GameWindow.getL());
		this.y = (int) ((Board.BOARD_SIZE-ENGEL_HEIGHT)*GameWindow.getL());

		lineSegmentList = new ArrayList<LineSegment>();
		xCoordinates = new ArrayList<>();
		yCoordinates = new ArrayList<>();
		List<LineSegment> segmentList = new ArrayList<LineSegment>();
		
		segmentList.add(new LineSegment(new Vect(x, y), new Vect(x+GameWindow.getL()/4, y)));
		segmentList.add(new LineSegment(new Vect(x+GameWindow.getL()/4, y), new Vect(x+GameWindow.getL()/4, y+GameWindow.getL()*3)));
		segmentList.add(new LineSegment(new Vect(x+GameWindow.getL()/4, y+GameWindow.getL()*3), new Vect(x, y+GameWindow.getL()*3)));
		segmentList.add(new LineSegment(new Vect(x, y+GameWindow.getL()*3), new Vect(x, y)));
		
		setLineSegmentList(segmentList);
		
		movementStrategy= NoMovementStrategy.getInstance();
	}
	/**
	 * Invokes the private Engel constructor
	 * @effects an instance of a Engel object, which is immutable.
	 */
	public static synchronized Engel getInstance() {
		if (instance == null) {
			instance = new Engel();
		}

		return instance;
	}

	/**
	 * 
	 * @effects x Coordinate of Engel
	 */
	private int getX() {
		return x;
	}
	
	/**
	 * 
	 * @effects y Coordinate of Engel
	 */
	private int getY() {
		return y;
	}

	public IMovementStrategy getMovementStrategy() {
		return movementStrategy;
	}

	public void setMovementStrategy(IMovementStrategy movementStrategy) {
		this.movementStrategy = movementStrategy;
	}
	
	/**
	 * 
	 * @effects the line segment of the Engel
	 */
	public List<LineSegment> getLineSegmentList() {
		return lineSegmentList;
	}
	
	/**
	 * 
	 * @effects the x coordinates of the Engel
	 */
	public List<Integer> getXCoordinates() {
		return xCoordinates;
	}
	/**
	 * 
	 * @effects the y coordinates of the Engel
	 */
	public List<Integer> getYCoordiantes() {
		return yCoordinates;
	}
	
	/**
	 * @effects the cornerCircleList
	 */
	public List<Circle> getCornerCircleList() {
		return cornerCircleList;
	}

	/**
	 * @param cornerCircleList the cornerCircleList to set
	 */
	public void setCornerCircleList(List<Circle> cornerCircleList) {
		this.cornerCircleList = cornerCircleList;
	}

	/**
	 * 
	 * @param list the line segment list to set
	 */
	public void setLineSegmentList(List<LineSegment> list) {
		lineSegmentList = list;
		List<Integer> xS = new ArrayList<>();
		List<Integer> yS = new ArrayList<>();
		List<Circle> cornerList = new ArrayList<Circle>();
		
		for(LineSegment segment : lineSegmentList) {
			xS.add((int)segment.p1().x());
			yS.add((int)segment.p1().y());
		}
		
		setXCoordinates(xS);
		setYCoordinates(yS);
		
		int[] xArray = new int[lineSegmentList.size()];
		int[] yArray = new int[lineSegmentList.size()];
		
		for(int i=0; i<lineSegmentList.size(); i++) {
			xArray[i] = xS.get(i);
			yArray[i] = yS.get(i);
		}
		
		setXArray(xArray);
		setYArray(yArray);
		
		for(LineSegment lineSegment : lineSegmentList) {
			Circle cornerCircle = new Circle(lineSegment.p1(), 1);
			cornerList.add(cornerCircle);
		}
		
		setCornerCircleList(cornerList);
	}
	
	/**
	 * 
	 * @param list x coordinate list to set
	 */
	public void setXCoordinates(List<Integer> list) {
		xCoordinates = list;
	}
	/**
	 * 
	 * @param list y coordinates list to set
	 */
	public void setYCoordinates(List<Integer> list) {
		xCoordinates = list;
	}
	/**
	 * 
	 * @param array x Array 
	 */
	public void setXArray(int[] array) {
		xArray = array;
	}
	
	/**
	 * 
	 * @effects x Array
	 */
	public int[] getXArray() {
		return xArray;
	}
	
	/**
	 * 
	 * @param array y Array
	 */
	public void setYArray(int[] array) {
		yArray = array;
	}
	/**
	 * 
	 * @effects y Array
	 */
	public int[] getYArray() {
		return yArray;
	}
	
	@Override
	public String toString() {
		return "Engel: \n" + "X: " + getX() + ": \n" + "Y: " + getY();
	}
	
	public boolean repOK() {
		if(getX()<0 || getY()<0 || getX()>GameWindow.getL()*Board.BOARD_SIZE || getY()>GameWindow.getL()*Board.BOARD_SIZE) {
			return false;
		}
		return true;
	}
    
}
