package io.github.simplex.serialization.nbt.server;

import io.github.simplex.serialization.command.SimplexData;
import io.github.simplex.serialization.nbt.NbtDeserializer;
import io.github.simplex.serialization.object.content.Flammables;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.command.argument.BlockStateArgumentType;
import net.minecraft.command.argument.ItemStackArgumentType;
import net.minecraft.item.Item;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class NbtCommand {
    public static void register() {
        SimplexData.CMD.then(literal("nbt").then(
                literal("add")
                        .then(
                                literal("compostables")
                                        .then(
                                                argument("item", ItemStackArgumentType.itemStack())
                                                        .then(
                                                                argument("chance", FloatArgumentType.floatArg(0, 1))
                                                                        .executes(ctx -> {
                                                                            Item item = ItemStackArgumentType.getItemStackArgument(ctx, "item").getItem();
                                                                            Float chance = FloatArgumentType.getFloat(ctx, "chance");
                                                                            NbtDeserializer.getRegistry().getCompostables().getMap().put(item, chance);
                                                                            NbtDeserializer.getInstance().serializeQuietly();
                                                                            return 1;
                                                                        })
                                                        )
                                        )
                        )
                        .then(
                                literal("flammables")
                                        .then(
                                                argument("block", BlockStateArgumentType.blockState())
                                                        .then(
                                                                argument("burn", IntegerArgumentType.integer(0))
                                                                        .then(
                                                                                argument("spread", IntegerArgumentType.integer(0))
                                                                                        .executes(ctx -> {
                                                                                            Block block = BlockStateArgumentType.getBlockState(ctx, "block").getBlockState().getBlock();
                                                                                            int burn = IntegerArgumentType.getInteger(ctx, "burn");
                                                                                            int spread = IntegerArgumentType.getInteger(ctx, "spread");
                                                                                            NbtDeserializer.getRegistry().getFlammables().getFlammables().add(new Flammables.Entry(block, burn, spread));
                                                                                            NbtDeserializer.getInstance().serializeQuietly();
                                                                                            return 1;
                                                                                        })
                                                                        )
                                                        )
                                        )
                        )
                        .then(
                                literal("fuels")
                                        .then(
                                                argument("item", ItemStackArgumentType.itemStack())
                                                        .then(
                                                                argument("time", IntegerArgumentType.integer(0, 32767))
                                                                        .executes(ctx -> {
                                                                            Item item = ItemStackArgumentType.getItemStackArgument(ctx, "item").getItem();
                                                                            int time = IntegerArgumentType.getInteger(ctx, "time");
                                                                            NbtDeserializer.getRegistry().getFuels().getMap().put(item, time);
                                                                            NbtDeserializer.getInstance().serializeQuietly();
                                                                            return 1;
                                                                        })
                                                        )
                                        )
                        )
                        .then(
                                literal("paveables")
                                        .then(
                                                argument("from", BlockStateArgumentType.blockState())
                                                        .then(
                                                                argument("to", BlockStateArgumentType.blockState())
                                                                        .executes(ctx -> {
                                                                            Block from = BlockStateArgumentType.getBlockState(ctx, "from").getBlockState().getBlock();
                                                                            BlockState to = BlockStateArgumentType.getBlockState(ctx, "to").getBlockState();
                                                                            NbtDeserializer.getRegistry().getPaveables().getMap().put(from, to);
                                                                            NbtDeserializer.getInstance().serializeQuietly();
                                                                            NbtDeserializer.getInstance().deserializeQuietly();
                                                                            return 1;
                                                                        })
                                                        )
                                        )
                        )
                        .then(
                                literal("strippables")
                                        .then(
                                                argument("from", BlockStateArgumentType.blockState())
                                                        .then(
                                                                argument("to", BlockStateArgumentType.blockState())
                                                                        .executes(ctx -> {
                                                                            Block from = BlockStateArgumentType.getBlockState(ctx, "from").getBlockState().getBlock();
                                                                            Block to = BlockStateArgumentType.getBlockState(ctx, "to").getBlockState().getBlock();
                                                                            NbtDeserializer.getRegistry().getStrippables().getMap().put(from, to);
                                                                            NbtDeserializer.getInstance().serializeQuietly();
                                                                            NbtDeserializer.getInstance().deserializeQuietly();
                                                                            return 1;
                                                                        })
                                                        )
                                        )
                        )
                        .then(
                                literal("tillables")
                                        .then(
                                                argument("from", BlockStateArgumentType.blockState())
                                                        .then(
                                                                argument("to", BlockStateArgumentType.blockState())
                                                                        .executes(ctx -> {
                                                                            Block from = BlockStateArgumentType.getBlockState(ctx, "from").getBlockState().getBlock();
                                                                            BlockState to = BlockStateArgumentType.getBlockState(ctx, "to").getBlockState();
                                                                            NbtDeserializer.getRegistry().getTillables().getMap().put(from, to);
                                                                            NbtDeserializer.getInstance().serializeQuietly();
                                                                            NbtDeserializer.getInstance().deserializeQuietly();
                                                                            return 1;
                                                                        })
                                                        )
                                        )
                        )
        ).then(
                literal("remove")
                        .then(
                                literal("compostables")
                                        .then(
                                                argument("key", ItemStackArgumentType.itemStack())
                                                        .executes(ctx -> {
                                                            Item item = ItemStackArgumentType.getItemStackArgument(ctx, "key").getItem();
                                                            NbtDeserializer.getRegistry().getCompostables().getMap().remove(item);
                                                            NbtDeserializer.getInstance().serializeQuietly();
                                                            NbtDeserializer.getInstance().deserializeQuietly();
                                                            return 1;
                                                        })
                                        )
                        )
                        .then(
                                literal("flammables")
                                        .then(
                                                argument("key", BlockStateArgumentType.blockState())
                                                        .executes(ctx -> {
                                                            Block block = BlockStateArgumentType.getBlockState(ctx, "key").getBlockState().getBlock();
                                                            NbtDeserializer.getRegistry().getFlammables().getFlammables().removeIf(entry -> entry.getBlock() == block);
                                                            NbtDeserializer.getInstance().serializeQuietly();
                                                            NbtDeserializer.getInstance().deserializeQuietly();
                                                            return 1;
                                                        })
                                        )
                        )
                        .then(
                                literal("fuels")
                                        .then(
                                                argument("key", ItemStackArgumentType.itemStack())
                                                        .executes(ctx -> {
                                                            Item item = ItemStackArgumentType.getItemStackArgument(ctx, "key").getItem();
                                                            NbtDeserializer.getRegistry().getFuels().getMap().remove(item);
                                                            NbtDeserializer.getInstance().serializeQuietly();
                                                            NbtDeserializer.getInstance().deserializeQuietly();
                                                            return 1;
                                                        })
                                        )
                        )
                        .then(
                                literal("paveables")
                                        .then(
                                                argument("key", BlockStateArgumentType.blockState())
                                                        .executes(ctx -> {
                                                            Block from = BlockStateArgumentType.getBlockState(ctx, "key").getBlockState().getBlock();
                                                            NbtDeserializer.getRegistry().getPaveables().getMap().remove(from);
                                                            NbtDeserializer.getInstance().serializeQuietly();
                                                            NbtDeserializer.getInstance().deserializeQuietly();
                                                            return 1;
                                                        })
                                        )
                        )
                        .then(
                                literal("strippables")
                                        .then(
                                                argument("key", BlockStateArgumentType.blockState())
                                                        .executes(ctx -> {
                                                            Block from = BlockStateArgumentType.getBlockState(ctx, "key").getBlockState().getBlock();
                                                            NbtDeserializer.getRegistry().getStrippables().getMap().remove(from);
                                                            NbtDeserializer.getInstance().serializeQuietly();
                                                            NbtDeserializer.getInstance().deserializeQuietly();
                                                            return 1;
                                                        })
                                        )
                        )
                        .then(
                                literal("tillables")
                                        .then(
                                                argument("key", BlockStateArgumentType.blockState())
                                                        .executes(ctx -> {
                                                            Block from = BlockStateArgumentType.getBlockState(ctx, "key").getBlockState().getBlock();
                                                            NbtDeserializer.getRegistry().getTillables().getMap().remove(from);
                                                            NbtDeserializer.getInstance().serializeQuietly();
                                                            NbtDeserializer.getInstance().deserializeQuietly();
                                                            return 1;
                                                        })
                                        )
                        )
        ));
    }
}
