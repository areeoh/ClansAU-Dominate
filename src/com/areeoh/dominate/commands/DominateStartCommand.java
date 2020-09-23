package com.areeoh.dominate.commands;

import com.areeoh.dominate.DominateGame;
import com.areeoh.dominate.DominateManager;
import com.areeoh.dominate.DominateWorld;
import com.areeoh.dominate.events.DominateStartEvent;
import com.areeoh.framework.commands.Command;
import com.areeoh.framework.commands.CommandManager;
import com.areeoh.utility.UtilMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DominateStartCommand extends Command<Player> {

    public DominateStartCommand(CommandManager manager) {
        super(manager, "DominateStartCommand", Player.class);
        setCommand("start");
        setIndex(1);
        setRequiredArgs(2);
    }

    @Override
    public boolean execute(Player player, String[] strings) {
        final DominateManager.DominateMap dominateMap = DominateManager.DominateMap.valueOf(strings[1].toUpperCase());
        DominateWorld dominateWorld = null;
        try {
            dominateWorld = dominateMap.getClazz().getConstructor(DominateManager.class).newInstance(getManager(DominateManager.class));
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        if (dominateWorld == null) {
            UtilMessage.message(player, "Dominate", "Could not find map " + ChatColor.YELLOW + strings[1] + ChatColor.GRAY + ".");
            return false;
        }
        DominateGame game = new DominateGame(getManager(DominateManager.class), dominateWorld);
        getManager(DominateManager.class).setDominateGame(game);
        Bukkit.getServer().getPluginManager().callEvent(new DominateStartEvent(game));
        UtilMessage.broadcast("Dominate", ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " has started the game.");
        UtilMessage.broadcast("Dominate", "Map loaded: " + ChatColor.GREEN + dominateWorld.getWorld().getName());
        return true;
    }

    @Override
    public void invalidArgsRequired(Player sender) {
        UtilMessage.message(sender, "Dominate", "You did not input a map name.");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        List<String> list = Arrays.stream(DominateManager.DominateMap.values()).map(dominateMap -> dominateMap.name().toLowerCase()).collect(Collectors.toList());
        list.removeIf(s -> !s.contains(args[1].toLowerCase()));
        return list;
    }
}
