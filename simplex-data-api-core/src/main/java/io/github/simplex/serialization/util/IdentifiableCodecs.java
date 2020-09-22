package io.github.simplex.serialization.util;

import com.mojang.serialization.Codec;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public interface IdentifiableCodecs {
    Codec<Block> BLOCK = Identifier.CODEC.xmap(Registry.BLOCK::get, Registry.BLOCK::getId);
    Codec<Item> ITEM = Identifier.CODEC.xmap(Registry.ITEM::get, Registry.ITEM::getId);
}