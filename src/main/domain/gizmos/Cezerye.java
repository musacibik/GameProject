package main.domain.gizmos;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import main.domain.Board;
import main.domain.Engel;
import main.domain.Point;
import main.domain.strategy.IMovementStrategy;
import main.physics.LineSegment;
import main.physics.Vect;
import main.ui.GameWindow;

/**
 *  OVERVIEW: This class represents Cezerye object. Cezerye is a subclass of Gizmo class. Cezerye is a special kind of square-shaped 
 * 	Gizmo which gives special power-ups to the player which hits it with the ball.  
 *  @since 0.1
 *  @version 0.1
 *  @author JusticeLeague
 */
public class Cezerye extends Gizmo {

	private IMovementStrategy movementStrategy;
	private double length=1;
	private int lengthPixel;	
	private double appearTime;
	private boolean visible;
	private boolean collidesWith;
	private boolean edited;
	private Random rd;

	/**
	 * Constructor of Cezerye class. 
	 */
	public Cezerye() {
		lengthPixel = (int)(length*GameWindow.getL());
		setEdited(false);
		setAppearTime(5);
		visible = true;
		collidesWith = false;
		rd = new Random();
	}
	
	
	
	/**
	 * @effects: Cezerye's x and y coordinates are set randomly in a way that it does not overlap
	 */
	public void random() {
		boolean available = true;
		do {
			available = true;
			setX(rd.nextInt(Board.BOARD_SIZE-(int)getLength())*GameWindow.getL());
			setY(rd.nextInt(Board.BOARD_SIZE-2*Engel.ENGEL_HEIGHT-(int)getLength())*GameWindow.getL());
			for(Gizmo gizmo : Board.getInstance().getGizmos()) {
				if(!(gizmo instanceof Cezerye)) {
					for(Point gizmoPoint : gizmo.getBorderPoints()) {
						for(Point point : getBorderPoints()) {
							if(gizmoPoint.getX() == point.getX() && gizmoPoint.getY() == point.getY()) {
								available = false;
							}
						}
					}
				}
			}
			if(!((getX() >= 0
					&& getX() <= GameWindow.getL() * (Board.BOARD_SIZE/2 - 1)) ||
					(getX() >= GameWindow.getL() * (Board.BOARD_SIZE/2 + 1)
							&& getX() <= GameWindow.getL() * (Board.BOARD_SIZE - 1)))) {
				available = false;
			}
		}
		while(!available);
	}

	/**
	 * @effects return visible (the visibility field of the Cezerye)
	 */
	public boolean isVisible() {
		return visible;
	}

	/**
	 * @modifies  visible (the visibility field of the Cezerye) 
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	/**
	 * 
	 * @modifies x coordinate of the Cezerye
	 */
	public void setX(int x) {
		super.setX(x);
	}

	/**
	 * 
	 * @effects returns the super class' (Gizmo's) getX() method
	 */
	public int getX() {
		return super.getX();
	}


	/**
	 * 
	 * @modifies y coordinate of the Cezerye
	 * @effects segmentList of the Cezerye object which consists of segments is set to proper values to handle the collision
	 *  between cezerye and the ball. 
	 *  Cezerye object's upper-left corner's coordinate is added to the borderPoints list of the object to handle overlaps with
	 *   another objects on the board.
	 */
	public void setY(int y) {
		super.setY(y);
		List<LineSegment> segmentList = new ArrayList<LineSegment>();

		segmentList.add(new LineSegment(new Vect(getX(), y), new Vect(getX()+lengthPixel, y)));
		segmentList.add(new LineSegment(new Vect(getX()+lengthPixel, y), new Vect(getX()+lengthPixel, y+lengthPixel)));
		segmentList.add(new LineSegment(new Vect(getX()+lengthPixel, y+lengthPixel), new Vect(getX(), y+lengthPixel)));
		segmentList.add(new LineSegment(new Vect(getX(), y+lengthPixel), new Vect(getX(), y)));	

		setLineSegmentList(segmentList);

		super.setBorderPoints(new ArrayList<Point>());
		super.getBorderPoints().add(new Point(getX(), y));
	}

	/**
	 * 
	 * @effects returns the super class' (Gizmo's) getY() method
	 */
	public int getY() {
		return super.getY();
	}

