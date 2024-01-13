package com.unrealdinnerbone.crafty.player;

import java.security.PublicKey;
import java.time.Instant;

public record ProfileKey(Instant expiresAt, PublicKey key, byte[] keySignature) {
}
