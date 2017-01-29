package uk.endercraft.enderhub.tasks;

import org.bukkit.scheduler.BukkitRunnable;

import uk.endercraft.enderhub.config.ServersConfig;
import uk.endercraft.enderhub.config.ServersConfig.Server;

public class PingTask extends BukkitRunnable {

	@Override
	public void run() {
		for (Server s : ServersConfig.get().getAllServers())
			s.ping();
	}

}
