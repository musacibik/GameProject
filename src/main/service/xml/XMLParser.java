package main.service.xml;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import main.domain.Ball;
import main.domain.Board;
import main.domain.Cezmi;
import main.domain.Engel;
import main.domain.Orientation;
import main.domain.Player;
import main.domain.Point;
import main.domain.gizmos.Cezerye;
import main.domain.gizmos.Firildak;
import main.domain.gizmos.Gizmo;
import main.domain.gizmos.GizmoFactory;
import main.domain.gizmos.LeftTokat;
import main.domain.gizmos.RightTokat;
import main.domain.gizmos.TriangleTakoz;
import main.ui.GameWindow;


/** OVERVIEW: This class represents the XML parsing process. It parses the data 
 *  of the given XML file. It does the required validations for the values in the 
 *  XML file and gets these values in order to load to the Board object, which is
 *  consisted of the Balls, Gizmos, Players, Cezmis and more.
 *  @since 0.1
 *  @version 0.1
 *  @author JusticeLeague
 */
public class XMLParser {

	private static XMLParser instance;

	/**
	 *  Constructor for the XMLParser
	 */
	private XMLParser() {
		gizmoList = new ArrayList<>();
		ballList = new ArrayList<>();
		playerList = new ArrayList<>();
	} 

	/**
	 * Invokes the private constructor of the XMLParser class, by the rules of the singleton pattern. 
	 * Only one XMLParser object can exist and it is immutable. 
	 * @effects an instance of a XMLParser object.
	 */
	public static synchronized XMLParser getInstance(){
		if (instance == null){
			instance = new XMLParser();
		}
		return instance;
	}

	private int l = GameWindow.getL();

	private ArrayList<Gizmo> gizmoList;
	private ArrayList<Ball> ballList;
	private ArrayList<Player> playerList;

	private int level;
	private double gravity;
	private double friction1;
	private double friction2;

	private Document doc;
	private Node boardNode;

	/**
	 *  Parses the data of the given XML file. It reads the given XML file and gets the data
	 *  inside of it. Does the validations based on the logical requirements of the game in 
	 *  order to prevent loading invalid files to the game. After the validation process, it 
	 *  gets these values, such as Ball objects, Gizmo objects, Player objects, level value,
	 *  friction values, gravity value, and loads to the Board object.
	 *  @requires XSDValidator object has to validate the XML file based on the XSD file. 
	 *  @effects the boolean value that represents the validation success
	 *  @modifies xmlFile
	 */
	public boolean parse(File xmlFile){
		try{		

			gizmoList = new ArrayList<>();
			ballList = new ArrayList<>();
			playerList = new ArrayList<>();

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();
			boardNode = doc.getDocumentElement();


			if(readGeneralAttributes() == false) return false;
			if(readCezmi1() == false) return false;
			if(readCezmi2() == false) return false;
			if(readKeys() == false) return false;		
			if(readGizmos() == false) return false;
			if(readCezeryes() == false) return false;
			if(readBalls() == false) return false;
			
			Board.getInstance().setBalls(ballList);
			Board.getInstance().setGizmos(gizmoList);
			Board.getInstance().setPlayers(playerList);
			Board.getInstance().setLevel(level);
			Board.GRAVITY = gravity;
			Board.FRICTION_1 = friction1;
			Board.FRICTION_2 = friction2;

		} catch (ParserConfigurationException | SAXException | IOException e) {
			System.err.println(e);
			return false;
		}

		return true;
	}

