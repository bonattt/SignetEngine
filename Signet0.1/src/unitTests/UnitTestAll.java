package unitTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class UnitTestAll {
	public static void main(String[] args){
		Result result = JUnitCore.runClasses(TestSuite.class);
		for (Failure failure : result.getFailures()) {
			System.out.println(failure.toString());
		}
		System.out.println(result.wasSuccessful());
	}
}
@RunWith(Suite.class)
	@Suite.SuiteClasses({
		IntegrationTestSaveLoad.class,
	    UnitTestBody.class,
	    UnitTestBodyPart.class,
	    UnitTestCreature.class,
	    UnitTestFirstAid.class,
	    UnitTestGear.class,
	    UnitTestInventory.class,
	    UnitTestItem.class,
	    UnitTestItemContainer.class,
	    UnitTestSaveAndLoad.class,
	    UnitTestWound.class,
	    UnitTestWoundMedication.class
	})
class TestSuite {
}