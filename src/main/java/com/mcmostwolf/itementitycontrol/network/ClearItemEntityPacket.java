package com.mcmostwolf.itementitycontrol.network;

import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.OutgoingChatMessage;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

import static com.mojang.text2speech.Narrator.LOGGER;

public class ClearItemEntityPacket {
    private final int state;

    public ClearItemEntityPacket(Integer state) {
        this.state = state;
    }

    public ClearItemEntityPacket(FriendlyByteBuf buf) {
        state = buf.readInt();
    }
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(state);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer sender = ctx.get().getSender();
            if (sender != null) {
                LOGGER.info("发送者没问题");
                MinecraftServer server = sender.server;
                if (state == 1) {
                    server.getCommands().performPrefixedCommand(server.createCommandSourceStack(), "kill @e[type=minecraft:item]");
                    if (ChatFormatting.getById(ChatFormatting.PREFIX_CODE) != null) {
                        sender.displayClientMessage(Component.translatable("message.itementitycontrol.cleaned_global").withStyle(ChatFormatting.getById(ChatFormatting.PREFIX_CODE)), true);
                    }
                    else {
                        sender.displayClientMessage(Component.translatable("message.itementitycontrol.cleaned_global").withStyle(ChatFormatting.GOLD), true);
                    }
                    sender.sendChatMessage(OutgoingChatMessage.create(PlayerChatMessage.unsigned(sender.getUUID(), Component.translatable("message.itementitycontrol.cleaned_global_say").getString())), true, ChatType.bind(ChatType.CHAT, sender));
                }
                else if (state == 0) {
                    server.getCommands().performPrefixedCommand(server.createCommandSourceStack(), "execute at " + sender.getName().getString() + " run kill @e[type=minecraft:item, distance=..64]");
                    if (ChatFormatting.getById(ChatFormatting.PREFIX_CODE) != null) {
                        sender.displayClientMessage(Component.translatable("message.itementitycontrol.cleaned_unglobal").withStyle(ChatFormatting.getById(ChatFormatting.PREFIX_CODE)), true);
                    }
                    else {
                        sender.displayClientMessage(Component.translatable("message.itementitycontrol.cleaned_unglobal").withStyle(ChatFormatting.GOLD), true);
                    }
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