	/**
	 *  Parses the general attributes, such as friction values and gravity value, of the XML file
	 *  @requires XSDValidator object has to validate the given XML file based on the XSD file. 
	 *  @effects the boolean value that represents the validation success for general attributes of the Board.
	 */
	private boolean readGeneralAttributes() {

		double defaultFriction1 = .025;
		double defaultFriction2 = .025;
		double defaultGravity = 25.0;	

		if(boardNode.getNodeType() == Node.ELEMENT_NODE){
			Element boardElement = (Element) boardNode;

			if(Integer.parseInt(boardElement.getAttribute("level")) == 1){
				level = 1;	
			} else if(Integer.parseInt(boardElement.getAttribute("level")) == 2){
				level = 2;
			} else {
				System.out.println("Level value is not valid\n");
				return false;
			}

			if(!"".equals(boardElement.getAttribute("friction1"))){
				double tempValue = Double.parseDouble(boardElement.getAttribute("friction1"));
				if(0 <= tempValue || tempValue < 1){
					friction1 = tempValue;
				} else {
					System.out.println("Friction1 value is not valid\n");
					return false;
				}			
			} else {
				friction1 = defaultFriction1;
			}

			if(!"".equals(boardElement.getAttribute("friction2"))){
				double tempValue = Double.parseDouble(boardElement.getAttribute("friction2"));
				if(0 <= tempValue || tempValue < 1){
					friction2 = tempValue;
				} else {
					System.out.println("Friction2 value is not valid\n");
					return false;
				}
			} else {
				friction2 = defaultFriction2;
			}		

			if(!"".equals(boardElement.getAttribute("gravity"))){
				double tempValue = Double.parseDouble(boardElement.getAttribute("gravity"));
				if(0 <= tempValue){
					gravity = tempValue;
				} else {
					System.out.println("Gravity value is not valid\n");
					return false;
				}
			} else {
				gravity = defaultGravity;
			}

		}

		return true;
	}

	/**
	 *  Parses the Ball objects of the XML file
	 *  @requires XSDValidator object has to validate the given XML file based on the XSD file. 
	 *  @effects the boolean value that represents the validation success for Ball objects of the Board.
	 */
	private boolean readBalls() {
		NodeList ballList = doc.getElementsByTagName("ball");		
		if(this.level == 1 && ballList.getLength() != 1) return false;
		if(this.level == 2 && ballList.getLength() != 2) return false;
		for(int i = 0; i < ballList.getLength(); i++){
			Node tempNode = ballList.item(i);
			if(tempNode.getNodeType() == Node.ELEMENT_NODE){
				Element tempElement = (Element) tempNode;
				Ball ballTemp = new Ball();

				/* Just for now,
				 * 
				 * TODO: Validations for velocities of the ball are not implemented
				 * because of their illogical values.
				 * 
				 * TODO: Validations for coordinates of the ball are not implemented.
				 * 
				 */
				ballTemp.setX((Double.parseDouble(tempElement.getAttribute("x")) * l));
				ballTemp.setY((Double.parseDouble(tempElement.getAttribute("y")) * l));
				ballTemp.setxVel((Double.parseDouble(tempElement.getAttribute("xVelocity")) * l) / 5000);
				ballTemp.setyVel((Double.parseDouble(tempElement.getAttribute("yVelocity")) * l) / 5000);
				ballTemp.setBallNumber(i+1);

				double totalScore = playerList.get(0).getScore() + playerList.get(1).getScore();
				if (0 <= totalScore && totalScore < 2) {
					ballTemp.setDiameter(1.0);
				} else if (2 <= totalScore && totalScore < 4) {
					ballTemp.setDiameter(0.9);
				} else if (4 <= totalScore && totalScore < 6) {
					ballTemp.setDiameter(0.8);
				} else if (6 <= totalScore && totalScore < 8) {
					ballTemp.setDiameter(0.7);
				} else if (8 <= totalScore && totalScore < 10) {
					ballTemp.setDiameter(0.6);
				} else if (10 <= totalScore && totalScore < 12) {
					ballTemp.setDiameter(0.5);
				} else if (12 <= totalScore && totalScore < 14) {
					ballTemp.setDiameter(0.4);
				} else if (14 <= totalScore && totalScore < 16) {
					ballTemp.setDiameter(0.3);
				} else if (16 <= totalScore && totalScore < 18) {
					ballTemp.setDiameter(0.2);
				}

				for (Gizmo gizmo : gizmoList) {
					for (Point point : gizmo.getBorderPoints()) {
						for (Point ballPoint : ballTemp.getBorderPoints()) {
							if (ballPoint.getX() == point.getX() && ballPoint.getY() == point.getY()) {
								return false;
							}
						}
					}
				}

				if(!isPlaceBall(ballTemp)) {
					return false;
				}

				this.ballList.add(ballTemp);
			}		
		}

		return true;
	}

