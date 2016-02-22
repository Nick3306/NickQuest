package Nick3306.github.io.NickQuest;

import java.util.ArrayList;

import org.bukkit.entity.Player;

public class QuestPlayer 
{
	Player player;
	ArrayList<Quest> currentQuests = new ArrayList<Quest>();
	ArrayList<Quest> completedQuests = new ArrayList<Quest>();
	int level;
	double exp;
	QuestPlayer(Player player, int level, double exp)
	{
		this.player = player;
		this.level = level;
		this.exp = exp;
	}
	public Player getPlayer() 
	{
		return player;
	}
	public int getLevel() 
	{
		return level;
	}
	public void setLevel(int level) 
	{
		this.level = level;
	}
	public double getExp() 
	{
		return exp;
	}
	public void setExp(double exp) 
	{
		this.exp = exp;
	}
}
