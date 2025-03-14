package org.vooogle.elytraRework

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerItemHeldEvent
import org.bukkit.plugin.java.JavaPlugin
import org.vooogle.elytraRework.utils.CampfireManager
import org.vooogle.elytraRework.utils.ConfigManager
import org.vooogle.elytraRework.utils.FireworkManager
import org.vooogle.elytraRework.commands.Commands
import org.vooogle.elytraRework.listeners.EnchantmentListener
import org.vooogle.elytraRework.utils.EnchantmentManager

class ElytraRework : JavaPlugin(), Listener {

    private lateinit var configManager: ConfigManager
    private lateinit var enchantmentManager: EnchantmentManager

    override fun onEnable() {
        // Plugin startup logic
        configManager = ConfigManager(this)
        configManager.setup()

        enchantmentManager = EnchantmentManager(this)

        // Register event listeners
        server.pluginManager.registerEvents(CampfireManager(this), this)
        server.pluginManager.registerEvents(FireworkManager(this), this)
        server.pluginManager.registerEvents(EnchantmentListener(this), this)
        server.pluginManager.registerEvents(this, this)

        // Register commands
        this.getCommand("elytrarework")?.setExecutor(Commands(this))
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }

    fun getConfigManager(): ConfigManager {
        return configManager
    }

    fun getEnchantmentManager(): EnchantmentManager {
        return enchantmentManager
    }

    @EventHandler
    fun onPlayerItemHeld(event: PlayerItemHeldEvent) {
        val player = event.player
        val item = player.inventory.getItem(event.newSlot)
        if (item != null) {
            enchantmentManager.applyEnchantments(item)
        }
    }
}