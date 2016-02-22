package Nick3306.github.io.NickQuest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerLoginEvent;

import Quest1.Quest1;
import Quest2.Quest2;
//This listener gets the player that joins and grabs what quest they are on from the config.
public class JoinListener implements Listener
{
	File playerConfig;
	FileConfiguration data;
	private Main plugin;
	public JoinListener(Main plugin)
	 {
	   this.plugin = plugin;
	 }

	@EventHandler
	public void playerJoin(PlayerLoginEvent event) throws FileNotFoundException, IOException, InvalidConfigurationException
	{
		ArrayList<Quest> quests = new ArrayList<Quest>();
		Player player = event.getPlayer();
		UUID uuid = player.getUniqueId();
		playerConfig = new File(plugin.getDataFolder()+"/user_data/" + uuid+".yml");
		data = YamlConfiguration.loadConfiguration(playerConfig);
		
		if(playerConfig.exists())
		{
			data.load(playerConfig);
			Bukkit.getLogger().info("Config Exists!");
		}
		else
		{
			data.save(playerConfig);
			data.set("CompletedQuests", "");
			data.set("CurrentQuests", "");
			data.save(playerConfig);
		}
		if(data.getConfigurationSection("CurrentQuests") == null)
		{
			Bukkit.getLogger().info("Current quests is null");
		}
		if(data.getConfigurationSection("CurrentQuests") != null)
		{
			Set<String> keys = data.getConfigurationSection("CurrentQuests").getKeys(true);	
			String[] keysArray = keys.toArray(new String[0]);
		
			for(int i = 0 ; i < keysArray.length; i++)
			{
				int part = data.getInt("CurrentQuests." + keysArray[i]);
				addQuest(keysArray[i], part, player, quests);
			}
		}
		plugin.playerQuests.put(player, quests);
	}
	public void addQuest(String input, int part, Player player, ArrayList<Quest> quests)
	{
		if(input.equalsIgnoreCase("Quest1"))
		{
			Quest1 quest = new Quest1(part, player, this.plugin);
			quests.add(quest);
		}
		if(input.equalsIgnoreCase("Quest2"))
		{
			Quest2 quest = new Quest2(part, player, this.plugin);
			quests.add(quest);
		}
	}
}
