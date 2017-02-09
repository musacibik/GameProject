package test.domain.gizmos;

import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;
import main.domain.Orientation;
import main.domain.gizmos.GizmoFactory;
import main.domain.gizmos.LeftTokat;
import main.domain.strategy.SlapStrategy;

public class LeftTokatTest extends TestCase {

	private LeftTokat leftTokat;
	
	@Before
	public void setUp() throws Exception {
		super.setUp();	
		leftTokat = (LeftTokat) GizmoFactory.getInstance().getGizmo("leftTokat");
	}
	
	@Test
	public void testLeftTokatConstructor() {
		assertEquals(leftTokat.getOrientation(), Orientation.ORIENTATION_ZERO);
		assertEquals(leftTokat.getMovementStrategy(), SlapStrategy.getInstance());
	}
	
	@Test 
	public void testSetY(){
		leftTokat.setY(50); // 50 is just a random value.
		assertEquals(leftTokat.getLineSegmentList().size(), 4);
		assertEquals(leftTokat.getCircleList().size(), 2);
	}
	
}
