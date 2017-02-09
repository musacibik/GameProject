package test.domain.gizmos;

import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;
import main.domain.gizmos.Firildak;
import main.domain.gizmos.GizmoFactory;
import main.domain.strategy.RotateStrategy;

public class FirildakTest extends TestCase {
	
	private Firildak firildak;
	
	@Before
	public void setUp() throws Exception {
		super.setUp();	
		firildak = (Firildak) GizmoFactory.getInstance().getGizmo("firildak");
	}
	
	@Test
	public void testFirildakConstructor() {
		assertEquals(firildak.getMovementStrategy(), RotateStrategy.getInstance());
	}
	
	@Test
	public void testSetY(){
		firildak.setY(50); // 50 is just a random value.
		assertEquals(firildak.getLineSegmentList().size(), 4);
	}
	
}
