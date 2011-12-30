import javax.swing.*;
import java.awt.*;  //this does NOT include java.awt.event classes
import java.awt.event.*;

/*
"...implements ActionListener":
- Implementing ActionListener interface
- Allows us to include the actionPerformed method below
*** When this class is registered with an event-generator such as a button with the command:
	button.addActionListener(this);
  the actionPerformed method will be called whenever the button is clicked

- Some of this will be explained in more detail when we cover interfaces.
*/
public class SimpleGui implements ActionListener
{
	//these objects are declared at the class level
	//if we declared them inside any one of the methods, the various OTHER methods
	//would not be able to see them (an issue of SCOPE)
	JFrame frame;
	Container cp;
	JButton button;

/*
- The bulk of this program's code is NOT inside main, instead, we use
main() only to instantiate the current class, and then use that instantiation
to invoke a method that DOES contain the bulk of the code
- This has to do with static/non-static issues, again, to be discussed later
*/
public static void main(String[] args)
{
  SimpleGui g = new SimpleGui();
  g.go();

} //end method main()


/*
This method contains the bulk of our program's code. See the comment preceding 'main()'
*/
public void go()
{
  frame = new JFrame("Practice GUI");
  cp = frame.getContentPane();
  frame.setSize(300, 300);
  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

  button = new JButton("Click Me");

  //The first argument to the add() method will make more sense when we discuss layouts
  cp.add(BorderLayout.CENTER, button);

  //Tell the button that this class is a 'listener'
  //This is known as "registering" as a listener
  //ANY class can be registered as a listener
  //The keyword 'this' refers in this case, to the current class
  button.addActionListener(this);

  frame.setVisible(true);
} //end method go()

/*
 This method is automatically invoked by the event generator.
 The event generator invokes the method for EVERY class that has registered
 as a listener. In other words, you could have several different classes,
 all of which are registered as listeners to the same button.
 The button will go down its list of listener classes (it keeps such a list)
 and will invoke the 'actionPerformed' method in every one of those classes.
*/
public void actionPerformed(ActionEvent ae) {
 System.out.println("The button was clicked!");
} //end method actionPerformed()

} //end class SimpleGui