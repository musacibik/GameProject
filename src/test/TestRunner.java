package test;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import test.domain.gizmos.CezeryeTest;
import test.domain.gizmos.FirildakTest;
import test.domain.gizmos.GizmoFactoryTest;
import test.domain.gizmos.LeftTokatTest;
import test.domain.gizmos.RightTokatTest;
import test.domain.gizmos.SquareTakozTest;
import test.domain.gizmos.TriangleTakozTest;

public class TestRunner {
	
	public static void main(String[] args){
		Result result = JUnitCore.runClasses(GizmoFactoryTest.class, CezeryeTest.class, FirildakTest.class,
				LeftTokatTest.class, RightTokatTest.class, SquareTakozTest.class, TriangleTakozTest.class);
		
		for(Failure failure : result.getFailures()) {
			System.out.println(failure.toString());
		}
		
		System.out.println("Test cases for all classes: " + result.wasSuccessful());
	}
}
