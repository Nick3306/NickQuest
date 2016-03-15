package Quest1;

import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import Nick3306.github.io.NickQuest.Main;
import Nick3306.github.io.NickQuest.Quest;
import Nick3306.github.io.NickQuest.QuestPlayer;
import Nick3306.github.io.NickQuest.Utilities;

public class Quest1Listener implements Listener
{
	private Main plugin;
	private Utilities util;
	public Quest1Listener(Main plugin)
	 {
	   this.plugin = plugin;
	   this.util = this.plugin.util;
	 }
	@EventHandler
	public void EntityDeath(EntityDeathEvent event) throws IOException
	{
		// Call the function in quest 1 to increment skeleton kills
		if(event.getEntity().getKiller() != null)
		{
			Player player = event.getEntity().getKiller();
			QuestPlayer questPlayer = util.getQuestPlayer(player);
			if(util.hasQuest(player, 1))// If player is on quest 1
			{
				Quest1 currentQuest = (Quest1) util.getQuest(player, 1);		//if player is on part 1 of quest 1	
				if (currentQuest.getPart() == 2) 							
				{
					if(event.getEntity().getType() == EntityType.SKELETON)
					{	
						if(event.getEntity().getKiller() == player)
						{								
							currentQuest.killsInc();											
							player.sendMessage(Integer.toString(currentQuest.skelKills));
						}						
					}
				}			
			}
		}
	}
}
