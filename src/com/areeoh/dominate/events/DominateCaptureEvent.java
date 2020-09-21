package com.areeoh.dominate.events;

import com.areeoh.dominate.capturepoint.CapturePoint;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class DominateCaptureEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final CapturePoint capturePoint;

    public DominateCaptureEvent(CapturePoint capturePoint) {
        this.capturePoint = capturePoint;
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