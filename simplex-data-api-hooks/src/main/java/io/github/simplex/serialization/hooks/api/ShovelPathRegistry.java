package io.github.simplex.serialization.hooks.api;

import io.github.simplex.serialization.hooks.Block2BlockStateMap;
import io.github.simplex.serialization.hooks.mixin.ShovelItemAccessor;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;

public enum ShovelPathRegistry implements Block2BlockStateMap {
    INSTANCE;

    @Override
    public BlockState get(Block block) {
        return ShovelItemAccessor.getPathStates().get(block);
    }

    @Override
    public void add(Block block, BlockState state) {
        ShovelItemAccessor.getPathStates().put(block, state);
    }

    @Override
    public void remove(Block block) {
        ShovelItemAccessor.getPathStates().remove(block);
    }
}
