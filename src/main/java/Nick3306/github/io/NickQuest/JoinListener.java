package Nick3306.github.io.NickQuest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.sql.*;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerLoginEvent;

import Quest1.Quest1;
import Quest2.Quest2;
//This listener gets the player that joins and grabs what quest they are on from the config.
public class JoinListener implements Listener
{
	private Main plugin;
	private Utilities util;
	public JoinListener(Main plugin)
	 {
	   this.plugin = plugin;
	   this.util = this.plugin.util;
	 }
	@EventHandler
	public void playerJoin(PlayerLoginEvent event)
	{
		double playerExp;
		int playerLevel;
		Player player = event.getPlayer();
		String uuid = player.getUniqueId().toString();
		try 
		{
			Connection myConn = DriverManager.getConnection("jdbc:mysql://172.245.215.194:3306/mc22128","mc22128","d203b0cf75");
			Bukkit.getLogger().info("Sucessfully connected");
			
			//get stats
			PreparedStatement myStatement = myConn.prepareStatement("SELECT * FROM nick_stats WHERE uuid =?;");
			myStatement.setString(1, uuid);			
			ResultSet rs = myStatement.executeQuery();
			if(rs.next() == false)
			{
				Bukkit.getLogger().info("Player does not exist in DB nick_stats");
				PreparedStatement preparedStmt = myConn.prepareStatement("INSERT INTO nick_stats " + "VALUES (?,1,0)");
				preparedStmt.setString(1, uuid);
				preparedStmt.execute();
				Bukkit.getLogger().info("Player added to DB nick_stats!");				
			}
			else
			{
				playerLevel = rs.getInt("level");
				playerExp = rs.getDouble("exp");
				QuestPlayer joiningPlayer = new QuestPlayer(player, playerLevel, playerExp);
				plugin.questPlayers.add(joiningPlayer);
			}
			
			// get quest data
			myStatement = myConn.prepareStatement("SELECT * FROM player_quests WHERE uuid =?;");
			myStatement.setString(1, uuid);			
			rs = myStatement.executeQuery();
			if(rs.next() == false)
			{
				Bukkit.getLogger().info("Player does not exist in DB");
				PreparedStatement preparedStmt = myConn.prepareStatement("INSERT INTO player_quests " + "VALUES (?,0,0,0,0,0,0,0,0,0,0)");
				preparedStmt.setString(1, uuid);
				preparedStmt.execute();
				Bukkit.getLogger().info("Player added to DB!");
				myConn.close();
			}
			else
			{
				Bukkit.getLogger().info("Player is in the DB!");
				for(int i = 1; i < 11 ; i++)
				{
					int questNum = i;
					int part = rs.getInt(i);
					if(part != -1)
					{
						addQuest(questNum, part, player);
					}
				}
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	public void addQuest(int questNum, int part, Player player)
	{
		QuestPlayer joiningPlayer = util.getQuestPlayer(player);
		if(questNum == 1)
		{
			if(part == 999)
			{
				Quest1 newQuest = new Quest1(part, player, plugin);
				joiningPlayer.completedQuests.add(newQuest);
			}
			else if (questNum == 1)
			{
				Quest1 newQuest = new Quest1(part, player, plugin);
				joiningPlayer.currentQuests.add(newQuest);
			}
			else if (questNum == 2)
			{
				Quest2 newQuest = new Quest2(part, player, plugin);
				joiningPlayer.currentQuests.add(newQuest);
			}
		}
	}
}
