package io.github.simplex.serialization.object.content;

import java.util.Map;

import io.github.simplex.serialization.util.IdentifiableCodecs;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.item.Item;
import net.minecraft.item.Items;

import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;

public class Compostables implements ObjectHolder {
    public static final Codec<Compostables> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.unboundedMap(IdentifiableCodecs.ITEM, Codec.FLOAT).optionalFieldOf("compostables", ImmutableMap.of()).forGetter(Compostables::getMap)
    ).apply(instance, Compostables::new));
    private final Map<Item, Float> map;

    public Compostables(Map<Item, Float> map) {
        this.map = Maps.newHashMap(map);
    }

    public Map<Item, Float> getMap() {
        return this.map;
    }

    @Override
    public void addAll() {
        this.getMap().forEach(CompostingChanceRegistry.INSTANCE::add);
    }

    @Override
    public void removeAll() {
        this.getMap().keySet().forEach(CompostingChanceRegistry.INSTANCE::remove);
    }

    public static Compostables getDefault() {
        return new Compostables(
                ImmutableMap.of(
                        Items.POISONOUS_POTATO, 0.1F,
                        Items.DIRT, 0.25F,
                        Items.GRASS_BLOCK, 0.25F
                )
        );
    }
}