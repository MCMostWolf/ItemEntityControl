package com.mcmostwolf.itementitycontrol.network;

import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

import static com.mojang.text2speech.Narrator.LOGGER;

public class ChangeCleanMethodPacket {
    private final int state;

    public ChangeCleanMethodPacket(Integer state) {
        this.state = state;
    }

    public ChangeCleanMethodPacket(FriendlyByteBuf buf) {
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
                if (state == 1) {
                    if (ChatFormatting.getById(ChatFormatting.PREFIX_CODE) != null) {
                        sender.displayClientMessage(Component.translatable("message.itementitycontrol.change_to_global").withStyle(ChatFormatting.getById(ChatFormatting.PREFIX_CODE)), true);
                    }
                    else {
                        sender.displayClientMessage(Component.translatable("message.itementitycontrol.change_to_global").withStyle(ChatFormatting.DARK_RED), true);
                    }
                }
                else if (state == 0) {
                    if (ChatFormatting.getById(ChatFormatting.PREFIX_CODE) != null) {
                        sender.displayClientMessage(Component.translatable("message.itementitycontrol.change_to_unglobal").withStyle(ChatFormatting.getById(ChatFormatting.PREFIX_CODE)), true);
                    }
                    else {
                        sender.displayClientMessage(Component.translatable("message.itementitycontrol.change_to_unglobal").withStyle(ChatFormatting.GOLD), true);
                    }
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
