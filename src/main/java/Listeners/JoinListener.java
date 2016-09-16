package Listeners;

import java.sql.*;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import Nick3306.github.io.NickQuest.Main;
import Nick3306.github.io.NickQuest.QuestPlayer;
import Nick3306.github.io.NickQuest.Utilities;
import Skills.ArcherySkill;
import Skills.ArmorSkill;
import Skills.HealthSkill;
import Skills.MagicSkill;
import Skills.MeleeSkill;
import Skills.MiningSkill;

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
		/*
		 * 
		 * 
		 The following is used to grab stuff from the DB when a player joins
		 *
		 *
		 */
		final Player player = event.getPlayer();
		final String uuid = player.getUniqueId().toString();
	
		Bukkit.getScheduler().runTaskAsynchronously(this.plugin, new Runnable() {
		public void run() {
		try 
		{
			Connection myConn = DriverManager.getConnection("jdbc:mysql://158.69.55.217:3306/mc30874","mc30874","a52df14135");
			Bukkit.getLogger().info("Sucessfully connected on login");
			
			/*
			 * 
			 * 
			 Get stats from the DB
			 *
			 *
			 */
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
			
			/*
			 * 
			 * 
			 Get quest data from DB
			 *
			 *
			 */
			myStatement = myConn.prepareStatement("SELECT * FROM player_quests WHERE uuid =?;");
			myStatement.setString(1, uuid);			
			rs = myStatement.executeQuery();
			//If player doesn't exist add them to the DB
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
			
			/*
			 * 
			 * 
			 Gets skills from DB
			 *
			 *
			 */
			myStatement = myConn.prepareStatement("SELECT * FROM nick_skills WHERE uuid =?;");
			myStatement.setString(1, uuid);			
			rs = myStatement.executeQuery();
			if(rs.next() == false)
			{
				Bukkit.getLogger().info("Player does not exist in DB nick_skills!");
				PreparedStatement preparedStmt = myConn.prepareStatement("INSERT INTO nick_skills " + "VALUES (?,0,0,0,0,0,0,0,0,0)");
				preparedStmt.setString(1, uuid);
				preparedStmt.execute();
				Bukkit.getLogger().info("Player added to DB!");
			}
			else
			{
				boolean frost = false;
				boolean fire = false;
				boolean poison = false;
				if(rs.getDouble("archery_frost") == 1)
				{
					frost = true;
				}
				if(rs.getDouble("archery_fire") == 1)
				{
					fire = true;
				}
				if(rs.getDouble("archery_poison") == 1)
				{
					poison = true;
				}
				QuestPlayer joiningPlayer = util.getQuestPlayer(player);		
				joiningPlayer.skills.add(new ArcherySkill("archery",(rs.getInt("archery")),frost, fire, poison));
				joiningPlayer.skills.add(new MeleeSkill("Melee", (rs.getInt("melee"))));
				joiningPlayer.skills.add(new MagicSkill("Magic", (rs.getInt("magic"))));
				joiningPlayer.skills.add(new HealthSkill("Health", (rs.getInt("health"))));
				joiningPlayer.skills.add(new ArmorSkill("Armor", (rs.getInt("armor"))));
				joiningPlayer.skills.add(new MiningSkill("Mining", (rs.getInt("mining"))));
			}	
			myConn.close();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}

		
		}});
		/*
		 * 
		 * 
		 The following is used to grab stuff from the DB when a player joins
		 *
		 *
		 */
	
	}
	public void addQuest(int questNum, int part, Player player)
	{
		/*
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
		*/
	}
}
