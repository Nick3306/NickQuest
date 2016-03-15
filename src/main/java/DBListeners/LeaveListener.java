package DBListeners;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
	public void playerExit(PlayerQuitEvent event)
	{
		Player player = event.getPlayer();
		String uuid = player.getUniqueId().toString();
		QuestPlayer questPlayer = util.getQuestPlayer(player);
		HashMap<Integer,Integer> questParts = new HashMap<Integer,Integer>();
		String query = "UPDATE player_quests SET ";
		
		try 
		{
			Connection myConn = DriverManager.getConnection("jdbc:mysql://172.245.215.194:3306/mc22128","mc22128","d203b0cf75");
			Bukkit.getLogger().info("Sucessfully connected");
			//get and send quests
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
				query = query + "Quest" + Integer.toString(questPlayer.completedQuests.get(i).getQuestNum())+ "=?,";
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
			myStatement.setString(uuidCounter+2, uuid);
			myStatement.execute();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}

	}
}
