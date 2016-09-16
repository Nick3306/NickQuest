package Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import Nick3306.github.io.NickQuest.Main;
import Nick3306.github.io.NickQuest.QuestPlayer;
import Nick3306.github.io.NickQuest.Utilities;
import Skills.ArcherySkill;

import org.bukkit.entity.Player;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Creature;
import org.bukkit.entity.LivingEntity;


public class EntityDamageListener implements Listener
{
	private Main plugin;
	private Utilities util;
	public EntityDamageListener(Main plugin)
	 {
	   this.plugin = plugin;
	   this.util = this.plugin.util;
	 }
	
	/*
	 * 
	 * 
	 For arrow damage
	 *
	 *
	 */
	@EventHandler
	public void onArrowHit(EntityDamageByEntityEvent e) 
	{
		if (e.getDamager() instanceof Arrow) 
		{
			Arrow a = (Arrow) e.getDamager();
			if (a.getShooter() instanceof Player) 
			{
				Player player = (Player) a.getShooter();
				if(e.getEntity() instanceof Creature)
				{
					player.sendMessage("You are damaging a mob");
					QuestPlayer playerObject = util.getQuestPlayer(player);					
					LivingEntity mob = (LivingEntity) e.getEntity();
					ArcherySkill arch = (ArcherySkill) playerObject.getSkill("Archery");
					if(arch.hasPoison())
					{	
						PotionEffect poison = new PotionEffect(PotionEffectType.POISON,200, 1);
						player.sendMessage("Adding poison!");
						mob.addPotionEffect(poison);
					}
					if(arch.hasFire())
					{
						player.sendMessage("Adding fire!");
						mob.setFireTicks(100);
					}
					if(arch.hasFrost())
					{
						player.sendMessage("Adding frost!");
						mob.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,200, 1));
					}
					
				}
			}			
		}
	}
}
