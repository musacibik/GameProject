package main.domain.gizmos;

import java.util.ArrayList;
import java.util.List;

import main.domain.Orientation;
import main.domain.Point;
import main.domain.strategy.IMovementStrategy;
import main.physics.Angle;
import main.physics.Geometry;
import main.physics.LineSegment;
import main.physics.Vect;
import main.ui.GameWindow;

/**
 * OVERVIEW: TriangleTakoz is a triangle-shaped Gizmo object which does not
 * move.
 * 
 * @since 0.1
 * @version 0.1
 * @author JusticeLeague
 */
public class TriangleTakoz extends Gizmo {

	private Orientation orientation = Orientation.ORIENTATION_ZERO;
	private double length = 1;
	private int lengthPixel;
	private IMovementStrategy movementStrategy;

	/**
	 * Constructor of TriangleTakoz class.
	 */
	public TriangleTakoz() {
		lengthPixel = (int) (length * GameWindow.getL());
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
		setLengthPixel((int) (length * GameWindow.getL()));
	}
	
	/**
	 * takes movementStrategy of this
	 * 
	 * @effects the movementStrategy
	 */
	public IMovementStrategy getMovementStrategy() {
		return movementStrategy;
	}

	/**
	 * sets movementStrategy specifying how to move for this
	 * 
	 * @modifies movementStrategy
	 */
	public void setMovementStrategy(IMovementStrategy movementStrategy) {
		this.movementStrategy = movementStrategy;
	}

	/**
	 * sets x coordinate
	 * 
	 * @modifies x
	 */
	public void setX(int x) {
		super.setX(x);
	}

	/**
	 * takes x coordinates
	 * 
	 * @effects x
	 */
	public int getX() {
		return super.getX();
	}

	/**
	 * sets y coordinates and lineSegmentList
	 * 
	 * @modifies y coordinate, and lineSegmentList
	 */
	public void setY(int y) {
		super.setY(y);
		setOrientation(getOrientation());

		ArrayList<Point> borderList= new ArrayList<>();
		borderList.add(new Point(getX(), y));
		super.setBorderPoints(borderList);

	}

	/**
	 * takes y coordinate of this
	 * 
	 * @effects y
	 */
	public int getY() {
		return super.getY();
	}

	/**
	 * 
	 * @effects the orientation of this object
	 */
	public Orientation getOrientation() {
		return orientation;
	}

	/**
	 * 
	 * @modifies orientation of the object
	 */
	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
		
		List<LineSegment> segmentList = new ArrayList<LineSegment>();

		LineSegment segment1 = new LineSegment(new Vect(getX(), getY()), new Vect(getX(), getY()+lengthPixel));
		LineSegment segment2 = new LineSegment(new Vect(getX(), getY()+lengthPixel), new Vect(getX() + lengthPixel, getY() + lengthPixel));
		LineSegment segment3 = new LineSegment(new Vect(getX() + lengthPixel, getY() + lengthPixel), new Vect(getX(), getY()));

		Vect center = new Vect(getX() + getLengthPixel() / 2, getY() + getLengthPixel() / 2);

		if (orientation == Orientation.ORIENTATION_ZERO) {
		}

		else if (orientation == Orientation.ORIENTATION_90) {

			segment1 = Geometry.rotateAround(segment1, center, Angle.DEG_90);
			segment2 = Geometry.rotateAround(segment2, center, Angle.DEG_90);
			segment3 = Geometry.rotateAround(segment3, center, Angle.DEG_90);
		}

		else if (orientation == Orientation.ORIENTATION_180) {
			
			segment1 = Geometry.rotateAround(segment1, center, Angle.DEG_180);
			segment2 = Geometry.rotateAround(segment2, center, Angle.DEG_180);
			segment3 = Geometry.rotateAround(segment3, center, Angle.DEG_180);
		}

		else if (orientation == Orientation.ORIENTATION_270) {
			segment1 = Geometry.rotateAround(segment1, center, Angle.DEG_270);
			segment2 = Geometry.rotateAround(segment2, center, Angle.DEG_270);
			segment3 = Geometry.rotateAround(segment3, center, Angle.DEG_270);
		}

		segmentList.add(segment1);
		segmentList.add(segment2);
		segmentList.add(segment3);

		setLineSegmentList(segmentList);
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
	public void setLineSegmentList(List<LineSegment> list) {
		super.setLineSegmentList(list);
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
	 * @modifies the x coordinates of this object only in an array(for painting
	 *           polygon)
	 */
	public void setXArray(int[] array) {
		super.setXArray(array);
	}

	/**
	 * @effects the x coordinates of this object only in an array(for painting
	 *          polygon)
	 */
	public int[] getXArray() {
		return super.getXArray();
	}

	/**
	 * 
	 * @modifies the y coordinates of this object only in an array(for painting
	 *           polygon)
	 */
	public void setYArray(int[] array) {
		super.setYArray(array);
	}

	/**
	 * @effects the y coordinates of this object only in an array(for painting
	 *          polygon)
	 */
	public int[] getYArray() {
		return super.getYArray();
	}

}
