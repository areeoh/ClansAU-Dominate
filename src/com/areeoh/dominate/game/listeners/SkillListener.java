package com.areeoh.dominate.game.listeners;

import com.areeoh.champions.skills.events.SkillActivateEvent;
import com.areeoh.core.framework.Module;
import com.areeoh.core.utility.UtilMessage;
import com.areeoh.dominate.game.Game;
import com.areeoh.dominate.game.GameManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class SkillListener extends Module<GameManager> implements Listener {

    public SkillListener(GameManager manager) {
        super(manager, "SkillListener");
    }

    @EventHandler
    public void onSkillActivate(SkillActivateEvent event) {
        Game game = getManager().getGame(event.getPlayer());
        if(game != null && game.getWorld().getName().equals(event.getPlayer().getWorld().getName()) && !game.isSpectating(event.getPlayer())) {
            return;
        }
        event.setCancelled(true);
    }
}
