package com.unrealdinnerbone.crafty.api.events;

import net.minecraft.world.phys.Vec3;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class PlayerVehicleEvent extends Event
{
    private static final HandlerList HANDLERS = new HandlerList();
    private final UUID player;
    private final Vec3 vec3;
    private final float yaw;
    private final float pitch;

    public PlayerVehicleEvent(UUID player, Vec3 vec3, float yaw, float pitch) {
        super(true);
        this.player = player;
        this.vec3 = vec3;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public UUID getPlayer() {
        return player;
    }

    public float getYaw() {
        return yaw;
    }

    public Vec3 getVec3() {
        return vec3;
    }

    public float getPitch() {
        return pitch;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    @NotNull
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
