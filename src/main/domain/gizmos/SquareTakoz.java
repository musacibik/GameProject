package main.domain.gizmos;

import java.util.ArrayList;
import java.util.List;

import main.domain.Point;
import main.domain.strategy.IMovementStrategy;
import main.physics.LineSegment;
import main.physics.Vect;
import main.ui.GameWindow;

/**
 *  OVERVIEW: SquareTakoz is a square-shaped Gizmo object which does not move.
 *  @since 0.1
 *  @version 0.1
 *  @author JusticeLeague
 */
public class SquareTakoz extends Gizmo {
	private double length = 1;
	private int lengthPixel;
	private IMovementStrategy movementStrategy;

	/**
	 * Constructor of SquareTakoz class. 
	 */
	public SquareTakoz() {
		lengthPixel = (int)(length * GameWindow.getL());
	}

	/**
	 * @effects the lengthPixel
	 */
	@Override
	public int getLengthPixel() {
		return lengthPixel;
	}

	/**
	 * @modifies lengthPixel the lengthPixel to set
	 */
	@Override
	public void setLengthPixel(int lengthPixel) {
		this.lengthPixel = lengthPixel;
		setY(getY());
	}

	/**
	 * @effects the length
	 */
	@Override
	public double getLength() {
		return length;
	}

	/**
	 * @modifies length the length to set
	 */
	@Override
	public void setLength(double length) {
		this.length = length;
		setLengthPixel((int)(length*GameWindow.getL()));
	}
	/**
	 * sets x coordinate
	 * @modifies x
	 */
	public void setX(int x) {
		super.setX(x);
	}
	/**
	 * takes x coordinates
	 * @effects x
	 */
	public int getX() {
		return super.getX();
	}
	
	/**
	 * sets y coordinates and lineSegmentList
	 * @modifies y coordinate, and lineSegmentList 
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
	 * takes y coordinate of this
	 * @effects y
	 */
	public int getY() {
		return super.getY();
	}
	/**
	 * @effects the linesegmentlist of this object
	 */
	public List<LineSegment> getLineSegmentList() {
		return super.getLineSegmentList();
	}
	/**
	 * @effects the x coordinates of this object
	 */
	public List<Integer> getXCoordinates() {
		return super.getXCoordinates();
	}
	/**
	 * @effects the y coordinates of this object
	 */
	public List<Integer> getYCoordiantes() {
		return super.getYCoordiantes();
	}
	/**
	 * 
	 * @modifies linesegments of this object
	 */
	public void setLineSegmentList(List<LineSegment> segmentList) {
		super.setLineSegmentList(segmentList);
	}
	/**
	 * 
	 * @modifies the x coordinates of this object
	 */
	public void setXCoordinates(List<Integer> list) {
		super.setXCoordinates(list);
	}
	/**
	 * 
	 * @modifies the x coordinates of this object
	 */
	public void setYCoordinates(List<Integer> list) {
		super.setYCoordinates(list);
	}
	/**
	 * 
	 * @modifies the x coordinates of this object only in an array(for painting polygon)
	 */
	public void setXArray(int[] array) {
		super.setXArray(array);
	}
	/**
	 * @effects the x coordinates of this object only in an array(for painting polygon)
	 */
	public int[] getXArray() {
		return super.getXArray();
	}
	/**
	 * 
	 * @modifies the x coordinates of this object only in an array(for painting polygon)
	 */
	public void setYArray(int[] array) {
		super.setYArray(array);
	}
	/**
	 * @effects the y coordinates of this object only in an array(for painting polygon)
	 */
	public int[] getYArray() {
		return super.getYArray();
	}
	
	/**
	 * takes movementStrategy of this
	 * @effects the movementStrategy
	 */
	public IMovementStrategy getMovementStrategy() {
		return movementStrategy;
	}
	
	/**
	 * sets movementStrategy specifying how to move for this
	 * @modifies movementStrategy
	 */
	public void setMovementStrategy(IMovementStrategy movementStrategy) {
		this.movementStrategy = movementStrategy;
	}

}
