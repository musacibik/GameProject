/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import main.domain.Board;
import main.domain.gizmos.Cezerye;
import main.domain.gizmos.Gizmo;
import main.domain.gizmos.GizmoFactory;
import main.service.xml.XMLParser;
import main.service.xml.XMLValidator;
import main.service.xml.XMLWriter;

/**
 *
 * @author JusticeLeague
 */
public final class GameWindow extends JFrame {

	private static final long serialVersionUID = -5480408589711823712L;
	private static final int referenceHeight=1000;
	
	private static int l;
	private static String gizmoType;
	public static Dimension SCREEN_DIM;
	private GameFrame frame;
	private JPanel startingWindow;
	private JPanel mainWindow;
	private FileOperations fileOperations;
	private JTextField scoreField1;
	private JTextField scoreField2;
	private JTextField player1Name;
	private JTextField player2Name;
	private JButton level1Button;
	private JButton level2Button; 
	private JButton startingButton;
	public JButton restartButton;
	private JTextField player1Field;
	private JTextField player2Field;
	private static GameWindow instance;
	public static boolean isRestart;

	public static synchronized GameWindow getInstance() {
		if(instance==null){
			instance=new GameWindow();
		}
		return instance;
	}

	private GameWindow() {
		super("HadiCezmiGame");
		adjustScreen();

		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException er) {
			er.printStackTrace();
		}

		prepareGame();
		super.setContentPane(getStartingPanel());

		fileOperations = new FileOperations();

		gizmoType = "null";

		mainWindow = new JPanel();
		JButton startButton = new JButton();
		JButton pauseButton = new JButton();
		JButton loadButton = new JButton();
		JButton saveButton = new JButton();
		restartButton = new JButton();
		JButton quitButton = new JButton();
		JButton squareTakozButton = new JButton("Square");
		JButton triangleTakozButton = new JButton("Triangle");
		JButton firildakButton = new JButton("Firildak");
		JButton leftTokatButton = new JButton("Left Tokat");
		JButton rightTokatButton = new JButton("Right Tokat");
		JLabel gizmosLabel = new JLabel();

		startButton.setIcon(new ImageIcon("icons/start.png"));
		pauseButton.setIcon(new ImageIcon("icons/pause.png"));
		loadButton.setIcon(new ImageIcon("icons/load.png"));
		saveButton.setIcon(new ImageIcon("icons/save.png"));
		restartButton.setIcon(new ImageIcon("icons/restart.png"));
		quitButton.setIcon(new ImageIcon("icons/quit.png"));
		squareTakozButton.setIcon(new ImageIcon("icons/square.png"));
		triangleTakozButton.setIcon(new ImageIcon("icons/triangle.png"));
		firildakButton.setIcon(new ImageIcon("icons/firildak.png"));
		leftTokatButton.setIcon(new ImageIcon("icons/left.png"));
		rightTokatButton.setIcon(new ImageIcon("icons/right.png"));
		gizmosLabel.setIcon(new ImageIcon("icons/gizmos.png"));

		startButton.setToolTipText("Starts the game");	
		pauseButton.setToolTipText("Pauses the game");
		loadButton.setToolTipText("Loads a saved game");
		saveButton.setToolTipText("Saves a game into an XML file");
		restartButton.setToolTipText("Restarts the game");
		quitButton.setToolTipText("Quits the game");
		squareTakozButton.setToolTipText("Adds a Square Takoz");
		triangleTakozButton.setToolTipText("Adds a Triangle Takoz");
		firildakButton.setToolTipText("Adds a Firildak");
		leftTokatButton.setToolTipText("Adds a Left Tokat");
		rightTokatButton.setToolTipText("Adds a Right Tokat");

		Color buttonBackgroundColor = new Color(231,238,247);

