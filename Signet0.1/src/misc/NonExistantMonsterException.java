package misc;

public class NonExistantMonsterException extends Exception {
	
	public NonExistantMonsterException(String monsterName, String callLocation) {
		super(buildMessage(monsterName, callLocation));
	}
	
	private static String buildMessage(String monsterName, String callLocation){
		if (monsterName == null){
			return "monster name was null:\n     called from " + callLocation;
		}
		StringBuilder str = new StringBuilder();
		str.append("monster \"");
		str.append("\" cannot be found in the game. Please make sure you spelled the name corrrectly:\n    Called from ");
		str.append(callLocation);
		return str.toString();
	}
}
