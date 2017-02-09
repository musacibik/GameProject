/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import main.domain.Ball;
import main.domain.Board;
import main.domain.Cezmi;
import main.domain.Engel;
import main.domain.Paintable;
import main.domain.Player;
import main.domain.Point;
import main.domain.gizmos.Cezerye;
import main.domain.gizmos.Firildak;
import main.domain.gizmos.Gizmo;
import main.domain.gizmos.GizmoFactory;
import main.domain.gizmos.LeftTokat;
import main.domain.gizmos.RightTokat;
import main.domain.gizmos.SquareTakoz;
import main.domain.gizmos.TriangleTakoz;
import main.domain.strategy.BallMovementStrategy;
import main.physics.Circle;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

/**
 *
 * @author JusticeLeague
 */
public final class GameFrame extends JPanel {

	private static final long serialVersionUID = -8512539717604279424L;
	private Graphics t;
	private List<Paintable> list = new ArrayList<>();
	private final EditWindow editWindow;
	private final AnimationWindow animationWindow;
	private final MouseClass mouseClass;
	private Timer timer;
	private boolean pause;
	private Gizmo selectedGizmo;
	private Gizmo editGizmo;
	private boolean editMode;
	public static boolean scorestreak;
	private AudioStream in;
	private static GameFrame instance;

	/**
	 * Invokes the private GameFrame() constructor, in order to create an
	 * instance of GameFrame object, which is added to the GameWindow.
	 * Animations and paint queues(basically the whole game) are handled here.
	 *
	 * @return a GameFrame object, which is only one and immutable
	 */
	public static synchronized GameFrame getInstance() {
		if (instance == null) {
			instance = new GameFrame();
		}
		return instance;
	}

	/**
	 * Main constructor for the Frame class. By the rules of the singleton
	 * pattern, only one GameFrame object exists, and is immutable. Timer,
	 * Paintable(Observable) and the AnimationWindow objects are assigned here.
	 */
	private GameFrame() {
		super();
		animationWindow = new AnimationWindow();
		timer = new Timer(2, animationWindow);
		mouseClass = new MouseClass();
		editWindow = new EditWindow();
		setEditMode(true);
		updateList();
		setPause(true);
		scorestreak = false;
	}

	/**
	 * Overriden from JPanel
	 *
	 * @param g Panel graphics, almost never used.
	 */
	@Override
	public void paint(Graphics g) {
		this.t = g;
		super.paint(g);
		paintAll(list);
	}

	/**
	 * Paint method for the all paintable objects. The List<Paintable> is
	 * painted with each element having a different painting style.
	 *
	 * @param temp a list of paintable object, which is going to be drawn onto
	 * the JPanel
	 */
	public void paintAll(List<Paintable> temp) {
		if (isEditMode() == true) {
			for (int i = 0; i < 26; i++) {
				t.drawLine(GameWindow.getL() * i, 0, GameWindow.getL() * i, GameWindow.getL() * Board.BOARD_SIZE - 6 * GameWindow.getL());
				t.setColor(Color.WHITE);
			}
			for (int i = 0; i < 20; i++) {
				t.drawLine(0, GameWindow.getL() * i, GameWindow.getL() * Board.BOARD_SIZE, GameWindow.getL() * i);
				t.setColor(Color.WHITE);
			}
		}
		for (Paintable i : temp) {
			if (i instanceof Cezmi) {
				for (Player j : Board.getInstance().getPlayers()) {
					t.fillOval((int) j.getCezmi().getX(), (int) j.getCezmi().getY(), 2 * GameWindow.getL(),
							2 * GameWindow.getL());

					if(j.getNum() == 1)
					t.setColor(Color.BLUE.darker());

					if(j.getNum() == 2)
						t.setColor(Color.GREEN.brighter());
				}
			}
			if (i instanceof SquareTakoz) {
				t.setColor(Color.GREEN);
				t.fillPolygon(((SquareTakoz) i).getXArray(), ((SquareTakoz) i).getYArray(),
						((SquareTakoz) i).getXCoordinates().size());
			}
			if (i instanceof TriangleTakoz) {
				t.setColor(Color.CYAN);
				t.fillPolygon(((TriangleTakoz) i).getXArray(), ((TriangleTakoz) i).getYArray(),
						((TriangleTakoz) i).getXCoordinates().size());
			}
			if (i instanceof Firildak) {
				t.setColor(Color.PINK);
				t.fillPolygon(((Firildak) i).getXArray(), ((Firildak) i).getYArray(),
						((Firildak) i).getXCoordinates().size());
			}
			if (i instanceof Cezerye) {
				if (((Cezerye) i).isVisible()) {
					t.setColor(Color.WHITE);
				} else {
					t.setColor(Color.BLACK);
				}

				t.fillPolygon(((Cezerye) i).getXArray(), ((Cezerye) i).getYArray(),
						((Cezerye) i).getXCoordinates().size());
			}
			if (i instanceof Engel) {
				t.setColor(Color.RED);
				t.fillPolygon(((Engel) i).getXArray(), ((Engel) i).getYArray(), ((Engel) i).getXCoordinates().size());
			}
			if (i instanceof LeftTokat) {
				t.setColor(Color.YELLOW);
				t.fillPolygon(((LeftTokat) i).getXArray(), ((LeftTokat) i).getYArray(),
						((LeftTokat) i).getXCoordinates().size());
				for (Circle circle : ((LeftTokat) i).getCircleList()) {
					t.fillOval((int) (circle.getCenter().x() - ((LeftTokat) i).getLengthPixel() / 4),
							(int) (circle.getCenter().y() - ((LeftTokat) i).getLengthPixel() / 4), ((LeftTokat) i).getLengthPixel() / 2,
							((LeftTokat) i).getLengthPixel() / 2);
				}
			}
			if (i instanceof RightTokat) {
				t.setColor(Color.BLUE.darker());
				t.fillPolygon(((RightTokat) i).getXArray(), ((RightTokat) i).getYArray(),
						((RightTokat) i).getXCoordinates().size());
				for (Circle circle : ((RightTokat) i).getCircleList()) {
					t.fillOval((int) (circle.getCenter().x() - ((RightTokat) i).getLengthPixel() / 4),
							(int) (circle.getCenter().y() - ((RightTokat) i).getLengthPixel() / 4), ((RightTokat) i).getLengthPixel() / 2,
							((RightTokat) i).getLengthPixel() / 2);
				}
			}
			if (i instanceof Ball) {
				t.setColor(Color.RED);
				t.fillOval((int) ((Ball) i).getX(), (int) ((Ball) i).getY(),
						(int) (((Ball) i).getDiameter() * GameWindow.getL()),
						(int) (((Ball) i).getDiameter() * GameWindow.getL()));
			}

		}
	}