		squareTakozButton.setBorderPainted(false);
		squareTakozButton.setFocusPainted(false);
		squareTakozButton.setBackground(Color.WHITE);
		triangleTakozButton.setBorderPainted(false);
		triangleTakozButton.setFocusPainted(false);
		triangleTakozButton.setBackground(Color.WHITE);
		firildakButton.setBorderPainted(false);
		firildakButton.setFocusPainted(false);
		firildakButton.setBackground(Color.WHITE);
		leftTokatButton.setBorderPainted(false);
		leftTokatButton.setFocusPainted(false);
		leftTokatButton.setBackground(Color.WHITE);
		rightTokatButton.setBorderPainted(false);
		rightTokatButton.setFocusPainted(false);
		rightTokatButton.setBackground(Color.WHITE);	
		startButton.setBorderPainted(false);
		startButton.setFocusPainted(false);
		startButton.setBackground(Color.WHITE);
		pauseButton.setBorderPainted(false);
		pauseButton.setFocusPainted(false);
		pauseButton.setBackground(Color.WHITE);
		loadButton.setBorderPainted(false);
		loadButton.setFocusPainted(false);
		loadButton.setBackground(Color.WHITE);
		saveButton.setBorderPainted(false);
		saveButton.setFocusPainted(false);
		saveButton.setBackground(Color.WHITE);
		restartButton.setBorderPainted(false);
		restartButton.setFocusPainted(false);
		restartButton.setBackground(Color.WHITE);
		restartButton.setVisible(false);
		quitButton.setBorderPainted(false);
		quitButton.setFocusPainted(false);
		quitButton.setBackground(Color.WHITE);

		squareTakozButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				squareTakozButton.setBackground(buttonBackgroundColor);
			}
			public void mouseExited(java.awt.event.MouseEvent evt) {
				squareTakozButton.setBackground(Color.WHITE);
			}
		});
		triangleTakozButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				triangleTakozButton.setBackground(buttonBackgroundColor);
			}
			public void mouseExited(java.awt.event.MouseEvent evt) {
				triangleTakozButton.setBackground(Color.WHITE);
			}
		});
		firildakButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				firildakButton.setBackground(buttonBackgroundColor);
			}
			public void mouseExited(java.awt.event.MouseEvent evt) {
				firildakButton.setBackground(Color.WHITE);
			}
		});
		leftTokatButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				leftTokatButton.setBackground(buttonBackgroundColor);
			}
			public void mouseExited(java.awt.event.MouseEvent evt) {
				leftTokatButton.setBackground(Color.WHITE);
			}
		});
		rightTokatButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				rightTokatButton.setBackground(buttonBackgroundColor);
			}
			public void mouseExited(java.awt.event.MouseEvent evt) {
				rightTokatButton.setBackground(Color.WHITE);
			}
		});
		startButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				startButton.setBackground(buttonBackgroundColor);
			}
			public void mouseExited(java.awt.event.MouseEvent evt) {
				startButton.setBackground(Color.WHITE);
			}
		});
		pauseButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				pauseButton.setBackground(buttonBackgroundColor);
			}
			public void mouseExited(java.awt.event.MouseEvent evt) {
				pauseButton.setBackground(Color.WHITE);
			}
		});
		loadButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				loadButton.setBackground(buttonBackgroundColor);
			}
			public void mouseExited(java.awt.event.MouseEvent evt) {
				loadButton.setBackground(Color.WHITE);
			}
		});
		saveButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				saveButton.setBackground(buttonBackgroundColor);
			}
			public void mouseExited(java.awt.event.MouseEvent evt) {
				saveButton.setBackground(Color.WHITE);
			}
		});
		restartButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				restartButton.setBackground(buttonBackgroundColor);
			}
			public void mouseExited(java.awt.event.MouseEvent evt) {
				restartButton.setBackground(Color.WHITE);
			}
		});
		quitButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				quitButton.setBackground(buttonBackgroundColor);
			}
			public void mouseExited(java.awt.event.MouseEvent evt) {
				quitButton.setBackground(Color.WHITE);
			}
		});

		scoreField1 = new JTextField("0");
		scoreField1.setBackground(Color.BLACK);
		scoreField1.setForeground(Color.WHITE);
		scoreField1.setHorizontalAlignment(JTextField.CENTER);
		scoreField2 = new JTextField("0");
		scoreField2.setBackground(Color.BLACK);
		scoreField2.setForeground(Color.WHITE);
		scoreField2.setHorizontalAlignment(JTextField.CENTER);
		player1Field = new JTextField("Player 1");
		player1Field.setBackground(Color.BLACK);
		player1Field.setForeground(Color.WHITE);
		player1Field.setHorizontalAlignment(JTextField.CENTER);
		player1Field.setEditable(false);
		player2Field = new JTextField("Player 2");
		player2Field.setBackground(Color.BLACK);
		player2Field.setForeground(Color.WHITE);
		player2Field.setHorizontalAlignment(JTextField.CENTER);
		player2Field.setEditable(false);

		JToolBar operationToolbar = new JToolBar();
		operationToolbar.setFloatable(false);
		operationToolbar.setBackground(Color.WHITE);
		operationToolbar.setBorderPainted(false);
		JToolBar takozToolbar = new JToolBar();
		takozToolbar.setLayout(new BoxLayout(takozToolbar, BoxLayout.Y_AXIS));
		takozToolbar.setBackground(Color.WHITE);
		takozToolbar.setBorderPainted(false);
		takozToolbar.setFloatable(false);
		JToolBar playerToolbar = new JToolBar();
		playerToolbar.setFloatable(false);
		playerToolbar.setBackground(Color.WHITE);
		playerToolbar.setBorderPainted(false);

		operationToolbar.add(startButton);
		operationToolbar.add(pauseButton);
		operationToolbar.add(loadButton);
		operationToolbar.add(saveButton);
		operationToolbar.add(restartButton);
		operationToolbar.add(quitButton);
		takozToolbar.add(gizmosLabel);
		takozToolbar.add(squareTakozButton);
		takozToolbar.add(triangleTakozButton);
		takozToolbar.add(firildakButton);
		takozToolbar.add(leftTokatButton);
		takozToolbar.add(rightTokatButton);

		playerToolbar.add(player1Field);
		playerToolbar.add(scoreField1);
		playerToolbar.add(player2Field);
		playerToolbar.add(scoreField2);

		frame = GameFrame.getInstance();

		frame.setBackground(Color.BLACK);
		JScrollPane field  = new JScrollPane(frame);
		mainWindow.setLayout(new BorderLayout());
		SCREEN_DIM = new Dimension(Board.BOARD_SIZE * l, Board.BOARD_SIZE * l);
		field.setPreferredSize(SCREEN_DIM);
		mainWindow.add(field,BorderLayout.CENTER);
		mainWindow.add(operationToolbar, BorderLayout.NORTH);
		mainWindow.add(takozToolbar, BorderLayout.WEST);
		mainWindow.add(playerToolbar, BorderLayout.SOUTH);

		startButton.addActionListener(new ButtonListener("Start"));
		pauseButton.addActionListener(new ButtonListener("Pause"));
		loadButton.addActionListener(new ButtonListener("Load"));
		saveButton.addActionListener(new ButtonListener("Save"));
		restartButton.addActionListener(new ButtonListener("Restart"));
		quitButton.addActionListener(new ButtonListener("Quit"));
		squareTakozButton.addActionListener(new ButtonListener(squareTakozButton.getText()));
		triangleTakozButton.addActionListener(new ButtonListener(triangleTakozButton.getText()));
		firildakButton.addActionListener(new ButtonListener(firildakButton.getText()));
		leftTokatButton.addActionListener(new ButtonListener(leftTokatButton.getText()));
		rightTokatButton.addActionListener(new ButtonListener(rightTokatButton.getText()));
	}

	private void adjustScreen(){
		int height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		if(height < referenceHeight) {
			l = 20;
		}
		else {
			l = 20;
		}
	}

	/**
	 * @return the gizmoType
	 */
	public static String getGizmoType() {
		return gizmoType;
	}

	/**
	 * @param gizmoType the gizmoType to set
	 */
	public static void setGizmoType(String gizmoType) {
		GameWindow.gizmoType = gizmoType;
	}


	private class ButtonListener implements ActionListener {

		private String name;

		public ButtonListener(String name) {
			this.name = name;
		}

		@Override
		public void actionPerformed(ActionEvent e) {

			if(name.equalsIgnoreCase("level1")) {
				Board.getInstance().setLevel(1);
				Board.getInstance().updateBalls();
				GameFrame.getInstance().updateList();
				level1Button.setIcon(new ImageIcon("icons/level-1_yes.png"));
				level2Button.setIcon(new ImageIcon("icons/level-2_no.png"));
			}
			else if(name.equalsIgnoreCase("level2")) {
				Board.getInstance().setLevel(2);
				Board.getInstance().updateBalls();
				GameFrame.getInstance().updateList();
				level1Button.setIcon(new ImageIcon("icons/level-1_no.png"));
				level2Button.setIcon(new ImageIcon("icons/level-2_yes.png"));
			}
			else if(name.equalsIgnoreCase("starting")) {
				if(Board.getInstance().getLevel() == 0) {
					JOptionPane.showMessageDialog(rootPane,"Please select a level", null, JOptionPane.OK_OPTION, new ImageIcon("icons/cezmi1.png"));

				} else {
					GameWindow.getInstance().getContentPane().removeAll();
					Board.getInstance().getPlayers().get(0).setName(player1Name.getText());
					Board.getInstance().getPlayers().get(1).setName(player2Name.getText());

					startingButton.setPressedIcon(new ImageIcon());

					player1Field.setText(Board.getInstance().getPlayers().get(0).getName());
					player2Field.setText(Board.getInstance().getPlayers().get(1).getName());

					GameWindow.getInstance().setContentPane(mainWindow);
					GameWindow.getInstance().revalidate();
					GameWindow.getInstance().pack();
				}
			}
			else if(name.equalsIgnoreCase("Start")) {
				gizmoType = "null";
				ArrayList<Gizmo> tempList = new ArrayList<>();

				for(Gizmo g: Board.getInstance().getGizmos()){
					if(!(g instanceof Cezerye)){
						tempList.add(g);
					}
				}

				if(tempList.size() == 8){

					boolean cezeryeExists = false;
					for(Gizmo g: Board.getInstance().getGizmos()){
						if(g instanceof Cezerye){
							cezeryeExists = true;
						}
					}

					if(!cezeryeExists){
						Cezerye cezerye = (Cezerye) GizmoFactory.getInstance().getGizmo("cezerye");
						cezerye.random();
						Board.getInstance().getGizmos().add(cezerye);
						GameFrame.getInstance().updateList();
					}

					frame.start(true);
					frame.removeMouseListener(frame.getEditWindow());
					frame.removeMouseMotionListener(frame.getMouseClass());
					frame.setEditMode(false);
					restartButton.setVisible(true);			
				}else{
					JOptionPane.showMessageDialog(rootPane, "Gizmos are incomplete ! Please complete the edit mode !");
				}
			}
			else if(name.equalsIgnoreCase("Pause")) {
				gizmoType = "null";
				frame.start(false);
			}
			else if(name.equalsIgnoreCase("Load")) {
				gizmoType = "null";
				frame.start(false);
				restartButton.setVisible(false);
				if (fileOperations.showLoad() == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileOperations.getLoadChooser().getSelectedFile();

					if (XMLValidator.getInstance().validateXML(selectedFile) == true) {
						if(XMLParser.getInstance().parse(selectedFile) == true){

							GameFrame.getInstance().updateList();
							GameFrame.getInstance().repaint();
							if(!GameFrame.getInstance().isEditMode()){
								GameFrame.getInstance().setEditMode(true);
							}
							Board.getInstance().updateBalls();
						} else {
							JOptionPane.showMessageDialog(rootPane, "XML file validation is failed..");
						}
					} else {
						JOptionPane.showMessageDialog(rootPane, "XSD file validation is failed..");
					}
				} else {
					System.err.println("No XML file was chosen");
				}
			}
			else if(name.equalsIgnoreCase("Save")) {
				gizmoType = "null";
				frame.start(false);
				if (fileOperations.showSave() == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileOperations.getSaveChooser().getSelectedFile();
					String fileName = selectedFile.toString();

					if(!fileName.endsWith(".xml"))
						selectedFile = new File(fileName.concat(".xml"));

					XMLWriter.getInstance().writeToXML(selectedFile);
					JOptionPane.showMessageDialog(rootPane, "Saved filed successfully..");

				} else {
					System.err.println("Closed dialog without saving a file.");
				}

			}
			else if(name.equalsIgnoreCase("Restart")) {
				isRestart = true;
				frame.start(true);
				restartButton.setVisible(false);
			}
			else if(name.equalsIgnoreCase("Quit")) {
				System.exit(0);
			}
			else if(name.equalsIgnoreCase("Square")) {
				gizmoType = "SquareTakoz";
			}
			else if(name.equalsIgnoreCase("Triangle")) {
				gizmoType = "TriangleTakoz";
			}
			else if(name.equalsIgnoreCase("Firildak")) {
				gizmoType = "Firildak";
			}
			else if(name.equalsIgnoreCase("Left Tokat")) {
				gizmoType = "LeftTokat";
			}
			else if(name.equalsIgnoreCase("Right Tokat")) {
				gizmoType = "RightTokat";
			}
		}
	}

	public void prepareGame() {
		setStartingPanel(new JPanel());
		getStartingPanel().setBackground(Color.WHITE);
		getStartingPanel().setLayout(null);

		JLabel cezmiImage1 = new JLabel();
		JLabel cezmiImage2 = new JLabel();
		JLabel label1 = new JLabel();
		JLabel label2 = new JLabel();
		label1.setIcon(new ImageIcon("icons/label-level.png"));		
		label2.setIcon(new ImageIcon("icons/label-names.png"));
		cezmiImage1.setIcon(new ImageIcon("icons/cezmi1.png"));
		cezmiImage2.setIcon(new ImageIcon("icons/cezmi2.png"));

		level1Button = new JButton();
		level2Button = new JButton();	
		startingButton = new JButton();
		level1Button.setIcon(new ImageIcon("icons/level-1_no.png"));
		level2Button.setIcon(new ImageIcon("icons/level-2_no.png"));	
		startingButton.setIcon(new ImageIcon("icons/button_start.png"));	

		player1Name = new JTextField("Player 1", 8);
		player2Name = new JTextField("Player 2", 8);

		player1Name.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		player2Name.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

		player1Name.setFont(new Font("SansSerif", Font.BOLD, 20));
		player2Name.setFont(new Font("SansSerif", Font.BOLD, 20));

		player1Name.setHorizontalAlignment(JTextField.CENTER);
		player2Name.setHorizontalAlignment(JTextField.CENTER);

		level1Button.setBorderPainted(false);
		level1Button.setFocusPainted(false);
		level1Button.setContentAreaFilled(false);
		level2Button.setBorderPainted(false);
		level2Button.setFocusPainted(false);
		level2Button.setContentAreaFilled(false);
		startingButton.setBorderPainted(false);
		startingButton.setFocusPainted(false);
		startingButton.setContentAreaFilled(false);

		getStartingPanel().setPreferredSize(new Dimension(500, 500));
		cezmiImage1.setBounds(100, 60, 128, 128);
		cezmiImage2.setBounds(280, 160, 128, 128);
		player1Name.setBounds(245, 120, 150, 40);
		player2Name.setBounds(100, 220, 150, 40);
		label1.setBounds(110, 290, 280, 40);
		label2.setBounds(110, 25, 280, 40);
		level1Button.setBounds(120, 340, 120, 40);
		level2Button.setBounds(260, 340, 120, 40);
		startingButton.setBounds(110, 420, 280, 40);
		getStartingPanel().add(label1);
		getStartingPanel().add(label2);
		getStartingPanel().add(level1Button);
		getStartingPanel().add(level2Button);
		getStartingPanel().add(startingButton);
		getStartingPanel().add(cezmiImage1);
		getStartingPanel().add(cezmiImage2);
		getStartingPanel().add(player1Name);
		getStartingPanel().add(player2Name);
		getStartingPanel().validate();

		level1Button.addActionListener(new ButtonListener("level1"));
		level2Button.addActionListener(new ButtonListener("level2"));
		startingButton.addActionListener(new ButtonListener("starting"));		

	}

	/**
	 * @return the scoreField1
	 */
	public JTextField getScoreField1() {
		return scoreField1;
	}


	/**
	 * @param scoreField1 the scoreField1 to set
	 */
	public void setScoreField1(JTextField scoreField1) {
		this.scoreField1 = scoreField1;
	}


	/**
	 * @return the scoreField2
	 */
	public JTextField getScoreField2() {
		return scoreField2;
	}

	/**
	 * @param scoreField2 the scoreField2 to set
	 */
	public void setScoreField2(JTextField scoreField2) {
		this.scoreField2 = scoreField2;
	}

	public static int getL() {
		return l;
	}


	public JPanel getStartingPanel() {
		return startingWindow;
	}


	public void setStartingPanel(JPanel startingPanel) {
		this.startingWindow = startingPanel;
	}

}
