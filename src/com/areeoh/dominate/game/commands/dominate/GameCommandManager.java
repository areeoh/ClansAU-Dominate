package com.areeoh.dominate.game.commands.dominate;

import com.areeoh.core.client.Rank;
import com.areeoh.core.framework.Plugin;
import com.areeoh.core.framework.commands.Command;
import com.areeoh.core.framework.commands.CommandManager;
import org.bukkit.entity.Player;

public class GameCommandManager extends CommandManager {

    public GameCommandManager(Plugin plugin) {
        super(plugin, "DominateCommandManager");
    }

    @Override
    public void registerModules() {
        addModule(new BaseCommand(this));
        addModule(new CreateCommand(this));
        addModule(new ForceStartCommand(this));
    }

    class BaseCommand extends Command<Player> {

        public BaseCommand(CommandManager manager) {
            super(manager, "GameBaseCommand", Player.class);
            setCommand("game");
            setIndex(0);
            setRequiredArgs(0);
            setRequiredRank(Rank.OWNER);
        }

        @Override
        public boolean execute(Player player, String[] args) {
            return false;
        }
    }
}
