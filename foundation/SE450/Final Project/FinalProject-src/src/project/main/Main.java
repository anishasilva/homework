package project.main;

import project.ui.UI;
import project.ui.UIFactory;

/**
 * A static class to demonstrate the visualization aspect of simulation.
 */
public class Main {
	private Main() {
	}

	public static void main(String[] args) {

		UI ui = UIFactory.popupUi();
		Control start = new Control(ui);
		start.run();
		System.exit(0);
	}
}
