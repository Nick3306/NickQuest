package Quest1;
import java.io.File;
import java.io.IOException;


// This is the unique class for quest 1, each player on quest1 will be given an instance of this class.
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import Nick3306.github.io.NickQuest.Main;
import Nick3306.github.io.NickQuest.Quest;

public class Quest1 implements Quest
{
	private Main plugin;
	public Quest1(Main plugin)
	 {
	   this.plugin = plugin;
	 }
	String name = "Starting out";
	int questNum = 1;
	int part = 0;
	Player player;
	int skelKills;
	File playerConfig;
	FileConfiguration data;
	public Quest1(int section, Player player, Main plugin)         // section will correspond to what part has been completed and will be stored in the config to be used for this constructor
	{	
		this.plugin = plugin;
		this.player = player;
		if (section == 1)
		{
			part = 1;
		}
		if (section == 2)
		{
			part = 2;
		}
		playerConfig = new File(plugin.getDataFolder()+"/user_data/" + player.getUniqueId()+".yml");
		data = YamlConfiguration.loadConfiguration(playerConfig);
	}
	@Override
	public String questInfo()
	{
		if(part == 0)
		{
			return "Talk to john the blacksmith about a job he needs done";
		}
		if(part == 1)
		{
			return "Kill " + (10-skelKills) + " skeletons to get bones for john the blacksmith";
		}
		if(part == 2)
		{
			return "Give the materials to john the blacksmith for a reward";
		}
		return "";
	}
	@Override
	public String getName()
	{
		return name;
	}
	@Override
	public int getPart()
	{
		return part;
	}
	@Override
	public int getQuestNum()
	{
		return questNum;
	}
	void killsInc() throws IOException
	{
		// Config was returning null from here
		//Called from the quest 1 listener and increments the variable for numbe rof skeletons killed
		skelKills++;
		player.getInventory().addItem(new ItemStack(Material.BONE, 1));
		player.updateInventory(); 
		if(skelKills >=10)
		{
			data.set("CurrentQuests" +  ".Quest1", 2);
			data.save(playerConfig);
			part = 2;
			player.sendMessage(ChatColor.GREEN + "Task Complete! ");
			player.sendMessage(ChatColor.GREEN + "New Task: " + questInfo());
			player.sendMessage(ChatColor.GREEN + "Since npc interactions dont work yet, type /quest turnin");
		}
		  
	}

}