	/**
	 * 
	 * @effects returns the super class' (Gizmo's) getLineSegmentList() method
	 */
	public List<LineSegment> getLineSegmentList() {
		return super.getLineSegmentList();
	}

	/**
	 * 
	 * @effects  returns the super class' (Gizmo's) getXCoordinates() method
	 */
	public List<Integer> getXCoordinates() {
		return super.getXCoordinates();
	}

	/**
	 * 
	 * @effects  returns the super class' (Gizmo's) getYCoordiantes() method
	 */
	public List<Integer> getYCoordiantes() {
		return super.getYCoordiantes();
	}
	/**
	 * 
	 * @modifies lineSegmentList of the Cezerye object
	 */
	public void setLineSegmentList(List<LineSegment> list) {
		super.setLineSegmentList(list);
	}
	/**
	 * 
	 * @modifies XCoordinates list of the Cezerye object
	 */
	public void setXCoordinates(List<Integer> list) {
		super.setXCoordinates(list);
	}
	/**
	 * 
	 * @modifies YCoordinates list of the Cezerye object
	 */
	public void setYCoordinates(List<Integer> list) {
		super.setYCoordinates(list);
	}
	/**
	 * 
	 * @modifies XCoordinates array of the Cezerye object
	 */
	public void setXArray(int[] array) {
		super.setXArray(array);
	}
	/**
	 * 
	 * @effects returns the super class' (Gizmo's) getXArray() method
	 */
	public int[] getXArray() {
		return super.getXArray();
	}

	/**
	 * 
	 * @modifies YCoordinates array of the Cezerye object
	 */
	public void setYArray(int[] array) {
		super.setYArray(array);
	}

	/**
	 * 
	 * @effects returns the super class' (Gizmo's) getYArray() method
	 */
	public int[] getYArray() {
		return super.getYArray();
	}

	/**
	 * 
	 * @effects returns appearTime
	 */
	public double getAppearTime() {
		return appearTime;
	}


	/**
	 * 
	 * @modifies appearTime of the Cezerye
	 */
	public void setAppearTime(double appearTime) {
		this.appearTime = appearTime;
	}

	/**
	 * 
	 * @effects returns movementStrategy
	 */
	public IMovementStrategy getMovementStrategy() {
		return movementStrategy;
	}

	/**
	 * 
	 * @modifies movementStrategy of the Cezerye
	 */
	public void setMovementStrategy(IMovementStrategy movementStrategy) {
		this.movementStrategy = movementStrategy;
	}

	/**
	 * 
	 * @effects returns collidesWith (the field for checking whether Cezerye collidies with another object)
	 */
	public boolean isCollidesWith() {
		return collidesWith;
	}

	/**
	 * 
	 * @modifies collidesWith (the field for checking whether Cezerye collidies with another object) of the Cezerye
	 */
	public void setCollidesWith(boolean collidesWith) {
		this.collidesWith = collidesWith;
	}

	/**
	 * @effects returns lengthPixel
	 * @overrides Its superclass (Gizmo's)  getLengthPixel() method
	 */
	@Override
	public int getLengthPixel() {
		return lengthPixel;
	}

	/**
	 * @modifies lengthPixel  of the Cezerye
	 * and lineSegmentList by calling setY() method
	 * @overrides Its superclass (Gizmo's)  setLengthPixel() method
	 */
	@Override
	public void setLengthPixel(int lengthPixel) {
		this.lengthPixel = lengthPixel;
		setY(getY());
	}

	/**
	 * @effects returns length (in terms of L)  of the Cezerye
	 * @overrides Its superclass (Gizmo's)  getLength() method
	 */
	@Override
	public double getLength() {
		return length;
	}

	/**
	 * @modifies length (in terms of L) of the Cezerye
	 * and lengthPixel of the Cezerye
	 * @overrides Its superclass (Gizmo's)  setLength() method
	 */
	@Override
	public void setLength(double length) {
		this.length = length;
		setLengthPixel((int)(length*GameWindow.getL()));
	}

	public boolean isEdited() {
		return edited;
	}

	public void setEdited(boolean edited) {
		this.edited = edited;
	}

}

