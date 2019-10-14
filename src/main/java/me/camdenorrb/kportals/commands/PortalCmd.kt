package me.camdenorrb.kportals.commands

import me.camdenorrb.kportals.KPortals
import me.camdenorrb.kportals.messages.Messages
import org.bukkit.ChatColor.AQUA
import org.bukkit.ChatColor.RED
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor

/**
 * Created by camdenorrb on 3/20/17.
 */

class PortalCmd(val kPortals: KPortals) : TabExecutor {

	override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<out String>): List<String> {
		return kPortals.subCmds.map { it.arg }
	}

	override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<out String>): Boolean {

		if (args.isEmpty()) return { sender.sendMessage(Messages.help); true }()


		val arguments = args.toMutableList()

		val subCmdArg = arguments.removeAt(0)

		val subCmd = kPortals.subCmds.find { it.arg.equals(subCmdArg, true) }
				?: return { sender.sendMessage(Messages.help); true }()


		if (sender.hasPermission(subCmd.permission).not()) sender.sendMessage("${RED}You don't have the necessary permission node $AQUA\"${subCmd.permission}\"!")

		if (subCmd.execute(sender, kPortals, arguments).not()) sender.sendMessage("$RED${subCmd.usage}")

		return true
	}

}