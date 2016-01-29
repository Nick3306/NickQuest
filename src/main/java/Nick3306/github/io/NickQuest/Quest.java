package Nick3306.github.io.NickQuest;

import org.bukkit.ChatColor;

//This interface is implemented by each special quest class and allows the commands to pull info from each unique quest (Avoids casting problems)
public interface Quest 
{
	int questNum = 0;
	int part = 0;
	int questNumber = 0;
	public String questInfo();
	public String getName();
	public int getPart();
	public int getQuestNum();
}
