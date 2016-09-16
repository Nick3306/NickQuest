package Skills;

public class ArcherySkill implements Skill
{	
	String name;
	int level;
	boolean frostArrow;
	boolean fireArrow;
	boolean poisonArrow;
	public ArcherySkill(String name, int level, boolean frost, boolean fire, boolean poison)
	{
		this.name = name;
		this.level = level;
		this.frostArrow = frost;
		this.fireArrow = fire;
		this.poisonArrow = poison;
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
	
	public boolean hasFire()
	{
		if(fireArrow == true)
			return true;
		else return false;
	}
	
	public boolean hasFrost()
	{
		if(frostArrow == true)
			return true;
		else return false;
	}
	
	public boolean hasPoison()
	{
		if(poisonArrow == true)
			return true;
		else return false;
	}

}
