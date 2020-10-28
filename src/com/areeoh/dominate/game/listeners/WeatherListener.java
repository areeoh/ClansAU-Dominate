package com.areeoh.dominate.game.listeners;

import com.areeoh.core.framework.Module;
import com.areeoh.dominate.game.GameManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WeatherListener extends Module<GameManager> implements Listener {

    public WeatherListener(GameManager manager) {
        super(manager, "WeatherListener");
    }

    @EventHandler
    public void onWeatherChangeEvent(WeatherChangeEvent event) {
        if (!event.getWorld().hasStorm()) {
            event.setCancelled(true);
        }
    }
}