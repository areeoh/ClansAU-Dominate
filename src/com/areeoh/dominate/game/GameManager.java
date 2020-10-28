package com.areeoh.dominate.game;

import com.areeoh.core.framework.Manager;
import com.areeoh.core.framework.Module;
import com.areeoh.core.framework.Plugin;
import com.areeoh.dominate.game.entity.GameSelectorVillager;
import com.areeoh.dominate.game.listeners.*;
import com.areeoh.dominate.game.listeners.dominate.CapturePointListener;
import com.areeoh.dominate.game.listeners.GameListener;
import com.areeoh.dominate.game.listeners.dominate.CustomItemHandler;
import com.areeoh.dominate.utility.CustomEntityRegistry;
import net.minecraft.server.v1_8_R3.EntityVillager;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.*;

public class GameManager extends Manager<Module> {

    private final Set<Game> games = new HashSet<>();

    public GameManager(Plugin plugin) {
        super(plugin, "DominateManager");
    }

    @Override
    public void registerModules() {
        addModule(new CapturePointListener(this));
        addModule(new HungerListener(this));
        addModule(new ClassSelectListener(this));
        addModule(new WeatherListener(this));
        addModule(new MobSpawnListener(this));
        addModule(new ArmorStandListener(this));
        addModule(new UnequipArmorListener(this));
        addModule(new GameListener(this));
        addModule(new CustomItemHandler(this));
        addModule(new PlayerPvpListener(this));
        addModule(new PlayerRespawnListener(this));
        addModule(new PlayerDeathListener(this));
        addModule(new PlayerSoupListener(this));
        addModule(new PlayerDropItemListener(this));
        addModule(new GameSelectorListener(this));
        addModule(new DurabilityListener(this));
        addModule(new MenuListener(this));
        addModule(new PlayerWorldListener(this));
        addModule(new PlayerJoinListener(this));
        addModule(new SpectateListener(this));
        addModule(new BreakBlockListener(this));
        addModule(new PlaceBlockListener(this));
        addModule(new SkillListener(this));
    }

    @Override
    public void onEnable() {
        CustomEntityRegistry.registerEntity("Villager", 120, EntityVillager.class, GameSelectorVillager.class);
        GameSelectorVillager selectorVillager = new GameSelectorVillager(((CraftWorld) Bukkit.getWorld("world")).getHandle());

        selectorVillager.spawn(new Location(Bukkit.getWorld("world"), 0.5, 5, 5.5));

        for (File file : Objects.requireNonNull(Bukkit.getWorlds().get(0).getWorldFolder().getParentFile().listFiles())) {
            if(!file.isDirectory()) {
                continue;
            }
            if(file.getName().endsWith("_TEMP")) {
                FileUtils.deleteQuietly(file);
            }
        }
    }


    public Set<Game> getGames() {
        return games;
    }

    public <E> List<E> getGames(Class<? extends E> clazz) {
        List<E> list = new ArrayList<>();
        for (Game game : getGames()) {
            if (clazz.isAssignableFrom(game.getClass())) {
                list.add(clazz.cast(game));
            }
        }
        return list;
    }

    public Game getGame(Player player) {
        return getGame(player.getUniqueId());
    }

    public Game getGame(UUID uuid) {
        for (Game game : getGames()) {
            if(game.getPlayers().contains(uuid)) {
                return game;
            }
        }
        return null;
    }
}
