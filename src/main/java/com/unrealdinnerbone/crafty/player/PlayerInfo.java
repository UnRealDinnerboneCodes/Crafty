package com.unrealdinnerbone.crafty.player;

import com.destroystokyo.paper.profile.PlayerProfile;
import net.kyori.adventure.text.Component;
import org.bukkit.GameMode;
import org.jetbrains.annotations.Nullable;

public record PlayerInfo(int latency, boolean listed, GameMode gameMode, PlayerProfile playerProfile, @Nullable Component displayName, @Nullable ChatSession chatSession) {}
