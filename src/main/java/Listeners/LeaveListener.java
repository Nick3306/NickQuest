package Listeners;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import Nick3306.github.io.NickQuest.Main;
import Nick3306.github.io.NickQuest.QuestPlayer;
import Nick3306.github.io.NickQuest.Utilities;
import Skills.ArcherySkill;

public class LeaveListener implements Listener
{
	private Main plugin;
	private Utilities util;
	public LeaveListener(Main plugin)
	 {
	   this.plugin = plugin;
	   this.util = this.plugin.util;
	 }
	@EventHandler
	public void playerExit(final PlayerQuitEvent event)
	{
		Bukkit.getScheduler().runTaskAsynchronously(this.plugin, new Runnable() {
			public void run() {
				Connection myConn = null;
				Player player = event.getPlayer();
				String uuid = player.getUniqueId().toString();
				String query = "UPDATE player_quests SET ";
				QuestPlayer questPlayer = util.getQuestPlayer(player);
				HashMap<Integer,Integer> questParts = new HashMap<Integer,Integer>();
				
		try 
		{
			myConn = DriverManager.getConnection("jdbc:mysql://158.69.55.217:3306/mc30874","mc30874","a52df14135");
			Bukkit.getLogger().info("Sucessfully connected");
			
			/*
			 * 
			 * 
			 Save quests to DB
			 *
			 *
			 */
			for(int i = 0 ; i < questPlayer.currentQuests.size(); i++)
			{
				if(i == 0)
				{
				query = query + "Quest" + Integer.toString(questPlayer.currentQuests.get(i).getQuestNum())+ "=?";
				}
				else
				{
					query = query + ",Quest" + Integer.toString(questPlayer.currentQuests.get(i).getQuestNum())+ "=?";
				}
				questParts.put(questPlayer.currentQuests.get(i).getQuestNum(), questPlayer.currentQuests.get(i).getPart());
			}
			for(int i = 0 ; i < questPlayer.completedQuests.size(); i++)
			{
				if(i == 0)
				{
				query = query + "Quest" + Integer.toString(questPlayer.completedQuests.get(i).getQuestNum())+ "=?";
				}
				else
				{
					query = query + ",Quest" + Integer.toString(questPlayer.completedQuests.get(i).getQuestNum())+ "=?";
				}
				questParts.put(questPlayer.completedQuests.get(i).getQuestNum(), 999);
			}
			query = query+" WHERE uuid =?;";
			Bukkit.getLogger().info("Query is: " + query);
			Set<Integer> keys = questParts.keySet();
			Object[] keysArr = keys.toArray();
			int uuidCounter = 0;
			PreparedStatement myStatement = myConn.prepareStatement(query);		
			for(int i = 0; i < keysArr.length; i++)
			{
				myStatement.setString(i+1, Integer.toString(questParts.get((Integer) keysArr[i])));
				uuidCounter = i;
			}
			if(keysArr.length == 0)
			{
				myStatement.setString(1, uuid);
			}
			else
			{
				myStatement.setString(uuidCounter+2, uuid);
			}
			myStatement.execute();
			
			/*
			 * 
			 * 
			 Save stats to DB
			 *
			 *
			 */
			myStatement = myConn.prepareStatement("UPDATE nick_stats SET level=?,exp=? WHERE uuid=?");
			myStatement.setString(1, Integer.toString(questPlayer.getLevel()));
			myStatement.setString(2, Double.toString(questPlayer.getExp()));
			myStatement.setString(3, uuid);
			myStatement.execute();
			
			/*
			 * 
			 * 
			 Save Skills to the DB
			 *
			 *
			 */
			myStatement = myConn.prepareStatement("UPDATE nick_skills SET mining=?,archery=?,melee=?,magic=?,archery_frost=?,archery_fire=?,archery_poison=? WHERE uuid=?;");
			myStatement.setString(1,Double.toString(questPlayer.getSkillLevel("mining")));
			myStatement.setString(2,Double.toString(questPlayer.getSkillLevel("archery")));
			myStatement.setString(3,Double.toString(questPlayer.getSkillLevel("melee")));
			myStatement.setString(4,Double.toString(questPlayer.getSkillLevel("magic")));
			ArcherySkill arch = (ArcherySkill)questPlayer.getSkill("archery");
			myStatement.setString(5,Boolean.toString(arch.hasFrost()));
			myStatement.setString(6,Boolean.toString(arch.hasFire()));
			myStatement.setString(7,Boolean.toString(arch.hasPoison()));
			myStatement.setString(8, uuid);
			myStatement.execute();
			myConn.close();
		} 
		catch (SQLException e) 
		{

		}

			}});

	}
}
