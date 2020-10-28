package com.areeoh.dominate.game.events;

import com.areeoh.dominate.game.dominate.DominateGame;
import com.areeoh.dominate.game.capturepoint.CapturePoint;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class DominateCaptureEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final CapturePoint capturePoint;
    private final DominateGame dominateGame;

    public DominateCaptureEvent(CapturePoint capturePoint, DominateGame dominateGame) {
        this.capturePoint = capturePoint;
        this.dominateGame = dominateGame;
    }

    public DominateGame getDominateGame() {
        return dominateGame;
    }

    public CapturePoint getCapturePoint() {
        return capturePoint;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}