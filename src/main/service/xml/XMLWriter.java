package main.service.xml;

import java.awt.event.KeyEvent;
import java.io.File;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import main.domain.Ball;
import main.domain.Board;
import main.domain.Player;
import main.domain.gizmos.Cezerye;
import main.domain.gizmos.Firildak;
import main.domain.gizmos.Gizmo;
import main.domain.gizmos.LeftTokat;
import main.domain.gizmos.RightTokat;
import main.domain.gizmos.SquareTakoz;
import main.domain.gizmos.TriangleTakoz;
import main.ui.GameWindow;

/** OVERVIEW: This class represents the XML writing process. It writes the data 
 *  of the current game, which is consisted of the Balls, Gizmos, Players, Cezmis 
 *  and more, to an XML file.
 *  @since 0.1
 *  @version 0.1
 *  @author JusticeLeague
 */
public class XMLWriter {

	private Board board;
	private Document doc;
	private int l = GameWindow.getL();

	private static XMLWriter instance;

	/**
	 *  Empty constructor for the XMLWriter, nothing to be done
	 */
	private XMLWriter() { }

	/**
	 * Instantiates the empty XMLWriter object, in order to utilize its methods
	 * @effects the instance of the XMLWriter
	 */
	public static synchronized XMLWriter getInstance(){
		if (instance == null){
			instance = new XMLWriter();
		}

		return instance;
	}

	/**
	 * This method writes the gathered data to an XML file
	 * @modifies file object, in which the data will be written
	 */
	public void writeToXML(File file) {
		this.board = Board.getInstance();

		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			doc = docBuilder.newDocument();

			Element rootElement = doc.createElement("board");
			doc.appendChild(rootElement);

			writeBalls(rootElement);
			writeCezmis(rootElement);
			writeCezeryes(rootElement);
			writeGizmos(rootElement);
			writeKeys(rootElement);
			writeOther(rootElement);

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);

