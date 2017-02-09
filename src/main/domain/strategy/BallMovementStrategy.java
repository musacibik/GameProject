package main.domain.strategy;

import java.util.Random;
import java.util.Stack;

import main.domain.Ball;
import main.domain.Board;
import main.domain.Engel;
import main.domain.Paintable;
import main.domain.Player;
import main.domain.gizmos.Cezerye;
import main.domain.gizmos.Firildak;
import main.domain.gizmos.Gizmo;
import main.domain.gizmos.LeftTokat;
import main.domain.gizmos.RightTokat;
import main.domain.gizmos.SquareTakoz;
import main.domain.gizmos.TriangleTakoz;
import main.physics.Circle;
import main.physics.Geometry;
import main.physics.Geometry.VectPair;
import main.physics.LineSegment;
import main.physics.Vect;
import main.ui.GameFrame;
import main.ui.GameWindow;

public class BallMovementStrategy implements IMovementStrategy {

	private static final Random RD = new Random();
	private static BallMovementStrategy instance;
	public static final Stack<Boolean> streakList = new Stack<>();
	private double deltaT;

	private BallMovementStrategy() {
	}

	public static IMovementStrategy getInstance() {
		if (instance == null) {
			instance = new BallMovementStrategy();
		}

		return instance;
	}

	public String toString(){
		return ("Ball Movement");
	}

