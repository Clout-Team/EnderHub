package uk.endercraft.enderhub.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class Weatherman implements Listener {
	
	@EventHandler
	public void onClimateChange(WeatherChangeEvent event){
		event.setCancelled(true);
	}

}
