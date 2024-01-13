package com.unrealdinnerbone.crafty.player;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.security.PublicKey;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class PlayerInfoEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private final UUID player;
    private final List<Action> actions;
    private final List<PlayerInfo> infos;

    public PlayerInfoEvent(UUID player, List<Action> actions, List<PlayerInfo> infos) {
        super(true);
        this.player = player;
        this.actions = actions;
        this.infos = infos;
    }

    public UUID getPlayer() {
        return player;
    }

    public List<Action> getActions() {
        return Collections.unmodifiableList(actions);
    }

    public List<PlayerInfo> getPlayerInfos() {
        return Collections.unmodifiableList(infos);
    }

    public void setPlayerInfos(List<PlayerInfo> infos) {
        this.infos.clear();
        this.infos.addAll(infos);
    }

    public void setActions(List<Action> actions) {
        this.actions.clear();
        this.actions.addAll(actions);
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }


    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
