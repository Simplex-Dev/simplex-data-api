package io.github.simplex.serialization.object.content;

import java.util.Map;

import io.github.simplex.serialization.util.IdentifiableCodecs;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.item.Item;
import net.minecraft.item.Items;

import net.fabricmc.fabric.api.registry.FuelRegistry;

/**
 * An {@link ObjectHolder} for Fuels. A Fuel is termed
 * as an item that can burn in a furnace for a specific time.
 * It stores a map of {@link Item} and {@link Integer} and registers
 * them to the fuel registry.
 *
 * @see FuelRegistry
 */
public class Fuels implements ObjectHolder {
    public static final Codec<Fuels> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.unboundedMap(IdentifiableCodecs.ITEM, Codec.INT).optionalFieldOf("fuels", ImmutableMap.of()).forGetter(Fuels::getMap)
    ).apply(instance, Fuels::new));

    private final Map<Item, Integer> map;

    Fuels(Map<Item, Integer> map) {
        this.map = Maps.newHashMap(map);
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