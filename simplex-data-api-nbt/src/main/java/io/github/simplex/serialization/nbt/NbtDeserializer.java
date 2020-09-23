package io.github.simplex.serialization.nbt;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Consumer;

import io.github.simplex.serialization.AbstractDeserializer;
import io.github.simplex.serialization.hooks.mixin.MinecraftServerAccessor;
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
import net.minecraft.server.MinecraftServer;

import net.fabricmc.loader.api.FabricLoader;

public class NbtDeserializer extends AbstractDeserializer {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Consumer<String> STDERR = System.err::println;
    private static final ObjectsHolder REGISTRY = new ObjectsHolder();
    private static NbtDeserializer instance;
    private static Path configDatPath = FabricLoader.getInstance().getConfigDir().resolve("simplex-data-api-nbt.dat");

    {instance = this;}

    public static NbtDeserializer getInstance() {
        return instance;
    }

    public static ObjectsHolder getRegistry() {
        return REGISTRY;
    }

    @Override
    public void serialize() throws IOException {
        REGISTRY.removeAll();
        REGISTRY.addAll();
        this.check();
        CompoundTag all = new CompoundTag();
        System.out.println(((CompoundTag) Compostables.CODEC.encodeStart(NbtOps.INSTANCE, REGISTRY.getCompostables()).getOrThrow(false, STDERR)).getCompound("compostables").toString());
        all.put("compostables", ((CompoundTag) Compostables.CODEC.encodeStart(NbtOps.INSTANCE, REGISTRY.getCompostables()).getOrThrow(false, STDERR)).getCompound("compostables"));
        all.put("flammables", ((CompoundTag) Flammables.CODEC.encodeStart(NbtOps.INSTANCE, REGISTRY.getFlammables()).getOrThrow(false, STDERR)).getCompound("flammables"));
        all.put("fuels", ((CompoundTag) Fuels.CODEC.encodeStart(NbtOps.INSTANCE, REGISTRY.getFuels()).getOrThrow(false, STDERR)).getCompound("fuels"));
        all.put("paveables", ((CompoundTag) Paveables.CODEC.encodeStart(NbtOps.INSTANCE, REGISTRY.getPaveables()).getOrThrow(false, STDERR)).getCompound("paveables"));
        all.put("strippables", ((CompoundTag) Strippables.CODEC.encodeStart(NbtOps.INSTANCE, REGISTRY.getStrippables()).getOrThrow(false, STDERR)).getCompound("strippables"));
        all.put("tillables", ((CompoundTag) Tillables.CODEC.encodeStart(NbtOps.INSTANCE, REGISTRY.getTillables()).getOrThrow(false, STDERR)).getCompound("tillables"));
        NbtIo.writeCompressed(all, configDatPath.toFile());
    }

    @Override
    public void deserialize() throws IOException {
        this.check();
        CompoundTag all = NbtIo.readCompressed(configDatPath.toFile());

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
        if (Files.isDirectory(configDatPath)) {
            Files.delete(configDatPath);
        }
        if (!Files.exists(configDatPath)) {
            Files.createFile(configDatPath);
            NbtIo.writeCompressed(new CompoundTag(), configDatPath.toFile());
        }
    }

    @Override
    public void onServerStarting(MinecraftServer server) {
        configDatPath = ((MinecraftServerAccessor) server).getSession().getWorldDirectory(server.getOverworld().getRegistryKey()).toPath();
        LOGGER.info("Registering Nbt Deserializer!");
        NbtCommand.register();
        this.deserializeQuietly();
        this.serializeQuietly();
    }

    @Override
    public void onServerStopping(MinecraftServer minecraftServer) {
        LOGGER.info("Unregistering Nbt Deserializer!");
        this.serializeQuietly();
        REGISTRY.removeAll();
    }
}
