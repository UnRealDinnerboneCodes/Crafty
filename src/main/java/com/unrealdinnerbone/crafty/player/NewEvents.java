package com.unrealdinnerbone.crafty.player;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketListenerAbstract;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.event.simple.PacketPlaySendEvent;
import com.github.retrooper.packetevents.protocol.chat.RemoteChatSession;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.protocol.player.PublicProfileKey;
import com.github.retrooper.packetevents.protocol.player.TextureProperty;
import com.github.retrooper.packetevents.protocol.player.UserProfile;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerPlayerInfoUpdate;
import io.github.retrooper.packetevents.util.SpigotConversionUtil;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;

import java.util.EnumSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class NewEvents implements PacketListener {

    @Override
    public void onPacketSend(PacketSendEvent event) {
        if(event.getPacketType() == PacketType.Play.Server.PLAYER_INFO_UPDATE) {
            WrapperPlayServerPlayerInfoUpdate wrapperPlayServerPlayerInfoUpdate = new WrapperPlayServerPlayerInfoUpdate(event);
            List<Action> actions = wrapperPlayServerPlayerInfoUpdate.getActions()
                    .stream()
                    .map(Convert::convertAction)
                    .toList();
            List<PlayerInfo> playerInfos = wrapperPlayServerPlayerInfoUpdate.getEntries()
                    .stream()
                    .map(entry -> {
                        GameMode gameMode = SpigotConversionUtil.toBukkitGameMode(entry.getGameMode());
                        PlayerProfile gameProfile = Convert.convertProfile(entry.getGameProfile());
                        ChatSession chatSession = Convert.convertChatSession(entry.getChatSession());
                        return new PlayerInfo(entry.getLatency(), entry.isListed(), gameMode, gameProfile, entry.getDisplayName(), chatSession);
                    }).toList();
            UUID uuid = event.getUser().getUUID();
            PlayerInfoEvent playerInfoUpdateEvent = new PlayerInfoEvent(uuid, actions, playerInfos);
            if(playerInfoUpdateEvent.callEvent()) {
                EnumSet<WrapperPlayServerPlayerInfoUpdate.Action> actions1 = EnumSet.copyOf(playerInfoUpdateEvent.getActions().stream().map(DeConvert::convertAction).toList());
                wrapperPlayServerPlayerInfoUpdate.setActions(actions1);
                wrapperPlayServerPlayerInfoUpdate.setEntries(playerInfoUpdateEvent.getPlayerInfos().stream().map(playerInfo -> {
                    com.github.retrooper.packetevents.protocol.player.GameMode gameMode = SpigotConversionUtil.fromBukkitGameMode(playerInfo.gameMode());
                    UserProfile gameProfile = DeConvert.convertProfile(playerInfo.playerProfile());
                    return new WrapperPlayServerPlayerInfoUpdate.PlayerInfo(gameProfile, playerInfo.listed(), playerInfo.latency(), gameMode, playerInfo.displayName(), DeConvert.convertChatSession(playerInfo.chatSession()));

                }).toList());
            }




        }
    }



    public static class Convert {
        public static Action convertAction(WrapperPlayServerPlayerInfoUpdate.Action action) {
            return Action.valueOf(action.name());
        }

        public static GameMode convertGamemode(com.github.retrooper.packetevents.protocol.player.GameMode gameMode) {
            return GameMode.valueOf(gameMode.name());
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

        public static WrapperPlayServerPlayerInfoUpdate.Action convertAction(Action action) {
            return WrapperPlayServerPlayerInfoUpdate.Action.valueOf(action.name());
        }

        public static com.github.retrooper.packetevents.protocol.player.GameMode convertGamemode(GameMode gameMode) {
            return com.github.retrooper.packetevents.protocol.player.GameMode.valueOf(gameMode.name());
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