	/**
	 * @return
	 */
	public boolean isEditMode() {
		// TODO Auto-generated method stub
		return this.editMode;
	}

	/**
	 * This method is used for updating the GameFrame's object data by getting
	 * the Board's objects.
	 */
	public void updateList() {
		list.clear();
		for (Gizmo i : Board.getInstance().getGizmos()) {
			list.add(i);
		}
		list.add(Engel.getInstance());
		for (Player i : Board.getInstance().getPlayers()) {
			list.add(i.getCezmi());
		}
		int temp  = 0;
		for (Ball i : Board.getInstance().getBalls()) {
			temp++;
			i.setBallNumber(temp);
			list.add(i);
		}
	}

	/**
	 * This method is used to invoke the AnimationWindow object in the rate of
	 * 2ms.
	 *
	 * @param logic true : Starts the animation by adding and removing the
	 * AnimationWindow object continuously. false: Pauses the animation by
	 * removing the AnimationWindow object and not adding it back.
	 */
	public void start(boolean logic) {
		if (isPause() == true && logic == true) {
			addKeyListener(animationWindow);
			requestFocus();
			timer.start();
			setPause(false);
		} else if (isPause() == false && logic == false) {
			removeKeyListener(animationWindow);
			timer.stop();
			setPause(true);
		}
	}

	public boolean isPause() {
		return pause;
	}

	public void setPause(boolean pause) {
		this.pause = pause;
	}

	public MouseClass getMouseClass() {
		return mouseClass;
	}

	public EditWindow getEditWindow() {
		return editWindow;
	}

	/**
	 * @return the selectedGizmo
	 */
	public Gizmo getSelectedGizmo() {
		return selectedGizmo;
	}

	/**
	 * @param selectedGizmo the selectedGizmo to set
	 */
	public void setSelectedGizmo(Gizmo selectedGizmo) {
		this.selectedGizmo = selectedGizmo;
	}

	/**
	 * @return the editGizmo
	 */
	public Gizmo getEditGizmo() {
		return editGizmo;
	}

	/**
	 * @param editGizmo the editGizmo to set
	 */
	public void setEditGizmo(Gizmo editGizmo) {
		this.editGizmo = editGizmo;
	}

	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
		if(editMode == true){
			GameFrame.super.addMouseMotionListener(getMouseClass());
			GameFrame.super.addMouseListener(getEditWindow());
		}else{
			GameFrame.super.removeMouseListener(getEditWindow());
			GameFrame.super.removeMouseMotionListener(getMouseClass());
		}
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
	