	/**
	 *  Parses the Cezmi objects of the XML file
	 *  @requires XSDValidator object has to validate the given XML file based on the XSD file. 
	 *  @effects the boolean value that represents the validation success for Cezmi objects of the Board.
	 */
	private boolean readCezmi1() {		
		double winningScore = 10.0;
		Node cezmiNode1 = doc.getElementsByTagName("cezmi1").item(0);

		if(cezmiNode1.getNodeType() == Node.ELEMENT_NODE){
			Element cezmiElement1 = (Element) cezmiNode1;

			double tempX = Double.parseDouble(cezmiElement1.getAttribute("x")) * l;
			double tempY = Double.parseDouble(cezmiElement1.getAttribute("y")) * l;

			if(this.level == 1){

				if((tempX + GameWindow.getL() < GameWindow.getL()) || 
						(tempX + GameWindow.getL() > Board.BOARD_SIZE*GameWindow.getL()/2 - 1.2*GameWindow.getL())){
					System.out.println("X coordinate of Cezmi1 is not valid\n");
					return false;
				}

				if(tempY != GameWindow.getL()*Board.BOARD_SIZE-GameWindow.getL()){
					System.out.println("Y coordinate of Cezmi1 is not valid\n");
					return false;
				} 

				Cezmi cezmi1 = new Cezmi(tempX, tempY);

				if(Double.parseDouble(cezmiElement1.getAttribute("score")) >= winningScore) {
					System.out.println("Score for Player 1 is not valid\n");
					return false;
				}

				Player player1 = new Player("Player1", 1);

				cezmi1.setBelongsTo(player1);
				player1.setCezmi(cezmi1);
				player1.setScore(Double.parseDouble(cezmiElement1.getAttribute("score")));

				this.playerList.add(player1);
			}

			if(this.level == 2){

				if(tempY == GameWindow.getL()*Board.BOARD_SIZE-GameWindow.getL()){
					if((tempX + GameWindow.getL() < 0)|| 
							(tempX + GameWindow.getL() > Board.BOARD_SIZE*GameWindow.getL()/2 - 1.2*GameWindow.getL())){
						System.out.println("X coordinate of Cezmi1 is not valid\n");
						return false;
					}	
				} else if(GameWindow.getL() * 25 - (Engel.ENGEL_HEIGHT)*GameWindow.getL() * 2 <= tempY){
					if(tempX + GameWindow.getL() != 0){
						System.out.println("X coordinate of Cezmi1 is not validddd\n");
						return false;
					}
				} else {
					System.out.println("Y coordinate of Cezmi1 is not valid\n");
					return false;
				}

				Cezmi cezmi1 = new Cezmi(tempX, tempY);

				if(Double.parseDouble(cezmiElement1.getAttribute("score")) >= winningScore) {
					System.out.println("Score for Player 1 is not valid\n");
					return false;
				}

				Player player1 = new Player("Player1", 1);

				cezmi1.setBelongsTo(player1);
				player1.setCezmi(cezmi1);
				player1.setScore(Double.parseDouble(cezmiElement1.getAttribute("score")));

				this.playerList.add(player1);
			}

		}

		return true;
	}

