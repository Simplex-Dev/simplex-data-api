package io.github.simplex.serialization.object;

import java.util.Map;

import io.github.simplex.serialization.util.IdentifiableCodecs;
import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.item.Item;
import net.minecraft.item.Items;

public class Compostable {
    public static final Codec<Compostable> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.unboundedMap(IdentifiableCodecs.ITEM, Codec.FLOAT).fieldOf("composting").forGetter(Compostable::getMap)
    ).apply(instance, Compostable::new));
    private final Map<Item, Float> map;

    public Compostable(Map<Item, Float> map) {
        this.map = map;
    }

    public Map<Item, Float> getMap() {
        return this.map;
    }

    public static Compostable getDefault() {
        return new Compostable(
                ImmutableMap.of(
                        Items.POISONOUS_POTATO, 0.1F,
                        Items.DIRT, 0.25F,
                        Items.GRASS_BLOCK, 0.25F
                )
        );
    }
}