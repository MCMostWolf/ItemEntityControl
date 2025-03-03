package com.mcmostwolf.itementitycontrol.event;

import com.mcmostwolf.itementitycontrol.config.ConfigCommon;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityLeaveLevelEvent;
import net.minecraftforge.event.entity.item.ItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

import static com.mcmostwolf.itementitycontrol.ItemEntityControl.startTime;
import static com.mojang.text2speech.Narrator.LOGGER;


public class EventHandler {
    int count = 0;
    long durationInMillis = 1000L; // 1秒等于1000毫秒
    @SubscribeEvent
    public void setItemEntity(ItemEvent event) {
        ItemEntity itemEntity = event.getEntity();
        if (ConfigCommon.InvulnerableItem.get()) {
            if (ConfigCommon.WhitelistItems.get().contains(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(itemEntity.getItem().getItem())).toString())) {
                itemEntity.setInvulnerable(true);
            }
        }
        else {
            if (ConfigCommon.BlacklistItems.get().contains(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(itemEntity.getItem().getItem())).toString())) {
                itemEntity.setInvulnerable(false);
            }
        }
        LOGGER.info(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(itemEntity.getItem().getItem())).toString());
    }

    @SubscribeEvent
    public void voidProtect(EntityLeaveLevelEvent event) {
        Level level = event.getLevel();
        if (event.getEntity() instanceof ItemEntity itemEntity) {
            if (itemEntity.getY() < level.getMinBuildHeight()) {
                ItemEntity newItemEntity = new ItemEntity(level, itemEntity.getX(), level.getMinBuildHeight()+0.5f, itemEntity.getZ(), itemEntity.getItem());
                ItemEntity theNew = newItemEntity.spawnAtLocation(newItemEntity.getItem());
                if (theNew != null) {
                    theNew.setNoGravity(true);
                }
            }
        }
    }

    @SubscribeEvent
    public void clearSchedule(TickEvent.LevelTickEvent event) {
        if (event.level.getServer() != null) {
            if (ConfigCommon.ClearSchedule.get() && ((System.currentTimeMillis() - startTime) > ((ConfigCommon.CleanTime.get() - 5) * durationInMillis))) {
                if ((System.currentTimeMillis() - startTime) < ((ConfigCommon.CleanTime.get() - 4) * durationInMillis) && count == 0) {
                    event.level.getServer().getCommands().performPrefixedCommand(event.level.getServer().createCommandSourceStack(), "say " + Component.translatable("message.itementitycontrol.before_clean").getString());
                    count = 1;
                } else if ((System.currentTimeMillis() - startTime) > (ConfigCommon.CleanTime.get() * durationInMillis)) {
                    startTime = System.currentTimeMillis();
                    event.level.getServer().getCommands().performPrefixedCommand(event.level.getServer().createCommandSourceStack(), "kill @e[type=item]");
                    event.level.getServer().getCommands().performPrefixedCommand(event.level.getServer().createCommandSourceStack(), "say " + Component.translatable("message.itementitycontrol.after_clean").getString());
                    count = 0;
                }
            }
        }
    }
}
