package net.obmc.OBGameplayAdjuster;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;


public class OBGameplayAdjuster extends JavaPlugin
{
	static Logger log = Logger.getLogger("Minecraft");
	
	public static OBGameplayAdjuster instance;
    private PlayerListener playerlistener;
	public static DamageManager damagemgr = null;
    
	private Boolean deop = false;;
	private Boolean forcesurvival = false;
	private Boolean stopalldamage = false;

	private static String plugin = "OBGameplayAdjuster";
	private static String pluginprefix = "[" + plugin + "]";
	private static String chatmsgprefix = ChatColor.AQUA + "" + ChatColor.BOLD + plugin + ChatColor.DARK_GRAY + ChatColor.BOLD + " » " + ChatColor.LIGHT_PURPLE + "";
	private static String logmsgprefix = pluginprefix + " » ";

    public OBGameplayAdjuster() {
    	instance = this;
    	damagemgr = new DamageManager();
    }
    
    // make our (public) main class methods and variables available to other classes
    public static OBGameplayAdjuster getInstance() {
    	return instance;
    }
    
    // enable the plugin
    public void onEnable() {
    	
		/**
		 * Initialize Stuff
		 */
		initializeStuff();

		/**
		 * Register stuff
		 */
		registerStuff();
		
		// enable the repeating task
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
            	
        		// check if anyone is op and deop according to the rule
            	for (Player player : Bukkit.getOnlinePlayers()) {
            		if (player.isOp() && deop) {
                    	log.log(Level.INFO, logmsgprefix + "Deop'ing " + player.getName());
                    	player.sendMessage(ChatColor.LIGHT_PURPLE + "OBGameplayAdjuster - " + ChatColor.RED + "You were deop'd per the gameplay setting.");
                    	player.setOp(false);
                    }
            	}
            }
        }, 0L, 100L);		
    }
    
    // save config disable the plugin
    public void onDisable() {
    	this.getConfig().set("damage", OBGameplayAdjuster.getDamageManager().listConfig());
    	saveConfig();
    }
    
	/**
	 * Initialize Stuff
	 */
	public void initializeStuff() {
		this.saveDefaultConfig();
		Configuration config = this.getConfig();

		this.deop = config.getBoolean("deop");
		this.forcesurvival = config.getBoolean("forcesurvival");
		this.stopalldamage = config.getBoolean("stopalldamage");
		OBGameplayAdjuster.getDamageManager().setFromConfig();
		
		log.log(Level.INFO, "[OBGameplayAdjuster] Loaded configuration.");
	}
	
	/**
	 * Register things
	 */
	public void registerStuff() {
        // event listener for player events
		this.playerlistener = new PlayerListener();
        this.getServer().getPluginManager().registerEvents((Listener)this.playerlistener, (Plugin)this);
        // event listener for commands
        this.getCommand("gpa").setExecutor(new CommandListener());

	}

    /**
     * Routine getters and setters
     */
 	public boolean getDeop() {
		return this.deop;
	}
	public void setDeop(Boolean mode) {
		this.deop = mode;
	}
 	public boolean getForceSurvival() {
		return this.forcesurvival;
	}
	public void setForceSurvival(Boolean mode) {
		this.forcesurvival = mode;
	}
 	public boolean getStopAllDamage() {
		return this.stopalldamage;
	}
	public void setStopAllDamage(Boolean mode) {
		this.stopalldamage = mode;
	}
	public static DamageManager getDamageManager() {
		return damagemgr;
	}
	
	// consistent messaging
	public static String getPluginName() {
		return plugin;
	}
	public static String getPluginPrefix() {
		return pluginprefix;
	}
	public String getChatMsgPrefix() {
		return chatmsgprefix;
	}
	public String getLogMsgPrefix() {
		return logmsgprefix;
	}
}