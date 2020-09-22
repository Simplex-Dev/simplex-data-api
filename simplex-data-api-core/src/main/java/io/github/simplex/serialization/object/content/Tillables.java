package io.github.simplex.serialization.object.content;

import java.util.Map;

import io.github.simplex.serialization.util.IdentifiableCodecs;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;

public class Tillables implements ObjectHolder {
    public static final Codec<Tillables> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.unboundedMap(IdentifiableCodecs.BLOCK, BlockState.CODEC).optionalFieldOf("tillables", ImmutableMap.of()).forGetter(Tillables::getMap)
    ).apply(instance, Tillables::new));

    private final Map<Block, BlockState> map;

    public Tillables(Map<Block, BlockState> map) {
        this.map = Maps.newHashMap(map);
    }

    public Map<Block, BlockState> getMap() {
        return this.map;
    }

    @Override
    public void addAll() {

    }

    @Override
    public void removeAll() {

    }

    public static Tillables getDefault() {
        return new Tillables(ImmutableMap.of());
    }
}
