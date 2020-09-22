package io.github.simplex.serialization.command;

import io.github.simplex.serialization.AbstractDeserializer;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import net.minecraft.server.command.ServerCommandSource;

import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;
import static net.minecraft.server.command.CommandManager.literal;

public final class SimplexData {
    public static final LiteralArgumentBuilder<ServerCommandSource> CMD = literal("simplexdata").requires(source -> source.hasPermissionLevel(2));

    public static void initialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> dispatcher.register(CMD));
        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            FabricLoader.getInstance().getEntrypoints("simplex-data-api", AbstractDeserializer.class).forEach(d -> d.onServerStarting(server));
        });
    }
}
