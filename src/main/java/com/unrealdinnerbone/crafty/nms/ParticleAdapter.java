package com.unrealdinnerbone.crafty.nms;

import com.google.gson.*;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import com.unrealdinnerbone.crafty.api.ParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;

import java.lang.reflect.Type;

public class ParticleAdapter implements JsonSerializer<ParticleOption>, JsonDeserializer<ParticleOption> {
    @Override
    public JsonElement serialize(ParticleOption src, Type typeOfSrc, JsonSerializationContext context) {
        if(src instanceof CraftyParticle craftyParticle) {
            DataResult<JsonElement> encode = ParticleTypes.CODEC.encode(craftyParticle.getParticleOptions(), JsonOps.INSTANCE, JsonOps.INSTANCE.empty());
            if(encode.error().isPresent()) {
                throw new JsonParseException(encode.error().get().message());
            }
            return encode.result().orElseThrow(() -> new JsonParseException("Can't serialize " + src.getClass().getName()));
        }else {
            throw new JsonParseException("Can't serialize " + src.getClass().getName());
        }
    }

    @Override
    public ParticleOption deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        DataResult<Pair<ParticleOptions, JsonElement>> decode = ParticleTypes.CODEC.decode(JsonOps.INSTANCE, json);
        if(decode.error().isPresent()) {
            throw new JsonParseException(decode.error().get().message());
        }
        return new CraftyParticle(decode.result().orElseThrow(() -> new JsonParseException("Could not parse")).getFirst());
    }
}
