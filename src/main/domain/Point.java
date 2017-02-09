package main.domain;

/**
 * 
 * @author JusticeLeague
 *
 */
public class Point {
	
	private int x;
	private int y;
	
	/**
	 * Initial constructor for the point object. Since we did not use the java's point class, we defined our own point class
	 * @param x X coordinate to set
	 * @param y Y coordinate to set
	 */
	public Point(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	/**
	 * 
	 * @return the X coordinate
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * 
	 * @return set the X coordinate
	 */
	public void setX(int x) {
		this.x = x;
	}
	
	/**
	 * 
	 * @return the Y coordinate
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * 
	 * @return set the Y coordinate
	 */
	public void setY(int y) {
		this.y = y;
	}
}
