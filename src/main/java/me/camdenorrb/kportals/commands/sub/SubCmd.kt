package me.camdenorrb.kportals.commands.sub

import me.camdenorrb.kportals.KPortals
import org.bukkit.command.CommandSender

/**
 * Created by camdenorrb on 3/20/17.
 */

abstract class SubCmd(val arg: String, val usage: String, val permission: String) {

	abstract fun execute(sender: CommandSender, kPortals: KPortals, args: MutableList<String>): Boolean

}