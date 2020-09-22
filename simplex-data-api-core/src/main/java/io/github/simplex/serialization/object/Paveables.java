package io.github.simplex.serialization.object;

import java.util.Map;

import io.github.simplex.serialization.hooks.api.ShovelPathRegistry;
import io.github.simplex.serialization.util.IdentifiableCodecs;
import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

public class Paveables implements StorageObject {
    public static final Codec<Paveables> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.unboundedMap(IdentifiableCodecs.BLOCK, BlockState.CODEC).fieldOf("shovel_path").forGetter(Paveables::getMap)
    ).apply(instance, Paveables::new));

    private final Map<Block, BlockState> map;

    Paveables(Map<Block, BlockState> map) {
        this.map = map;
    }

    public Map<Block, BlockState> getMap() {
        return this.map;
    }

    @Override
    public void addAll() {
        this.getMap().forEach(ShovelPathRegistry.INSTANCE::add);
    }

    @Override
    public void removeAll() {
        this.getMap().keySet().forEach(ShovelPathRegistry.INSTANCE::remove);
    }

    public static Paveables getDefault() {
        return new Paveables(
                ImmutableMap.of(
                        Blocks.DIRT, Blocks.GRASS_PATH.getDefaultState(),
                        Blocks.COBBLESTONE, Blocks.GRAVEL.getDefaultState(),
                        Blocks.GRAVEL, Blocks.SAND.getDefaultState()
                )
        );
    }
}