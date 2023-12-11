package com.unrealdinnerbone.crafty.particle;

import com.unrealdinnerbone.crafty.api.ParticleOption;
import net.minecraft.core.particles.*;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.craftbukkit.v1_20_R3.CraftParticle;
import org.joml.Vector3f;

public class CraftyParticle implements ParticleOption {

    private final Particle particle;
    private final Object data;

    private final ParticleOptions particleOptions;

    public CraftyParticle(Particle particle, Object data) {
        this.particle = particle;
        this.data = data;
        this.particleOptions = CraftParticle.createParticleParam(particle, data);
    }

    //ParticleTypes.CODEC

    public CraftyParticle(ParticleOptions options) {
        this.particle = CraftParticle.minecraftToBukkit(options.getType());
        this.data = convertOptionsToType(options);
        this.particleOptions = options;
    }

    @Override
    public Particle getParticle() {
        return particle;
    }

    @Override
    public Object getData() {
        return data;
    }

    public ParticleOptions getParticleOptions() {
        return particleOptions;
    }

    public static Object convertOptionsToType(ParticleOptions particleOptions) {
        if(particleOptions instanceof BlockParticleOption blockParticleOption) {
            return blockParticleOption.getState().createCraftBlockData();
        }else if(particleOptions instanceof DustColorTransitionOptions dustColorTransitionOptions) {
            return new Particle.DustTransition(fromVec3(dustColorTransitionOptions.getFromColor()),
                    fromVec3(dustColorTransitionOptions.getToColor()),
                    dustColorTransitionOptions.getScale());
        }else if(particleOptions instanceof DustParticleOptions dustParticleOptions) {
            return new Particle.DustOptions(fromVec3(dustParticleOptions.getColor()), dustParticleOptions.getScale());
        }else if(particleOptions instanceof ItemParticleOption itemParticleOption) {
            return itemParticleOption.getItem().asBukkitMirror();
        }else if(particleOptions instanceof SculkChargeParticleOptions sculkChargeParticleOptions) {
            return sculkChargeParticleOptions.roll();
        }else if(particleOptions instanceof ShriekParticleOption shriekParticleOption) {
            return shriekParticleOption.getDelay();
        }else if(particleOptions instanceof SimpleParticleType simpleParticleType) {
            return null;
        }else if(particleOptions instanceof VibrationParticleOption vibrationParticleOption) {
            throw new UnsupportedOperationException("VibrationParticleOption is not supported");
        }else {
            throw new UnsupportedOperationException("Unknown ParticleOption: " + particleOptions.getClass().getName());
        }
    }

    public static Color fromVec3(Vector3f vec3) {
        return Color.fromRGB((int) (vec3.x() * 255), (int) (vec3.y() * 255), (int) (vec3.z() * 255));
    }
}