	/**
	 * Just for fun we added scorestreak sound that trigger when a certain action has happened.(In a lazy way)
	 * 2 scores consequently:
	 * 		dominating.wav
	 * 3 scores consequently:
	 * 		unstoppable.wav
	 * 4 scores consequently:
	 * 		wicked_sick.wav
	 * 5 scores consequently:
	 * 		godlike.wav
	 * 6 or higher scores consequetly:
	 * 		holy_shit.wav
	 * 
	 * enjoy :) (Files downloaded and converted to .wav from default announcer from dota2
	 *           ---> http://dota2.gamepedia.com/Announcer_responses)
	 *           All credit goes to the original creator, not to us.
	 */
	private void streaks() {
		if (BallMovementStrategy.streakList.size() == 2) {
			if (scorestreak == true) {
				try {
					in = new AudioStream(new FileInputStream("dominating.wav"));
					AudioPlayer.player.start(in);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			scorestreak = false;
		}
		if (BallMovementStrategy.streakList.size() == 3) {
			if (scorestreak == true) {
				try {
					in = new AudioStream(new FileInputStream("unstoppable.wav"));
					AudioPlayer.player.start(in);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			scorestreak = false;
		}
		if (BallMovementStrategy.streakList.size() == 4) {
			if (scorestreak == true) {
				try {
					in = new AudioStream(new FileInputStream("wicked_sick.wav"));
					AudioPlayer.player.start(in);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			scorestreak = false;
		}
		if (BallMovementStrategy.streakList.size() == 5) {
			if (scorestreak == true) {
				try {
					in = new AudioStream(new FileInputStream("godlike.wav"));
					AudioPlayer.player.start(in);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			scorestreak = false;
		}
		
		
		if (BallMovementStrategy.streakList.size() >= 6) {
			if (scorestreak == true) {
				try {
					in = new AudioStream(new FileInputStream("holy_shit.wav"));
					AudioPlayer.player.start(in);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			scorestreak = false;
		}
		
	}

	/**
	 * Main Class for animations, the only other class that can do animations is
	 * the EditWindow class. This class' object is updated according to the
	 * refresh rate of the timer object, which is currently set to 2ms.
	 * actionPerformed() method is used to repaint the Panel, which shows all
	 * the paintable objects. This method has constraints, which is handled in
	 * the keyListener() part. After all the keys are gathered in the List<Key>,
	 * the frame is updated with the repaint() method, which is inherited from
	 * the JPanel class. The constraints define the movement of the Cezmi and
	 * the Tokat objects, since there is no other object that needs a key
	 * activation. Ball object is updated automatically. Also another challange
	 * was to implement the actions of cezerye, that has different behaviour,
	 * which are explained down below 1: Rescales the gizmos 2x on your own side
	 * for 2 seconds 2: Shrinks the gizmos 1/2x on the opponents side for 2
	 * seconds 3: Freezes opponents cezmi, preventing them to move for 2 seconds
	 *
	 * Toolkit.getDefaultToolKit().sync() method is called only to improve
	 * performance on Linux distributions.
	 *
	 */
	private final class AnimationWindow implements ActionListener, KeyListener {

		private int leftTokatRotation = 0; //90 degrees arc for leftTokat
		private List<Integer> keyPressedList = new ArrayList<>();
		private int rightTokatRotation = 0; //90 degreed arc for rightTokat
		private Random rand = new Random();
		private double randVal = 500 * (rand.nextInt(26) + 5); //randomized cezerye blink
		private double randVal2 = 500 * (rand.nextInt(26) + 5) + findCezeryeAppearTime(); //randomized cezerye blink
		private double fiveSec = randVal2 - findCezeryeAppearTime(); //five seconds for cezerye blink (stays on board for 5 seconds)
		private int firstCezmiFreezeTimer = 1000; //freeze timer for first cezmi
		private int secondCezmiFreezeTimer = 1000; //freeze timer for second cezmi
		private int gizmoRescaleTimer = 1000; //grow or shrink timer for gizmos
		private List<Gizmo> rescaleList = new ArrayList<>();
		private int ballTimer = 1000;

		private double findCezeryeAppearTime(){
			Cezerye temp = null;
			for(Gizmo i : Board.getInstance().getGizmos()){
				if(i instanceof Cezerye){
					temp = (Cezerye) i;
				}
			}
			if(temp == null){
				return 2500;
			}
			return temp.getAppearTime() * 500;
		}

		private void restartProperties(){
			leftTokatRotation = 0;
			rightTokatRotation = 0;
			firstCezmiFreezeTimer = 1000;
			secondCezmiFreezeTimer = 1000;
			gizmoRescaleTimer = 1000;
			randVal = 500 * (rand.nextInt(26) + 5);
			randVal2 = 500 * (rand.nextInt(26) + 5) + 2500;
			fiveSec = randVal2 - 2500;
			ballTimer = 1000;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			for (Player player : Board.getInstance().getPlayers()) {
				if (player.getScore() >= 10) {
					if (player.getNum() == 1) {
						JOptionPane.showMessageDialog(getRootPane(), player.getName() + " wins !\nWith a score difference"
								+ " of " + (player.getScore() - Board.getInstance().getPlayers().get(1).getScore()) + " points !\nCongratulations " + player.getName() + " !");
					}
					if (player.getNum() == 2) {
						JOptionPane.showMessageDialog(getRootPane(), player.getName() + " wins !\nWith a score difference"
								+ " of " + (player.getScore() - Board.getInstance().getPlayers().get(0).getScore()) + " points !\nCongratulations " + player.getName() + " !");
					}
					Object[] choose = {"New Game", "Quit"};
					int finalise = JOptionPane.showOptionDialog(getRootPane(), "Game Over !", "End Game", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, choose, choose[0]);
					if (finalise == 0) {
						start(false);
						Cezerye tempCezerye = null;
						for(Gizmo i : Board.getInstance().getGizmos()){
							if(i instanceof Cezerye){
								tempCezerye = (Cezerye) i;
							}
						}
						Board.getInstance().getGizmos().clear();
						Board.getInstance().getGizmos().add(tempCezerye);
						Board.getInstance().getPlayers().get(0).createCezmi();
						Board.getInstance().getPlayers().get(0).setScore(0);
						Board.getInstance().getPlayers().get(1).createCezmi();
						Board.getInstance().getPlayers().get(1).setScore(0);
						Board.getInstance().getBalls().clear();
						if (Board.getInstance().getLevel() == 1) {
							Ball tempBall = new Ball();
							tempBall.setX(Board.getInstance().getPlayers().get(0).getCezmi().getX() + GameWindow.getL() / 2);
							tempBall.setY(Board.getInstance().getPlayers().get(0).getCezmi().getY() - 5 * GameWindow.getL());
							tempBall.setxVel(0);
							tempBall.setDiameter(1);
							Board.getInstance().getBalls().add(tempBall);
						}
						if (Board.getInstance().getLevel() == 2) {
							Ball tempBall2 = new Ball();
							tempBall2.setX(Board.getInstance().getPlayers().get(0).getCezmi().getX() + GameWindow.getL() / 2);
							tempBall2.setY(Board.getInstance().getPlayers().get(0).getCezmi().getY() - 5 * GameWindow.getL());
							tempBall2.setxVel(0);
							Ball tempBall3 = new Ball();
							tempBall3.setX(Board.getInstance().getPlayers().get(1).getCezmi().getX() + GameWindow.getL() / 2);
							tempBall3.setY(Board.getInstance().getPlayers().get(1).getCezmi().getY() - 5 * GameWindow.getL());
							tempBall3.setxVel(0);
							Board.getInstance().getBalls().add(tempBall2);
							Board.getInstance().getBalls().add(tempBall3);
						}
						updateList();
						setEditMode(true);
						keyPressedList.clear();

						leftTokatRotation = 0;
						rightTokatRotation = 0;
						firstCezmiFreezeTimer = 1000;
						secondCezmiFreezeTimer = 1000;
						gizmoRescaleTimer = 1000;
						randVal = 500 * (rand.nextInt(26) + 5);
						randVal2 = 500 * (rand.nextInt(26) + 5) + 2500;
						fiveSec = randVal2 - 2500;
						ballTimer = 1000;

						GameWindow.getInstance().prepareGame();
						GameWindow.getInstance().setContentPane(GameWindow.getInstance().getStartingPanel());
						GameWindow.getInstance().revalidate();
						GameWindow.getInstance().pack();
						GameWindow.getInstance().restartButton.setVisible(false);
						Board.getInstance().setLevel(0);
					} else {
						System.exit(0);
					}
				}
			}

			if(GameWindow.isRestart){
				start(false);
				Cezerye tempCezerye = null;
				for(Gizmo i : Board.getInstance().getGizmos()){
					if(i instanceof Cezerye){
						tempCezerye = (Cezerye) i;
					}
				}

				Board.getInstance().getGizmos().clear();
				Board.getInstance().getGizmos().add(tempCezerye);
				Board.getInstance().getPlayers().get(0).createCezmi();
				Board.getInstance().getPlayers().get(0).setScore(0);
				Board.getInstance().getPlayers().get(1).createCezmi();
				Board.getInstance().getPlayers().get(1).setScore(0);
				Board.getInstance().getBalls().clear();

				Ball tempBall = new Ball();
				tempBall.setX(Board.getInstance().getPlayers().get(0).getCezmi().getX() + GameWindow.getL() / 2);
				tempBall.setY(Board.getInstance().getPlayers().get(0).getCezmi().getY() - 5 * GameWindow.getL());
				tempBall.setxVel(0);
				tempBall.setDiameter(1);
				Board.getInstance().getBalls().add(tempBall);

				updateList();

				if(!isEditMode()){
					setEditMode(true);
				}
				keyPressedList.clear();
				restartProperties();

				GameWindow.getInstance().prepareGame();
				GameWindow.getInstance().setContentPane(GameWindow.getInstance().getStartingPanel());
				GameWindow.getInstance().revalidate();
				GameWindow.getInstance().pack();
				Board.getInstance().setLevel(0);

				GameWindow.isRestart = false;
			}

			for (Paintable i : list) {
				if (i instanceof Ball) {
					if(!((Ball) i).isGoal()){
						i.getMovementStrategy().moveAnimation(i);
					}else if(ballTimer <=0){
						((Ball) i).setGoal(false);
						((Ball) i).setxVel(0);
						((Ball) i).setyVel(0);
						ballTimer = 1000;
					}else{
						ballTimer--;
					}
				}
				if (i instanceof Firildak) {
					i.getMovementStrategy().moveAnimation(i);
				}
				if (i instanceof Cezerye) {
					if(((Cezerye) i).isEdited()){
						randVal2 = 500 * (rand.nextInt(26) + 5) + findCezeryeAppearTime();
						fiveSec = randVal2 - findCezeryeAppearTime();
						((Cezerye) i).setEdited(false);
					}
					if (!((Cezerye) i).isCollidesWith()) {
						if (randVal2 == 0) {
							((Cezerye) i).setVisible(true);
							i.getMovementStrategy().moveAnimation(i);
							//BlinkStrategy.deactivateBlink((Cezerye) i);
							rand = new Random();
							((Cezerye) i).setAppearTime(5);
							randVal2 = 500 * (rand.nextInt(26) + 5) + findCezeryeAppearTime();
							fiveSec = randVal2 - findCezeryeAppearTime();
						}
						if (randVal2 == fiveSec) {
							((Cezerye) i).setAppearTime(0);
							((Cezerye) i).setVisible(false);
							i.getMovementStrategy().moveAnimation(i);
							//BlinkStrategy.activateBlink((Cezerye) i);
						}
						randVal2--;
						if((randVal2 -fiveSec)/500 >= 0){
							((Cezerye) i).setAppearTime((double)(randVal2 -fiveSec)/500);
						}else{
							((Cezerye) i).setAppearTime(0);
						}
						if(((Cezerye) i).getAppearTime() < 0){
							((Cezerye) i).setAppearTime(0);
						}
					}
					if (((Cezerye) i).isCollidesWith()) {
						((Cezerye) i).setVisible(false);
						i.getMovementStrategy().moveAnimation(i);
						((Cezerye) i).setAppearTime(0);
						//BlinkStrategy.activateBlink((Cezerye) i);
						randVal--;
					}
					if (randVal == 0) {
						rand = new Random();
						((Cezerye) i).setAppearTime(5);
						randVal2 = 500 * (rand.nextInt(26) + 5) + findCezeryeAppearTime();
						fiveSec = randVal2 - findCezeryeAppearTime();
						((Cezerye) i).setAppearTime(5);
						((Cezerye) i).setVisible(true);
						i.getMovementStrategy().moveAnimation(i);
						//BlinkStrategy.deactivateBlink((Cezerye) i);
						rand = new Random();
						randVal = 500 * (rand.nextInt(26) + 5);
						((Cezerye) i).setCollidesWith(false);
					}
				}
				if (i instanceof Cezmi) {
					if (((Cezmi) i).getBelongsTo().equals(Board.getInstance().getPlayers().get(0))) {
						if (!((Cezmi) i).canMove()) {
							if (firstCezmiFreezeTimer == 0) {
								((Cezmi) i).setCanMove(true);
								firstCezmiFreezeTimer = 1000;
							} else {
								firstCezmiFreezeTimer--;
							}
						}
					} else if (((Cezmi) i).getBelongsTo().equals(Board.getInstance().getPlayers().get(1))) {
						if (!((Cezmi) i).canMove()) {
							if (secondCezmiFreezeTimer == 0) {
								((Cezmi) i).setCanMove(true);
								secondCezmiFreezeTimer = 1000;
							} else {
								secondCezmiFreezeTimer--;
							}
						}
					}
				}
				if (i instanceof LeftTokat) {
					if (LeftTokat.isIsMoving()) {
						if (leftTokatRotation == 0) {
							LeftTokat.setAngVel(0);
							LeftTokat.setIsMoving(false);
						} else {
							leftTokatRotation--;
							i.getMovementStrategy().moveAnimation(i);
						}
					}
				}
				if (i instanceof RightTokat) {
					if (RightTokat.isIsMoving()) {
						if (rightTokatRotation == 0) {
							RightTokat.setAngVel(0);
							RightTokat.setIsMoving(false);
						} else {
							rightTokatRotation--;
							i.getMovementStrategy().moveAnimation(i);
						}
					}
				}
			}
			for (Gizmo j : Board.getInstance().getGizmos()) {
				if (j.isRescaled()) {
					if (!rescaleList.contains(j)) {
						rescaleList.add(j);
					}
				}
			}
			if (gizmoRescaleTimer == 0) {
				for (Gizmo k : rescaleList) {
					k.setLength(1);
					k.setRescale(false);
				}
				rescaleList.clear();
				gizmoRescaleTimer = 1000;
			} else if (!rescaleList.isEmpty()) {
				gizmoRescaleTimer--;
			}

			if (keyPressedList.size() >= 1) {
				if (keyPressedList.contains(new Integer(32))) {
					if (leftTokatRotation <= 0) {
						LeftTokat.setIsMoving(true);
						leftTokatRotation = 90;
						LeftTokat.setAngVel(LeftTokat.specAngVel);
						LeftTokat.specAngVel = -LeftTokat.specAngVel;
					}
				}
				if (keyPressedList.contains(new Integer(10))) {
					if (rightTokatRotation <= 0) {
						RightTokat.setIsMoving(true);
						rightTokatRotation = 90;
						RightTokat.setAngVel(RightTokat.specAngVel);
						RightTokat.specAngVel = -RightTokat.specAngVel;
					}
				}
				if (keyPressedList.contains(new Integer(Board.getInstance().getPlayers().get(1).getKey1()))) {
					for (Paintable i : list) {
						if (i instanceof Cezmi) {
							if (((Cezmi) i).canMove()) {
								if (((Cezmi) i).getBelongsTo().equals(Board.getInstance().getPlayers().get(1))) {
									((Cezmi) i).setDirection(false);
									i.getMovementStrategy().moveAnimation(i);
								}
							}
						}
					}
				}
				if (keyPressedList.contains(new Integer(Board.getInstance().getPlayers().get(1).getKey2()))) {
					for (Paintable i : list) {
						if (i instanceof Cezmi) {
							if (((Cezmi) i).canMove()) {
								if (((Cezmi) i).getBelongsTo().equals(Board.getInstance().getPlayers().get(1))) {
									((Cezmi) i).setDirection(true);
									i.getMovementStrategy().moveAnimation(i);
								}
							}
						}
					}
				}
				if (keyPressedList.contains(new Integer(Board.getInstance().getPlayers().get(0).getKey1()))) {
					for (Paintable i : list) {
						if (i instanceof Cezmi) {
							if (((Cezmi) i).canMove()) {
								if (((Cezmi) i).getBelongsTo().equals(Board.getInstance().getPlayers().get(0))) {
									((Cezmi) i).setDirection(false);
									i.getMovementStrategy().moveAnimation(i);
								}
							}
						}
					}
				}
				if (keyPressedList.contains(new Integer(Board.getInstance().getPlayers().get(0).getKey2()))) {
					for (Paintable i : list) {
						if (i instanceof Cezmi) {
							if (((Cezmi) i).canMove()) {
								if (((Cezmi) i).getBelongsTo().equals(Board.getInstance().getPlayers().get(0))) {
									((Cezmi) i).setDirection(true);
									i.getMovementStrategy().moveAnimation(i);
								}
							}
						}
					}
				}
			}
			streaks();
			Toolkit.getDefaultToolkit().sync();
			repaint();
		}

		@Override
		public void keyPressed(KeyEvent e) {
			Player player1Keys = Board.getInstance().getPlayers().get(0);
			Player player2Keys = Board.getInstance().getPlayers().get(1);
			int p1k1 = player1Keys.getKey1();
			int p1k2 = player1Keys.getKey2();
			int p2k1 = player2Keys.getKey1();
			int p2k2 = player2Keys.getKey2();
			if(e.getKeyCode() == 10){
				if (!keyPressedList.contains(new Integer(e.getKeyCode()))) {
					keyPressedList.add(new Integer(e.getKeyCode()));
				}
			}else if(e.getKeyCode() == 32){
				if (!keyPressedList.contains(new Integer(e.getKeyCode()))) {
					keyPressedList.add(new Integer(e.getKeyCode()));
				}
			}else if (e.getKeyCode() == p1k1){
				if (!keyPressedList.contains(new Integer(e.getKeyCode()))) {
					keyPressedList.add(new Integer(e.getKeyCode()));
				}
			}else if (e.getKeyCode() == p1k2){
				if (!keyPressedList.contains(new Integer(e.getKeyCode()))) {
					keyPressedList.add(new Integer(e.getKeyCode()));
				}
			}else if (e.getKeyCode() == p2k1){
				if (!keyPressedList.contains(new Integer(e.getKeyCode()))) {
					keyPressedList.add(new Integer(e.getKeyCode()));
				}
			}else if (e.getKeyCode() == p2k2){
				if (!keyPressedList.contains(new Integer(e.getKeyCode()))) {
					keyPressedList.add(new Integer(e.getKeyCode()));
				}
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			keyPressedList.remove(new Integer(e.getKeyCode()));
		}

		@Override
		public void keyTyped(KeyEvent e) {
		}

	}



	private class EditWindow implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent arg0) {
			int xCord = arg0.getX() / GameWindow.getL() * GameWindow.getL();
			int yCord = arg0.getY() / GameWindow.getL() * GameWindow.getL();

			if (SwingUtilities.isLeftMouseButton(arg0)) {
				GameFrame.getInstance().setSelectedGizmo(null);
				boolean available = true;
				int rightCount = 0;
				int leftCount = 0;
				for (Gizmo gizmo : Board.getInstance().getGizmos()) {
					if (!(gizmo instanceof Cezerye)) {
						if (gizmo.getX() < Board.BOARD_SIZE * GameWindow.getL() / 2) {
							leftCount++;
						} else if (gizmo.getX() >= Board.BOARD_SIZE * GameWindow.getL() / 2) {
							rightCount++;
						}
					}
				}
				if (xCord < Board.BOARD_SIZE * GameWindow.getL() / 2) {
					leftCount++;
				} else if (xCord >= Board.BOARD_SIZE * GameWindow.getL() / 2) {
					rightCount++;
				}
				if (leftCount > 4 || rightCount > 4) {
					available = false;
				}
				Gizmo newGizmo = GizmoFactory.getInstance().getGizmo(GameWindow.getGizmoType());
				if (!GameWindow.getGizmoType().equalsIgnoreCase("null")) {
					if (GameWindow.getGizmoType().equalsIgnoreCase("SquareTakoz")) {
						((SquareTakoz) newGizmo).setX(xCord);
						((SquareTakoz) newGizmo).setY(yCord);
					} else if (GameWindow.getGizmoType().equalsIgnoreCase("TriangleTakoz")) {
						((TriangleTakoz) newGizmo).setX(xCord);
						((TriangleTakoz) newGizmo).setY(yCord);
					} else if (GameWindow.getGizmoType().equalsIgnoreCase("Firildak")) {
						((Firildak) newGizmo).setX(xCord);
						((Firildak) newGizmo).setY(yCord);
					} else if (GameWindow.getGizmoType().equalsIgnoreCase("LeftTokat")) {
						((LeftTokat) newGizmo).setX(xCord);
						((LeftTokat) newGizmo).setY(yCord);
						for (Gizmo countGizmo : Board.getInstance().getGizmos()) {
							if ((countGizmo instanceof LeftTokat)) {
								available = false;
							}
						}
					} else if (GameWindow.getGizmoType().equalsIgnoreCase("RightTokat")) {
						((RightTokat) newGizmo).setX(xCord);
						((RightTokat) newGizmo).setY(yCord);
						for (Gizmo countGizmo : Board.getInstance().getGizmos()) {
							if ((countGizmo instanceof RightTokat)) {
								available = false;
							}
						}
					}
					for (Point gizmoPoint : newGizmo.getBorderPoints()) {
						for (Gizmo gizmo : Board.getInstance().getGizmos()) {
							for (Point point : gizmo.getBorderPoints()) {
								if (gizmoPoint.getX() == point.getX() && gizmoPoint.getY() == point.getY()) {
									available = false;
								}
							}
						}
						for (Ball ball : Board.getInstance().getBalls()) {
							for (Point point : ball.getBorderPoints()) {
								if (gizmoPoint.getX() == point.getX() && gizmoPoint.getY() == point.getY()) {
									available = false;
								}
							}
						}
					}
					if (isPlace(newGizmo)) {
						if (available) {
							Board.getInstance().getGizmos().add(newGizmo);
							list.add(newGizmo);
							repaint();
						}
					}
				}
			} else if (SwingUtilities.isRightMouseButton(arg0)) {
				Gizmo deletedGizmo = null;
				for (Gizmo gizmo : Board.getInstance().getGizmos()) {
					for (Point gizmoPoint : gizmo.getBorderPoints()) {
						if (gizmoPoint.getX() == xCord && gizmoPoint.getY() == yCord) {
							deletedGizmo = gizmo;
						}
					}
				}
				if (deletedGizmo != null) {
					if (!(deletedGizmo instanceof Cezerye)) {
						Board.getInstance().getGizmos().remove(deletedGizmo);
						list.remove(deletedGizmo);
						repaint();
					}
				}
			} else if (SwingUtilities.isMiddleMouseButton(arg0)) {
				for (Gizmo gizmo : Board.getInstance().getGizmos()) {
					for (Point gizmoPoint : gizmo.getBorderPoints()) {
						if (gizmoPoint.getX() == xCord && gizmoPoint.getY() == yCord) {
							if (gizmo instanceof TriangleTakoz) {
								((TriangleTakoz) gizmo).setOrientation(((TriangleTakoz) gizmo).getOrientation().rotateClockwise());
							} else if (gizmo instanceof LeftTokat) {
								((LeftTokat) gizmo).setOrientation(((LeftTokat) gizmo).getOrientation().rotateClockwise());
							} else if (gizmo instanceof RightTokat) {
								((RightTokat) gizmo).setOrientation(((RightTokat) gizmo).getOrientation().rotateClockwise());
							}
							repaint();
						}
					}
				}
			}
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			int xCord = arg0.getX() / GameWindow.getL() * GameWindow.getL();
			int yCord = arg0.getY() / GameWindow.getL() * GameWindow.getL();
			boolean available = true;
			if (!GameWindow.getGizmoType().equalsIgnoreCase("null")) {
				editGizmo = GizmoFactory.getInstance().getGizmo(GameWindow.getGizmoType());
				if (GameWindow.getGizmoType().equalsIgnoreCase("SquareTakoz")) {
					((SquareTakoz) editGizmo).setX(xCord);
					((SquareTakoz) editGizmo).setY(yCord);
				} else if (GameWindow.getGizmoType().equalsIgnoreCase("TriangleTakoz")) {
					((TriangleTakoz) editGizmo).setX(xCord);
					((TriangleTakoz) editGizmo).setY(yCord);
				} else if (GameWindow.getGizmoType().equalsIgnoreCase("Firildak")) {
					((Firildak) editGizmo).setX(xCord);
					((Firildak) editGizmo).setY(yCord);
				} else if (GameWindow.getGizmoType().equalsIgnoreCase("LeftTokat")) {
					((LeftTokat) editGizmo).setX(xCord);
					((LeftTokat) editGizmo).setY(yCord);
					for (Gizmo countGizmo : Board.getInstance().getGizmos()) {
						if ((countGizmo instanceof LeftTokat)) {
							available = false;
						}
					}
				} else if (GameWindow.getGizmoType().equalsIgnoreCase("RightTokat")) {
					((RightTokat) editGizmo).setX(xCord);
					((RightTokat) editGizmo).setY(yCord);
					for (Gizmo countGizmo : Board.getInstance().getGizmos()) {
						if ((countGizmo instanceof RightTokat)) {
							available = false;
						}
					}
				}
				for (Point gizmoPoint : editGizmo.getBorderPoints()) {
					for (Gizmo gizmo : Board.getInstance().getGizmos()) {
						for (Point point : gizmo.getBorderPoints()) {
							if (gizmoPoint.getX() == point.getX() && gizmoPoint.getY() == point.getY()) {
								available = false;
							}
						}
					}
					for (Ball ball : Board.getInstance().getBalls()) {
						for (Point point : ball.getBorderPoints()) {
							if (gizmoPoint.getX() == point.getX() && gizmoPoint.getY() == point.getY()) {
								available = false;
							}
						}
					}
				}
				if (isPlace(editGizmo)) {
					if (available) {
						list.add(editGizmo);
						repaint();
					}
				}
			}
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			list.remove(editGizmo);
			editGizmo = null;
			repaint();
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			int xCord = arg0.getX() / GameWindow.getL() * GameWindow.getL();
			int yCord = arg0.getY() / GameWindow.getL() * GameWindow.getL();
			if (SwingUtilities.isLeftMouseButton(arg0)) {
				for (Gizmo gizmo : Board.getInstance().getGizmos()) {
					for (Point gizmoPoint : gizmo.getBorderPoints()) {
						if (gizmoPoint.getX() == xCord && gizmoPoint.getY() == yCord) {
							selectedGizmo = gizmo;
						}
					}
				}
			}
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			if (SwingUtilities.isLeftMouseButton(arg0)) {
				selectedGizmo = null;
			}
		}

	}


	private class MouseClass implements MouseMotionListener {

		@Override
		public void mouseDragged(MouseEvent e) {
			int xCord = e.getX() / GameWindow.getL() * GameWindow.getL();
			int yCord = e.getY() / GameWindow.getL() * GameWindow.getL();

			if (selectedGizmo != null) {
				boolean available = true;
				//List<Gizmo> gizmoList = Board.getInstance().getGizmos();
				//gizmoList.remove(movedGizmo);
				List<Gizmo> gizmoList = new ArrayList<>();
				gizmoList.addAll(Board.getInstance().getGizmos());
				gizmoList.remove(selectedGizmo);

				Gizmo dummyGizmo = GizmoFactory.getInstance().getGizmo(selectedGizmo.getClass().getSimpleName());

				if(dummyGizmo instanceof SquareTakoz) {
					((SquareTakoz)dummyGizmo).setX(xCord);
					((SquareTakoz)dummyGizmo).setY(yCord);
				}
				else if(dummyGizmo instanceof TriangleTakoz) {
					((TriangleTakoz)dummyGizmo).setX(xCord);
					((TriangleTakoz)dummyGizmo).setY(yCord);
				}
				else if(dummyGizmo instanceof Firildak) {
					((Firildak)dummyGizmo).setX(xCord);
					((Firildak)dummyGizmo).setY(yCord);
				}
				else if(dummyGizmo instanceof LeftTokat) {
					((LeftTokat)dummyGizmo).setX(xCord);
					((LeftTokat)dummyGizmo).setY(yCord);
				}
				else if(dummyGizmo instanceof RightTokat) {
					((RightTokat)dummyGizmo).setX(xCord);
					((RightTokat)dummyGizmo).setY(yCord);
				}
				else if(dummyGizmo instanceof Cezerye) {
					((Cezerye)dummyGizmo).setX(xCord);
					((Cezerye)dummyGizmo).setY(yCord);
				}

				for (Gizmo gizmo : gizmoList) {
					for (Point gizmoPoint : gizmo.getBorderPoints()) {
						for (Point point : dummyGizmo.getBorderPoints()) {
							if (gizmoPoint.getX() == point.getX() && gizmoPoint.getY() == point.getY()) {
								available = false;
							}
						}
					}
				}

				for (Ball ball : Board.getInstance().getBalls()) {
					for (Point ballPoint : ball.getBorderPoints()) {
						for (Point point : dummyGizmo.getBorderPoints()) {
							if (ballPoint.getX() == point.getX() && ballPoint.getY() == point.getY()) {
								available = false;
							}
						}
					}
				}

				if (available) {

					if (isPlace(dummyGizmo)) {
						if (selectedGizmo instanceof SquareTakoz) {
							((SquareTakoz) selectedGizmo).setX(xCord);
							((SquareTakoz) selectedGizmo).setY(yCord);
						} else if (selectedGizmo instanceof TriangleTakoz) {
							((TriangleTakoz) selectedGizmo).setX(xCord);
							((TriangleTakoz) selectedGizmo).setY(yCord);
						} else if (selectedGizmo instanceof Firildak) {
							((Firildak) selectedGizmo).setX(xCord);
							((Firildak) selectedGizmo).setY(yCord);
						} else if (selectedGizmo instanceof LeftTokat) {
							((LeftTokat) selectedGizmo).setX(xCord);
							((LeftTokat) selectedGizmo).setY(yCord);
						} else if (selectedGizmo instanceof RightTokat) {
							((RightTokat) selectedGizmo).setX(xCord);
							((RightTokat) selectedGizmo).setY(yCord);
						} else if (selectedGizmo instanceof Cezerye) {
							((Cezerye) selectedGizmo).setX(xCord);
							((Cezerye) selectedGizmo).setY(yCord);
						}
						repaint();
					}
				}
			}
		}


		@Override
		public void mouseMoved(MouseEvent e) {

			int xCord = e.getX() / GameWindow.getL() * GameWindow.getL();
			int yCord = e.getY() / GameWindow.getL() * GameWindow.getL();
			//editGizmo = GameFrame.getInstance().getEditGizmo();

			if (editGizmo != null) {
				if (!list.contains(editGizmo)) {
					list.add(editGizmo);
				}

				boolean available = true;

				//List<Gizmo> gizmoList = Board.getInstance().getGizmos();
				//gizmoList.remove(movedGizmo);
				Gizmo dummyGizmo = editGizmo;
				dummyGizmo.setX(xCord);
				dummyGizmo.setY(yCord);

				for (Gizmo gizmo : Board.getInstance().getGizmos()) {
					for (Point gizmoPoint : gizmo.getBorderPoints()) {
						for (Point point : dummyGizmo.getBorderPoints()) {
							if (gizmoPoint.getX() == point.getX() && gizmoPoint.getY() == point.getY()) {
								available = false;
							}
						}
					}
				}

				for (Ball ball : Board.getInstance().getBalls()) {
					for (Point ballPoint : ball.getBorderPoints()) {
						for (Point point : dummyGizmo.getBorderPoints()) {
							if (ballPoint.getX() == point.getX() && ballPoint.getY() == point.getY()) {
								available = false;
							}
						}
					}
				}

				if (available) {
					if (isPlace(dummyGizmo)) {
						if (editGizmo instanceof SquareTakoz) {
							((SquareTakoz) editGizmo).setX(xCord);
							((SquareTakoz) editGizmo).setY(yCord);
						} else if (editGizmo instanceof TriangleTakoz) {
							((TriangleTakoz) editGizmo).setX(xCord);
							((TriangleTakoz) editGizmo).setY(yCord);
						} else if (editGizmo instanceof Firildak) {
							((Firildak) editGizmo).setX(xCord);
							((Firildak) editGizmo).setY(yCord);
						} else if (editGizmo instanceof LeftTokat) {
							((LeftTokat) editGizmo).setX(xCord);
							((LeftTokat) editGizmo).setY(yCord);
						} else if (editGizmo instanceof RightTokat) {
							((RightTokat) editGizmo).setX(xCord);
							((RightTokat) editGizmo).setY(yCord);
						}
						repaint();
					} else {
						list.remove(editGizmo);
						repaint();
					}
				} else {
					list.remove(editGizmo);
					repaint();
				}

			}

		}

	}

}
