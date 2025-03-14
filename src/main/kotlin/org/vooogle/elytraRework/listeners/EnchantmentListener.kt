package org.vooogle.elytraRework.listeners

import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.enchantment.EnchantItemEvent
import org.bukkit.event.inventory.PrepareAnvilEvent
import org.bukkit.inventory.AnvilInventory
import org.vooogle.elytraRework.ElytraRework

class EnchantmentListener(private val plugin: ElytraRework) : Listener {

    @EventHandler
    fun onEnchantItem(event: EnchantItemEvent) {
        val item = event.item
        if (item.type == Material.ELYTRA) {
            val configManager = plugin.getConfigManager()
            val enchantments = event.enchantsToAdd

            if (!configManager.isMendingAllowed() && enchantments.containsKey(Enchantment.MENDING)) {
                enchantments.remove(Enchantment.MENDING)
            }
            if (!configManager.isUnbreakingAllowed() && enchantments.containsKey(Enchantment.UNBREAKING)) {
                enchantments.remove(Enchantment.UNBREAKING)
            }
            if (!configManager.isCurseOfBindingAllowed() && enchantments.containsKey(Enchantment.BINDING_CURSE)) {
                enchantments.remove(Enchantment.BINDING_CURSE)
            }
            if (!configManager.isCurseOfVanishingAllowed() && enchantments.containsKey(Enchantment.VANISHING_CURSE)) {
                enchantments.remove(Enchantment.VANISHING_CURSE)
            }
        }
    }

    @EventHandler
    fun onPrepareAnvil(event: PrepareAnvilEvent) {
        val inventory = event.inventory as AnvilInventory
        val item = inventory.result ?: return
        if (item.type == Material.ELYTRA) {
            val configManager = plugin.getConfigManager()
            val enchantments = item.enchantments

            if (!configManager.isMendingAllowed() && enchantments.containsKey(Enchantment.MENDING)) {
                item.removeEnchantment(Enchantment.MENDING)
            }
            if (!configManager.isUnbreakingAllowed() && enchantments.containsKey(Enchantment.UNBREAKING)) {
                item.removeEnchantment(Enchantment.UNBREAKING)
            }
            if (!configManager.isCurseOfBindingAllowed() && enchantments.containsKey(Enchantment.BINDING_CURSE)) {
                item.removeEnchantment(Enchantment.BINDING_CURSE)
            }
            if (!configManager.isCurseOfVanishingAllowed() && enchantments.containsKey(Enchantment.VANISHING_CURSE)) {
                item.removeEnchantment(Enchantment.VANISHING_CURSE)
            }
        }
    }
}