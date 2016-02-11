package Nick3306.github.io.NickQuest;

import org.bukkit.entity.Player;

public class Utilities
{
	public String testString = "test";
	private Main plugin;
	public Utilities(Main plugin)
	 {
	   this.plugin = plugin;
	 }
	public boolean hasQuest(Player player, int questNum)
	{
		for (int i = 0; i < plugin.playerQuests.get(player).size(); i++)
		{
			if(plugin.playerQuests.get(player).get(i).getQuestNum()== questNum)
			{
				return true;
			}
		}
		return false;
	}
	public Quest getQuest(Player player, int questNum)
	{
		for (int i = 0; i < plugin.playerQuests.get(player).size(); i++)
		{
			if(plugin.playerQuests.get(player).get(i).getQuestNum() == questNum)
			{
				return plugin.playerQuests.get(player).get(i);
			}
		}
		return null;
		
	}
}
