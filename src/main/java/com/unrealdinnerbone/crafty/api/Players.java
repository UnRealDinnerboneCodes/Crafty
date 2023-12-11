package com.unrealdinnerbone.crafty.api;

import org.bukkit.craftbukkit.v1_20_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class Players {

    public static void sendToConfig(Player player) {
        if(player instanceof CraftPlayer craftPlayer) {
            craftPlayer.getHandle().connection.switchToConfig();
        }
    }
}
