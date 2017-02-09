package main.domain;


/** OVERVIEW: This enum represents the Orientation value of some of the objects 
 *  in order to state the direction of them in the Board.
 *  @version 0.1
 *  @since 0.1
 *  @author JusticeLeague
 */
public enum Orientation {
	
	ORIENTATION_ZERO,
	ORIENTATION_90, 
	ORIENTATION_180, 
	ORIENTATION_270;
	
	/**
	 * A representation of an Orientation enum. Returns a proper string 
	 * that is ready to be printed.
	 * @return a String representation of the Orientation enum.
	 */
	public String toString(){
		switch(this){
		case ORIENTATION_ZERO:
			return "0";
		case ORIENTATION_90:
			return "90";
		case ORIENTATION_180:
			return "180";
		case ORIENTATION_270:
			return "270";
		default:
			return "0";
		}
	}
	
	/** 
	 * Rotates the Orientation enum 90 degrees clockwise 
	 * @effects the Orientation
	 */
	public Orientation rotateClockwise(){
		switch(this){
		case ORIENTATION_ZERO:
			return ORIENTATION_90;
		case ORIENTATION_90:
			return ORIENTATION_180;
		case ORIENTATION_180:
			return ORIENTATION_270;
		case ORIENTATION_270:
			return ORIENTATION_ZERO;
		default:
			return ORIENTATION_ZERO;
		}
	}
	
	/** 
	 * Converts the value of the Orientation from degree to the enum.
	 * @modifies str
	 * @effects the Orientation
	 */
	public static Orientation convertToOrientation(String str){
		switch(str){
		case "0":
			return ORIENTATION_ZERO;
		case "90":
			return ORIENTATION_90;
		case "180":
			return ORIENTATION_180;
		case "270":
			return ORIENTATION_270;
		default:
			return ORIENTATION_ZERO;
		}	
	}

}
