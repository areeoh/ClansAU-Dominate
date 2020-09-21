package com.areeoh.dominate.commands;

import com.areeoh.ClansAUCore;
import com.areeoh.client.Rank;
import com.areeoh.framework.commands.Command;
import com.areeoh.framework.commands.CommandManager;
import org.bukkit.entity.Player;

public class DominateCommandManager extends CommandManager {

    public DominateCommandManager(ClansAUCore plugin) {
        super(plugin, "DominateCommandManager");
    }

    @Override
    public void registerModules() {
        addModule(new BaseCommand(this));
        addModule(new DominateStartCommand(this));
    }

    class BaseCommand extends Command<Player> {

        public BaseCommand(CommandManager manager) {
            super(manager, "DominateBaseCommand", Player.class);
            setCommand("dominate");
            setAliases("dom");
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
