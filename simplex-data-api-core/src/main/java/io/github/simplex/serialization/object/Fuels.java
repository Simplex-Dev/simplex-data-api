package io.github.simplex.serialization.object;

import java.util.Map;

import io.github.simplex.serialization.util.IdentifiableCodecs;
import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.item.Item;
import net.minecraft.item.Items;

import net.fabricmc.fabric.api.registry.FuelRegistry;

public class Fuels implements StorageObject {
    public static final Codec<Fuels> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.unboundedMap(IdentifiableCodecs.ITEM, Codec.INT).fieldOf("fuel").forGetter(Fuels::getMap)
    ).apply(instance, Fuels::new));

    private final Map<Item, Integer> map;

    Fuels(Map<Item, Integer> map) {
        this.map = map;
    }

    public Map<Item, Integer> getMap() {
        return this.map;
    }

    @Override
    public void addAll() {
        this.getMap().forEach(FuelRegistry.INSTANCE::add);
    }

    @Override
    public void removeAll() {
        this.getMap().keySet().forEach(FuelRegistry.INSTANCE::remove);
    }

    public static Fuels getDefault() {
        return new Fuels(ImmutableMap.of(
                Items.GUNPOWDER, 1200,
                Items.BLAZE_POWDER, 1200
        ));
    }
}