package com.unrealdinnerbone.crafty.api.events;

import com.unrealdinnerbone.crafty.api.player.Action;
import com.unrealdinnerbone.crafty.api.player.PlayerInfo;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

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
        return actions;
    }

    public List<PlayerInfo> getPlayerInfos() {
        return infos;
    }

    @Override
    @NotNull
    public HandlerList getHandlers() {
        return HANDLERS;
    }


    @NotNull
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