	/**
	 *  Parses the Cezmi objects of the XML file
	 *  @requires XSDValidator object has to validate the given XML file based on the XSD file. 
	 *  @effects the boolean value that represents the validation success for Cezmi objects of the Board.
	 */
	private boolean readCezmi2() {
		double winningScore = 10.0;		
		Node cezmiNode2 = doc.getElementsByTagName("cezmi2").item(0);

		if(cezmiNode2.getNodeType() == Node.ELEMENT_NODE){
			Element cezmiElement2 = (Element) cezmiNode2;

			double tempX = Double.parseDouble(cezmiElement2.getAttribute("x")) *l;
			double tempY = Double.parseDouble(cezmiElement2.getAttribute("y")) *l;

			if(this.level == 1){

				if((tempX + GameWindow.getL() > Board.BOARD_SIZE * GameWindow.getL() - GameWindow.getL()) ||
						(tempX + GameWindow.getL() < Board.BOARD_SIZE*GameWindow.getL()/2 + 1.15*GameWindow.getL())){
					System.out.println("X coordinate of Cezmi2 is not valid\n");
					return false;
				}

				if(tempY != GameWindow.getL()*Board.BOARD_SIZE-GameWindow.getL()){
					System.out.println("Y coordinate of Cezmi2 is not valid\n");
					return false;
				} 

				Cezmi cezmi2 = new Cezmi(tempX, tempY);

				if(Double.parseDouble(cezmiElement2.getAttribute("score")) >= winningScore){
					System.out.println("Score for Player 2 is not valid\n");
					return false;
				}

				Player player2 = new Player("Player2", 2);

				cezmi2.setBelongsTo(player2);
				player2.setCezmi(cezmi2);
				player2.setScore(Double.parseDouble(cezmiElement2.getAttribute("score")));	

				this.playerList.add(player2);
			}

			if(this.level == 2){

				if(tempY == GameWindow.getL()*Board.BOARD_SIZE-GameWindow.getL()){
					if((tempX + GameWindow.getL() > Board.BOARD_SIZE * GameWindow.getL() - GameWindow.getL()) ||
							(tempX + GameWindow.getL() < Board.BOARD_SIZE*GameWindow.getL()/2 + 1.15*GameWindow.getL())){
						System.out.println("X coordinate of Cezmi2 is not valid\n");
						return false;
					}
				} else if(GameWindow.getL() * 25 - (Engel.ENGEL_HEIGHT)*GameWindow.getL() * 2 <= tempY){
					if(tempX + GameWindow.getL() != Board.BOARD_SIZE * GameWindow.getL()){
						System.out.println("X coordinate of Cezmi2 is not valid\n");
						return false;
					}
				} else {
					System.out.println("Y coordinate of Cezmi2 is not valid\n");
					return false;
				}

				Cezmi cezmi2 = new Cezmi(tempX, tempY);

				if(Double.parseDouble(cezmiElement2.getAttribute("score")) >= winningScore){
					System.out.println("Score for Player 2 is not valid\n");
					return false;
				}

				Player player2 = new Player("Player2", 2);

				cezmi2.setBelongsTo(player2);
				player2.setCezmi(cezmi2);
				player2.setScore(Double.parseDouble(cezmiElement2.getAttribute("score")));	

				this.playerList.add(player2);
			}
		}

		return true;
	}

	/**
	 *  Parses the Cezerye objects of the XML file
	 *  @requires XSDValidator object has to validate the given XML file based on the XSD file. 
	 *  @effects the boolean value that represents the validation success for Cezerye objects of the Board.
	 */
	private boolean readCezeryes() {
		double defaultAppearTime = 5.0;

		NodeList cezeryeList = doc.getElementsByTagName("cezeryes");

		for(int i = 0; i < cezeryeList.getLength(); i++) {
			NodeList childList = cezeryeList.item(i).getChildNodes();

			for(int j = 0; j < childList.getLength(); j++){
				Node childNode = childList.item(j);

				if(childNode.getNodeType() == Node.ELEMENT_NODE) {
					Element childElement = (Element) childNode;

					String cezeryeType = childNode.getNodeName();
					Cezerye cezeryeTemp = (Cezerye) GizmoFactory.getInstance().getGizmo(cezeryeType);

					/* Just for now,
					 * Validations for coordinates of the cezeryes are not implemented.
					 * TODO: Will be implemented later on.
					 */

					if(!"".equals(childElement.getAttribute("x"))){
						cezeryeTemp.setX(Integer.parseInt(childElement.getAttribute("x")) * l);
					} else {
						cezeryeTemp.setX(1 * l);
					}

					if(!"".equals(childElement.getAttribute("y"))){
						cezeryeTemp.setY(Integer.parseInt(childElement.getAttribute("y")) * l);
					} else {
						cezeryeTemp.setY(1 * l);
					}

					if(!"".equals(childElement.getAttribute("time"))){
						double appearTime = Double.parseDouble((childElement.getAttribute("time")));

						/*
						 * TODO: Check for the value of the cezerye's appear time.
						 *  Is it between 0.0 - 5.0 ?
						 */
						if(0.0 <= appearTime && appearTime <= defaultAppearTime){
							cezeryeTemp.setAppearTime(appearTime);
							cezeryeTemp.setEdited(true);
						} else {
							return false;
						}
					} else {
						cezeryeTemp.setAppearTime(0.0);
					}

					for (Gizmo gizmo : this.gizmoList) {
						for (Point gizmoPoint : gizmo.getBorderPoints()) {
							for (Point point : cezeryeTemp.getBorderPoints()) {
								if (gizmoPoint.getX() == point.getX() && gizmoPoint.getY() == point.getY()) {
									return false;
								}
							}
						}
					}

					if(!isPlace(cezeryeTemp)) {
						return false;
					}

					this.gizmoList.add(cezeryeTemp);
				}

			}
		}
		return true;
	}

