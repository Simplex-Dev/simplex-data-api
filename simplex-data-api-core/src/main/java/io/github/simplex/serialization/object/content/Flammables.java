package io.github.simplex.serialization.object.content;

import java.util.List;

import io.github.simplex.serialization.util.IdentifiableCodecs;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.block.Block;

import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;

/**
 * An {@link ObjectHolder} for Flammables. A Flammable is termed
 * as a block that has a burn chance and spread chance attribute.
 * It stores a list of {@link Entry} and registers them to the
 * flammable block registry.
 *
 * @see FlammableBlockRegistry
 * @see Entry
 */
public class Flammables implements ObjectHolder {
    public static final Codec<Flammables> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.list(Entry.CODEC).optionalFieldOf("flammables", ImmutableList.of()).forGetter(Flammables::getFlammables)
    ).apply(instance, Flammables::new));

    private final List<Entry> flammables;

    public Flammables(List<Entry> flammables) {
        // Dfu loves immutable collections
        this.flammables = Lists.newArrayList(flammables);
    }

    public List<Entry> getFlammables() {
        return this.flammables;
    }

    public static Flammables getDefault() {
        return new Flammables(ImmutableList.of());
    }

    @Override
    public void addAll() {
        this.getFlammables().forEach(entry -> FlammableBlockRegistry.getDefaultInstance().add(entry.getBlock(), entry.getBurn(), entry.getSpread()));
    }

    @Override
    public void removeAll() {
        this.getFlammables().stream().map(Entry::getBlock).forEach(FlammableBlockRegistry.getDefaultInstance()::remove);
    }

    /**
     * Stores a block, burn chance and spread chance.
     */
    public static class Entry {
        public static final Codec<Entry> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                IdentifiableCodecs.BLOCK.fieldOf("block").forGetter(Entry::getBlock),
                Codec.INT.fieldOf("burn").forGetter(Entry::getBurn),
                Codec.INT.fieldOf("spread").forGetter(Entry::getSpread)
        ).apply(instance, Entry::new));

        private final Block block;
        private final int burn;
        private final int spread;

        public Entry(Block block, int burn, int spread) {
            this.block = block;
            this.burn = burn;
            this.spread = spread;
        }

        public Block getBlock() {
            return this.block;
        }

        public int getBurn() {
            return this.burn;
        }

        public int getSpread() {
            return this.spread;
        }
    }
}
