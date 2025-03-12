package org.vooogle.elytraRework.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.vooogle.elytraRework.ElytraRework

class Commands(private val plugin: ElytraRework) : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (args.isNotEmpty() && args[0].equals("reload", ignoreCase = true)) {
            plugin.getConfigManager().reloadConfig()
            sender.sendMessage("ElytraRework configuration reloaded.")
            return true
        }
        return false
    }
}