package main.domain.gizmos;

import java.util.ArrayList;
import java.util.List;

import main.domain.Paintable;
import main.domain.Point;
import main.domain.strategy.IMovementStrategy;
import main.physics.Circle;
import main.physics.LineSegment;

public abstract class Gizmo extends Paintable {

	private int x;
	private int y;
	private List<LineSegment> lineSegmentList;
	private List<Integer> xCoordinates;
	private List<Integer> yCoordinates;
	private int[] xArray;
	private int[] yArray;
	private List<Circle> cornerCircleList;
	private double length;
	private int lengthPixel;
	private boolean rescale;
	private IMovementStrategy movementStrategy;
	private ArrayList<Point> borderPoints;

	public Gizmo() {
		this.rescale = false;
		lineSegmentList = new ArrayList<LineSegment>();
		xCoordinates = new ArrayList<>();
		yCoordinates = new ArrayList<>();
		setBorderPoints(new ArrayList<>());
	}

	public void rotateClockwise() {
		if (this instanceof LeftTokat) {
			((LeftTokat) this).setOrientation(((LeftTokat) this).getOrientation().rotateClockwise());

		} else if (this instanceof RightTokat) {
			((RightTokat) this).setOrientation(((RightTokat) this).getOrientation().rotateClockwise());

		} else if (this instanceof TriangleTakoz) {
			((TriangleTakoz) this).setOrientation(((TriangleTakoz) this).getOrientation().rotateClockwise());

		}
	}

	public void setLineSegmentList(List<LineSegment> list) {
		lineSegmentList = list;
		List<Integer> xS = new ArrayList<>();
		List<Integer> yS = new ArrayList<>();
		List<Circle> cornerList = new ArrayList<Circle>();

		for (LineSegment segment : lineSegmentList) {
			xS.add((int) segment.p1().x());
			yS.add((int) segment.p1().y());
		}

		setXCoordinates(xS);
		setYCoordinates(yS);

		int[] xArray = new int[lineSegmentList.size()];
		int[] yArray = new int[lineSegmentList.size()];

		for (int i = 0; i < lineSegmentList.size(); i++) {
			xArray[i] = xS.get(i);
			yArray[i] = yS.get(i);
		}

		setXArray(xArray);
		setYArray(yArray);

		for (LineSegment lineSegment : lineSegmentList) {
			Circle cornerCircle = new Circle(lineSegment.p1(), 1);
			cornerList.add(cornerCircle);
		}

		setCornerCircleList(cornerList);
	}
	
	public String toString(){
		if(this instanceof LeftTokat){
			return ("LeftTokat: " + "\n" +
					"X coordinate: "+ this.getX() +"\n"  +
					"Y coordinate: "+ this.getY() + "\n"  +
					"Orientation: "+ ((LeftTokat)this).getOrientation().toString() + "\n"  +
					"Angular Velocity: "+ LeftTokat.angVel + "\n"  +
					"IsMoving: " + LeftTokat.isIsMoving() + "\n" +
					"Movement: "+ this.getMovementStrategy().toString() + "\n" 
					);
			
		} else if(this instanceof RightTokat){
			
			return ("RightTokat: "+ "\n" +
					"X coordinate: "+ this.getX() + "\n"  +
					"Y coordinate: "+ this.getY() +"\n" +
					"Orientation: "+ ((RightTokat)this).getOrientation().toString() + "\n"  +
					"Angular Velocity: "+ RightTokat.angVel + "\n"  +
					"IsMoving: " + RightTokat.isIsMoving()+ "\n"  +
					"Movement: "+ this.getMovementStrategy().toString() + "\n" 
					
					);
			
		} else if(this instanceof TriangleTakoz){
			
			return ("TriangleTakoz: "+ "\n" +
					"X coordinate: "+ this.getX() + "\n"  +
					"Y coordinate: "+ this.getY() + "\n"  +
					"Orientation: "+ ((TriangleTakoz)this).getOrientation().toString()+ "\n"  +
					"Movement: "+ this.getMovementStrategy().toString() + "\n" 
			);			
		}
		else if(this instanceof SquareTakoz){
			
			return ("SquareTakoz: "+ "\n" +
					"X coordinate: "+ this.getX() + "\n" +
					"Y coordinate: "+ this.getY() + "\n" +
					"Movement: "+ this.getMovementStrategy().toString() + "\n" 
					);			
		}
		else if(this instanceof Firildak){
			
			return ("Firildak: "+ "\n" +
					"X coordinate: "+ this.getX() + "\n"  +
					"Y coordinate: "+ this.getY() + "\n"  +
					"Angular Velocity: "+ ((Firildak)this).getAngularVelocity() + "\n"  +
					"Movement: "+ this.getMovementStrategy().toString() + "\n" 
					);			
		}
		else if(this instanceof Cezerye){
			
			return ("Cezerye" + "\n" +
					"X coordinate: "+ this.getX() + "\n"  +
					"Y coordinate: "+ this.getY() + "\n"  +
					"Appear Time: "+ ((Cezerye)this).getAppearTime() + "\n" +
					"Visibility: "+ ((Cezerye)this).isVisible() +"\n"  +
					"Movement: "+ this.getMovementStrategy().toString() + "\n" 
					);			
		}
		else{
			return("Gizmo: "+ "\n" +
					"X coordinate: "+ this.getX() + "\n"  +
					"Y coordinate: "+ this.getY() + "\n"  +
					"Movement: "+ this.getMovementStrategy().toString() + "\n" 
					);
		}
		
	}
	
	@Override
	public IMovementStrategy getMovementStrategy() {
		return movementStrategy;
	}

	@Override
	public void setMovementStrategy(IMovementStrategy movementStrategy) {
		this.movementStrategy = movementStrategy;
	}
	
	public boolean isRescaled() {
		return rescale;
	}

	public void setRescale(boolean shrink) {
		this.rescale = shrink;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getX() {
		return x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getY() {
		return y;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public List<LineSegment> getLineSegmentList() {
		return lineSegmentList;
	}

	public List<Integer> getXCoordinates() {
		return xCoordinates;
	}

	public List<Integer> getYCoordiantes() {
		return yCoordinates;
	}

	/**
	 * @return the cornerCircleList
	 */
	public List<Circle> getCornerCircleList() {
		return cornerCircleList;
	}

	/**
	 * @param cornerCircleList
	 *            the cornerCircleList to set
	 */
	public void setCornerCircleList(List<Circle> cornerCircleList) {
		this.cornerCircleList = cornerCircleList;
	}

	public void setXCoordinates(List<Integer> list) {
		xCoordinates = list;
	}

	public void setYCoordinates(List<Integer> list) {
		xCoordinates = list;
	}

	/**
	 * @return
	 */
	public double getLength() {
		length = 0;
		return length;
	}

	/**
	 * 
	 * @return
	 */
	public boolean repOK() {
		return true;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getLengthPixel() {
		lengthPixel = 0;
		return lengthPixel;
	}
	
	/**
	 * 
	 * @param i
	 */
	public void setLengthPixel(int i) {
		this.lengthPixel = i;
	}
	
	public void setXArray(int[] array) {
		xArray = array;
	}

	public int[] getXArray() {
		return xArray;
	}

	public void setYArray(int[] array) {
		yArray = array;
	}

	public int[] getYArray() {
		return yArray;
	}
	
	public ArrayList<Point> getBorderPoints() {
		return borderPoints;
	}

	public void setBorderPoints(ArrayList<Point> borderPoints) {
		this.borderPoints = borderPoints;
	}
	
}