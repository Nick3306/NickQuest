package Nick3306.github.io.NickQuest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import Skills.ArcherySkill;


public class QuestCommands implements CommandExecutor
{
	private Main plugin;
	private Utilities util;
	public QuestCommands(Main plugin)
	 {
	   this.plugin = plugin;
	   this.util = this.plugin.util;
	 }
	@Override
	public boolean onCommand(CommandSender sender, Command command, String lable, String[] args) 
	{
		if(command.getName().equalsIgnoreCase("Quest"))
		{
			if(args.length == 0)
			{
				sender.sendMessage(ChatColor.RED + "----------OptiQuests----------");
				sender.sendMessage(ChatColor.GREEN + "/quest info  :  List your current quests");
				sender.sendMessage(ChatColor.GREEN + "/quest info (ID)  :  Us ethe ID given by /quest info to view details on that specific quest");
				sender.sendMessage(ChatColor.GREEN + "/quest getquest  :  Get the first quest for testing purposes. Will be removed");
				sender.sendMessage(ChatColor.GREEN + "/quest turnin  :  Turn in finished first quest for testing purposes. Will be removed.");
				return true;
			}
			Player player = (Player) sender;
			QuestPlayer questPlayer = util.getQuestPlayer(player);
			File playerConfig = new File(plugin.getDataFolder()+"/user_data/" + player.getUniqueId()+".yml");
			FileConfiguration data = YamlConfiguration.loadConfiguration(playerConfig);
			if(args[0].equalsIgnoreCase("info"))
			{
				if(args.length < 2)
				{
					for (int i = 0; i < questPlayer.currentQuests.size(); i++)
					{
						player.sendMessage(ChatColor.RED + "Current Quests");
						player.sendMessage(ChatColor.GREEN + Integer.toString(i+1) + " " + questPlayer.currentQuests.get(i).getName());
					}
					return true;
				}
				if(args.length == 2)
				{
					int index = Integer.parseInt(args[1]) - 1;
					player.sendMessage(ChatColor.GREEN + questPlayer.currentQuests.get(index).questInfo());
					return true;
				}
			}

			else if(args[0].equalsIgnoreCase("Completed"))
			{

			}
			else if(args[0].equalsIgnoreCase("Arrow"))
			{
				ArcherySkill arch = (ArcherySkill) questPlayer.getSkill("Archery");
				if(arch.hasFrost())
				{
					player.sendMessage("frost");
				}
				if(arch.hasFire())
				{
					player.sendMessage("fire");
				}
				if(arch.hasPoison())
				{
					player.sendMessage("Poison");
				}
			}
			else
			{
				sender.sendMessage(ChatColor.GREEN + "Unrecognized command. Use /quest for command info");
				return false;
			}
		}
		return false;
	}

}
