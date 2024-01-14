package com.unrealdinnerbone.crafty.packet;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.chat.RemoteChatSession;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.protocol.player.PublicProfileKey;
import com.github.retrooper.packetevents.protocol.player.TextureProperty;
import com.github.retrooper.packetevents.protocol.player.UserProfile;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerPlayerInfoUpdate;
import com.unrealdinnerbone.crafty.api.events.PlayerInfoEvent;
import com.unrealdinnerbone.crafty.api.player.Action;
import com.unrealdinnerbone.crafty.api.player.ChatSession;
import com.unrealdinnerbone.crafty.api.player.PlayerInfo;
import com.unrealdinnerbone.crafty.api.player.ProfileKey;
import io.github.retrooper.packetevents.util.SpigotConversionUtil;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class PacketHandler implements PacketListener {

    @Override
    public void onPacketSend(PacketSendEvent event) {
        if(event.getPacketType() == PacketType.Play.Server.PLAYER_INFO_UPDATE) {
            WrapperPlayServerPlayerInfoUpdate wrapperPlayServerPlayerInfoUpdate = new WrapperPlayServerPlayerInfoUpdate(event);
            List<Action> actions = new ArrayList<>(wrapperPlayServerPlayerInfoUpdate.getActions().stream().map(Convert::convertAction).toList());
            List<PlayerInfo> playerInfos = wrapperPlayServerPlayerInfoUpdate.getEntries()
                    .stream()
                    .map(Convert::convertPlayerInfo).toList();
            UUID uuid = event.getUser().getUUID();
            PlayerInfoEvent playerInfoUpdateEvent = new PlayerInfoEvent(uuid, actions, playerInfos);
            if(playerInfoUpdateEvent.callEvent()) {
                wrapperPlayServerPlayerInfoUpdate.setActions(EnumSet.copyOf(playerInfoUpdateEvent.getActions().stream().map(DeConvert::convertAction).toList()));
                wrapperPlayServerPlayerInfoUpdate.setEntries(playerInfoUpdateEvent.getPlayerInfos().stream()
                        .map(DeConvert::convertPlayerInfo).toList());
            }
        }
    }

    public static class Convert {

        public static PlayerInfo convertPlayerInfo(WrapperPlayServerPlayerInfoUpdate.PlayerInfo playerInfo) {
            GameMode gameMode = SpigotConversionUtil.toBukkitGameMode(playerInfo.getGameMode());
            PlayerProfile gameProfile = Convert.convertProfile(playerInfo.getGameProfile());
            ChatSession chatSession = Convert.convertChatSession(playerInfo.getChatSession());
            return new PlayerInfo(playerInfo.getLatency(), playerInfo.isListed(), gameMode, gameProfile, playerInfo.getDisplayName(), chatSession);
        }
        public static Action convertAction(WrapperPlayServerPlayerInfoUpdate.Action action) {
            return Action.valueOf(action.name());
        }

        public static PlayerProfile convertProfile(UserProfile userProfile) {
            PlayerProfile profileExact = Bukkit.createProfileExact(userProfile.getUUID(), userProfile.getName());
            List<ProfileProperty> properties = userProfile.getTextureProperties().stream().map(textureProperty -> new ProfileProperty(textureProperty.getName(), textureProperty.getValue(), textureProperty.getSignature())).collect(Collectors.toList());
            profileExact.setProperties(properties);
            return profileExact;
        }

        public static ChatSession convertChatSession(RemoteChatSession chatSession) {
            if(chatSession == null) {
                return null;
            }
            PublicProfileKey publicProfileKey = chatSession.getPublicProfileKey();
            ProfileKey profileKey = new ProfileKey(publicProfileKey.getExpiresAt(), publicProfileKey.getKey(), publicProfileKey.getKeySignature());
            return new ChatSession(chatSession.getSessionId(), profileKey);
        }
    }

    public static class DeConvert {

        public static WrapperPlayServerPlayerInfoUpdate.PlayerInfo convertPlayerInfo(PlayerInfo playerInfo) {
            com.github.retrooper.packetevents.protocol.player.GameMode gameMode = SpigotConversionUtil.fromBukkitGameMode(playerInfo.gameMode());
            UserProfile gameProfile = DeConvert.convertProfile(playerInfo.playerProfile());
            return new WrapperPlayServerPlayerInfoUpdate.PlayerInfo(gameProfile, playerInfo.listed(), playerInfo.latency(), gameMode, playerInfo.displayName(), DeConvert.convertChatSession(playerInfo.chatSession()));
        }

        public static WrapperPlayServerPlayerInfoUpdate.Action convertAction(Action action) {
            return WrapperPlayServerPlayerInfoUpdate.Action.valueOf(action.name());
        }

        public static UserProfile convertProfile(PlayerProfile playerProfile) {
            UserProfile userProfile = new UserProfile(playerProfile.getId(), playerProfile.getName());
            List<TextureProperty> properties = playerProfile.getProperties().stream().map(profileProperty -> new TextureProperty(profileProperty.getName(), profileProperty.getValue(), profileProperty.getSignature())).collect(Collectors.toList());
            userProfile.setTextureProperties(properties);
            return userProfile;
        }

        public static RemoteChatSession convertChatSession(ChatSession chatSession) {
            if(chatSession == null) {
                return null;
            }
            PublicProfileKey publicProfileKey = new PublicProfileKey(chatSession.profileKey().expiresAt(), chatSession.profileKey().key(), chatSession.profileKey().keySignature());
            return new RemoteChatSession(chatSession.sessionId(), publicProfileKey);
        }
    }
}
