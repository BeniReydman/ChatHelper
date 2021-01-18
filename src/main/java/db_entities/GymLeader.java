package db_entities;

public class GymLeader {
	private String id;
	private String playerID;
	private String gymID;
	
	public GymLeader(String id, String playerID, String gymID) {
		this.id = id;
		this.playerID = playerID;
		this.gymID = gymID;
	}

	public String getId() {
		return id;
	}

	public String getplayerID() {
		return playerID;
	}


	public String getGymID() {
		return gymID;
	}

}
