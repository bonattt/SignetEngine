package unitTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class AllDialogue {
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
		DialogueDisplayNodeSaveLoad.class,
		DialogueEventNodeSaveLoad.class,
		DialogueSelectionNodeSaveLoad.class,
		DialoguePointerNodeSaveLoad.class,
		DialogueNodeSaveLoadIntegrationTest.class
	})
class DialogueTestSuite {
	
}