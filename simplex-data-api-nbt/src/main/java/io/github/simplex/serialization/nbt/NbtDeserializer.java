package io.github.simplex.serialization.nbt;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import io.github.simplex.serialization.AbstractDeserializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.fabricmc.loader.api.FabricLoader;

public class NbtDeserializer extends AbstractDeserializer {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Path CONFIG_DAT_PATH = FabricLoader.getInstance().getConfigDir().resolve("simplex-data-api-nbt.dat");

    @Override
    public void serialize() throws IOException {

    }

    @Override
    public void deserialize() throws IOException {

    }

    @Override
    protected void check() throws IOException {
        if (Files.isDirectory(CONFIG_DAT_PATH)) {
            Files.delete(CONFIG_DAT_PATH);
        }
        if (!Files.exists(CONFIG_DAT_PATH)) {
            Files.createFile(CONFIG_DAT_PATH);
        }
    }

    @Override
    public void onInitialize() {

    }
}
