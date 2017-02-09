/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.domain;

import main.domain.strategy.CezmiMovement;
import main.domain.strategy.IMovementStrategy;

/**
 * OVERVIEW: This class represents a Cezmi object that has coordinates and velocity
 * and is controlled by the Player.
 *  @since 0.1
 *  @version 0.1
 *  @author JusticeLeague
 */
public class Cezmi extends Paintable{

	private double x;
	private double y;
	private int xVel;
	private int yVel;
	private IMovementStrategy movementStrategy;
	private boolean direction;
	private Player belongsTo;
	private boolean canMove;
	
	/**    
	 * Initial constructor for the Cezmi object, since there is more than one Cezmi object, singleton pattern cannot be applied
	 */
	public Cezmi(double x, double y){
		this.x = x;
		this.y = y;
		this.xVel = 1;
		this.yVel = 1;
		canMove = true;
		movementStrategy= CezmiMovement.getInstance();
	}

	/**
	 * 
	 * @effects the X coordinate of the Cezmi
	 */
	public double getX() {
		return x;
	}

	/**
	 * 
	 * @param x the new X coordinate to set
	 * @modifies the X coordinate of the Cezmi
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * 
	 * @effects the Y coordinate of the Cezmi
	 */
	public double getY() {
		return y;
	}

	/**
	 * 
	 * @param y the new Y coordinate to set
	 * @modifies the Y coordinate of the Cezmi
	 */
	public void setY(double y) {
		this.y = y;
	}
	/**
	 * @return the movement of the Cezmi
	 */
	public IMovementStrategy getMovementStrategy() {
		return movementStrategy;
	}

	/**
	 * @param the movement strategy of the Cezmi
	 * @modifies the movement strategy of the Cezmi
	 */
	public void setMovementStrategy(IMovementStrategy movementStrategy) {
		this.movementStrategy = movementStrategy;
	}

	/**
	 * 
	 * @effects the Y velocity vector of the Cezmi
	 */
	public int getxVel() {
		return xVel;
	}

	/**
	 * 
	 * @param xVel the new X velocity vector to set
	 * @modifies the X velocity of the Cezmi
	 */
	public void setxVel(int xVel) {
		this.xVel = xVel;
	}

	/**
	 * 
	 * @effects the Y velocity vector of the Cezmi
	 */
	public int getyVel() {
		return yVel;
	}

	/**
	 * 
	 * @param yVel the new Y velocity vector to set
	 * @modifies the Y velocity of the Cezmi
	 */
	public void setyVel(int yVel) {
		this.yVel = yVel;
	}

	/**
	 * 
	 * @effects the direction of the Cezmi.
	 * true: right
	 * false: left
	 */
	public boolean isDirection() {
		return direction;
	}

	/**
	 * 
	 * @param direction modifies the Cezmi's moving direction.
	 * @modifies the direction of the Cezmi
	 */
	public void setDirection(boolean direction) {
		this.direction = direction;
	}

	/**
	 * 
	 * @effects returns a Player, which owns the Cezmi object
	 */
	public Player getBelongsTo() {
		return belongsTo;
	}

	/**
	 * 
	 * @param belongsTo sets the Cezmi's Player to another.
	 * @modifies the Player, which the Cezmi belongs to.
	 */
	public void setBelongsTo(Player belongsTo) {
		this.belongsTo = belongsTo;
	}

	/**
	 * 
	 * @effects returns whether the Cezmi can move.
	 */
	public boolean canMove() {
		return canMove;
	}

	/**
	 * 
	 * @param canMove changes whether the Cezmi object can move
	 * true: can move
	 * false: cannot move
	 * @modifies sets whether the Cezmi can or cannot move
	 */
	public void setCanMove(boolean canMove) {
		this.canMove = canMove;
	}
	
	/**
	 * @Override Object class
	 * 
	 * A representation of a Cezmi object. Scans all of its fields and returns a proper string 
	 * that is ready to be printed or modified.
	 * @effects String representation of the Cezmi object.
	 */
	public String toString(){
		String output = "";
		output = output + "Cezmi x :" +this.getX()+"\nCezmi y :"+this.getY()+"\nCezmi xVel :"+this.getxVel()+"\nCezmi yVel :"+this.getyVel()+"\n";
		return output;
	}

}
