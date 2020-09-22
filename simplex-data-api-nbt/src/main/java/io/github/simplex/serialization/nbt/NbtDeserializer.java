package io.github.simplex.serialization.nbt;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Consumer;

import io.github.simplex.serialization.AbstractDeserializer;
import io.github.simplex.serialization.nbt.server.NbtCommand;
import io.github.simplex.serialization.object.content.Compostables;
import io.github.simplex.serialization.object.content.Flammables;
import io.github.simplex.serialization.object.content.Fuels;
import io.github.simplex.serialization.object.content.Paveables;
import io.github.simplex.serialization.object.content.Strippables;
import io.github.simplex.serialization.object.content.Tillables;
import io.github.simplex.serialization.util.ObjectsHolder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtOps;

import net.fabricmc.loader.api.FabricLoader;

public class NbtDeserializer extends AbstractDeserializer {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Path CONFIG_DAT_PATH = FabricLoader.getInstance().getConfigDir().resolve("simplex-data-api-nbt.dat");
    private static final Consumer<String> STDERR = System.err::println;
    private static final ObjectsHolder REGISTRY = new ObjectsHolder();
    private static NbtDeserializer instance;

    public static NbtDeserializer getInstance() {
        return instance;
    }

    public static ObjectsHolder getRegistry() {
        return REGISTRY;
    }

    @Override
    public void serialize() throws IOException {
        this.check();
        REGISTRY.removeAll();
        REGISTRY.addAll();
        CompoundTag all = new CompoundTag();
        all.put("compostables", ((CompoundTag) Compostables.CODEC.encodeStart(NbtOps.INSTANCE, REGISTRY.getCompostables()).getOrThrow(false, STDERR)));
        all.put("flammables", ((CompoundTag) Flammables.CODEC.encodeStart(NbtOps.INSTANCE, REGISTRY.getFlammables()).getOrThrow(false, STDERR)));
        all.put("fuels", ((CompoundTag) Fuels.CODEC.encodeStart(NbtOps.INSTANCE, REGISTRY.getFuels()).getOrThrow(false, STDERR)));
        all.put("paveables", ((CompoundTag) Paveables.CODEC.encodeStart(NbtOps.INSTANCE, REGISTRY.getPaveables()).getOrThrow(false, STDERR)));
        all.put("strippables", ((CompoundTag) Strippables.CODEC.encodeStart(NbtOps.INSTANCE, REGISTRY.getStrippables()).getOrThrow(false, STDERR)));
        all.put("tillables", ((CompoundTag) Tillables.CODEC.encodeStart(NbtOps.INSTANCE, REGISTRY.getTillables()).getOrThrow(false, STDERR)));
        NbtIo.writeCompressed(all, CONFIG_DAT_PATH.toFile());
    }

    @Override
    public void deserialize() throws IOException {
        this.check();
        CompoundTag all = NbtIo.readCompressed(CONFIG_DAT_PATH.toFile());

        if (!all.contains("compostables")) {
            all.put("compostables", ((CompoundTag) Compostables.CODEC.encodeStart(NbtOps.INSTANCE, Compostables.getDefault()).getOrThrow(false, STDERR)).get("compostables"));
        }
        Compostables compostables = Compostables.CODEC.decode(NbtOps.INSTANCE, all).getOrThrow(false, STDERR).getFirst();
        REGISTRY.setCompostables(compostables);

        if (!all.contains("flammables")) {
            all.put("flammables", ((CompoundTag)Flammables.CODEC.encodeStart(NbtOps.INSTANCE, Flammables.getDefault()).getOrThrow(false, STDERR)).get("flammables"));
        }
        Flammables flammables = Flammables.CODEC.decode(NbtOps.INSTANCE, all).getOrThrow(false, STDERR).getFirst();
        REGISTRY.setFlammables(flammables);

        if (!all.contains("fuels")) {
            all.put("fuels", ((CompoundTag)Fuels.CODEC.encodeStart(NbtOps.INSTANCE, Fuels.getDefault()).getOrThrow(false, STDERR)).get("fuels"));
        }
        Fuels fuels = Fuels.CODEC.decode(NbtOps.INSTANCE, all).getOrThrow(false, STDERR).getFirst();
        REGISTRY.setFuels(fuels);

        if (!all.contains("paveables")) {
            all.put("paveables", ((CompoundTag)Paveables.CODEC.encodeStart(NbtOps.INSTANCE, Paveables.getDefault()).getOrThrow(false, STDERR)).get("paveables"));
        }
        Paveables paveables = Paveables.CODEC.decode(NbtOps.INSTANCE, all).getOrThrow(false, STDERR).getFirst();
        REGISTRY.setPaveables(paveables);

        if (!all.contains("strippables")) {
            all.put("strippables", ((CompoundTag)Strippables.CODEC.encodeStart(NbtOps.INSTANCE, Strippables.getDefault()).getOrThrow(false, STDERR)).get("strippables"));
        }
        Strippables strippables = Strippables.CODEC.decode(NbtOps.INSTANCE, all).getOrThrow(false, STDERR).getFirst();
        REGISTRY.setStrippables(strippables);

        if (!all.contains("tillables")) {
            all.put("tillables", ((CompoundTag)Tillables.CODEC.encodeStart(NbtOps.INSTANCE, Tillables.getDefault()).getOrThrow(false, STDERR)).get("tillables"));
        }
        Tillables tillables = Tillables.CODEC.decode(NbtOps.INSTANCE, all).getOrThrow(false, STDERR).getFirst();
        REGISTRY.setTillables(tillables);
    }

    @Override
    protected void check() throws IOException {
        if (Files.isDirectory(CONFIG_DAT_PATH)) {
            Files.delete(CONFIG_DAT_PATH);
        }
        if (!Files.exists(CONFIG_DAT_PATH)) {
            Files.createFile(CONFIG_DAT_PATH);
            NbtIo.writeCompressed(new CompoundTag(), CONFIG_DAT_PATH.toFile());
        }
    }

    @Override
    public void onInitialize() {
        LOGGER.info("Registering Nbt Deserializer!");
        NbtCommand.register();
        this.deserializeQuietly();
        this.serializeQuietly();
    }

    {
        instance = this;
    }
}
