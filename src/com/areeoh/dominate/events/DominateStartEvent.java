package com.areeoh.dominate.events;

import com.areeoh.dominate.DominateGame;
import com.areeoh.dominate.capturepoint.CapturePoint;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class DominateStartEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private final DominateGame dominateGame;

    public DominateStartEvent(DominateGame dominateGame) {
        this.dominateGame = dominateGame;
    }

    public DominateGame getDominateGame() {
        return dominateGame;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}