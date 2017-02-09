package test.domain.gizmos;

import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;
import main.domain.gizmos.Cezerye;
import main.domain.gizmos.Firildak;
import main.domain.gizmos.GizmoFactory;
import main.domain.gizmos.LeftTokat;
import main.domain.gizmos.RightTokat;
import main.domain.gizmos.SquareTakoz;
import main.domain.gizmos.TriangleTakoz;

public class GizmoFactoryTest extends TestCase {
		
	@Before
	public void setUp() throws Exception {
		super.setUp();	
	}
	
	@Test
	public void testGetInstance(){
		GizmoFactory gizmoFactory = GizmoFactory.getInstance();
		
		assertNotNull(gizmoFactory);
		assertSame(gizmoFactory, GizmoFactory.getInstance());
	}
	
	@Test
	public void testSquareTakozCreate(){
		assertTrue(GizmoFactory.getInstance().getGizmo("squareTakoz") instanceof SquareTakoz);
	}
	
	@Test
	public void testTriangleTakozCreate(){
		assertTrue(GizmoFactory.getInstance().getGizmo("triangleTakoz") instanceof TriangleTakoz);
	}
	
	@Test
	public void testLeftTokatCreate(){
		assertTrue(GizmoFactory.getInstance().getGizmo("leftTokat") instanceof LeftTokat);
	}
	
	@Test
	public void testRightTokatCreate(){
		assertTrue(GizmoFactory.getInstance().getGizmo("rightTokat") instanceof RightTokat);
	}
	
	@Test
	public void testCezeryeCreate(){
		assertTrue(GizmoFactory.getInstance().getGizmo("cezerye") instanceof Cezerye);
	}
	
	@Test
	public void testFirildakCreate(){
		assertTrue(GizmoFactory.getInstance().getGizmo("firildak") instanceof Firildak);
	}
	
}
