package io.github.simplex.serialization.hooks.api;

import io.github.simplex.serialization.hooks.Block2BlockMap;
import io.github.simplex.serialization.hooks.mixin.AxeItemAccessor;

import com.google.common.collect.Maps;

import net.minecraft.block.Block;

public enum StrippableBlockRegistry implements Block2BlockMap {
    INSTANCE;

    @Override
    public Block get(Block key) {
        return AxeItemAccessor.getStrippedBlocks().get(key);
    }

    @Override
    public void add(Block key, Block value) {
        AxeItemAccessor.getStrippedBlocks().put(key, value);
    }

    @Override
    public void remove(Block key) {
        AxeItemAccessor.getStrippedBlocks().remove(key);
    }

    static {
        AxeItemAccessor.setStrippedBlocks(Maps.newHashMap(AxeItemAccessor.getStrippedBlocks()));
    }
}
