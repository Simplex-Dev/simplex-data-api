package io.github.simplex.serialization;

import java.io.IOException;

import net.fabricmc.api.ModInitializer;

public abstract class AbstractDeserializer implements ModInitializer {
    public abstract void serialize() throws IOException;

    public void serializeQuietly() {
        try {
            this.serialize();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public abstract void deserialize() throws IOException;

    public void deserializeQuietly() {
        try {
            this.deserialize();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected abstract void check() throws IOException;

    protected void writeDefaultData() {
    }
}