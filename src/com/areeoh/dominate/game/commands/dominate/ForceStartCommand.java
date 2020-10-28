package com.areeoh.dominate.game.commands.dominate;

import com.areeoh.core.framework.commands.Command;
import com.areeoh.core.framework.commands.CommandManager;
import com.areeoh.core.utility.UtilMessage;
import com.areeoh.dominate.game.Game;
import com.areeoh.dominate.game.GameManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ForceStartCommand extends Command<Player> {

    public ForceStartCommand(CommandManager manager) {
        super(manager, "ForceStartCommand", Player.class);

        setCommand("forcestart");
        setIndex(1);
        setRequiredArgs(1);
    }

    @Override
    public boolean execute(Player player, String[] strings) {
        Game game = getManager(GameManager.class).getGame(player);
        if(game == null) {
            UtilMessage.message(player, "Game", "You are not in a game.");
            return false;
        }
        game.startGame();
        UtilMessage.broadcast("Game", ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " forcefully started the game.");
        return false;
    }
}
