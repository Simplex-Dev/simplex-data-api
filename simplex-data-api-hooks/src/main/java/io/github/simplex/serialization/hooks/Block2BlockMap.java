package io.github.simplex.serialization.hooks;

import net.minecraft.block.Block;

public interface Block2BlockMap {
    Block get(Block key);

    void add(Block key, Block value);

    void remove(Block key);
}
