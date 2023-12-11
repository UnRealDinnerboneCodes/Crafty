package com.unrealdinnerbone.crafty.api;

import com.destroystokyo.paper.ParticleBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;
import com.unrealdinnerbone.crafty.particle.CraftyParticle;
import com.unrealdinnerbone.crafty.particle.ParticleAdapter;
import org.bukkit.Particle;

public class Particles {

    public static <T extends JsonSerializer<ParticleOption> & JsonDeserializer<ParticleOption>> T getAdapter() {
        return (T) new ParticleAdapter();
    }
    public static ParticleOption createParticle(Particle particle, Object data) {
        return new CraftyParticle(particle, data);
    }

    public static ParticleOption createParticle(ParticleBuilder builder) {
        return new CraftyParticle(builder.particle(),  builder.data());
    }

    public static ParticleOption createParticle(Particle particle) {
        return new CraftyParticle(particle, null);
    }
}
