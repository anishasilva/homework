package project.main;

import project.model.*;
import project.model.swing.SwingAnimatorBuilder;
import project.model.text.TextAnimatorBuilder;
import project.ui.UI;
import project.ui.UIFactory;

/**
 * A static class to demonstrate the visualization aspect of
 * simulation.
 */
@SuppressWarnings("unused")
public class Main {
  private Main() {}
  public static void main(String[] args) {
	  UI ui = UIFactory.popupUi();
	  Control start = new Control(ui);
	  start.run();
    System.exit(0);
  }
}

