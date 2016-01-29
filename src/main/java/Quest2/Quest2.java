package Quest2;

import java.io.File;

import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.bukkit.WGBukkit;

import Nick3306.github.io.NickQuest.Main;
import Nick3306.github.io.NickQuest.Quest;

public class Quest2 implements Quest
{
	private Main plugin;
	public Quest2(Main plugin)
	 {
	   this.plugin = plugin;
	 }
	private String name = "Timed shit yo";
	private int questNum = 2;
	private int part = 0;
	private Player player;
	File playerConfig;
	FileConfiguration data;
	public Quest2(int section, Player player, Main plugin)         // section will correspond to what part has been completed and will be stored in the config to be used for this constructor
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
		Plugin worldguard = plugin.getServer().getPluginManager().getPlugin("WorldGuard");
		
	}
	@Override
	public String questInfo() 
	{
		return " ";
	}

	@Override
	public String getName() 
	{
		return name;
	}


	public int getPart() 
	{
		return part;
	}

	@Override
	public int getQuestNum() 
	{
		return questNum;
	}

}