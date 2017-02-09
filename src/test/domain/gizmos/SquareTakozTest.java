package test.domain.gizmos;

import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;
import main.domain.gizmos.GizmoFactory;
import main.domain.gizmos.SquareTakoz;
import main.domain.strategy.NoMovementStrategy;

public class SquareTakozTest extends TestCase {
	
	private SquareTakoz squareTakoz;
	
	@Before
	public void setUp() throws Exception {
		super.setUp();	
		squareTakoz = (SquareTakoz) GizmoFactory.getInstance().getGizmo("squareTakoz");
	}
	
	@Test
	public void testSquareTakozConstructor() {
		assertEquals(squareTakoz.getMovementStrategy(), NoMovementStrategy.getInstance());
	}
	
	@Test 
	public void testSetY(){
		squareTakoz.setY(50); // 50 is just a random value.
		assertEquals(squareTakoz.getLineSegmentList().size(), 4);
	}
}
