package project.main;

import project.model.*;
import project.model.swing.SwingAnimatorBuilder;
import project.ui.*;

public class Control {
	private static final int EXITED = 0;
	private static final int EXIT = 1;
	private static final int START = 2;
	private static final int EDIT = 3;
	private static final int NUMSTATES = 10;

	private int _state;
	private UIMenu[] _menus;
	private UIFormTest _numberTest;
	private UIFormTest _patternTest;
	private UIFormTest _integerTest;

	private UIForm _getNum;
	private UIForm _getPattern;
	private UIForm _getMinMax;
	private UIForm _getRowCol;

	private UI _ui;

	Control(UI ui) {
		_ui = ui;
		_menus = new UIMenu[NUMSTATES];
		_state = START;
		addStart(START);
		addEdit(EDIT);
		addExit(EXIT);

		_numberTest = new UIFormTest() {
			public boolean run(String input) {
				try {
					Double.parseDouble(input);
					return true;
				} catch (NumberFormatException e) {
					return false;
				}
			}
		};
		_patternTest = new UIFormTest() {
			public boolean run(String input) {
				return (input.equalsIgnoreCase("simple") || input
						.equalsIgnoreCase("alternating"));
			}
		};
		_integerTest = new UIFormTest() {
			public boolean run(String input) {
				try {
					Integer.parseInt(input);
					return true;
				} catch (NumberFormatException e) {
					return false;
				}
			}
		};

		UIFormBuilder form = new UIFormBuilder();
		form.add("Minimum", _numberTest);
		form.add("Maximum", _numberTest);
		_getMinMax = form.toUIForm("Please enter range:");
		form = new UIFormBuilder();
		form.add("Value", _numberTest);
		_getNum = form.toUIForm("Please enter value:");
		form = new UIFormBuilder();
		form.add("Traffic Pattern", _patternTest);
		_getPattern = form.toUIForm("Enter pattern(alternating or simple):");
		form = new UIFormBuilder();
		form.add("Rows:", _integerTest);
		form.add("Columns:", _integerTest);
		_getRowCol = form.toUIForm("Please enter Row/Col");
	}

	void run() {
		try {
			while (_state != EXITED) {
				_ui.processMenu(_menus[_state]);
			}
		} catch (UIError e) {
			_ui.displayError("UI issue");
		}
	}

	private void addExit(int state) {
		UIMenuBuilder menu = new UIMenuBuilder();
		menu.add("Default", new UIMenuAction() {

			public void run() {
				_ui.displayError("Invalid Selection");
			}

		});
		menu.add("Yes", new UIMenuAction() {
			public void run() {
				_state = EXITED;
			}
		});
		menu.add("No", new UIMenuAction() {
			public void run() {
				_state = START;
			}
		});
		_menus[state] = menu.toUIMenu("Are you sure you want to exit?");
	}

	private void addStart(int state) {
		UIMenuBuilder menu = new UIMenuBuilder();
		menu.add("Default", new UIMenuAction() {
			public void run() {
				_ui.displayError("Invalid Selection");
			}
		});
		menu.add("Run Simulation (Swing version)", new UIMenuAction() {
			public void run() {

				AnimatorBuilder build = new SwingAnimatorBuilder();
				Model model = new Model(build, MP.getGridRows(), MP
						.getGridColumns());
				model.run((int) MP.getRuntime());
				model.dispose();
				TimeServerLinked.getServer().reset();

			}
		});
		menu.add("Run Simulation (Text version)", new UIMenuAction() {
			public void run() {

				AnimatorBuilder textBuild = new project.model.text.TextAnimatorBuilder();
				Model textModel = new Model(textBuild, MP.getGridRows(), MP
						.getGridColumns());
				textModel.run(MP.getRuntime());
				textModel.dispose();
				TimeServerLinked.getServer().reset();
			}
		});
		menu.add("Run Simulation (Simple version)", new UIMenuAction() {
			public void run() {
				AnimatorBuilder build = new SwingAnimatorBuilder();
				SimpleModel simple = new SimpleModel(build);
				MP.setCarEntryRateMax(3.0);
				simple.run(MP.getRuntime());
				simple.dispose();
				TimeServerLinked.getServer().reset();
				MP.setCarEntryRateMax(15.0);
			}
		});
		menu.add("Edit default sim params", new UIMenuAction() {
			public void run() {
				_state = EDIT;
			}
		});
		menu.add("Exit", new UIMenuAction() {
			public void run() {
				_state = EXIT;
			}
		});
		_menus[state] = menu.toUIMenu("Ray's Final Project.");
	}

