package com.areeoh.dominate.game.commands.dominate;

import com.areeoh.core.framework.commands.Command;
import com.areeoh.core.framework.commands.CommandManager;
import com.areeoh.core.utility.UtilFormat;
import com.areeoh.core.utility.UtilMessage;
import com.areeoh.dominate.game.dominate.DominateGame;
import com.areeoh.dominate.game.dominate.DominateMap;
import com.areeoh.dominate.game.GameManager;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public class CreateCommand extends Command<Player> {

    public CreateCommand(CommandManager manager) {
        super(manager, "GameCreateCommand", Player.class);
        setCommand("create");
        setIndex(1);
        setRequiredArgs(2);
    }

    @Override
    public boolean execute(Player player, String[] strings) {
        final DominateMap dominateMap = DominateMap.valueOf(strings[1].toUpperCase());

        DominateGame dominateGame = null;
        try {
            long l = System.currentTimeMillis();
            World newWorld = createNewWorld(Bukkit.getWorld(dominateMap.getWorldName()));
            Bukkit.broadcastMessage("Took " + (System.currentTimeMillis() - l) + " milliseconds to create a new world.");

            dominateGame = dominateMap.getClazz().getConstructor(World.class, GameManager.class).newInstance(newWorld, getManager(GameManager.class));
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | IOException e) {
            e.printStackTrace();
        }
        if (dominateGame == null) {
            UtilMessage.message(player, "Dominate", "Could not find map " + ChatColor.YELLOW + strings[1] + ChatColor.GRAY + ".");
            return false;
        }
        getManager(GameManager.class).getGames().add(dominateGame);
        UtilMessage.broadcast("Dominate", "Map loaded: " + ChatColor.GREEN + UtilFormat.cleanString(dominateMap.getWorldName()));

        return true;
    }

    @Override
    public void invalidArgsRequired(Player sender) {
        UtilMessage.message(sender, "Dominate", "You did not input a map name.");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        List<String> list = Arrays.stream(DominateMap.values()).map(dominateMap -> dominateMap.name().toLowerCase()).collect(Collectors.toList());
        list.removeIf(s -> !s.contains(args[1].toLowerCase()));
        return list;
    }

    synchronized World createNewWorld(World world) throws IOException {
        File worldDir = world.getWorldFolder();
        String newName = UUID.randomUUID() + "_TEMP";
        File folder = new File(worldDir.getParent(), newName);
        FileUtils.copyDirectory(worldDir, folder);
        for (File file : Objects.requireNonNull(folder.listFiles())) {
            if (file.isDirectory()) {
                continue;
            }
            if (file.getName().equals("uid.dat") || file.getName().equals("session.dat")) {
                file.delete();
            }
        }
        return new WorldCreator(newName).createWorld();
    }
}
