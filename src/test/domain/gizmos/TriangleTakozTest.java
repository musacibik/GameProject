package test.domain.gizmos;

import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;
import main.domain.Orientation;
import main.domain.gizmos.GizmoFactory;
import main.domain.gizmos.TriangleTakoz;
import main.domain.strategy.NoMovementStrategy;

public class TriangleTakozTest extends TestCase {

	private TriangleTakoz triangleTakoz;
	
	@Before
	public void setUp() throws Exception {
		super.setUp();	
		triangleTakoz = (TriangleTakoz) GizmoFactory.getInstance().getGizmo("triangleTakoz");
	}
	
	@Test
	public void testTriangleTakozConstructor() {
		assertEquals(triangleTakoz.getOrientation(), Orientation.ORIENTATION_ZERO);
		assertEquals(triangleTakoz.getMovementStrategy(), NoMovementStrategy.getInstance());
	}
	
	@Test 
	public void testSetY(){
		triangleTakoz.setY(50); // 50 is just a random value.
		assertEquals(triangleTakoz.getLineSegmentList().size(), 3);
	}
}
