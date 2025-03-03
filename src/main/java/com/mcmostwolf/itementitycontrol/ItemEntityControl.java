package com.mcmostwolf.itementitycontrol;

import com.mcmostwolf.itementitycontrol.config.ConfigCommon;
import com.mcmostwolf.itementitycontrol.event.ClientEventHandler;
import com.mcmostwolf.itementitycontrol.event.EventHandler;
import com.mcmostwolf.itementitycontrol.hotkey.ClearItemKeys;
import com.mcmostwolf.itementitycontrol.network.ChangeCleanMethodPacket;
import com.mcmostwolf.itementitycontrol.network.ClearItemEntityPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Mod(ItemEntityControl.MODID)
public class ItemEntityControl {
    public static final String MODID = "itementitycontrol";
    public static SimpleChannel network;
    public static long startTime = System.currentTimeMillis();
    public static final Logger LOGGER = LogManager.getLogger(MODID);
    public ItemEntityControl() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        network = NetworkRegistry.newSimpleChannel(new ResourceLocation(MODID, MODID), () -> "1.0", s -> true, s -> true);
        eventBus.register(this);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onClientSetup));

        //Client to Server
        network.registerMessage(0, ClearItemEntityPacket.class, ClearItemEntityPacket::toBytes, ClearItemEntityPacket::new, ClearItemEntityPacket::handle);
        network.registerMessage(1, ChangeCleanMethodPacket.class, ChangeCleanMethodPacket::toBytes, ChangeCleanMethodPacket::new, ChangeCleanMethodPacket::handle);

        MinecraftForge.EVENT_BUS.register(new EventHandler());

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigCommon.COMMON);
    }
    @OnlyIn(Dist.CLIENT)
    private void onClientSetup(FMLClientSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
        Minecraft.getInstance().options.keyMappings = ArrayUtils.add(Minecraft.getInstance().options.keyMappings, ClearItemKeys.CLEAR_ITEM.get());
        Minecraft.getInstance().options.keyMappings = ArrayUtils.add(Minecraft.getInstance().options.keyMappings, ClearItemKeys.CHANGE_CLEAR_METHOD.get());
    }
}
