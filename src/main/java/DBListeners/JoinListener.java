package DBListeners;

import java.sql.*;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import Nick3306.github.io.NickQuest.Main;
import Nick3306.github.io.NickQuest.QuestPlayer;
import Nick3306.github.io.NickQuest.Utilities;
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
		final Player player = event.getPlayer();
		final String uuid = player.getUniqueId().toString();
	
		Bukkit.getScheduler().runTaskAsynchronously(this.plugin, new Runnable() {
		public void run() {
		try 
		{
			Connection myConn = DriverManager.getConnection("jdbc:mysql://172.245.215.194:3306/mc22128","mc22128","d203b0cf75");
			Bukkit.getLogger().info("Sucessfully connected on login");
			
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
				QuestPlayer joiningPlayer = new QuestPlayer(player, 1, 0);
				plugin.questPlayers.add(joiningPlayer);
			}
			else
			{

				QuestPlayer joiningPlayer = new QuestPlayer(player, rs.getInt("level"),rs.getDouble("exp"));
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
			}
			else
			{
				Bukkit.getLogger().info("Player is in the DB!");
				for(int i = 2; i < 11 ; i++)
				{
					int questNum = i-1;
					int part = rs.getInt(i);
					Bukkit.getLogger().info("part is: " + Integer.toString(part));
					if(part != 0)
					{
						addQuest(questNum, part, player);
					}
				}
			}
			
			//get skills
			myStatement = myConn.prepareStatement("SELECT * FROM nick_skills WHERE uuid =?;");
			myStatement.setString(1, uuid);			
			rs = myStatement.executeQuery();
			if(rs.next() == false)
			{
				Bukkit.getLogger().info("Player does not exist in DB nick_skills!");
				PreparedStatement preparedStmt = myConn.prepareStatement("INSERT INTO nick_skills " + "VALUES (?,0,0,0,0)");
				preparedStmt.setString(1, uuid);
				preparedStmt.execute();
				Bukkit.getLogger().info("Player added to DB!");
			}
			else
			{
				QuestPlayer joiningPlayer = util.getQuestPlayer(player);
				joiningPlayer.skillsExp.put("mining", rs.getDouble("mining"));
				joiningPlayer.skillsExp.put("archery", rs.getDouble("archery"));
				joiningPlayer.skillsExp.put("sword", rs.getDouble("sword"));
				joiningPlayer.skillsExp.put("magic", rs.getDouble("magic"));
				joiningPlayer.skills.put("mining", Math.floor(rs.getDouble("mining")%1.5));
				joiningPlayer.skills.put("archery", Math.floor(rs.getDouble("archery")%1.5));
				joiningPlayer.skills.put("sword", Math.floor(rs.getDouble("sword")%1.5));
				joiningPlayer.skills.put("magic", Math.floor(rs.getDouble("magic")%1.5));
			}
			myConn.close();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		}});
		
	
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
			else if(part !=0)
			{
				Quest1 newQuest = new Quest1(part, player, plugin);
				joiningPlayer.currentQuests.add(newQuest);
				Bukkit.getLogger().info("quest 1 added");
				
			}
		}
		else if (questNum == 2)
		{
			if(part == 999)
			{
				Quest2 newQuest = new Quest2(part, player, plugin);
				joiningPlayer.completedQuests.add(newQuest);
			}
			else if(part !=0)
			{
				Quest2 newQuest = new Quest2(part, player, plugin);
				joiningPlayer.currentQuests.add(newQuest);
				Bukkit.getLogger().info("quest 2 added");
			}
		}
	}
}
