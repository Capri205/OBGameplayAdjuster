package net.obmc.OBGameplayAdjuster;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class PlayerCommandListener implements CommandExecutor {

	static Logger log = Logger.getLogger("Minecraft");
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		// for now only op can use the command
		if (!sender.isOp()) {
			sender.sendMessage(ChatColor.RED + "Sorry, command is reserved for server operators.");
			return true;
		}

		// usage if no arguments passed
		if (args.length == 0) {
			Usage(sender);
			return true;
		}

		// process the command and any arguments
		if (command.getName().equalsIgnoreCase("gpa")) {
			switch (args[0].toLowerCase()) {
			
				// toggle deop function
				case "deop":
					OBGameplayAdjuster.setDoDeop(!OBGameplayAdjuster.getDoDeop());
					break;
				// list up current settings 
				case "list":
					sender.sendMessage(ChatColor.LIGHT_PURPLE + "Current settings for OBGameplayAdjuster:");
					sender.sendMessage(ChatColor.LIGHT_PURPLE + "  DeOp: " + OBGameplayAdjuster.getDoDeop());
			        break;
				default:
					Usage(sender);
					break;
			}
		}
		return true;
	}

    void Usage(CommandSender sender) {
        sender.sendMessage(ChatColor.LIGHT_PURPLE + "/gpa" + ChatColor.GOLD + " - Display this menu");
        sender.sendMessage(ChatColor.LIGHT_PURPLE + "/gpa deop" + ChatColor.GOLD + " - Toggle deop on join on and off");
        sender.sendMessage(ChatColor.LIGHT_PURPLE + "/gpa list" + ChatColor.GOLD + " - Show current settings");
    }
}
