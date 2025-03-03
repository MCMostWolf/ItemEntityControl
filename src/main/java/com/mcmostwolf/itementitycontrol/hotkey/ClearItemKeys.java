package com.mcmostwolf.itementitycontrol.hotkey;

import net.minecraft.client.KeyMapping;
import net.minecraftforge.common.util.Lazy;
import org.lwjgl.glfw.GLFW;

public class ClearItemKeys {
    public static final Lazy<KeyMapping> CLEAR_ITEM = Lazy.of(() -> new KeyMapping(
            "key.itementitycontrol.clearitem",
            GLFW.GLFW_KEY_H,
            "key.mcmostwolf.itementitycontrol"
    ));
    public static final Lazy<KeyMapping>  CHANGE_CLEAR_METHOD = Lazy.of(() -> new KeyMapping(
            "key.itementitycontrol.change_clear_method",
            GLFW.GLFW_KEY_DOWN,
            "key.mcmostwolf.itementitycontrol"
    ));
}
