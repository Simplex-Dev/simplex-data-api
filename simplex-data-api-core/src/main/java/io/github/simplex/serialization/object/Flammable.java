package io.github.simplex.serialization.object;

import java.util.List;

import io.github.simplex.serialization.util.IdentifiableCodecs;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.block.Block;

import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;

public class Flammable implements Initializable {
    public static final Codec<Flammable> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.list(Entry.CODEC).fieldOf("flammables").forGetter(Flammable::getFlammables)
    ).apply(instance, Flammable::new));

    private final List<Entry> flammables;

    public Flammable(List<Entry> flammables) {
        // Dfu loves immutable collections
        this.flammables = Lists.newArrayList(flammables);
    }

    public List<Entry> getFlammables() {
        return this.flammables;
    }

    public static Flammable getDefault() {
        return new Flammable(ImmutableList.of());
    }

    @Override
    public void initialize() {
        for (Entry entry : this.getFlammables()) {
            FlammableBlockRegistry.getDefaultInstance().add(entry.getBlock(), entry.getBurn(), entry.getSpread());
        }
    }

    @Override
    public void removeAll() {
        this.getFlammables().stream().map(Entry::getBlock).forEach(FlammableBlockRegistry.getDefaultInstance()::remove);
    }

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