			StreamResult result = new StreamResult(file);

			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}i‌​ndent-amount", "2");
			transformer.transform(source, result);
		} catch (ParserConfigurationException | DOMException | TransformerException e) {
			System.err.println(e);
		}
	}

	/**
	 * Private method of the Ball vector. Ball data gathered and written to a file
	 * @modifies rootElement
	 */
	private void writeBalls(Element rootElement) {
		ArrayList<Ball> balls = board.getBalls();

		for(Ball ballInfo:balls){
			Element ball = doc.createElement("ball");
			Attr ballXPos = doc.createAttribute("x");
			Attr ballYPos = doc.createAttribute("y");
			Attr ballXVel = doc.createAttribute("xVelocity");
			Attr ballYVel = doc.createAttribute("yVelocity");
			DecimalFormat decimal = new DecimalFormat(".##", new DecimalFormatSymbols(Locale.US)); 
			ballXPos.setValue("" + decimal.format(ballInfo.getX() / l));
			ballYPos.setValue("" + decimal.format(ballInfo.getY() / l));
			ballXVel.setValue("" + decimal.format((ballInfo.getxVel()*5000) / l));
			ballYVel.setValue("" + decimal.format((ballInfo.getyVel()*5000) / l));
			ball.setAttributeNode(ballXVel);
			ball.setAttributeNode(ballYVel);
			ball.setAttributeNode(ballXPos);
			ball.setAttributeNode(ballYPos);
			rootElement.appendChild(ball);
		}
	}

	/**
	 * Private method of the Cezmi vector. Cezmi data gathered and written to a file
	 * @modifies rootElement
	 */
	private void writeCezmis(Element rootElement) {
		Player player1 = board.getPlayers().get(0);
		Player player2 = board.getPlayers().get(1);	

		Element cezmi1 = doc.createElement("cezmi1");
		Attr cezmi1X = doc.createAttribute("x");
		Attr cezmi1Y = doc.createAttribute("y");
		Attr score1 = doc.createAttribute("score");
		cezmi1X.setValue("" + (player1.getCezmi().getX() / l));
		cezmi1Y.setValue("" + (player1.getCezmi().getY() / l));
		score1.setValue("" + player1.getScore());

		cezmi1.setAttributeNode(cezmi1X);
		cezmi1.setAttributeNode(cezmi1Y);
		cezmi1.setAttributeNode(score1);
		rootElement.appendChild(cezmi1);

		Element cezmi2 = doc.createElement("cezmi2");
		Attr cezmi2X = doc.createAttribute("x");
		Attr cezmi2Y = doc.createAttribute("y");
		Attr score2 = doc.createAttribute("score");
		cezmi2X.setValue("" + (player2.getCezmi().getX() / l));
		cezmi2Y.setValue("" + (player2.getCezmi().getY() / l));
		score2.setValue("" + player2.getScore());

		cezmi2.setAttributeNode(cezmi2X);
		cezmi2.setAttributeNode(cezmi2Y);
		cezmi2.setAttributeNode(score2);
		rootElement.appendChild(cezmi2);
	}

	/**
	 * Private method of the Cezerye vector. Cezerye data gathered and written to a file
	 * @modifies rootElement
	 */
	private void writeCezeryes(Element rootElement) {
		List<Gizmo> gizmoInfo = board.getGizmos();

		Element cezeryes = doc.createElement("cezeryes");
		Element cezerye;
		Attr cezX, cezY, cezTime;
		rootElement.appendChild(cezeryes);

		for (Gizmo i : gizmoInfo) {
			if (i instanceof Cezerye) {
				Cezerye c = (Cezerye) i;
				cezerye = doc.createElement("cezerye");
				cezX = doc.createAttribute("x");
				cezY = doc.createAttribute("y");
				cezTime = doc.createAttribute("time");
				cezX.setValue("" + c.getX() / l);
				cezY.setValue("" + c.getY() / l);
				cezTime.setValue("" + c.getAppearTime());
				cezerye.setAttributeNode(cezX);
				cezerye.setAttributeNode(cezY);
				cezerye.setAttributeNode(cezTime);
				cezeryes.appendChild(cezerye);
			}
		}
	}
	/**
	 * Private method of the Gizmo vector. Gizmo data gathered and written to a file
	 * @modifies rootElement
	 */
	private void writeGizmos(Element rootElement) {
		List<Gizmo> gizmoInfo = board.getGizmos();

		Element gizmo = doc.createElement("gizmos");
		rootElement.appendChild(gizmo);

		Element squareTakoz, triangleTakoz, leftTokat, rightTokat, firildak;
		Attr x, y, orientation, angle;

		for (Gizmo i : gizmoInfo) {

			if (i instanceof SquareTakoz) {
				// SquareTakoz
				SquareTakoz k = (SquareTakoz) i;
				squareTakoz = doc.createElement("squareTakoz");
				x = doc.createAttribute("x");
				y = doc.createAttribute("y");
				x.setValue("" + k.getX() / l);
				y.setValue("" + k.getY() / l);
				squareTakoz.setAttributeNode(x);
				squareTakoz.setAttributeNode(y);
				gizmo.appendChild(squareTakoz);
			}
			if (i instanceof TriangleTakoz) {
				// TriangularTakoz
				TriangleTakoz k = (TriangleTakoz) i;
				triangleTakoz = doc.createElement("triangleTakoz");
				x = doc.createAttribute("x");
				y = doc.createAttribute("y");
				orientation = doc.createAttribute("orientation");
				x.setValue("" + k.getX() / l);
				y.setValue("" + k.getY() / l);
				orientation.setValue("" + k.getOrientation().toString());
				triangleTakoz.setAttributeNode(x);
				triangleTakoz.setAttributeNode(y);
				triangleTakoz.setAttributeNode(orientation);
				gizmo.appendChild(triangleTakoz);
			}
			if (i instanceof Firildak) {
				// Fırıldak
				Firildak k = (Firildak) i;
				firildak = doc.createElement("firildak");
				x = doc.createAttribute("x");
				y = doc.createAttribute("y");
				angle = doc.createAttribute("angle");
				x.setValue("" + k.getX() / l);
				y.setValue("" + k.getY() / l);
				angle.setValue("" + k.getAngle());
				firildak.setAttributeNode(x);
				firildak.setAttributeNode(y);
				firildak.setAttributeNode(angle);
				gizmo.appendChild(firildak);
			}
			if (i instanceof LeftTokat) {
				// LeftTokat
				LeftTokat k = (LeftTokat) i;
				leftTokat = doc.createElement("leftTokat");
				x = doc.createAttribute("x");
				y = doc.createAttribute("y");
				orientation = doc.createAttribute("orientation");
				x.setValue("" + k.getX() / l);
				y.setValue("" + k.getY() / l);
				orientation.setValue("" + k.getOrientation().toString());
				leftTokat.setAttributeNode(x);
				leftTokat.setAttributeNode(y);
				leftTokat.setAttributeNode(orientation);
				gizmo.appendChild(leftTokat);
			}
			if (i instanceof RightTokat) {
				// RightTokat
				RightTokat k = (RightTokat) i;
				rightTokat = doc.createElement("rightTokat");
				x = doc.createAttribute("x");
				y = doc.createAttribute("y");
				orientation = doc.createAttribute("orientation");
				x.setValue("" + k.getX() / l);
				y.setValue("" + k.getY() / l);
				orientation.setValue("" + k.getOrientation().toString());
				rightTokat.setAttributeNode(x);
				rightTokat.setAttributeNode(y);
				rightTokat.setAttributeNode(orientation);
				gizmo.appendChild(rightTokat);
			}

		}
	}

	/**
	 * Private method of the Key vector. Keyboard data gathered and written to a file
	 * @modifies rootElement
	 */
	private void writeKeys(Element rootElement) {
		Player player1 = board.getPlayers().get(0);
		Player player2 = board.getPlayers().get(1);

		Element keys = doc.createElement("keys");
		rootElement.appendChild(keys);

		Element key1Left = doc.createElement("key1left");
		Attr key1L = doc.createAttribute("key");
		key1L.setValue(KeyEvent.getKeyText(player1.getKey1()).toLowerCase());
		key1Left.setAttributeNode(key1L);
		keys.appendChild(key1Left);

		Element key1Right = doc.createElement("key1right");
		Attr key1R = doc.createAttribute("key");
		key1R.setValue(KeyEvent.getKeyText(player1.getKey2()).toLowerCase());
		key1Right.setAttributeNode(key1R);
		keys.appendChild(key1Right);

		Element key2Left = doc.createElement("key2left");
		Attr key2L = doc.createAttribute("key");
		key2L.setValue(KeyEvent.getKeyText(player2.getKey1()).toLowerCase());
		key2Left.setAttributeNode(key2L);
		keys.appendChild(key2Left);

		Element key2Right = doc.createElement("key2right");
		Attr key2R = doc.createAttribute("key");
		key2R.setValue(KeyEvent.getKeyText(player2.getKey2()).toLowerCase());
		key2Right.setAttributeNode(key2R);
		keys.appendChild(key2Right);
	}
	/**
	 * Private method of the other vectors. Other instantiation data gathered here and written to a file
	 * @modifies rootElement
	 */
	private void writeOther(Element rootElement){
		// Friction1, Friction2, gravity, level

		Attr level = doc.createAttribute("level");
		level.setValue(""+board.getLevel());
		rootElement.setAttributeNode(level);

		Attr friction1 = doc.createAttribute("friction1");
		friction1.setValue(""+Board.FRICTION_1);
		rootElement.setAttributeNode(friction1);

		Attr friction2 = doc.createAttribute("friction2");
		friction2.setValue(""+Board.FRICTION_2);
		rootElement.setAttributeNode(friction2);

		Attr gravity = doc.createAttribute("gravity");
		gravity.setValue(""+Board.GRAVITY);
		rootElement.setAttributeNode(gravity);		
	}

}
