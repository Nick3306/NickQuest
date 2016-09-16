package Listeners;

import java.io.IOException;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import Nick3306.github.io.NickQuest.Main;
import Nick3306.github.io.NickQuest.QuestPlayer;
import Nick3306.github.io.NickQuest.Utilities;


public class EntityDeathListener implements Listener
{
	private Main plugin;
	private Utilities util;
	public EntityDeathListener(Main plugin)
	 {
	   this.plugin = plugin;
	 }
	@EventHandler
	public void EntityDeath(EntityDeathEvent event) throws IOException
	{

	}
}