	/**
	 *  Parses the Gizmo objects of the XML file
	 *  @requires XSDValidator object has to validate the given XML file based on the XSD file. 
	 *  @effects the boolean value that represents the validation success for Gizmo objects of the Board.
	 */
	private boolean readGizmos() {
		/*
		 *  Player 1 can have merely one leftTokat.
		 *  Player 2 can have merely one RightTokat.
		 *  
		 *  Therefore, in case of having more than one tokats
		 *  in the XML file is not permitted to be loaded.
		 */
		int numLeftTokat = 0, numRightTokat = 0;

		NodeList gizmoList = doc.getElementsByTagName("gizmos");	
		/*
		 * In case of having more than 8 gizmos in XML file
		 * is not permitted to be loaded.
		 */
		int rightCount = 0;
		int leftCount = 0;

		for (int i = 0; i < gizmoList.getLength(); i++) {
			NodeList childList = gizmoList.item(i).getChildNodes();

			for(int j = 0; j < childList.getLength(); j++){
				Node childNode = childList.item(j);

				if (childNode.getNodeType() == Node.ELEMENT_NODE) {
					Element childElement = (Element) childNode;

					/* Just for now,
					 * Validations for coordinates and the directions
					 * of the gizmos are not implemented
					 * .
					 * TODO: Will be implemented later on.
					 */

					String gizmoType = childNode.getNodeName();    
					Gizmo gizmoTemp = GizmoFactory.getInstance().getGizmo(gizmoType);
					gizmoTemp.setX(Integer.parseInt(childElement.getAttribute("x")) * l);
					gizmoTemp.setY(Integer.parseInt(childElement.getAttribute("y")) * l);

					if (gizmoTemp.getX() < Board.BOARD_SIZE * GameWindow.getL() / 2) {
						leftCount++;
					}
					else if (gizmoTemp.getX() >= Board.BOARD_SIZE * GameWindow.getL() / 2) {
						rightCount++;
					}

					if(gizmoType.equalsIgnoreCase("triangleTakoz")) {
						((TriangleTakoz) gizmoTemp).setOrientation(Orientation.convertToOrientation(childElement.getAttribute("orientation")));
					}
					if(gizmoType.equalsIgnoreCase("leftTokat")){
						((LeftTokat) gizmoTemp).setOrientation(Orientation.convertToOrientation(childElement.getAttribute("orientation")));
						numLeftTokat++;
						if(numLeftTokat > 1) return false;
					}
					if(gizmoType.equalsIgnoreCase("rightTokat")){
						((RightTokat) gizmoTemp).setOrientation(Orientation.convertToOrientation(childElement.getAttribute("orientation")));
						numRightTokat++;
						if(numRightTokat > 1) return false;
					}
					if(gizmoType.equalsIgnoreCase("firildak")){
						((Firildak) gizmoTemp).setAngle(Integer.parseInt(childElement.getAttribute("angle")));
					}

					for (Gizmo gizmo : this.gizmoList) {
						for (Point gizmoPoint : gizmo.getBorderPoints()) {
							for (Point point : gizmoTemp.getBorderPoints()) {
								if (gizmoPoint.getX() == point.getX() && gizmoPoint.getY() == point.getY()) {
									return false;
								}
							}
						}
					}

					if(!isPlace(gizmoTemp)) {
						return false;
					}


					this.gizmoList.add(gizmoTemp);
				}
			}

		}

		if (leftCount > 4 || rightCount > 4) {
			return false;
		}

		return true;
	}

