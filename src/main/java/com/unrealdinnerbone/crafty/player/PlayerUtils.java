//package com.unrealdinnerbone.crafty.player;
//
//import com.comphenix.packetwrapper.wrappers.play.clientbound.WrapperPlayServerPlayerInfo;
//import com.comphenix.protocol.PacketType;
//import com.comphenix.protocol.ProtocolLibrary;
//import com.comphenix.protocol.ProtocolManager;
//import com.comphenix.protocol.events.PacketAdapter;
//import com.comphenix.protocol.events.PacketEvent;
//import com.comphenix.protocol.wrappers.EnumWrappers;
//import com.comphenix.protocol.wrappers.PlayerInfoData;
//import com.comphenix.protocol.wrappers.WrappedGameProfile;
//import net.kyori.adventure.text.Component;
//import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
//import org.bukkit.GameMode;
//import org.bukkit.entity.Player;
//import org.bukkit.plugin.Plugin;
//
//import java.util.*;
//import java.util.stream.Collectors;
//
//public class PlayerUtils
//{
//
//    private static final Map<UUID, String> NAME_CACHE = new HashMap<>();
//    public static void init(Plugin plugin) {
//        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
//
//        protocolManager.addPacketListener(new PacketAdapter(plugin, PacketType.Play.Server.PLAYER_INFO) {
//            @Override
//            public void onPacketReceiving(PacketEvent event) {
//
//            }
//
//            @Override
//            public void onPacketSending(PacketEvent event) {
//                WrapperPlayServerPlayerInfo wrapperPlayServerPlayerInfo = new WrapperPlayServerPlayerInfo(event.getPacket());
//                List<PlayerInfoEvent.Action> actions = wrapperPlayServerPlayerInfo.getActions().stream().map(action -> PlayerInfoEvent.Action.valueOf(action.name())).collect(Collectors.toList());
//                List<PlayerInfoData> entries = new ArrayList<>();
//                List<PlayerInfo> playerInfos = new ArrayList<>();
//                for (PlayerInfoData entry : entries) {
//                    GameMode gameMode = GameMode.valueOf(entry.getGameMode().name());
//                    Component deserialize = GsonComponentSerializer.gson().deserialize(entry.getDisplayName().getJson());
//                    entry.getProfile()
//                    PlayerInfo playerInfo = new PlayerInfo(entry.getProfileId(), entry.getLatency(), entry.isListed(), gameMode, deserialize);
//                }
//                for (PlayerInfoData entry : wrapperPlayServerPlayerInfo.getEntries()) {
//                    WrappedGameProfile wrappedProfile = entry.getProfile();
//                    UUID uuid = wrappedProfile.getUUID();
//                    if(!uuid.equals(event.getPlayer().getUniqueId())) {
//                        if(NAME_CACHE.containsKey(uuid)) {
//                            WrappedGameProfile newPrifle = new WrappedGameProfile(uuid, NAME_CACHE.get(uuid));
//                            newPrifle.getProperties().clear();
//                            newPrifle.getProperties().putAll(wrappedProfile.getProperties());
//                            entries.add(new PlayerInfoData(newPrifle, entry.getLatency(), entry.getGameMode(), entry.getDisplayName()));
//                        } else {
//                            entries.add(entry);
//                        }
//                    } else {
//                        entries.add(entry);
//                    }
//                }
//                wrapperPlayServerPlayerInfo.setEntries(entries);
//            }
//        });
//    }
//
//    public static void setPlayerName(UUID uuid, String name) {
//        NAME_CACHE.put(uuid, name);
//    }
//
//    public static void removePlayerName(UUID uuid) {
//        NAME_CACHE.remove(uuid);
//    }
//
//    public static Optional<String> getPlayerName(UUID uuid) {
//        return Optional.ofNullable(NAME_CACHE.get(uuid));
//    }
//}
