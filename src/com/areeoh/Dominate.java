package com.areeoh;

import com.areeoh.ClansAUCore;
import com.areeoh.framework.Manager;
import com.areeoh.utility.UtilMessage;
import com.areeoh.dominate.DominateManager;
import com.areeoh.dominate.commands.DominateCommandManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;

public class Dominate extends JavaPlugin {

    private ClansAUCore clansAUCore;
    private final Set<Manager> managers = new HashSet<>();

    public void onEnable() {
        this.clansAUCore = (ClansAUCore) Bukkit.getPluginManager().getPlugin("ClansAU-Core");
        registerManagers();

        for (Manager manager : managers) {
            if (manager.isEnabled()) {
                try {
                    manager.initialize(this);
                    System.out.println(manager.getName() + " initialised.");
                } catch (Exception ex) {
                    UtilMessage.log("Error", "Failed to load " + manager.getName());
                }
            }
            clansAUCore.getManagers().add(manager);
        }
    }

    private void registerManagers() {
        managers.add(new DominateManager(clansAUCore));

        managers.add(new DominateCommandManager(clansAUCore));
    }
}