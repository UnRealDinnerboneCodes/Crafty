package com.unrealdinnerbone.crafty.player;

import com.comphenix.packetwrapper.wrappers.play.clientbound.WrapperPlayServerPlayerInfo;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.PlayerInfoData;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import org.bukkit.plugin.Plugin;

import java.util.*;

public class PlayerUtils
{

    private static final Map<UUID, String> NAME_CACHE = new HashMap<>();
    public static void init(Plugin plugin) {
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();

        protocolManager.addPacketListener(new PacketAdapter(plugin, PacketType.Play.Server.PLAYER_INFO) {
            @Override
            public void onPacketReceiving(PacketEvent event) {

            }

            @Override
            public void onPacketSending(PacketEvent event) {
                WrapperPlayServerPlayerInfo wrapperPlayServerPlayerInfo = new WrapperPlayServerPlayerInfo(event.getPacket());
                List<PlayerInfoData> entries = new ArrayList<>();
                for (PlayerInfoData entry : wrapperPlayServerPlayerInfo.getEntries()) {
                    if(!entry.getProfile().getUUID().equals(event.getPlayer().getUniqueId()) && NAME_CACHE.containsKey(entry.getProfile().getUUID())) {
                        WrappedGameProfile profile = entry.getProfile();
                        WrappedGameProfile newPrifle = new WrappedGameProfile(profile.getUUID(), NAME_CACHE.get(profile.getUUID()));
                        newPrifle.getProperties().clear();
                        newPrifle.getProperties().putAll(profile.getProperties());
                        entries.add(new PlayerInfoData(newPrifle, entry.getLatency(), entry.getGameMode(), entry.getDisplayName()));
                    } else {
                        entries.add(entry);
                    }
                }
                wrapperPlayServerPlayerInfo.setEntries(entries);
            }
        });
    }

    public static void setPlayerName(UUID uuid, String name) {
        NAME_CACHE.put(uuid, name);
    }

    public static void removePlayerName(UUID uuid) {
        NAME_CACHE.remove(uuid);
    }

    public static Optional<String> getPlayerName(UUID uuid) {
        return Optional.ofNullable(NAME_CACHE.get(uuid));
    }
}
