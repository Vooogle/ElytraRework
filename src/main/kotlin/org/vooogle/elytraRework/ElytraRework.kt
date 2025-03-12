package org.vooogle.elytraRework

import org.bukkit.plugin.java.JavaPlugin
import org.vooogle.elytraRework.utils.CampfireManager
import org.vooogle.elytraRework.utils.ConfigManager
import org.vooogle.elytraRework.utils.FireworkManager
import org.vooogle.elytraRework.commands.Commands

class ElytraRework : JavaPlugin() {

    private lateinit var configManager: ConfigManager

    override fun onEnable() {
        // Plugin startup logic
        configManager = ConfigManager(this)
        configManager.setup()

        // Register event listeners
        server.pluginManager.registerEvents(CampfireManager(this), this)
        server.pluginManager.registerEvents(FireworkManager(this), this)

        // Register commands
        this.getCommand("elytrarework")?.setExecutor(Commands(this))
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }

    fun getConfigManager(): ConfigManager {
        return configManager
    }
}