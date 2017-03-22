package me.camdenorrb.kportals.portal

/**
 * Created by camdenorrb on 3/20/17.
 */

enum class PortalType {

	ConsoleCommand, PlayerCommand, Unknown, Random, Bungee, World;


	companion object {

		fun byName(name: String): PortalType? = values().find { name.equals(it.name, true) }

	}

}