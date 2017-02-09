package test.domain.gizmos;

import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;
import main.domain.Orientation;
import main.domain.gizmos.GizmoFactory;
import main.domain.gizmos.RightTokat;
import main.domain.strategy.SlapStrategy;

public class RightTokatTest extends TestCase {

	private RightTokat rightTokat;
	
	@Before
	public void setUp() throws Exception {
		super.setUp();	
		rightTokat = (RightTokat) GizmoFactory.getInstance().getGizmo("rightTokat");
	}
	
	@Test
	public void testRightTokatConstructor() {
		assertEquals(rightTokat.getOrientation(), Orientation.ORIENTATION_ZERO);
		assertEquals(rightTokat.getMovementStrategy(), SlapStrategy.getInstance());
	}
	
	@Test 
	public void testSetY(){
		rightTokat.setY(50); // 50 is just a random value.
		assertEquals(rightTokat.getLineSegmentList().size(), 4);
		assertEquals(rightTokat.getCircleList().size(), 2);
	}
}