	private void addEdit(int state) {
		UIMenuBuilder menu = new UIMenuBuilder();
		// 0
		menu.add("Default", new UIMenuAction() {
			public void run() {
				_ui.displayError("Invalid Selection");
			}
		});
		// 1
		menu.add("Show current values", new UIMenuAction() {
			public void run() {
				_ui.displayMessage(MP.output());
			}
		});
		// 2
		menu.add("Simulation time step", new UIMenuAction() {
			public void run() {
				String input[] = _ui.processForm(_getNum);
				MP.setTimeStep(Double.parseDouble(input[0]));
			}
		});
		// 3
		menu.add("Simulation run time", new UIMenuAction() {
			public void run() {
				String input[] = _ui.processForm(_getNum);
				MP.setRuntime(Double.parseDouble(input[0]));
			}
		});
		// 4
		menu.add("Grid size", new UIMenuAction() {
			public void run() {
				String input[] = _ui.processForm(_getRowCol);
				MP.setGridRows(Integer.parseInt((input[0])));
				MP.setGridColumns(Integer.parseInt((input[1])));

			}
		});
		// 5
		menu.add("Traffic pattern", new UIMenuAction() {
			public void run() {
				String input[] = _ui.processForm(_getPattern);
				boolean answer = true;
				if (input[0].equalsIgnoreCase("simple"))
					answer = false;
				MP.setTrafficPattern(answer);

			}
		});
		// 6
		menu.add("Car entry rate", new UIMenuAction() {
			public void run() {
				String input[] = _ui.processForm(_getMinMax);
				MP.setCarEntryRateMin(Double.parseDouble(input[0]));
				MP.setCarEntryRateMax(Double.parseDouble(input[1]));

			}
		});
		/*
		 * //7 menu.add("Road segment length", new UIMenuAction() { public void
		 * run(){ String input[] = _ui.processForm(_getMinMax);
		 * MP.setRoadSegmentLengthMin( Double.parseDouble(input[0]));
		 * MP.setRoadSegmentLengthMax( Double.parseDouble(input[1])); } });
		 */
		// 8
		menu.add("Intersection length", new UIMenuAction() {
			public void run() {
				String input[] = _ui.processForm(_getMinMax);
				MP.setIntersectionLengthMin(Double.parseDouble(input[0]));
				MP.setIntersectionLengthMax(Double.parseDouble(input[1]));
			}
		});
		// 9
		menu.add("Car length", new UIMenuAction() {
			public void run() {
				String input[] = _ui.processForm(_getMinMax);
				MP.setCarLengthMin(Double.parseDouble(input[0]));
				MP.setCarLengthMax(Double.parseDouble(input[1]));
			}
		});
		// 10
		menu.add("Car maximum velocity", new UIMenuAction() {
			public void run() {
				String input[] = _ui.processForm(_getMinMax);
				MP.setMaxVelocityMin(Double.parseDouble(input[0]));
				MP.setMaxVelocityMax(Double.parseDouble(input[1]));
			}
		});
		// 11
		menu.add("Car stop distance", new UIMenuAction() {
			public void run() {
				String input[] = _ui.processForm(_getMinMax);
				MP.setCarStopDistanceMin(Double.parseDouble(input[0]));
				MP.setCarStopDistanceMax(Double.parseDouble(input[1]));
			}
		});
		// 12
		menu.add("Car break distance", new UIMenuAction() {
			public void run() {
				String input[] = _ui.processForm(_getMinMax);
				MP.setBrakeDistanceMin(Double.parseDouble(input[0]));
				MP.setBrakeDistanceMax(Double.parseDouble(input[1]));
			}
		});
		// 13
		menu.add("Traffic light green time", new UIMenuAction() {
			public void run() {
				String input[] = _ui.processForm(_getMinMax);
				MP.setGreenTimeMin(Double.parseDouble(input[0]));
				MP.setGreenTimeMax(Double.parseDouble(input[1]));
			}
		});
		// 14
		menu.add("Traffic light yellow time", new UIMenuAction() {
			public void run() {
				String input[] = _ui.processForm(_getMinMax);
				MP.setYellowTimeMin(Double.parseDouble(input[0]));
				MP.setYellowTimeMax(Double.parseDouble(input[1]));
			}
		});
		// 15
		menu.add("Reset simulation and return to the main menu",
				new UIMenuAction() {
					public void run() {
						_state = START;
					}
				});
		_menus[state] = menu.toUIMenu("Project params to edit");
	}
}