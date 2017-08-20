package ua.ztest;

public abstract class Building {
	
	private int level = 1;
	
	public abstract void levelUp();
	
	public void increase(){
		level++;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
}