	/**
	 *  Parses the key values of the Player object of the XML file
	 *  @requires XSDValidator object has to validate the given XML file based on the XSD file. 
	 *  @effects the boolean value that represents the validation success for key values of the Player object of the Board.
	 */
	private boolean readKeys() {
		/*
		 * In case of having the same key code for different keys
		 * in XML file is not permitted to be loaded.
		 */
		ArrayList<Integer> keyCodeList = new ArrayList<Integer>();

		Node key1Left = doc.getElementsByTagName("key1left").item(0);
		Node key1Right = doc.getElementsByTagName("key1right").item(0);
		Node key2Left = doc.getElementsByTagName("key2left").item(0);
		Node key2Right = doc.getElementsByTagName("key2right").item(0);

		if (key1Left.getNodeType() == Node.ELEMENT_NODE) {
			Element tempElement = (Element) key1Left;

			if(tempElement.getAttribute("key").equalsIgnoreCase("left")){
				int keyCode = 37;

				if(keyCodeList.contains(keyCode)) {
					System.out.println("More than one key has same value");
					return false;
				}

				keyCodeList.add(keyCode);
				playerList.get(0).setKey1(keyCode);		

			} else{
				char key = tempElement.getAttribute("key").charAt(0);
				int keyCode = KeyEvent.getExtendedKeyCodeForChar(key);

				if(keyCode == 16785808){
					keyCode = 37;
				}

				/*
				 * Checks whether different keys have same key code value.
				 */
				if(keyCodeList.contains(keyCode))  {
					System.out.println("More than one key has same value");
					return false;
				}

				keyCodeList.add(keyCode);
				playerList.get(0).setKey1(keyCode);		
			}
		}

		if (key1Right.getNodeType() == Node.ELEMENT_NODE) {
			Element tempElement = (Element) key1Right;

			if(tempElement.getAttribute("key").equalsIgnoreCase("right")){
				int keyCode = 39;

				if(keyCodeList.contains(keyCode)) {
					System.out.println("More than one key has same value");
					return false;
				}

				keyCodeList.add(keyCode);
				playerList.get(0).setKey2(keyCode);		

			} else {
				char key = tempElement.getAttribute("key").charAt(0);
				int keyCode = KeyEvent.getExtendedKeyCodeForChar(key);

				// -> symbol keycode representation
				if(keyCode == 16785810){
					keyCode = 39;
				}

				/*
				 * Checks whether different keys have same key code value.
				 */
				if(keyCodeList.contains(keyCode)) {
					System.out.println("More than one key has same value");
					return false;
				}

				keyCodeList.add(keyCode);
				playerList.get(0).setKey2(keyCode);		
			}		
		}

		if (key2Left.getNodeType() == Node.ELEMENT_NODE) {
			Element tempElement = (Element) key2Left;

			if(tempElement.getAttribute("key").equalsIgnoreCase("left")){
				int keyCode = 37;

				if(keyCodeList.contains(keyCode)) {
					System.out.println("More than one key has same value");
					return false;
				}

				keyCodeList.add(keyCode);
				playerList.get(1).setKey1(keyCode);		

			} else{
				char key = tempElement.getAttribute("key").charAt(0);
				int keyCode = KeyEvent.getExtendedKeyCodeForChar(key);

				// <- symbol keycode representation
				if(keyCode == 16785808){
					keyCode = 37;
				}

				/*
				 * Checks whether different keys have same key code value.
				 */
				if(keyCodeList.contains(keyCode))  {
					System.out.println("More than one key has same value");
					return false;
				}

				keyCodeList.add(keyCode);
				playerList.get(1).setKey1(keyCode);		
			}

		}

		if (key2Right.getNodeType() == Node.ELEMENT_NODE) {
			Element tempElement = (Element) key2Right;

			if(tempElement.getAttribute("key").equalsIgnoreCase("right")){
				int keyCode = 39;

				if(keyCodeList.contains(keyCode)) {
					System.out.println("More than one key has same value");
					return false;
				}

				keyCodeList.add(keyCode);
				playerList.get(1).setKey2(keyCode);		

			} else {
				char key = tempElement.getAttribute("key").charAt(0);
				int keyCode = KeyEvent.getExtendedKeyCodeForChar(key);

				// -> symbol keycode representation
				if(keyCode == 16785810){
					keyCode = 39;
				}

				/*
				 * Checks whether different keys have same key code value.
				 */
				if(keyCodeList.contains(keyCode)) {
					System.out.println("More than one key has same value");
					return false;
				}

				keyCodeList.add(keyCode);
				playerList.get(1).setKey2(keyCode);		
			}

		}

		return true;
	}

