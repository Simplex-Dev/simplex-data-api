package io.github.simplex.serialization.hooks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;

public interface Block2BlockStateMap {
    BlockState get(Block block);

    void add(Block block, BlockState state);

    void remove(Block block);
}
