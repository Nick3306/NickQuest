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
		QuestPlayer questPlayer = getQuestPlayer(player);
		for(int i = 0; i< questPlayer.currentQuests.size(); i++)
		{
			if(questPlayer.currentQuests.get(i).getQuestNum() == questNum)
			{
				return true;
			}
		}
		return false;
	}
	public Quest getQuest(Player player, int questNum)
	{
		QuestPlayer questPlayer = getQuestPlayer(player);
		for(int i = 0; i< questPlayer.currentQuests.size(); i++)
		{
			if(questPlayer.currentQuests.get(i).getQuestNum() == questNum)
			{
				return questPlayer.currentQuests.get(i);
			}
		}
		return null;
		
	}
	public QuestPlayer getQuestPlayer(Player player)
	{
		for(int i = 0; i < plugin.questPlayers.size(); i++)
		{
			if(plugin.questPlayers.get(i).getPlayer() == player)
			{
				return plugin.questPlayers.get(i);
			}
		}
		return null;
	}
}
