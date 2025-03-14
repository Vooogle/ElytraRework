package org.vooogle.elytraRework.utils

import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.io.IOException

class ConfigManager(private val plugin: JavaPlugin) {

    private lateinit var configFile: File
    private lateinit var config: FileConfiguration

    fun setup() {
        configFile = File(plugin.dataFolder, "config.yml")
        if (!configFile.exists()) {
            plugin.saveResource("config.yml", false)
        }
        config = YamlConfiguration.loadConfiguration(configFile)
        checkConfigVersion()
    }

    fun getConfig(): FileConfiguration {
        return config
    }

    fun saveConfig() {
        try {
            config.save(configFile)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun reloadConfig() {
        config = YamlConfiguration.loadConfiguration(configFile)
        checkConfigVersion()
    }

    private fun checkConfigVersion() {
        val currentVersion = config.getInt("config-ver", 1)
        val latestVersion = 2

        if (currentVersion < latestVersion) {
            updateConfig(currentVersion)
            config.set("config-ver", latestVersion)
            saveConfig()
        }
    }

    private fun updateConfig(currentVersion: Int) {
        if (currentVersion < 2) {
            if (!config.contains("firework-disabled-message.enabled")) {
                config.set("firework-disabled-message.enabled", true)
            }
            if (!config.contains("firework-disabled-message.message")) {
                config.set("firework-disabled-message.message", "&cFirework Boosting is Disabled.")
            }
        }
    }

    fun isFireworkBoostEnabled(): Boolean {
        return config.getBoolean("firework-boost-enabled", false)
    }

    fun isCampfireBoostEnabled(): Boolean {
        return config.getBoolean("campfire-boost", false)
    }

    fun getCampfireBoostAmount(): Int {
        return config.getInt("campfire-boost-amount", 5)
    }

    fun getSignalCampfireBoostAmount(): Int {
        return config.getInt("signal-campfire-boost-amount", 5)
    }
    fun isMendingAllowed(): Boolean {
        return config.getBoolean("allow-mending", true)
    }

    fun isUnbreakingAllowed(): Boolean {
        return config.getBoolean("allow-unbreaking", true)
    }

    fun isCurseOfBindingAllowed(): Boolean {
        return config.getBoolean("allow-curse-of-binding", true)
    }

    fun isCurseOfVanishingAllowed(): Boolean {
        return config.getBoolean("allow-curse-of-vanishing", true)
    }
}