	@Override
	public void moveAnimation(Paintable context) {

		if(Board.getInstance().getLevel() == 1) {
			deltaT = 0.002;
		}
		else if(Board.getInstance().getLevel() == 2) {
			deltaT = 0.002;
		}

		((Ball) context).setxVel((500 / (double) GameWindow.getL()) * ((Ball) context).getxVel());
		((Ball) context).setxVel(((Ball) context).getxVel() * (1 - Board.FRICTION_1 * deltaT - Board.FRICTION_2 * Math.abs(((Ball) context).getxVel()) * deltaT));
		((Ball) context).setxVel(((Ball) context).getxVel() * (double) GameWindow.getL() / 500);

		((Ball) context).setyVel((500 / (double) GameWindow.getL()) * ((Ball) context).getyVel());
		((Ball) context).setyVel(((Ball) context).getyVel() * (1 - Board.FRICTION_1 * deltaT - Board.FRICTION_2 * Math.abs(((Ball) context).getyVel()) * deltaT));
		((Ball) context).setyVel(((Ball) context).getyVel() + (double) Board.GRAVITY / (double) 500);
		((Ball) context).setyVel(((Ball) context).getyVel() * (double) GameWindow.getL() / 500);

		Player player1 = Board.getInstance().getPlayers().get(0);
		Player player2 = Board.getInstance().getPlayers().get(1);

		Circle circleOfBall = new Circle(((Ball) context).getX() + ((Ball) context).getDiameter() * GameWindow.getL() / 2, ((Ball) context).getY() + ((Ball) context).getDiameter() * GameWindow.getL() / 2, ((Ball) context).getDiameter() * GameWindow.getL() / 2);
		Vect vectorOfBall = new Vect(((Ball) context).getxVel(), ((Ball) context).getyVel());

		((Ball) context).setX(((Ball) context).getX() + ((Ball) context).getxVel());
		if (((Ball) context).getX() <= 0) {
			((Ball) context).setX(0);
			((Ball) context).setxVel(-((Ball) context).getxVel());
			((Ball) context).setCollisionWall("West");
			if (((Ball) context).getCollisionSide().equalsIgnoreCase("Right")) {
				((Ball) context).setWithInteraction2(true);
			}
		}
		if (((Ball) context).getX() >= Board.BOARD_SIZE * GameWindow.getL() - ((Ball) context).getDiameter() * GameWindow.getL()) {
			((Ball) context).setX(Board.BOARD_SIZE * GameWindow.getL() - ((Ball) context).getDiameter() * GameWindow.getL());
			((Ball) context).setxVel(-((Ball) context).getxVel());
			((Ball) context).setCollisionWall("East");
			if (((Ball) context).getCollisionSide().equalsIgnoreCase("Left")) {
				((Ball) context).setWithInteraction1(true);
			}
		}

		((Ball) context).setY(((Ball) context).getY() + ((Ball) context).getyVel());
		if (((Ball) context).getY() <= 0) {
			if (((Ball) context).getCollisionWall().equalsIgnoreCase("Cezmi") && ((Ball) context).getCollisionType().equalsIgnoreCase("Cezmi")) {
				if (((Ball) context).getLastTouch() == 1) {
					player1.setScore(player1.getScore() - 0.5);
				} else if (((Ball) context).getLastTouch() == 2) {
					player2.setScore(player2.getScore() - 0.5);
				}
			}
			((Ball) context).setY(0);
			((Ball) context).setyVel(-((Ball) context).getyVel());
		}
		if (((Ball) context).getY() >= Board.BOARD_SIZE * GameWindow.getL() - ((Ball) context).getDiameter() * GameWindow.getL()) {
			System.out.println(((Ball)context).getBallNumber());
			if (((Ball) context).getX() < Board.BOARD_SIZE / 2 * GameWindow.getL()) {
				if (streakList.isEmpty()) {
                    streakList.push(true);
                    GameFrame.scorestreak = true;
                } else if (streakList.lastElement() == true) {
                    streakList.push(true);
                    GameFrame.scorestreak = true;
                } else {
                    streakList.removeAllElements();
                    GameFrame.scorestreak = false;
                }
				if (((Ball) context).getCollisionType().equalsIgnoreCase("LeftTokat") || ((Ball) context).getCollisionType().equalsIgnoreCase("RightTokat")) {
					player2.setScore(player2.getScore() + 2);
				} else {
					player2.setScore(player2.getScore() + 1);
				}
				if (!((Ball) context).isWithInteraction1()) {
					player1.setScore(player1.getScore() - 1);
				}
				if(Board.getInstance().getLevel() == 1) {
					((Ball) context).setX(Board.BOARD_SIZE*GameWindow.getL()/4 - ((Ball)context).getDiameter()*GameWindow.getL()/2);
				}
			} 
			else {
				if (streakList.isEmpty()) {
                    streakList.push(false);
                    GameFrame.scorestreak = true;
                } else if (streakList.lastElement() == false) {
                    streakList.push(false);
                    GameFrame.scorestreak = true;
                } else {
                    streakList.removeAllElements();
                    GameFrame.scorestreak = false;
                }
				if (((Ball) context).getCollisionType().equalsIgnoreCase("LeftTokat") || ((Ball) context).getCollisionType().equalsIgnoreCase("RightTokat")) {
					player1.setScore(player1.getScore() + 2);
				} else {
					player1.setScore(player1.getScore() + 1);
				}
				if (!((Ball) context).isWithInteraction2()) {
					player2.setScore(player2.getScore() - 1);
				}
				if(Board.getInstance().getLevel() == 1) {
					((Ball) context).setX(Board.BOARD_SIZE*GameWindow.getL()*3/4 - ((Ball)context).getDiameter()*GameWindow.getL()/2);
				}
			}
			
			if(Board.getInstance().getLevel() == 2) {
				if(((Ball)context).getBallNumber() == 1) {
					((Ball) context).setX(Board.BOARD_SIZE*GameWindow.getL()/4 - ((Ball)context).getDiameter()*GameWindow.getL()/2);
				}
				else if(((Ball)context).getBallNumber() == 2) {
					((Ball) context).setX(Board.BOARD_SIZE*GameWindow.getL()*3/4 - ((Ball)context).getDiameter()*GameWindow.getL()/2);
				}
			}
			((Ball) context).setY(19*GameWindow.getL());

			((Ball) context).setxVel(0);
			((Ball) context).setyVel(0);
			((Ball)context).setGoal(true);
			/* double newXVel = 0;
            double newYVel = 0;
            do {
                newXVel = Math.random();
                newYVel = Math.random();
            } while (newXVel < 0.4 || newYVel < 0.4 || newXVel > 0.6 || newYVel > 0.6);*/
			//will be changed to adapt the new coordinate which is on the top of the players cezmi
			//((Ball) context).setX((GameWindow.getL() * Board.BOARD_SIZE) / 4);
			//((Ball) context).setY((GameWindow.getL() * ((Board.BOARD_SIZE) / 4) - 5)); // 5 is the distance to Player 1.
			// ((Ball) context).setxVel(newXVel);
			//((Ball) context).setyVel(newYVel);
			((Ball) context).setLastTouch(0);
			//will be changed to adapt the new coordinate which is on the top of the players cezmi
			((Ball) context).setCollisionType("");
			((Ball) context).setCollisionSide("");
			((Ball) context).setCollisionWall("");
			((Ball) context).setWithInteraction1(true);
			((Ball) context).setWithInteraction2(true);
			//((Ball) context).setY(Board.BOARD_SIZE*GameWindow.getL() - ((Ball) context).getDiameter()/2);
			//((Ball) context).setyVel(-((Ball) context).getyVel());
		}

		for (Gizmo gizmo : Board.getInstance().getGizmos()) {
			if (gizmo instanceof LeftTokat) {
				Vect rotationCenter = ((LeftTokat) gizmo).getCircleList().get(0).getCenter();
				for (Circle circle : ((LeftTokat) gizmo).getCircleList()) {
					if (Geometry.timeUntilRotatingCircleCollision(circle, rotationCenter, Math.toRadians(LeftTokat.angVel), circleOfBall, vectorOfBall) <= 0) {
						Vect result = Geometry.reflectRotatingCircle(circle, rotationCenter, Math.toRadians(LeftTokat.angVel), circleOfBall, vectorOfBall);
						((Ball) context).setxVel(result.x());
						((Ball) context).setyVel(result.y());
						((Ball) context).setCollisionType("LeftTokat");
						int center = (int) circle.getCenter().x();
						if (center < Board.BOARD_SIZE / 2 * GameWindow.getL()) {
							((Ball) context).setCollisionSide("Left");
							((Ball) context).setWithInteraction1(false);
						} else {
							((Ball) context).setCollisionSide("Right");
							((Ball) context).setWithInteraction2(false);
						}
					}
				}
				for (LineSegment lineSegment : ((LeftTokat) gizmo).getLineSegmentList()) {
					if (Geometry.timeUntilRotatingWallCollision(lineSegment, rotationCenter, Math.toRadians(LeftTokat.angVel), circleOfBall, vectorOfBall) <= 0) {
						Vect result = Geometry.reflectRotatingWall(lineSegment, rotationCenter, Math.toRadians(LeftTokat.angVel), circleOfBall, vectorOfBall);
						((Ball) context).setxVel(result.x());
						((Ball) context).setyVel(result.y());
						((Ball) context).setCollisionType("LeftTokat");
						int middle = (int) (lineSegment.p1().x() + lineSegment.p2().x()) / 2;
						if (middle < Board.BOARD_SIZE / 2 * GameWindow.getL()) {
							((Ball) context).setCollisionSide("Left");
							((Ball) context).setWithInteraction1(false);
						} else {
							((Ball) context).setCollisionSide("Right");
							((Ball) context).setWithInteraction2(false);
						}
					}
				}
			} else if (gizmo instanceof RightTokat) {
				Vect rotationCenter = ((RightTokat) gizmo).getCircleList().get(0).getCenter();
				for (Circle circle : ((RightTokat) gizmo).getCircleList()) {
					if (Geometry.timeUntilRotatingCircleCollision(circle, rotationCenter, Math.toRadians(RightTokat.angVel), circleOfBall, vectorOfBall) <= 0) {
						Vect result = Geometry.reflectRotatingCircle(circle, rotationCenter, Math.toRadians(RightTokat.angVel), circleOfBall, vectorOfBall);
						((Ball) context).setxVel(result.x());
						((Ball) context).setyVel(result.y());
						((Ball) context).setCollisionType("RightTokat");
						int center = (int) circle.getCenter().x();
						if (center < Board.BOARD_SIZE / 2 * GameWindow.getL()) {
							((Ball) context).setCollisionSide("Left");
							((Ball) context).setWithInteraction1(false);
						} else {
							((Ball) context).setCollisionSide("Right");
							((Ball) context).setWithInteraction2(false);
						}
					}
				}
				for (LineSegment lineSegment : ((RightTokat) gizmo).getLineSegmentList()) {
					if (Geometry.timeUntilRotatingWallCollision(lineSegment, rotationCenter, Math.toRadians(RightTokat.angVel), circleOfBall, vectorOfBall) <= 0) {
						Vect result = Geometry.reflectRotatingWall(lineSegment, rotationCenter, Math.toRadians(RightTokat.angVel), circleOfBall, vectorOfBall);
						((Ball) context).setxVel(result.x());
						((Ball) context).setyVel(result.y());
						((Ball) context).setCollisionType("RightTokat");
						int middle = (int) (lineSegment.p1().x() + lineSegment.p2().x()) / 2;
						if (middle < Board.BOARD_SIZE / 2 * GameWindow.getL()) {
							((Ball) context).setCollisionSide("Left");
							((Ball) context).setWithInteraction1(false);
						} else {
							((Ball) context).setCollisionSide("Right");
							((Ball) context).setWithInteraction2(false);
						}
					}
				}
			} else if (gizmo instanceof Firildak) {
				Vect rotationCenter = new Vect(((Firildak) gizmo).getX() + ((Firildak) gizmo).getLengthPixel() / 2, ((Firildak) gizmo).getY() + ((Firildak) gizmo).getLengthPixel() / 2);
				for (Circle circle : ((Firildak) gizmo).getCornerCircleList()) {
					if (Geometry.timeUntilRotatingCircleCollision(circle, rotationCenter, Math.toRadians(((Firildak) gizmo).getAngularVelocity()), circleOfBall, vectorOfBall) <= 0) {
						Vect result = Geometry.reflectRotatingCircle(circle, rotationCenter, Math.toRadians(((Firildak) gizmo).getAngularVelocity()), circleOfBall, vectorOfBall);
						((Ball) context).setxVel(result.x());
						((Ball) context).setyVel(result.y());
						((Ball) context).setCollisionType("Firildak");
						int center = (int) circle.getCenter().x();
						if (center < Board.BOARD_SIZE / 2 * GameWindow.getL()) {
							((Ball) context).setCollisionSide("Left");
							((Ball) context).setWithInteraction1(false);
						} else {
							((Ball) context).setCollisionSide("Right");
							((Ball) context).setWithInteraction2(false);
						}
					}
				}
				for (LineSegment lineSegment : ((Firildak) gizmo).getLineSegmentList()) {
					if (Geometry.timeUntilRotatingWallCollision(lineSegment, rotationCenter, Math.toRadians(((Firildak) gizmo).getAngularVelocity()), circleOfBall, vectorOfBall) <= 0) {
						Vect result = Geometry.reflectRotatingWall(lineSegment, rotationCenter, Math.toRadians(((Firildak) gizmo).getAngularVelocity()), circleOfBall, vectorOfBall);
						((Ball) context).setxVel(result.x());
						((Ball) context).setyVel(result.y());
						((Ball) context).setCollisionType("Firildak");
						int middle = (int) (lineSegment.p1().x() + lineSegment.p2().x()) / 2;
						if (middle < Board.BOARD_SIZE / 2 * GameWindow.getL()) {
							((Ball) context).setCollisionSide("Left");
							((Ball) context).setWithInteraction1(false);
						} else {
							((Ball) context).setCollisionSide("Right");
							((Ball) context).setWithInteraction2(false);
						}
					}
				}
			} else if (gizmo instanceof SquareTakoz) {
				for (Circle circle : ((SquareTakoz) gizmo).getCornerCircleList()) {
					if (Geometry.timeUntilCircleCollision(circle, circleOfBall, vectorOfBall) <= 0) {
						Vect result = Geometry.reflectCircle(circle.getCenter(), circleOfBall.getCenter(), vectorOfBall);
						((Ball) context).setxVel(result.x());
						((Ball) context).setyVel(result.y());
						((Ball) context).setCollisionType("SquareTakoz");
						int center = (int) circle.getCenter().x();
						if (center < Board.BOARD_SIZE / 2 * GameWindow.getL()) {
							((Ball) context).setCollisionSide("Left");
							((Ball) context).setWithInteraction1(false);
						} else {
							((Ball) context).setCollisionSide("Right");
							((Ball) context).setWithInteraction2(false);
						}
					}
				}
				for (LineSegment lineSegment : ((SquareTakoz) gizmo).getLineSegmentList()) {
					if (Geometry.timeUntilWallCollision(lineSegment, circleOfBall, vectorOfBall) <= 0) {
						Vect result = Geometry.reflectWall(lineSegment, vectorOfBall);
						((Ball) context).setxVel(result.x());
						((Ball) context).setyVel(result.y());
						((Ball) context).setCollisionType("SquareTakoz");
						int middle = (int) (lineSegment.p1().x() + lineSegment.p2().x()) / 2;
						if (middle < Board.BOARD_SIZE / 2 * GameWindow.getL()) {
							((Ball) context).setCollisionSide("Left");
							((Ball) context).setWithInteraction1(false);
						} else {
							((Ball) context).setCollisionSide("Right");
							((Ball) context).setWithInteraction2(false);
						}
					}
				}
			} else if (gizmo instanceof TriangleTakoz) {
				for (Circle circle : ((TriangleTakoz) gizmo).getCornerCircleList()) {
					if (Geometry.timeUntilCircleCollision(circle, circleOfBall, vectorOfBall) <= 0) {
						Vect result = Geometry.reflectCircle(circle.getCenter(), circleOfBall.getCenter(), vectorOfBall);
						((Ball) context).setxVel(result.x());
						((Ball) context).setyVel(result.y());
						((Ball) context).setCollisionType("Triangle");
						int center = (int) circle.getCenter().x();
						if (center < Board.BOARD_SIZE / 2 * GameWindow.getL()) {
							((Ball) context).setCollisionSide("Left");
							((Ball) context).setWithInteraction1(false);
						} else {
							((Ball) context).setCollisionSide("Right");
							((Ball) context).setWithInteraction2(false);
						}
					}
				}
				for (LineSegment lineSegment : ((TriangleTakoz) gizmo).getLineSegmentList()) {
					if (Geometry.timeUntilWallCollision(lineSegment, circleOfBall, vectorOfBall) <= 0) {
						Vect result = Geometry.reflectWall(lineSegment, vectorOfBall);
						((Ball) context).setxVel(result.x());
						((Ball) context).setyVel(result.y());
						((Ball) context).setCollisionType("TriangleTakoz");
						int middle = (int) (lineSegment.p1().x() + lineSegment.p2().x()) / 2;
						if (middle < Board.BOARD_SIZE / 2 * GameWindow.getL()) {
							((Ball) context).setCollisionSide("Left");
							((Ball) context).setWithInteraction1(false);
						} else {
							((Ball) context).setCollisionSide("Right");
							((Ball) context).setWithInteraction2(false);
						}
					}
				}
			} else if (gizmo instanceof Cezerye) {
				if (!((Cezerye) gizmo).isCollidesWith()) {
					for (Circle circle : ((Cezerye) gizmo).getCornerCircleList()) {
						if (Geometry.timeUntilCircleCollision(circle, circleOfBall, vectorOfBall) <= 0) {
							Vect result = Geometry.reflectCircle(circle.getCenter(), circleOfBall.getCenter(), vectorOfBall);
							((Ball) context).setxVel(result.x());
							((Ball) context).setyVel(result.y());
							((Ball) context).setCollisionType("Cezerye");
							((Cezerye) gizmo).setCollidesWith(true);
							int select = RD.nextInt(3);
							// for now only 2nd will apply RD.nextInt(2);
							if (select == 0) {
								if (((Ball) context).getLastTouch() == 1) {
									for (Gizmo g : Board.getInstance().getGizmos()) {
										if (!(g instanceof Cezerye)) {
											if (g.getX() < (Board.BOARD_SIZE) / 2 * GameWindow.getL()) {
												g.setLength(2);
												g.setRescale(true);
											}
										}
									}
								} else if (((Ball) context).getLastTouch() == 2) {
									for (Gizmo g : Board.getInstance().getGizmos()) {
										if (!(g instanceof Cezerye)) {
											if (g.getX() > (Board.BOARD_SIZE) / 2 * GameWindow.getL()) {
												g.setLength(2);
												g.setRescale(true);
											}
										}
									}
								}
							} else if (select == 1) {
								if (((Ball) context).getLastTouch() == 1) {
									for (Gizmo g : Board.getInstance().getGizmos()) {
										if (!(g instanceof Cezerye)) {
											if (g.getX() > (Board.BOARD_SIZE) / 2 * GameWindow.getL()) {
												g.setLength(0.5);
												g.setRescale(true);
											}
										}
									}
								} else if (((Ball) context).getLastTouch() == 2) {
									for (Gizmo g : Board.getInstance().getGizmos()) {
										if (!(g instanceof Cezerye)) {
											if (g.getX() < (Board.BOARD_SIZE) / 2 * GameWindow.getL()) {
												g.setLength(0.5);
												g.setRescale(true);
											}
										}
									}
								}
							} else if (select == 2) {
								if (((Ball) context).getLastTouch() == 1) {
									Board.getInstance().getPlayers().get(1).getCezmi().setCanMove(false);
								} else if (((Ball) context).getLastTouch() == 2) {
									Board.getInstance().getPlayers().get(0).getCezmi().setCanMove(false);
								}
							}
						}
					}
				}
				if (!((Cezerye) gizmo).isCollidesWith()) {
					for (LineSegment lineSegment : ((Cezerye) gizmo).getLineSegmentList()) {
						if (Geometry.timeUntilWallCollision(lineSegment, circleOfBall, vectorOfBall) <= 0) {
							Vect result = Geometry.reflectWall(lineSegment, vectorOfBall);
							((Ball) context).setxVel(result.x());
							((Ball) context).setyVel(result.y());
							((Cezerye) gizmo).setCollidesWith(true);
							int select = RD.nextInt(3);
							//RD.nextInt(2);
							if (select == 0) {
								if (((Ball) context).getLastTouch() == 1) {
									for (Gizmo g : Board.getInstance().getGizmos()) {
										if (!(g instanceof Cezerye)) {
											if (g.getX() < (Board.BOARD_SIZE) / 2 * GameWindow.getL()) {
												g.setLength(2);
												g.setRescale(true);
											}
										}
									}
								} else if (((Ball) context).getLastTouch() == 2) {
									for (Gizmo g : Board.getInstance().getGizmos()) {
										if (!(g instanceof Cezerye)) {
											if (g.getX() > (Board.BOARD_SIZE) / 2 * GameWindow.getL()) {
												g.setLength(2);
												g.setRescale(true);
											}
										}
									}
								}
							} else if (select == 1) {
								if (((Ball) context).getLastTouch() == 1) {
									for (Gizmo g : Board.getInstance().getGizmos()) {
										if (!(g instanceof Cezerye)) {
											if (g.getX() > (Board.BOARD_SIZE) / 2 * GameWindow.getL()) {
												g.setLength(0.5);
												g.setRescale(true);
											}
										}
									}
								} else if (((Ball) context).getLastTouch() == 2) {
									for (Gizmo g : Board.getInstance().getGizmos()) {
										if (!(g instanceof Cezerye)) {
											if (g.getX() < (Board.BOARD_SIZE) / 2 * GameWindow.getL()) {
												g.setLength(0.5);
												g.setRescale(true);
											}
										}
									}
								}
							} else if (select == 2) {
								if (((Ball) context).getLastTouch() == 1) {
									Board.getInstance().getPlayers().get(1).getCezmi().setCanMove(false);
								} else if (((Ball) context).getLastTouch() == 2) {
									Board.getInstance().getPlayers().get(0).getCezmi().setCanMove(false);
								}
							}
						}
					}
				}
			}
		}

		for (Circle circle : Engel.getInstance().getCornerCircleList()) {
			if (Geometry.timeUntilCircleCollision(circle, circleOfBall, vectorOfBall) <= 0) {
				Vect result = Geometry.reflectCircle(circle.getCenter(), circleOfBall.getCenter(), vectorOfBall);
				((Ball) context).setxVel(result.x());
				((Ball) context).setyVel(result.y());
				String type = ((Ball) context).getCollisionType();
				if (((Ball) context).getCollisionSide().equalsIgnoreCase("Left")) {
					if (type.equalsIgnoreCase("SquareTakoz")
							|| type.equalsIgnoreCase("TriangleTakoz")
							|| type.equalsIgnoreCase("Firildak")
							|| type.equalsIgnoreCase("RightTokat")
							|| type.equalsIgnoreCase("LeftTokat")) {
						player1.setScore(player1.getScore() - 0.5);
					}
				} else if (((Ball) context).getCollisionSide().equalsIgnoreCase("Right")) {
					if (type.equalsIgnoreCase("SquareTakoz")
							|| type.equalsIgnoreCase("TriangleTakoz")
							|| type.equalsIgnoreCase("Firildak")
							|| type.equalsIgnoreCase("RightTokat")
							|| type.equalsIgnoreCase("LeftTokat")) {
						player2.setScore(player2.getScore() - 0.5);
					}
				}
				((Ball) context).setCollisionType("Engel");
			}
		}

		for (LineSegment lineSegment : Engel.getInstance().getLineSegmentList()) {
			if (Geometry.timeUntilWallCollision(lineSegment, circleOfBall, vectorOfBall) <= 0) {
				Vect result = Geometry.reflectWall(lineSegment, vectorOfBall);
				((Ball) context).setxVel(result.x());
				((Ball) context).setyVel(result.y());
				String type = ((Ball) context).getCollisionType();
				if (((Ball) context).getCollisionSide().equalsIgnoreCase("Left")) {
					if (type.equalsIgnoreCase("SquareTakoz")
							|| type.equalsIgnoreCase("TriangleTakoz")
							|| type.equalsIgnoreCase("Firildak")
							|| type.equalsIgnoreCase("RightTokat")
							|| type.equalsIgnoreCase("LeftTokat")) {
						player1.setScore(player1.getScore() - 0.5);
					}
				} else if (((Ball) context).getCollisionSide().equalsIgnoreCase("Right")) {
					if (type.equalsIgnoreCase("SquareTakoz")
							|| type.equalsIgnoreCase("TriangleTakoz")
							|| type.equalsIgnoreCase("Firildak")
							|| type.equalsIgnoreCase("RightTokat")
							|| type.equalsIgnoreCase("LeftTokat")) {
						player2.setScore(player2.getScore() - 0.5);
					}
				}
				((Ball) context).setCollisionType("Engel");
			}
		}

		for (Player player : Board.getInstance().getPlayers()) {
			Circle cezmiCircle = new Circle(player.getCezmi().getX() + GameWindow.getL(), player.getCezmi().getY() + GameWindow.getL(), GameWindow.getL());
			if (Geometry.timeUntilCircleCollision(cezmiCircle, circleOfBall, vectorOfBall) <= 0) {
				Vect result = Geometry.reflectCircle(cezmiCircle.getCenter(), circleOfBall.getCenter(), vectorOfBall);
				((Ball) context).setxVel(result.x() *2);
				((Ball) context).setyVel(result.y() *2);
				((Ball) context).setLastTouch(player.getNum());
				((Ball) context).setCollisionType("Cezmi");
				((Ball) context).setCollisionWall("Cezmi");
			}
		}

		//can't figure it out how to implement collision of two balls. When one ball makes reflection the other ball also makes reflection
		//which is unnecessary in that case using reflectBalls methods.
		/*
        for (Ball otherBall : Board.getInstance().getBalls()) {
            if (((Ball) context).getBallNumber() == 1 && otherBall.getBallNumber() != 1) {
                Circle ballCircle = new Circle(otherBall.getX() + otherBall.getDiameter() * GameWindow.getL() / 4, otherBall.getY() + otherBall.getDiameter() * GameWindow.getL() / 4, otherBall.getDiameter() * GameWindow.getL() / 4);
                Vect vecterOfOtherBall = new Vect(otherBall.getxVel(), otherBall.getyVel());
                if (Geometry.timeUntilBallBallCollision(circleOfBall, vectorOfBall, ballCircle, vecterOfOtherBall) <= 0) {
                    System.out.println("2 Ball Collision !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    VectPair result = Geometry.reflectBalls(circleOfBall.getCenter(), 1, vectorOfBall, ballCircle.getCenter(), 1, vecterOfOtherBall);
                    ((Ball) context).setxVel(result.v1.x());
                    ((Ball) context).setxVel(result.v1.y());
                    otherBall.setxVel(result.v2.x());
                    otherBall.setyVel(result.v2.y());

                }
            }
        }
		 */
		if(Board.getInstance().getLevel() == 2) {
			Ball num1 = Board.getInstance().getBalls().get(0);
			Circle num1circle = new Circle(num1.getX()+num1.getDiameter()*GameWindow.getL()/4,num1.getY()+num1.getDiameter()*GameWindow.getL()/4,num1.getDiameter()*GameWindow.getL()/4);
			Vect num1vec = new Vect(num1.getxVel(),num1.getyVel());
			Ball num2 = Board.getInstance().getBalls().get(1);
			Circle num2circle = new Circle(num2.getX()+num2.getDiameter()*GameWindow.getL()/4,num2.getY()+num2.getDiameter()*GameWindow.getL()/4,num2.getDiameter()*GameWindow.getL()/4);
			Vect num2vec = new Vect(num2.getxVel(),num2.getyVel());
			if(Geometry.timeUntilBallBallCollision(num1circle, num1vec, num2circle, num2vec) <= 0){
				VectPair resultVect = Geometry.reflectBalls(num1circle.getCenter(), 1, num1vec, num2circle.getCenter(), 1, num2vec);
				num1.setxVel(resultVect.v1.x());
				num1.setyVel(resultVect.v1.y());
				num2.setxVel(resultVect.v2.x());
				num2.setyVel(resultVect.v2.y());
				num1.setGoal(false);
				num2.setGoal(false);
			}
		}

		double totalScore = player1.getScore() + player2.getScore();
		if (0 <= totalScore && totalScore < 2) {
			((Ball) context).setDiameter(1.0);
		} else if (2 <= totalScore && totalScore < 4) {
			((Ball) context).setDiameter(0.9);
		} else if (4 <= totalScore && totalScore < 6) {
			((Ball) context).setDiameter(0.8);
		} else if (6 <= totalScore && totalScore < 8) {
			((Ball) context).setDiameter(0.7);
		} else if (8 <= totalScore && totalScore < 10) {
			((Ball) context).setDiameter(0.6);
		} else if (10 <= totalScore && totalScore < 12) {
			((Ball) context).setDiameter(0.5);
		} else if (12 <= totalScore && totalScore < 14) {
			((Ball) context).setDiameter(0.4);
		} else if (14 <= totalScore && totalScore < 16) {
			((Ball) context).setDiameter(0.3);
		} else if (16 <= totalScore && totalScore < 18) {
			((Ball) context).setDiameter(0.2);
		}

		/*if(((Ball) context).getxVel() > 1) {
        	((Ball) context).setxVel(1);
        }
        else if(((Ball) context).getxVel() < -1) {
        	((Ball) context).setxVel(-1);
        }
        if(((Ball) context).getyVel() > 1) {
        	((Ball) context).setyVel(1);
        }
        else if(((Ball) context).getyVel() < -1) {
        	((Ball) context).setyVel(-1);
        }*/

	}
}
