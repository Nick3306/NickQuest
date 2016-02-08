package Quest2;

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
	public void onRegionEnter(RegionEnterEvent e)
	{
		Player player = e.getPlayer();

		if(!util.hasQuest(player, 2))
		{
			player.sendMessage("You do not have quest 2!");
		}
		
		if(e.getRegion().getId().equalsIgnoreCase("test"))
		{
			player.sendMessage("In region");
		}		
	}
	@EventHandler
	public void onRegionLeave(RegionLeaveEvent e)
	{
		Player player = e.getPlayer();
		if(e.getRegion().getId().equalsIgnoreCase("test"))
		{
			player.sendMessage("left region");
		}		
	}
	@EventHandler
	public void onChestOpen(PlayerInteractEvent e)
	{
		if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK))
		{
			if(e.getClickedBlock().getType().equals(Material.CHEST))
			{
				
			}
		}
		
			
		
	}
}
