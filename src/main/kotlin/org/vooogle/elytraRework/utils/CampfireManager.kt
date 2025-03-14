package org.vooogle.elytraRework.utils

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.block.Block
import org.bukkit.block.data.type.Campfire
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.Vector
import org.vooogle.elytraRework.ElytraRework
import java.util.UUID

class CampfireManager(private val plugin: JavaPlugin) : Listener {

    private val cooldowns = mutableMapOf<UUID, Long>()

    @EventHandler
    fun onPlayerMove(event: PlayerMoveEvent) {
        val player = event.player
        if (player.isGliding) {
            checkCampfireBoost(player)
        }
    }

    private fun checkCampfireBoost(player: Player): Boolean {
        val configManager = (plugin as? ElytraRework)?.getConfigManager() ?: return false
        val debugMode = configManager.getConfig().getBoolean("debug-mode", false)

        if (!configManager.isCampfireBoostEnabled()) {
            if (debugMode) Bukkit.getLogger().info("Campfire boost is disabled in config.")
            return false
        }

        val playerLocation = player.location
        for (y in 1..24) {
            val blockBelow = playerLocation.clone().add(0.0, -y.toDouble(), 0.0).block
            if (debugMode) Bukkit.getLogger().info("Checking block at y-offset: $y, block type: ${blockBelow.type}")
            if (blockBelow.type == Material.CAMPFIRE || blockBelow.type == Material.SOUL_CAMPFIRE) {
                val campfireData = blockBelow.blockData as? Campfire ?: continue
                if (campfireData.isLit) {
                    val maxDistance = if (campfireData.isSignalFire) 24 else 10
                    if (y <= maxDistance && isUnobstructed(playerLocation.block, blockBelow)) {
                        val currentTime = System.currentTimeMillis()
                        val lastBoostTime = cooldowns[player.uniqueId] ?: 0
                        val cooldown = configManager.getConfig().getInt("campfire-boost-delay", 1) * 1000L

                        if (currentTime - lastBoostTime >= cooldown) {
                            val boostAmount = if (campfireData.isSignalFire) {
                                configManager.getSignalCampfireBoostAmount()
                            } else {
                                configManager.getCampfireBoostAmount()
                            }
                            applyBoost(player, boostAmount)
                            cooldowns[player.uniqueId] = currentTime
                            if (debugMode) {
                                Bukkit.getLogger().info("Campfire boost applied with amount: $boostAmount")
                            }
                            return true
                        } else if (debugMode) {
                            Bukkit.getLogger().info("Campfire boost on cooldown for player: ${player.name}")
                        }
                    }
                }
            }
        }
        return false
    }

    private fun isUnobstructed(start: Block, end: Block): Boolean {
        val direction = end.location.toVector().subtract(start.location.toVector()).normalize()
        val currentLocation = start.location.clone()
        while (currentLocation.y > end.location.y) {
            currentLocation.add(direction)
            val blockType = currentLocation.block.type
            if (!blockType.isAir && blockType != Material.CAMPFIRE && blockType != Material.SOUL_CAMPFIRE) {
                return false
            }
        }
        return true
    }

    private fun applyBoost(player: Player, boostAmount: Int) {
        val boost = Vector(0.0, boostAmount.toDouble(), 0.0)
        player.velocity = player.velocity.add(boost)

        val configManager = (plugin as? ElytraRework)?.getConfigManager() ?: return
        val sound = configManager.getConfig().getString("campfire-boost-sound.sound", "minecraft:entity.breeze.land") ?: "ENTITY_BREEZE_LAND"
        val volume = configManager.getConfig().getDouble("campfire-boost-sound.volume", 1.0)
        val pitch = configManager.getConfig().getDouble("campfire-boost-sound.pitch", 0.8)

        player.playSound(player.location, Sound.valueOf(sound), volume.toFloat(), pitch.toFloat())
    }
}