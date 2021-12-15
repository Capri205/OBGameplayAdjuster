package net.obmc.OBGameplayAdjuster;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {

	static Logger log = Logger.getLogger("Minecraft");
	
	// catch player join
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		//if (OBGameplayAdjuster.getInstance().getDoCancelDamage()) {
		//	log.log(Level.INFO, "debug - setting " + player.getName() + " health to full");
		//	player.setHealth(20.0);
		//}
//    	log.log(Level.INFO, "debug - in player join event - ");
//        if (event.getPlayer().isOp() && OBGameplayAdjuster.getDoDeop()) {
//        	log.log(Level.INFO, "debug - deop'ing " + event.getPlayer().getName());
//        	event.getPlayer().sendMessage(ChatColor.LIGHT_PURPLE + "OBGameplayAdjuster - " + ChatColor.RED + "You were deop'd on joining");
//        	event.getPlayer().setOp(false);
//        }
    }

	// catch player changing world events
//  @EventHandler
//  public void onPlayerChangedWorld(final PlayerChangedWorldEvent event) {
//  }
  
	// catch damage event
	@EventHandler
	public void onDamage(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if (OBGameplayAdjuster.getInstance().getStopAllDamage() || (OBGameplayAdjuster.getDamageManager().exists(event.getCause()))) {
				event.setCancelled(true);
			}
		}
	}
}
