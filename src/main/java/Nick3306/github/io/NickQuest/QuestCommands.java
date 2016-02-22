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

import Quest1.Quest1;
import Quest2.Quest2;

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
			else if(args[0].equalsIgnoreCase("getQuest"))
			{
				
				if(args[1].equalsIgnoreCase("1"))
				{
					if(util.hasQuest(player, 1))
					{
						player.sendMessage(ChatColor.GREEN + "You already have this quest!");
						return false;
					}
				
					player.sendMessage(data.getString("CompletedQuests"));
					List<String> list = data.getStringList("CompletedQuests");		
					if(!list.contains("Quest1"))
					{								
						Quest1 currentQuest = new Quest1(1, player, this.plugin);
						questPlayer.currentQuests.add(currentQuest);
						player.sendMessage(ChatColor.GREEN + "Quest 1 added!");
						data.set("CurrentQuests" + ".Quest1", 1);
						player.sendMessage(Integer.toString(data.getInt("CurrentQuests" + ".Quest1")));
						try {
							data.save(playerConfig);
						} catch (IOException e) 
						{

							e.printStackTrace();
						}				
						return true;
					}
						player.sendMessage(ChatColor.GREEN + "You have already completed this quest!");
						return false;	
					}
				if(args[1].equalsIgnoreCase("2"))
				{
					if(args[1].equalsIgnoreCase("2"))
					{
						if(util.hasQuest(player, 2))
						{
							player.sendMessage(ChatColor.GREEN + "You already have this quest!");
							return false;
						}
					
						player.sendMessage(data.getString("CompletedQuests"));
						List<String> list = data.getStringList("CompletedQuests");		
						if(!list.contains("Quest2"))
						{								
							Quest2 currentQuest = new Quest2(1, player, this.plugin);
							questPlayer.currentQuests.add(currentQuest);
							player.sendMessage(ChatColor.GREEN + "Quest 2 added!");
							data.set("CurrentQuests" + ".Quest2", 0);
							player.sendMessage(Integer.toString(data.getInt("CurrentQuests" + ".Quest2")));
							try 
							{
								data.save(playerConfig);
							} catch (IOException e) 
							{
								e.printStackTrace();
							}				
							return true;
						}
							player.sendMessage(ChatColor.GREEN + "You have already completed this quest!");
							return false;	
						}
				}
				else
				{
					player.sendMessage("That is not a quest, try 1 or 2!");
				}
			}		
			else if(args[0].equalsIgnoreCase("turnin"))
			{
				for(int i = 0; i < questPlayer.currentQuests.size(); i++)
				{
					if(questPlayer.currentQuests.get(i).getQuestNum() == 1)
					{
						if(questPlayer.currentQuests.get(i).getPart() == 2)
						{
							player.getInventory().removeItem(new ItemStack[] { new ItemStack(Material.BONE, 10)});
							player.updateInventory();
							player.sendMessage(ChatColor.GREEN + "Quest Completed! 500 XP gained!");
							questPlayer.currentQuests.remove(i);
							player.giveExp(500);
							List<String> list = data.getStringList("CompletedQuests");
							list.add("Quest1");
							data.set("CompletedQuests", list);
							data.getConfigurationSection("CurrentQuests").set("Quest1", null);
							try {
								data.save(playerConfig);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							return true;
						}
					}
				}
				player.sendMessage("You do not have or have not completed that quest!");
				return false;
			}
			else if(args[0].equalsIgnoreCase("Completed"))
			{
				File questConfig = new File(plugin.getDataFolder()+"quest_info.yml");
				FileConfiguration qConfig = YamlConfiguration.loadConfiguration(questConfig);
				if(args.length > 1)
				{
					player.sendMessage(ChatColor.GREEN + "Incorrect usage, /quest completed");
					return false;
				}
				List<String> list = data.getStringList("CompletedQuests");
				player.sendMessage(ChatColor.RED + "Completed Quests");
				
				for(int i = 0; i < list.size();i++)
				{
					player.sendMessage(list.get(i));
					player.sendMessage(ChatColor.GREEN+ "ID: " + qConfig.getInt(list.get(i) + ".ID"));
					player.sendMessage(ChatColor.GREEN+ "Name: " + qConfig.getString(list.get(i) + ".Name"));
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
