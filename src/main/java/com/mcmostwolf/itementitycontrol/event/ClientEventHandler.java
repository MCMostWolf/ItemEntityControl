package com.mcmostwolf.itementitycontrol.event;

import com.mcmostwolf.itementitycontrol.ItemEntityControl;
import com.mcmostwolf.itementitycontrol.config.ConfigCommon;
import com.mcmostwolf.itementitycontrol.hotkey.ClearItemKeys;
import com.mcmostwolf.itementitycontrol.network.ChangeCleanMethodPacket;
import com.mcmostwolf.itementitycontrol.network.ClearItemEntityPacket;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;


@OnlyIn(Dist.CLIENT)
public class ClientEventHandler {
    private static int state = 0;
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void keyInputEvent(TickEvent.ClientTickEvent event) {
        if (ClearItemKeys.CLEAR_ITEM.get().consumeClick()) {
            ItemEntityControl.network.sendToServer(new ClearItemEntityPacket(state));
        }
        else if (ClearItemKeys.CHANGE_CLEAR_METHOD.get().consumeClick() && ConfigCommon.CleanAll.get()) {
            if (state == 0) {
                state = 1;
            }
            else {
                state = 0;
            }
            ItemEntityControl.network.sendToServer(new ChangeCleanMethodPacket(state));
        }
    }
}
