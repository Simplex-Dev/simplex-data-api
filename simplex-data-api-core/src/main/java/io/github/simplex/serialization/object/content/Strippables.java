package io.github.simplex.serialization.object.content;

import java.util.Map;

import io.github.simplex.serialization.hooks.api.StrippableBlockRegistry;
import io.github.simplex.serialization.util.IdentifiableCodecs;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.block.Block;

public class Strippables implements ObjectHolder {
    public static final Codec<Strippables> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.unboundedMap(IdentifiableCodecs.BLOCK, IdentifiableCodecs.BLOCK).optionalFieldOf("strippables", ImmutableMap.of()).forGetter(Strippables::getMap)
    ).apply(instance, Strippables::new));

    private final Map<Block, Block> map;

    public Strippables(Map<Block, Block> map) {
        this.map = map;
    }

    public Map<Block, Block> getMap() {
        return this.map;
    }

    @Override
    public void addAll() {
        this.getMap().forEach(StrippableBlockRegistry.INSTANCE::add);
    }

    @Override
    public void removeAll() {
        this.getMap().keySet().forEach(StrippableBlockRegistry.INSTANCE::remove);
    }
}
