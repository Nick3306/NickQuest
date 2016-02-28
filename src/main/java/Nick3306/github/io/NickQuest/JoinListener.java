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
	File playerConfig;
	FileConfiguration data;
	private Main plugin;
	public JoinListener(Main plugin)
	 {
	   this.plugin = plugin;
	 }
	@EventHandler
	public void playerJoin(PlayerLoginEvent event)
	{
		Player player = event.getPlayer();
		String uuid = player.getUniqueId().toString();
		try 
		{
			Connection myConn = DriverManager.getConnection("jdbc:mysql://172.245.215.194:3306/mc22128","mc22128","d203b0cf75");

			Bukkit.getLogger().info("Sucessfully connected");
			PreparedStatement myStatement = myConn.prepareStatement("SELECT * FROM player_quests WHERE uuid =?;");
			myStatement.setString(1, uuid);			
			ResultSet myRS = myStatement.executeQuery();
			if(myRS.next() == false)
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
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	public void addQuest(String input, int part, Player player, ArrayList<Quest> quests)
	{
		
	}
}
