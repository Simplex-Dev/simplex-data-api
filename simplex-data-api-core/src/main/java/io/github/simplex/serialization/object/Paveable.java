package io.github.simplex.serialization.object;


import java.util.Map;

import io.github.simplex.serialization.util.IdentifiableCodecs;
import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

// TODO: add hooks for this
public class Paveable implements Initializable {
    public static final Codec<Paveable> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.unboundedMap(IdentifiableCodecs.BLOCK, BlockState.CODEC).fieldOf("shovel_path").forGetter(Paveable::getMap)
    ).apply(instance, Paveable::new));

    private final Map<Block, BlockState> map;

    Paveable(Map<Block, BlockState> map) {
        this.map = map;
    }

    public Map<Block, BlockState> getMap() {
        return this.map;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void removeAll() {

    }

    public static Paveable getDefault() {
        return new Paveable(
                ImmutableMap.of(
                        Blocks.DIRT, Blocks.GRASS_PATH.getDefaultState(),
                        Blocks.COBBLESTONE, Blocks.GRAVEL.getDefaultState(),
                        Blocks.GRAVEL, Blocks.SAND.getDefaultState()
                )
        );
    }
}