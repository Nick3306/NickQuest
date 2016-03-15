package Quest2;

import java.io.IOException;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import Nick3306.github.io.NickQuest.Main;
import Nick3306.github.io.NickQuest.Utilities;

import com.mewin.WGRegionEvents.events.RegionEnterEvent;
import com.mewin.WGRegionEvents.events.RegionLeaveEvent;


public class Quest2Listener implements Listener
{
	private Main plugin;
	private Utilities util;
	public Quest2Listener(Main plugin)
	 {
	   this.plugin = plugin;
	   this.util = this.plugin.util;
	 }
	@EventHandler
	public void onRegionEnter(RegionEnterEvent e) throws IOException
	{
		Player player = e.getPlayer();
		if(util.hasQuest(player, 2))
		{
			if(e.getRegion().getId().equalsIgnoreCase("quest2-1"))
			{
				((Quest2) util.getQuest(player, 2)).setPart(2);
			}	
		}
	}
	@EventHandler
	public void onRegionLeave(RegionLeaveEvent e)
	{
		Player player = e.getPlayer();
		if(util.hasQuest(player, 2))
		{
			if(e.getRegion().getId().equalsIgnoreCase("quest2-1"))
			{
				player.sendMessage("You have left the quest area!");
			}	
		}		
	}
	@EventHandler
	public void onChestOpen(PlayerInteractEvent e) throws IOException
	{
		Player player = e.getPlayer();
		if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK))
		{
			if(e.getClickedBlock().getType().equals(Material.CHEST))
			{
				if(util.hasQuest(player, 2))
				{
					if(util.getQuest(player, 2).getPart() == 2)
					{
						((Quest2) util.getQuest(player, 2)).setPart(3);
					}
				}
			}
		}							
	}
}
