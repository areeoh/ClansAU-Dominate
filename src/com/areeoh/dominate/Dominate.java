package com.areeoh.dominate;

import com.areeoh.core.ClansAUCore;
import com.areeoh.core.framework.Manager;
import com.areeoh.core.framework.Plugin;
import com.areeoh.core.scoreboard.ScoreboardManager;
import com.areeoh.dominate.game.GameManager;
import com.areeoh.dominate.game.commands.QuitCommand;
import com.areeoh.dominate.game.dominate.DominateScoreboard;
import com.areeoh.dominate.game.commands.dominate.GameCommandManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import java.util.Set;

public class Dominate extends Plugin {

    private ClansAUCore plugin;

    public void onEnable() {
        this.plugin = (ClansAUCore) Bukkit.getPluginManager().getPlugin("ClansAU-Core");

        final DominateScoreboard dominateScoreboard = new DominateScoreboard(plugin.getManager(ScoreboardManager.class));
        dominateScoreboard.initialize(this);
        plugin.getManager(ScoreboardManager.class).addModule(dominateScoreboard);

        Bukkit.getWorlds().forEach(world -> {
            for (Entity entity : world.getEntities()) {
                if(entity instanceof LivingEntity) {
                    entity.remove();
                }
            }
        });

        registerManagers();
    }

    private void registerManagers() {
        addManager(new GameManager(this));

        addManager(new GameCommandManager(this));
        addManager(new QuitCommand(this));
    }

    @Override
    public Set<Manager> getManagers() {
        return plugin.getManagers();
    }
}