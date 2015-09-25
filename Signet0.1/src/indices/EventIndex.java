package indices;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import misc.GameEvent;


public class EventIndex {

	HashMap<String, Class> eventIndex;
	
	public EventIndex(){
		eventIndex = new HashMap<String, Class>();
	}
	
	public GameEvent get(String[] args){
		String key = args[0];
		Class cls = eventIndex.get(key);
		
		try{
			GameEvent event = (GameEvent) cls.getConstructor().newInstance(args);
			return event;
		} catch (ClassCastException e) {	
			e.printStackTrace();
		} catch (NoSuchMethodException e){
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
