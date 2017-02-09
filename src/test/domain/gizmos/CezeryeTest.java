package test.domain.gizmos;

import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;
import main.domain.gizmos.Cezerye;
import main.domain.gizmos.GizmoFactory;
import main.domain.strategy.BlinkStrategy;

public class CezeryeTest extends TestCase {
	
	private Cezerye cezerye;
	
	@Before
	public void setUp() throws Exception {
		super.setUp();	
		cezerye = (Cezerye) GizmoFactory.getInstance().getGizmo("cezerye");
	}
	
	@Test
	public void testCezeryeConstructor() {
		assertEquals(cezerye.getAppearTime(), 5.0);  // Specified value is 5 on Blackboard.
		assertEquals(cezerye.getMovementStrategy(), BlinkStrategy.getInstance());
	}
	
	@Test 
	public void testSetY() {
		cezerye.setY(50); // 50 is just a random value.
		assertEquals(cezerye.getLineSegmentList().size(), 4);
	}
}
