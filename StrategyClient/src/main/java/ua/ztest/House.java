package ua.ztest;

public class House extends Building{

	@Override
	public void levelUp() {
		setLevel(getLevel()+1);
	}
}
