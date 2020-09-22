package io.github.simplex.serialization.object;

import java.util.Map;

import io.github.simplex.serialization.util.IdentifiableCodecs;
import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.item.Item;
import net.minecraft.item.Items;

public class Fuel {
    public static final Codec<Fuel> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.unboundedMap(IdentifiableCodecs.ITEM, Codec.INT).fieldOf("fuel").forGetter(Fuel::getMap)
    ).apply(instance, Fuel::new));

    private final Map<Item, Integer> map;

    Fuel(Map<Item, Integer> map) {
        this.map = map;
    }

    public Map<Item, Integer> getMap() {
        return this.map;
    }

    public static Fuel getDefault() {
        return new Fuel(ImmutableMap.of(
                Items.GUNPOWDER, 1200,
                Items.BLAZE_POWDER, 1200
        ));
    }
}