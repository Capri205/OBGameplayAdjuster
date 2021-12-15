package net.obmc.OBGameplayAdjuster;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class CommandListener implements CommandExecutor {

	static Logger log = Logger.getLogger("Minecraft");
	
	private String chatmsgprefix = null;
	private String logmsgprefix = null;
	
	public CommandListener() {
		chatmsgprefix = OBGameplayAdjuster.getInstance().getChatMsgPrefix();
		logmsgprefix = OBGameplayAdjuster.getInstance().getLogMsgPrefix();
	}
	
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
					OBGameplayAdjuster.getInstance().setDeop(!OBGameplayAdjuster.getInstance().getDeop());
					sender.sendMessage(chatmsgprefix + " DeOp is now " + ChatColor.GOLD + OBGameplayAdjuster.getInstance().getDeop());
					break;
				// toggle damage
				case "alldamage":
					OBGameplayAdjuster.getInstance().setStopAllDamage(!OBGameplayAdjuster.getInstance().getStopAllDamage());
					String msg = (OBGameplayAdjuster.getInstance().getStopAllDamage() ? "Stopping all" : "Allowing");
					sender.sendMessage(chatmsgprefix + msg + " damage to players");
					break;
				case "damage":
					if (args.length > 1) {
						String damagetype = args[1].toUpperCase();
						try {
							DamageCause.valueOf(damagetype);
							if (OBGameplayAdjuster.getDamageManager().toggle(damagetype)) {
								sender.sendMessage(chatmsgprefix + args[1] + " damage " + ChatColor.GOLD + "off");
							} else {
								sender.sendMessage(chatmsgprefix + args[1] + " damage " + ChatColor.GOLD + "on");
							}
						} catch (Exception e) {
							sender.sendMessage(chatmsgprefix + args[1] + " is an unknown damage type. use /gpa show");
						}
					} else {
						sender.sendMessage(chatmsgprefix + ChatColor.RED + " No damage type provided. use /gpa show");
					}
					break;
				// list up current settings 
				case "list":
					sender.sendMessage(chatmsgprefix + "Current settings:");
					sender.sendMessage(chatmsgprefix + "  deop: " + ChatColor.GOLD + OBGameplayAdjuster.getInstance().getDeop());
					sender.sendMessage(chatmsgprefix + "  forcesurvival: " + ChatColor.GOLD + OBGameplayAdjuster.getInstance().getForceSurvival());
					sender.sendMessage(chatmsgprefix + "  alldamage: " + ChatColor.GOLD + OBGameplayAdjuster.getInstance().getStopAllDamage());
					sender.sendMessage(chatmsgprefix + "  damage selections:");
					for (DamageCause damagetype : OBGameplayAdjuster.getDamageManager().list()) {
						sender.sendMessage(chatmsgprefix + "    " + ChatColor.GOLD + damagetype.toString());
					}
					break;
				case "show":
					sender.sendMessage(chatmsgprefix + "Available damage types:");
					for (DamageCause damagetype : DamageCause.values()) {
						sender.sendMessage(chatmsgprefix + "    " + damagetype.toString());
					}
					break;
				default:
					Usage(sender);
					break;
			}
		}
		return true;
	}

    void Usage(CommandSender sender) {
    	sender.sendMessage(chatmsgprefix + "/gpa deop" + ChatColor.GOLD + " - Deop all operators");
        sender.sendMessage(chatmsgprefix + "/gpa forcesurvival" + ChatColor.GOLD + " - Force survival gamemode");
        sender.sendMessage(chatmsgprefix + "/gpa alldamage" + ChatColor.GOLD + " - Toggle damage for players");
        sender.sendMessage(chatmsgprefix + "/gpa damage <damagetype>" + ChatColor.GOLD + " - Toggle specific player damage");
        sender.sendMessage(chatmsgprefix + "/gpa show" + ChatColor.GOLD + " - Show available damage types");
        sender.sendMessage(chatmsgprefix + "/gpa list" + ChatColor.GOLD + " - Show current settings");
    }
}
