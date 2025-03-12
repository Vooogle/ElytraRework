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
}