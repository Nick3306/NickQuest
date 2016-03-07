package Nick3306.github.io.NickQuest;

import java.util.ArrayList;
import java.util.HashMap;
import java.lang.*;

import org.bukkit.entity.Player;

public class QuestPlayer 
{
	private Player player;
	ArrayList<Quest> currentQuests = new ArrayList<Quest>();
	ArrayList<Quest> completedQuests = new ArrayList<Quest>();
	HashMap<String, Double> skills = new HashMap<String, Double>();
	private int level;
	private double exp;
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
	public double getSkillLevel(String skill)
	{
		return Math.floor(Math.pow(skills.get(skill), (1/3)));
	}
	public double getSkillExp(String skill)
	{
		return skills.get(skill);
	}
	public void setSkillExp(String skill, double exp)
	{
		skills.put(skill, exp);
	}
}
