package com.areeoh.dominate.teams;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;

import java.util.*;

public class Team {

    private final String name;
    private final ChatColor chatColor;
    private final DyeColor dyeColor;
    private final int particleColor;
    private final Color color;
    private final Set<UUID> players;
    private int score = 0;

    public Team(String name, ChatColor chatColor, Color color, DyeColor dyeColor, int particleColor) {
        this.name = name;
        this.chatColor = chatColor;
        this.color = color;
        this.dyeColor = dyeColor;
        this.particleColor = particleColor;
        this.players = new HashSet<>();
    }

    public int getParticleColor() {
        return particleColor;
    }

    public Color getColor() {
        return color;
    }

    public void addScore(int score) {
        this.score = Math.min(this.score + score, 15000);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public DyeColor getDyeColor() {
        return dyeColor;
    }

    public String getName() {
        return name;
    }

    public String getTag(boolean bold) {
        if(bold) {
            return getChatColor() + ChatColor.BOLD.toString() + getName();
        }
        return getChatColor() + getName();
    }

    public ChatColor getChatColor() {
        return chatColor;
    }

    public Set<UUID> getPlayers() {
        return players;
    }
}