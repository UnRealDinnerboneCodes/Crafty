package com.unrealdinnerbone.crafty.api;

import com.destroystokyo.paper.ParticleBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;
import com.unrealdinnerbone.crafty.api.ParticleOption;
import com.unrealdinnerbone.crafty.api.UpdatedCriteria;
import com.unrealdinnerbone.crafty.api.UpdatedObjective;
import com.unrealdinnerbone.crafty.nms.CraftyObjectiveCriteria;
import com.unrealdinnerbone.crafty.nms.CraftyParticle;
import com.unrealdinnerbone.crafty.nms.ParticleAdapter;
import com.unrealdinnerbone.crafty.nms.CraftyObjective;
import org.bukkit.Particle;
import org.bukkit.scoreboard.Objective;

public class NMSUtils {

    public static UpdatedObjective getObjective(Objective score) {
        return new CraftyObjective(score);
    }

    public static <T extends JsonSerializer<ParticleOption> & JsonDeserializer<ParticleOption>> T getParticleAdapter() {
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

    public static UpdatedCriteria getCriteria(String name) {
        return new CraftyObjectiveCriteria(name);
    }



}
