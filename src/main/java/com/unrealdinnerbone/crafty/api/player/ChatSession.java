package com.unrealdinnerbone.crafty.api.player;

import java.util.UUID;

public record ChatSession(UUID sessionId, ProfileKey profileKey) {}
