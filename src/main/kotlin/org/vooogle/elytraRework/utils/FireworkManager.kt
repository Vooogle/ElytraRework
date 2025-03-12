package org.vooogle.elytraRework.utils

import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.plugin.java.JavaPlugin
import org.vooogle.elytraRework.ElytraRework

class FireworkManager(private val plugin: JavaPlugin) : Listener {

    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {
        val player = event.player
        val configManager = (plugin as? ElytraRework)?.getConfigManager() ?: return

        if (!configManager.isFireworkBoostEnabled()) {
            if (player.isGliding && player.inventory.itemInMainHand.type == Material.FIREWORK_ROCKET) {
                event.isCancelled = true
                player.sendMessage("Firework boosting is disabled.")
            }
        }
    }
}