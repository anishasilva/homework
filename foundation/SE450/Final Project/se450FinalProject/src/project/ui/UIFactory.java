package project.ui;

public class UIFactory {
  private UIFactory() {}
  static private UI _popupUI = new PopupUI();
  static private UI _textUI = new TextUI();
  /*
   * created two seperate factory methods so the client can invoke which ever UI they want.
   */
  static public UI popupUi(){
	  return _popupUI;
  }
  static public UI textUI(){
	  return _textUI;
  }
}
