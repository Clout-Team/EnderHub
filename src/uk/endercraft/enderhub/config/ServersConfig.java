package uk.endercraft.enderhub.config;

import java.util.List;

import org.bukkit.Bukkit;

import com.google.common.collect.Lists;

import uk.endercraft.endercore.config.ConfigFile;
import uk.endercraft.enderhub.EnderHub;
import uk.endercraft.enderhub.serverpinger.StatusResponse;
import uk.endercraft.enderhub.serverpinger.ServerPinger;

public class ServersConfig extends ConfigFile {

	/**
	 * @author Rexcantor64
	 * @category Create the unique ServersConfig instance.
	 */
	private ServersConfig() {
		super("servers");
		for (String s : toBukkit().getKeys(false)) {
			servers.add(new Server(toBukkit().getString(s + ".minigame"), s, toBukkit().getString(s + ".host"),
					toBukkit().getInt(s + ".port")));
		}
	}

	private List<Server> servers = Lists.newArrayList();
	private static ServersConfig instance;

	public List<Server> getAllServers() {
		return servers;
	}

	public List<Server> getAllServer(String minigame) {
		List<Server> svrs = Lists.newArrayList();
		for (Server svr : servers)
			if (svr.getMinigame().equals(minigame))
				svrs.add(svr);
		return svrs;
	}

	public class Server {

		private final String minigame;
		private final String bungeeName;
		private final String host;
		private final int port;
		private final ServerStatus status = new ServerStatus();

		private Server(String minigame, String bungeeName, String host, int port) {
			this.minigame = minigame;
			this.bungeeName = bungeeName;
			this.host = host;
			this.port = port;
			status.setMapName("Unknown... Notify an Admin");
			status.setMax(0);
			status.setOnline(0);
			status.setStatus(ServerStatusType.RESTARTING);
		}

		public String getMinigame() {
			return minigame;
		}

		public String getBungeeName() {
			return bungeeName;
		}

		public String getHost() {
			return host;
		}

		public int getPort() {
			return port;
		}

		public ServerStatus getStatus() {
			return status;
		}

		public void ping() {
			Bukkit.getScheduler().runTaskAsynchronously(EnderHub.get(), new Runnable() {
				@Override
				public void run() {
					StatusResponse response = ServerPinger.get().fetchData(host, port);
					if (response == null) {
						status.setStatus(ServerStatusType.RESTARTING);
						return;
					}
					try {
						status.setMax(response.players.max);
						status.setOnline(response.players.online);
						String motd = response.description.text;
						String[] motd2 = motd.split(";");
						status.setStatus(ServerStatusType.values()[Integer.parseInt(motd2[0])]);
						status.setMapName(motd2[1]);
					} catch (Exception e) {
						EnderHub.get().getLogger().severe("Can't ping server " + bungeeName + ": " + e.getMessage());
					}
				}
			});
		}

	}

	public enum ServerStatusType {
		WAITING, PLAYING, RESTARTING;
	}

	public class ServerStatus {

		private ServerStatusType status;
		private int online;
		private int max;
		private String mapName;

		public ServerStatusType getStatus() {
			return status;
		}

		public void setStatus(ServerStatusType status) {
			this.status = status;
		}

		public int getOnline() {
			return online;
		}

		public void setOnline(int online) {
			this.online = online;
		}

		public int getMax() {
			return max;
		}

		public void setMax(int max) {
			this.max = max;
		}

		public String getMapName() {
			return mapName;
		}

		public void setMapName(String mapName) {
			this.mapName = mapName;
		}

	}

	public static ServersConfig get() {
		if (instance == null)
			instance = new ServersConfig();
		return instance;
	}

}
