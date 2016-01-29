package Nick3306.github.io.NickQuest;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import Quest1.Quest1Listener;
import Quest2.Quest2Listener;
public class Main extends JavaPlugin
{
	// The value of this hashmap will be the unique quests a player is on. Setting type as object allows them all to be stored in the same map. 
	//They will be casted when needed
	public HashMap<Player, ArrayList<Quest>> playerQuests = new HashMap<Player, ArrayList<Quest>>();
	public Utilities util;
	 public void onEnable()
	 {
		PluginManager pm = getServer().getPluginManager();
		this.getConfig().options().copyDefaults(true);
		saveDefaultConfig();
		pm.registerEvents(new Quest1Listener(this), this);
		pm.registerEvents(new JoinListener(this), this);
		pm.registerEvents(new Quest2Listener(this), this);
		getCommand("Quest").setExecutor(new QuestCommands(this));
	 }
	 public void onDisable()
	 {
		 
	 }
}
