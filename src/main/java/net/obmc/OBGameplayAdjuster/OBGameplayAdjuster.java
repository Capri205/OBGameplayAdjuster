package net.obmc.OBGameplayAdjuster;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;


public class OBGameplayAdjuster extends JavaPlugin
{
	static Logger log = Logger.getLogger("Minecraft");
	
	public static OBGameplayAdjuster instance;
	
    private PlayerJoinListener listen;
	public ConfigManager mlc;
	
	private static Boolean doDeop;
    
    public OBGameplayAdjuster() {
    	instance = this;
    }
    
    // make our (public) main class methods and variables available to other classes
    public static OBGameplayAdjuster getInstance() {
    	return instance;
    }
    
    // enable the plugin
    public void onEnable() {
        this.listen = new PlayerJoinListener();
        this.getServer().getPluginManager().registerEvents((Listener)this.listen, (Plugin)this);
        this.getCommand("gpa").setExecutor(new PlayerCommandListener());
		if (!manageConfigs()) {
			return;
		}
		
		// enable the repeating task
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
            	
        		// check if anyone is op and deop according to the rule
            	for (Player player : Bukkit.getOnlinePlayers()) {
            		if (player.isOp() && doDeop) {
                    	log.log(Level.INFO, "[OBGameplayAdjuster] - deop'ing " + player.getName());
                    	player.sendMessage(ChatColor.LIGHT_PURPLE + "OBGameplayAdjuster - " + ChatColor.RED + "You were deop'd per the gameplay setting.");
                    	player.setOp(false);
                    }
            	}
            }
        }, 0L, 100L);		
    }
    
    // disable the plugin
    public void onDisable() {
    	saveConfig();
    }
    
    // return the player join listener if needed 
    public PlayerJoinListener getListen() {
        return this.listen;
    }

    // save current configuration out to file
    public void saveConfig() {
    	log.log(Level.INFO, "[OBGameplayAdjuster] Saving configuration");
    	mlc.getConfig().set("DeOp", doDeop);
    	mlc.save();
    }
    
    // load configuration from file
	public void loadConfig() {
		if (new File("plugins/OBGameplayAdjuster/config.yml").exists()) {
			//log.log(Level.INFO, "[OBMetaProducer] config.yml successfully loaded.");
		} else {
			saveDefaultConfig();
			log.log(Level.INFO, "[OBGameplayAdjuster] New config.yml has been created.");
		}
	}
	
	// load configuration or create a default configuration
	public boolean manageConfigs() {
		loadConfig();
		try {
			mlc = new ConfigManager(this);
			doDeop = mlc.getConfig().getBoolean("DeOp");
		} catch (Exception e) {
			log.log(Level.WARNING, "[OBGameplayAdjuster] Error occurred while loading config.");
			e.printStackTrace();
			this.getServer().getPluginManager().disablePlugin(this);
			return false;
		}
		log.log(Level.INFO, "[OBGameplayAdjuster] Loaded configuration");
		log.log(Level.INFO, "[OBGameplayAdjuster]     DeOp on join is set to " + doDeop.toString());
		return true;
	}

	public static boolean getDoDeop() {
		return doDeop;
	}
	public static void setDoDeop(Boolean dodeop) {
		doDeop = dodeop;
	}

}