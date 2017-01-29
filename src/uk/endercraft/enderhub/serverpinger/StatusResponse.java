package uk.endercraft.enderhub.serverpinger;

import java.util.List;

public class StatusResponse {
	public Motd description;
	public Players players;
	public Version version;
	public String favicon;
	public int time;

	public StatusResponse() {
		this.description = new Motd();
		this.players = new Players();
		this.version = new Version();
	}

	public class Version {
		public String name;
		public String protocol;
	}

	public class Motd {
		public String text;
	}

	public class StatusPlayer {
		public String name;
		public String id;

		public String toString() {
			return "{Player:[" + this.name + "," + this.id + "]}";
		}
	}

	public class Players {
		public int max;
		public int online;
		public List<StatusResponse.StatusPlayer> sample;
	}
}
