package Skills;

public class HealthSkill implements Skill
{
	String name;
	int level;
	
	public HealthSkill(String name, int level)
	{
		this.name = name;
		this.level = level;
	}
	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public int getLevel()
	{
		return level;
	}

	@Override
	public void setLevel(int level)
	{
		this.level = level;
	}

}