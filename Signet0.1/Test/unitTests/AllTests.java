package unitTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import unitTests.body.UnitTestBody;
import unitTests.body.UnitTestBodyPart;
import unitTests.body.UnitTestWound;
import unitTests.body.UnitTestWoundMedication;
import unitTests.dialogue.DialogueDisplayNodeSaveLoad;
import unitTests.dialogue.DialogueEventNodeSaveLoad;
import unitTests.dialogue.DialogueNodeDeepEquals;
import unitTests.dialogue.DialogueNodeSaveLoadIntegrationTest;
import unitTests.dialogue.DialoguePointerNodeSaveLoad;
import unitTests.dialogue.DialogueSelectionNodeSaveLoad;
import unitTests.inventory.UnitTestFirstAid;
import unitTests.inventory.UnitTestGear;
import unitTests.inventory.UnitTestInventory;
import unitTests.inventory.UnitTestItem;
import unitTests.inventory.UnitTestItemContainer;
import unitTests.inventory.UnitTestWeapon;
import unitTests.location.UnitTestLocation;
import unitTests.location.UnitTestTravelPath;

public class AllTests {
	public static void main(String[] args){
		Result result = JUnitCore.runClasses(AllTestSuite.class);
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
	    UnitTestLocation.class,
	    UnitTestSaveAndLoad.class,
	    UnitTestTravelPath.class,
	    UnitTestWeapon.class,
	    UnitTestWound.class,
	    UnitTestWoundMedication.class,
	    
		DialogueDisplayNodeSaveLoad.class,
		DialogueEventNodeSaveLoad.class,
		DialogueSelectionNodeSaveLoad.class,
		DialoguePointerNodeSaveLoad.class,
		DialogueNodeSaveLoadIntegrationTest.class,
		DialogueNodeDeepEquals.class
	})
class AllTestSuite {
}