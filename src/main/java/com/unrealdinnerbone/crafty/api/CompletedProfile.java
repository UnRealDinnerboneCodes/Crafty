package com.unrealdinnerbone.crafty.api;

import com.destroystokyo.paper.profile.PlayerProfile;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public interface CompletedProfile extends PlayerProfile {

    @Override
    @NotNull UUID getId();
}
