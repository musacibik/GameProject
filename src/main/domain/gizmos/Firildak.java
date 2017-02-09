package main.domain.gizmos;

import java.util.ArrayList;
import java.util.List;

import main.domain.Board;
import main.domain.Point;
import main.domain.strategy.IMovementStrategy;
import main.physics.Angle;
import main.physics.LineSegment;
import main.physics.Vect;
import main.ui.GameWindow;

/**
 * OVERVIEW: Firildak is a special type of square-shaped Gizmo object which rotates around its center with
 * a constant angular velocity
 *  @since 0.1
 *  @version 0.1
 *  @author JusticeLeague
 */
public class Firildak extends Gizmo{
	
	private int angularVelocity;
	private IMovementStrategy movementStrategy;
	private double length=1;
	private int lengthPixel;	

	private Angle radian;
	private int angle;
	
	/**
	 * Constructor of Firildak class. 
	 */
	public Firildak() {
		lengthPixel=(int)(length*GameWindow.getL());
	}

	/**
	 * @effects  returns angularVelocity
	 */
	public int getAngularVelocity() {
		return angularVelocity;
	}

	/**
	 * @modifies angularVelocity of the Firildak
	 */
	public void setAngularVelocity(int angularVelocity) {
		this.angularVelocity = angularVelocity;
	}
	
	/**
	 * @effects returns lengthPixel
	 */
	@Override
	public int getLengthPixel() {
		return lengthPixel;
	}

	/**
	 * @modifies lenghtPixel of the Firildak and lineSegmentList of the Firildak by calling setY() method
	 */
	@Override
	public void setLengthPixel(int lengthPixel) {
		this.lengthPixel = lengthPixel;
		setY(getY());
	}

	/**
	 * @effects returns length (in terms of L)
	 */
	@Override
	public double getLength() {
		return length;
	}

	/**
	 * @modifies length (in terms of L) of the Firildak and lenghtPixel of the Firildak
	 */
	@Override
	public void setLength(double length) {
		this.length = length;
		setLengthPixel((int)(length*GameWindow.getL()));
	}


	/**
	 * @modifies x coordinate, angularVelocity and radian. Angular velocity value is selected according to the position of the Firildak
	 * on the board.
	 */
	public void setX(int x) {
		super.setX(x);
		if(getX() > Board.BOARD_SIZE*GameWindow.getL()/2) {
			angularVelocity = -2;
		}
		else {
			angularVelocity = 2;
		}
		
			radian = new Angle(Math.toRadians(angularVelocity));
	}
	
	/**
	 * @effects returns the superclass' (Gizmo's) getX() method
	 */
	public int getX() {
		return super.getX();
	}
	
	/**
	 * @modifies y coordinate, lineSegmentList and borderPoints list of the Firildak
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
		super.getBorderPoints().add(new Point(getX()-lengthPixel, y));
		super.getBorderPoints().add(new Point(getX()+lengthPixel, y));
		super.getBorderPoints().add(new Point(getX(), y+lengthPixel));
		super.getBorderPoints().add(new Point(getX(), y-lengthPixel));
	}
	
	/**
	 * @effects returns the superclass' (Gizmo's) getY() method
	 */
	public int getY() {
		return super.getY();
	}
	
	
	/**
	 * @effects returns radian of the Firildak
	 */
	public Angle getRadian() {
		return radian;
	}
	
	/**
	 * @modifies radian of the Firildak
	 */
	public void setRadian(Angle radian) {
		this.radian = radian;
	}
	
	/**
	 * @effects  returns angle of the Firildak
	 */
	public int getAngle() {
		return angle;
	}
	
	/**
	 * @modifies angle of the Firildak
	 */
	public void setAngle(int angle) {
		this.angle = angle;
	}
	
	/**
	 * @effects returns the superclass' (Gizmo's) getLineSegmentList() method
	 */
	public List<LineSegment> getLineSegmentList() {
		return super.getLineSegmentList();
	}
	
	/**
	 * @effects returns the superclass' (Gizmo's) getXCoordinates() method
	 */
	public List<Integer> getXCoordinates() {
		return super.getXCoordinates();
	}
	
	/**
	 * @effects returns the superclass' (Gizmo's) getYCoordiantes() method
	 */
	public List<Integer> getYCoordiantes() {
		return super.getYCoordiantes();
	}
	
	/**
	 * @modifies lineSegmentList of the Firildak by calling the superclass' (Gizmo's) setLineSegmentList(List<LineSegment> list) method
	 */
	public void setLineSegmentList(List<LineSegment> list) {
		super.setLineSegmentList(list);
	}
	
	/**
	 * @modifies XCoordinates of the Firildak by calling the superclass' (Gizmo's) setXCoordinates(List<Integer> list) method
	 */
	public void setXCoordinates(List<Integer> list) {
		super.setXCoordinates(list);
	}
	
	/**
	 *  @modifies YCoordinates of the Firildak by calling the superclass' (Gizmo's) setYCoordinates(List<Integer> list) method
	 */
	public void setYCoordinates(List<Integer> list) {
		super.setYCoordinates(list);
	}
	
	/**
	 *  @modifies XArray of the Firildak by calling the superclass' (Gizmo's) setXArray(int[] array) method
	 */
	public void setXArray(int[] array) {
		super.setXArray(array);
	}
	
	/**
	 * @effects returns the superclass' (Gizmo's) getXArray() method
	 */
	public int[] getXArray() {
		return super.getXArray();
	}
	
	/**
	 * @modifies YArray of the Firildak by calling the superclass' (Gizmo's) setYArray(int[] array) method
	 */
	public void setYArray(int[] array) {
		super.setYArray(array);
	}
	
	/**
	 * @effects returns the superclass' (Gizmo's) getYArray() method
	 */
	public int[] getYArray() {
		return super.getYArray();
	}
	
	/**
	 * @effects returns movementStrategy of the Firildak
	 */
	public IMovementStrategy getMovementStrategy() {
		return movementStrategy;
	}
	
	/**
	 * @modifies movementStrategy of the Firildak
	 */
	public void setMovementStrategy(IMovementStrategy movementStrategy) {
		this.movementStrategy = movementStrategy;
	}
	
}
