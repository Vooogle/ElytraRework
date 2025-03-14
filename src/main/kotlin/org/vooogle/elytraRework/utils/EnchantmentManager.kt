package org.vooogle.elytraRework.utils

import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack
import org.vooogle.elytraRework.ElytraRework

class EnchantmentManager(private val plugin: ElytraRework) {

    fun applyEnchantments(item: ItemStack) {
        val configManager = plugin.getConfigManager()

        if (item.type == Material.ELYTRA) {
            if (!configManager.isMendingAllowed() && item.containsEnchantment(Enchantment.MENDING)) {
                item.removeEnchantment(Enchantment.MENDING)
            }
            if (!configManager.isUnbreakingAllowed() && item.containsEnchantment(Enchantment.UNBREAKING)) {
                item.removeEnchantment(Enchantment.UNBREAKING)
            }
            if (!configManager.isCurseOfBindingAllowed() && item.containsEnchantment(Enchantment.BINDING_CURSE)) {
                item.removeEnchantment(Enchantment.BINDING_CURSE)
            }
            if (!configManager.isCurseOfVanishingAllowed() && item.containsEnchantment(Enchantment.VANISHING_CURSE)) {
                item.removeEnchantment(Enchantment.VANISHING_CURSE)
            }
        }
    }
}