package io.github.simplex.serialization.hooks.api;

import io.github.simplex.serialization.hooks.Block2BlockStateMap;
import io.github.simplex.serialization.hooks.mixin.HoeItemAccessor;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;

public enum TillableBlockRegistry implements Block2BlockStateMap {
    INSTANCE;

    @Override
    public BlockState get(Block block) {
        return HoeItemAccessor.getTilledBlocks().get(block);
    }

    @Override
    public void add(Block block, BlockState state) {
        HoeItemAccessor.getTilledBlocks().put(block, state);
    }

    @Override
    public void remove(Block block) {
        HoeItemAccessor.getTilledBlocks().remove(block);
    }
}
