package com.unrealdinnerbone.crafty;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import com.unrealdinnerbone.crafty.player.NewEvents;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;
import org.bukkit.plugin.Plugin;

public class Utils
{
    public static void onLoad(Plugin plugin) {
        PacketEvents.setAPI(SpigotPacketEventsBuilder.build(plugin));
        PacketEvents.getAPI().getSettings()
                .bStats(false)
                .checkForUpdates(false)
                .reEncodeByDefault(false);
        PacketEvents.getAPI().load();
    }

    public static void onEnable() {
        PacketEvents.getAPI().getEventManager().registerListener(new NewEvents(), PacketListenerPriority.HIGH);
    }

    public static void onDisable() {
        PacketEvents.getAPI().terminate();
    }
}
