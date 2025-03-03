package com.mcmostwolf.itementitycontrol.config;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.List;

public class ConfigCommon {
    public static ForgeConfigSpec COMMON;
    public static ForgeConfigSpec.BooleanValue InvulnerableItem;
    public static ForgeConfigSpec.BooleanValue CleanAll;
    public static ForgeConfigSpec.BooleanValue ClearSchedule;
    public static ForgeConfigSpec.IntValue CleanTime;
    public static ForgeConfigSpec.ConfigValue<List<String>> WhitelistItems;
    public static ForgeConfigSpec.ConfigValue<List<String>> BlacklistItems;

    static {
        ForgeConfigSpec.Builder CONFIG_BUILDER = new ForgeConfigSpec.Builder();
        CONFIG_BUILDER.comment("Common Configs");
        CONFIG_BUILDER.push("common");
        InvulnerableItem = CONFIG_BUILDER.comment("If true, use whitelist, if false, use blacklist")
                .define("InvulnerableItem", true);
        WhitelistItems = CONFIG_BUILDER.comment("The items in the list are Invulnerable")
                .define("WhitelistItems", new ArrayList<>());
        BlacklistItems = CONFIG_BUILDER.comment("The items in the list are not Invulnerable")
                .define("BlacklistItems", new ArrayList<>());
        CleanAll = CONFIG_BUILDER.comment("If true, you could clean all items")
                .define("CleanAll", false);
        ClearSchedule = CONFIG_BUILDER.comment("If true, clear items scheduled")
                .define("ClearSchedule", true);
        CleanTime = CONFIG_BUILDER.comment("Clean time")
                .defineInRange("CleanTime", 20, 0, Integer.MAX_VALUE);
        CONFIG_BUILDER.pop();
        COMMON = CONFIG_BUILDER.build();
    }
}
