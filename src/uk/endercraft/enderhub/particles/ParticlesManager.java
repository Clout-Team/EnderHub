package uk.endercraft.enderhub.particles;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import uk.endercraft.enderhub.EnderHub;
import uk.endercraft.enderhub.particles.particles.DevParticle;
import uk.endercraft.enderhub.particles.particles.DragonBreathParticle;
import uk.endercraft.enderhub.particles.particles.ExplodeParticle;
import uk.endercraft.enderhub.particles.particles.FireworkSparksParticle;
import uk.endercraft.enderhub.particles.particles.HeartParticle;
import uk.endercraft.enderhub.particles.particles.NoteParticle;
import uk.endercraft.enderhub.particles.particles.RainbowParticle;
import uk.endercraft.enderhub.particles.particles.VillagerParticle;
import uk.endercraft.enderhub.particles.particles.WaterParticle;

public class ParticlesManager {

	private static ParticlesManager instance;

	private List<Particle> particles = Lists.newArrayList();

	private Map<Player, Particle> playerParticles = Maps.newHashMap();

	private ParticlesManager() {
		a(new WaterParticle());
		a(new HeartParticle());
		a(new NoteParticle());
		a(new VillagerParticle());
		a(new RainbowParticle());
		a(new FireworkSparksParticle());
		a(new ExplodeParticle());
		a(new DevParticle());
		a(new DragonBreathParticle());
		Bukkit.getScheduler().runTaskTimer(EnderHub.get(), new Runnable() {
			@Override
			public void run() {
				for (Entry<Player, Particle> entry : playerParticles.entrySet())
					entry.getValue().spawnParticles(entry.getKey());
			}
		}, 0L, 20L);
	}

	private void a(Particle kit) {
		particles.add(kit);
	}

	public List<Particle> getAll() {
		return particles;
	}

	public Particle getById(int id) {
		for (Particle kit : particles)
			if (kit.getID() == id)
				return kit;
		return null;
	}

	public void setParticle(Player p, Particle particle) {
		playerParticles.put(p, particle);
	}

	public Particle getParticle(Player p) {
		return playerParticles.get(p);
	}

	public void removeParticle(Player p) {
		playerParticles.remove(p);
	}

	public static ParticlesManager get() {
		if (instance == null)
			instance = new ParticlesManager();
		return instance;
	}

}
