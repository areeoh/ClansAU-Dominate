package com.areeoh.dominate.game.commands;

import com.areeoh.champions.role.RoleManager;
import com.areeoh.core.countdown.CountdownManager;
import com.areeoh.core.countdown.TitleCountdown;
import com.areeoh.core.framework.Plugin;
import com.areeoh.core.framework.commands.Command;
import com.areeoh.core.framework.commands.CommandManager;
import com.areeoh.core.scoreboard.ScoreboardManager;
import com.areeoh.core.utility.UtilMessage;
import com.areeoh.dominate.game.Game;
import com.areeoh.dominate.game.GameManager;
import com.areeoh.dominate.game.dominate.DominateScoreboard;
import com.areeoh.dominate.teams.Team;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class QuitCommand extends CommandManager {

    public QuitCommand(Plugin plugin) {
        super(plugin, "QuitCommand");
    }

    @Override
    public void registerModules() {
        addModule(new Command(this, "QuitCommand", Player.class) {
            @Override
            public boolean execute(CommandSender sender, String[] strings) {
                Player player = (Player) sender;

                Game game = getManager(GameManager.class).getGame(player);
                if(game == null) {
                    UtilMessage.message(player, "Game", "You are not in a game.");
                    return false;
                }
                player.getInventory().clear();
                player.getInventory().setHelmet(null);
                player.getInventory().setChestplate(null);
                player.getInventory().setLeggings(null);
                player.getInventory().setBoots(null);

                game.getTeam(player).getPlayers().remove(player.getUniqueId());
                getManager(ScoreboardManager.class).getModule(DominateScoreboard.class).giveNewScoreboard(player);

                player.teleport(Bukkit.getWorld("world").getSpawnLocation());

                for (RoleManager manager : getPlugin().getManagers(RoleManager.class)) {
                    manager.getUsers().remove(player.getUniqueId());
                }


                return true;
            }
        }.setCommand("quit").setIndex(0).setAliases("leave", "lobby"));
    }

    public void doSomething() {

    }
}