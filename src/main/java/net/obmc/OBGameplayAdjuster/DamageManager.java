package net.obmc.OBGameplayAdjuster;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.event.entity.EntityDamageEvent.DamageCause;


public class DamageManager {

	static Logger log = Logger.getLogger("Minecraft");

	private ArrayList<DamageCause> damagelist = null;
	private String chatmsgprefix = null;
	private String logmsgprefix = null;
	
	// a map of damage types and their setting
	public DamageManager() {
		//damagemap = new EnumMap<>(DamageCause.class);
		damagelist = new ArrayList<DamageCause>();
		chatmsgprefix = OBGameplayAdjuster.getInstance().getChatMsgPrefix();
		logmsgprefix = OBGameplayAdjuster.getInstance().getLogMsgPrefix();
	}
	
	// build our damage settings from the config
	public void setFromConfig() {
		if (OBGameplayAdjuster.getInstance().getConfig().contains("damage")) {
			List<String> damagekeys = OBGameplayAdjuster.getInstance().getConfig().getStringList("damage");
			log.log(Level.INFO, logmsgprefix + " Applying " + damagekeys.size() +" damage entries from config");
			Iterator<String> dit = damagekeys.iterator();
			while(dit.hasNext()) {
				String damagetype = dit.next();
				try {
					damagelist.add(DamageCause.valueOf(damagetype));
				} catch (Exception e) {
					log.log(Level.INFO, logmsgprefix + " Invalid damage type in config (" + damagetype + ")");
				}
			}
		} else {
			log.log(Level.INFO, logmsgprefix + " Config doesn't contain a damage list or has no entries.");
		}
	}
	
	// setter and getter for damage type
	public Boolean exists(DamageCause damagetype) {
		if (damagelist.contains(damagetype)) {
			return true;
		}
		return false;
	}

	// add or remove a damage type from list (true = added, false = removed)
	public Boolean toggle(String damagetype) {
		damagetype = damagetype.toUpperCase();
		if (damagelist.contains(DamageCause.valueOf(damagetype))) {
			damagelist.remove(DamageCause.valueOf(damagetype));
			return false;
		} else {
			damagelist.add(DamageCause.valueOf(damagetype));
		}
		return true;
	}

	// check a string is an actual damage cause
	private boolean check(String damagetype) {
		try {
			DamageCause.valueOf(damagetype);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	private void add(DamageCause damagetype) {
		if (!damagelist.contains(damagetype)) {
			damagelist.add(damagetype);
		}
	}
	
	// return the current damage selection in various ways
	public ArrayList<DamageCause> list() {
		return damagelist;
	}
	public List<String> listConfig() {
		List<String> configlist = new ArrayList<String>();
		if (damagelist.size() > 0) {
			for (DamageCause cause : damagelist) {
				configlist.add(cause.toString());
			}
		}
		return configlist;
	}
}
