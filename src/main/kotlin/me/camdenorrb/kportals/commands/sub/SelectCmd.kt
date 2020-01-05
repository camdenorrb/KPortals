package me.camdenorrb.kportals.commands.sub

import me.camdenorrb.kportals.KPortals
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class SelectCmd : SubCmd("-select", "/Portal -select", "kportals.select") {

    override fun execute(sender: CommandSender, plugin: KPortals, args: List<String>): Boolean {

        if (sender !is Player) {
            sender.sendMessage("${ChatColor.RED}You must be a player to select a portal!")
            return true
        }

        if (sender.inventory.addItem(plugin.selectionItem).isNotEmpty()) {
            sender.sendMessage("${ChatColor.RED}Couldn't fit the portal selection item in your inventory!")
        }
        else {
            sender.sendMessage("${ChatColor.GREEN}You have been given a portal selection item!")
        }

        return true
    }

}