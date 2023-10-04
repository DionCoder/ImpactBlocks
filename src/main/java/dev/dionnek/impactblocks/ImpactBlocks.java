package dev.dionnek.impactblocks;

import dev.dionnek.impactblocks.listener.ImpactBlocksListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class ImpactBlocks extends JavaPlugin {
    public static ImpactBlocks instance;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new ImpactBlocksListener(this), this);
    }

    @Override
    public void onDisable() {
        reloadConfig();
    }
}
