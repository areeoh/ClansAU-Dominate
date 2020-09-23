package com.areeoh.dominate.listeners;

import com.areeoh.dominate.DominateManager;
import com.areeoh.framework.Module;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WeatherHandler extends Module<DominateManager> implements Listener {

    public WeatherHandler(DominateManager manager) {
        super(manager, "WeatherHandler");
    }

    @EventHandler
    public void onWeatherChangeEvent(WeatherChangeEvent event) {
        if (!event.getWorld().hasStorm()) {
            event.setCancelled(true);
        }
    }
}