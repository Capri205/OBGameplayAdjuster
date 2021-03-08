package net.obmc.OBGameplayAdjuster;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.Listener;

public class PlayerJoinListener implements Listener
{
	static Logger log = Logger.getLogger("Minecraft");
	
    public PlayerJoinListener() {
    }
    
    // catch player join events
    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {
//    	log.log(Level.INFO, "debug - in player join event - ");
//        if (event.getPlayer().isOp() && OBGameplayAdjuster.getDoDeop()) {
//        	log.log(Level.INFO, "debug - deop'ing " + event.getPlayer().getName());
//        	event.getPlayer().sendMessage(ChatColor.LIGHT_PURPLE + "OBGameplayAdjuster - " + ChatColor.RED + "You were deop'd on joining");
//        	event.getPlayer().setOp(false);
//        }
    }
    
    // catch player changing world events
//    @EventHandler
//    public void onPlayerChangedWorld(final PlayerChangedWorldEvent event) {
//    }
}

