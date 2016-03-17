package unitTests.dialogue;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

import testingMothers.DialogueMother;
import dialogue.Dialogue;
import dialogue.DialogueNode;

public class DialogueNodeDeepEquals {

	DialogueNode scenes, chat, under;

	@Before
	public void setup() throws Exception {
		Field f = Dialogue.class.getDeclaredField("start");
		f.setAccessible(true);
		scenes = (DialogueNode) f.get(DialogueMother.seriesOfScenes());
		chat = (DialogueNode) f.get(DialogueMother.simpleChat());
		under = (DialogueNode) f.get(DialogueMother.undertail());
	}
	
	
	@Test
	public void sceneToChat() {
		assertFalse(scenes.deepEquals(chat, new HashSet<Integer>()));
	}
	
	@Test
	public void sceneToUnder() {
		assertFalse(scenes.deepEquals(under, new HashSet<Integer>()));
	}
	
	@Test
	public void chatToUnder() {
		assertFalse(chat.deepEquals(under, new HashSet<Integer>()));
	}
	
	@Test
	public void sceneToScene() throws Exception {
		Dialogue d = DialogueMother.seriesOfScenes();
		Field f = Dialogue.class.getDeclaredField("start");
		f.setAccessible(true);
		DialogueNode eqScene = (DialogueNode) f.get(d);
		assertTrue(eqScene.deepEquals(scenes, new HashSet<Integer>()));
	}
	
	@Test
	public void chatToChat() throws Exception {
		Dialogue d = DialogueMother.simpleChat();
		Field f = Dialogue.class.getDeclaredField("start");
		f.setAccessible(true);
		DialogueNode eqScene = (DialogueNode) f.get(d);
		assertTrue(eqScene.deepEquals(chat, new HashSet<Integer>()));
	}
	
	@Test
	public void underToUnder() throws Exception {
		Dialogue d = DialogueMother.undertail();
		Field f = Dialogue.class.getDeclaredField("start");
		f.setAccessible(true);
		DialogueNode eqScene = (DialogueNode) f.get(d);
		assertTrue(eqScene.deepEquals(under, new HashSet<Integer>()));
	}
	
	@Test
	public void sceneToSelf() {
		assertTrue(scenes.deepEquals(scenes, new HashSet<Integer>()));
	}
	
	@Test
	public void chatToSelf() {
		assertTrue(chat.deepEquals(chat, new HashSet<Integer>()));
	}
	
	@Test
	public void underToSelf() {
		assertTrue(under.deepEquals(under, new HashSet<Integer>()));
	}
	

}
