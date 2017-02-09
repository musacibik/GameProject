/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.domain.gizmos;

import main.domain.strategy.BlinkStrategy;
import main.domain.strategy.NoMovementStrategy;
import main.domain.strategy.RotateStrategy;
import main.domain.strategy.SlapStrategy;


/**
 * OVERVIEW: This singleton class generates a new gizmo object when its getGizmo method is called
 *  @since 0.1
 *  @version 0.1
 *  @author JusticeLeague
 */
public class GizmoFactory {

	private static GizmoFactory instance;
	private Gizmo gizmo;

	/**
	 * 
	 * @effects returns an instance of this class in a way that, if an instance is created before, a new instance
	 * does not created again. Previously created instance is used
	 */
	public static synchronized GizmoFactory getInstance() {
		if (instance == null) {
			instance = new GizmoFactory();
		}

		return instance;
	}

	/**
	 *  Constructor for the GizmoFactory
	 */
	private GizmoFactory() {
		gizmo = null;
	}
	
	/**
	 * @effects creates and returns a gizmo object according to the (String type) parameter
	 */
	public Gizmo getGizmo(String type) {
		if (type.equalsIgnoreCase("squareTakoz")) {
			gizmo = new SquareTakoz();
			((SquareTakoz) gizmo).setMovementStrategy(NoMovementStrategy.getInstance());
	    } else if (type.equalsIgnoreCase("triangleTakoz")) {
			gizmo = new TriangleTakoz();
			((TriangleTakoz) gizmo).setMovementStrategy(NoMovementStrategy.getInstance());
	    } else if (type.equalsIgnoreCase("firildak")) {
			gizmo = new Firildak();
			((Firildak) gizmo).setMovementStrategy(RotateStrategy.getInstance());
		} else if (type.equalsIgnoreCase("rightTokat")) {
			gizmo = new RightTokat();
			((RightTokat) gizmo).setMovementStrategy(SlapStrategy.getInstance());
		} else if (type.equalsIgnoreCase("leftTokat")) {
			gizmo = new LeftTokat();
			((LeftTokat) gizmo).setMovementStrategy(SlapStrategy.getInstance());
		} else if (type.equalsIgnoreCase("cezerye")) {
			gizmo = new Cezerye();
			((Cezerye) gizmo).setMovementStrategy(BlinkStrategy.getInstance());
		}

		return gizmo;
	}
}
