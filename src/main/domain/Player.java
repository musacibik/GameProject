/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.domain;

import main.ui.GameWindow;

/** OVERVIEW: This class represents the Player object that 
 *  plays the game with its own Cezmi.
 *  @version 0.1
 *  @since 0.1
 *  @author JusticeLeague
 */
public class Player {
	
    private String name;
    private double score;
    private int key1, key2;
    private Cezmi cezmi;
    private int num;

	/**
	 * Initial constructor for the Player class. This class is responsible of holding the data that is 
	 * related to the player object, such as Cezmi objects, key values and score values.
	 * @modifies name, num
	 */
	public Player(String name,int num) {
		this.name = name;
		setNum(num);
		setKey();
		createCezmi();
	}

	/**
	 * Creates a Cezmi object with some default values
	 * based on the num value of the Player object.
	 */
	public void createCezmi(){

		if(getNum()==1){
			Cezmi temp = new Cezmi((GameWindow.getL()*Board.BOARD_SIZE)/4-GameWindow.getL(),GameWindow.getL()*Board.BOARD_SIZE-GameWindow.getL());
			temp.setDirection(false);
			temp.setBelongsTo(this);
			this.setCezmi(temp);
		}
		if(getNum()==2){
			Cezmi temp = new Cezmi(3*(GameWindow.getL()*Board.BOARD_SIZE)/4-GameWindow.getL(),GameWindow.getL()*Board.BOARD_SIZE-GameWindow.getL());
			temp.setDirection(true);
			temp.setBelongsTo(this);
			this.setCezmi(temp);
		}
	}

	/**
	 * A representation of a Player object. Scans all of its essential 
	 * fields and returns a proper string that is ready to be printed 
	 * or modified.
	 * @Override Object class
	 * @effects a String representation of the Board object.
	 */
	@Override
	public String toString() {
		return getName() + ": \n" + "Score: " + getScore();
	}

	/**
	 * gets the name of the Player
	 * @effects the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * sets the Player name
	 * @modifies name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * gets the num value of the Player
	 * @effects the num
	 */
	public int getNum() {
		return num;
	}

	/**
	 * sets the Player num
	 * @modifies num
	 */
	public void setNum(int num) {
		if(num==1)
			this.num = num;
		else if(num==2)
			this.num = num;
		else{
			this.num=1;
			System.err.println("Player number is not valid!");
		}
	}

	/**
	 * gets the score of the Player
	 * @effects the score
	 */
	public double getScore() {
		return score;
	}

	/**
	 * sets the Player score
	 * @modifies score
	 */
	public void setScore(double score) {
		this.score = score;
		if(num == 1) {
			GameWindow.getInstance().getScoreField1().setText("" + score);
		}
		else if(num == 2) {
			GameWindow.getInstance().getScoreField2().setText("" + score);
		}
	}

	/**
	 * sets the keys with default values based on the num value of the Player
	 * @modifies key1 and key2
	 */
	public void setKey(){
		if(num==1){
			this.key1=65;
			this.key2=68;
		}        
		else if(num==2){
			this.key1=37;
			this.key2=39;
		}   
	}

	/**
	 * gets the first key of the Player
	 * @effects the key1
	 */
	public int getKey1() {
		return key1;
	}

	/**
	 * sets the first key of the Player
	 * @modifies key1
	 */
	public void setKey1(int key1) {
		this.key1 = key1;
	}

	/**
	 * gets the second key of the Player
	 * @effects the key2
	 */
	public int getKey2() {
		return key2;
	}

	/**
	 * sets the second key of the Player
	 * @modifies key2
	 */
	public void setKey2(int key2) {
		this.key2 = key2;
	}

	/**
	 * gets the Cezmi of the Player
	 * @effects the cezmi
	 */
	public Cezmi getCezmi() {
		return cezmi;
	}

	/**
	 * sets the Cezmi of the Player.
	 * @modifies cezmi
	 */
	public void setCezmi(Cezmi cezmi) {
		this.cezmi = cezmi;
	}

	public boolean repOK() {
		if(getNum() != 1 && getNum() != 2) {
			return false;
		}
		return true;
	}

}