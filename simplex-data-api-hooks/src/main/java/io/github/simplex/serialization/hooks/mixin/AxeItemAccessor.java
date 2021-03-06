package io.github.simplex.serialization.hooks.mixin;

import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.block.Block;
import net.minecraft.item.AxeItem;

@Mixin(AxeItem.class)
public interface AxeItemAccessor {
    @Accessor("STRIPPED_BLOCKS")
    static Map<Block, Block> getStrippedBlocks() {
        throw new AssertionError();
    }

    @Accessor("STRIPPED_BLOCKS")
    static void setStrippedBlocks(Map<Block, Block> map) {
        throw new AssertionError();
    }
}
