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

public class Quest1Listener implements Listener
{
	private Main plugin;
	public Quest1Listener(Main plugin)
	 {
	   this.plugin = plugin;
	 }
	@EventHandler
	public void EntityDeath(EntityDeathEvent event) throws IOException
	{
		// Call the function in quest 1 to increment skeleton kills
		if(event.getEntity().getKiller() != null)
		{
			Player player = event.getEntity().getKiller();
			for(int i = 0; i < plugin.playerQuests.get(player).size(); i++)// If player is on quest 1
			{	
				if(((Quest) plugin.playerQuests.get(player).get(i)).getQuestNum() == 1)
				{
					if (((Quest1) plugin.playerQuests.get(player).get(i)).part == 1) 							//if player is on part 1 of quest 1	
					{
						if(event.getEntity().getType() == EntityType.SKELETON)
						{	
							if(event.getEntity().getKiller() == player)
							{								
								((Quest1) plugin.playerQuests.get(player).get(i)).killsInc();											//Cast default object as quest1
								player.sendMessage(Integer.toString(((Quest1) plugin.playerQuests.get(player).get(i)).skelKills));
							}
						}
					}
				}			
			}
		}
	}
}