	public boolean isPlace(Gizmo gizmo) {
		if (gizmo instanceof Firildak) {
			if (((((Firildak) gizmo).getX() >= GameWindow.getL() * ((Firildak) gizmo).getLength()
					&& ((Firildak) gizmo).getX() <= GameWindow.getL() * (Board.BOARD_SIZE/2 - 2*((Firildak) gizmo).getLength())) ||
					(((Firildak) gizmo).getX() >= GameWindow.getL() * (Board.BOARD_SIZE/2 + 2*((Firildak) gizmo).getLength())
					&& ((Firildak) gizmo).getX() <= GameWindow.getL() * (Board.BOARD_SIZE - 2 * ((Firildak) gizmo).getLength())))		
					&& ((Firildak) gizmo).getY() >= GameWindow.getL() * ((Firildak) gizmo).getLength()
					&& ((Firildak) gizmo).getY() <= GameWindow.getL() * (Board.BOARD_SIZE - (2 * Engel.ENGEL_HEIGHT) - 2 * ((Firildak) gizmo).getLength())) {
				return true;
			}
		} else if (gizmo instanceof LeftTokat) {
			if (((LeftTokat) gizmo).getX() >= 0
					&& ((LeftTokat) gizmo).getX() <= GameWindow.getL() * (Board.BOARD_SIZE / 2 - 2 * ((LeftTokat) gizmo).getLength())
					&& ((LeftTokat) gizmo).getY() >= 0
					&& ((LeftTokat) gizmo).getY() <= GameWindow.getL() * (Board.BOARD_SIZE - (2 * Engel.ENGEL_HEIGHT) - 2 * ((LeftTokat) gizmo).getLength())) {
				return true;
			}
		} else if (gizmo instanceof RightTokat) {
			if (((RightTokat) gizmo).getX() >= GameWindow.getL() * (Board.BOARD_SIZE / 2 + 1)
					&& ((RightTokat) gizmo).getX() <= GameWindow.getL() * (Board.BOARD_SIZE - 2 * ((RightTokat) gizmo).getLength())
					&& ((RightTokat) gizmo).getY() >= 0
					&& ((RightTokat) gizmo).getY() <= GameWindow.getL() * (Board.BOARD_SIZE - (2 * Engel.ENGEL_HEIGHT) - 2 * ((RightTokat) gizmo).getLength())) {
				return true;
			}
		} else if (((gizmo.getX() >= 0
				&& gizmo.getX() <= GameWindow.getL() * (Board.BOARD_SIZE/2 - 1)) ||
				(gizmo.getX() >= GameWindow.getL() * (Board.BOARD_SIZE/2 + 1)
				&& gizmo.getX() <= GameWindow.getL() * (Board.BOARD_SIZE - 1)))
				&& gizmo.getY() >= 0
				&& gizmo.getY() <= GameWindow.getL() * (Board.BOARD_SIZE - (2 * Engel.ENGEL_HEIGHT) - 1)) {
			return true;
		}
		return false;
	}

	public boolean isPlaceBall(Ball ball) {
		if (ball.getX() >= 0
				&& ball.getX() <= GameWindow.getL() * (Board.BOARD_SIZE - ball.getDiameter())
				&& ball.getY() >= 0
				&& ball.getY() <= GameWindow.getL() * (Board.BOARD_SIZE - ball.getDiameter())) {
			return true;
		}
		System.out.println("buradaaaa");
		return false;
	}

}
