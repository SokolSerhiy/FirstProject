package ua;

import ua.annotation.Start;
import ua.app.Strategy;

@Start
public class Main {

	public static void main(String[] args) {
		Strategy.run(Main.class, args);
	}
}