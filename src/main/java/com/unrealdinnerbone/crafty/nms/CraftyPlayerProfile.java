package com.unrealdinnerbone.crafty.nms;

import com.destroystokyo.paper.profile.CraftPlayerProfile;
import com.mojang.authlib.GameProfile;
import com.unrealdinnerbone.crafty.api.CompletedProfile;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class CraftyPlayerProfile extends CraftPlayerProfile implements CompletedProfile {

    public CraftyPlayerProfile(GameProfile profile) {
        super(profile);
        if(profile.getId() == null) {
            throw new IllegalArgumentException("GameProfile must have a valid UUID");
        }
    }

    @SuppressWarnings("DataFlowIssue")
    @NotNull
    @Override
    public UUID getId() {
        return super.getId();
    }
}
