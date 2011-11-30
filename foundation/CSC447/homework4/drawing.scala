/////////////////////////////////////////////////////////////////////////////
//
// SUBMISSION INFORMATION
//
// Indicate the status of each of the following tasks (PICK ONE):
//
// TASK: INTERPRETER
// * Fully completed and tested.
//
// TASK: ADDING A NEW OPERATION
// * Fully completed and tested.
//
// ANY OTHER COMMENTS?
//
//attempted the advanced optional comment on the bottom, but quickly choose not to do it.  sorry.
//
/////////////////////////////////////////////////////////////////////////////


import java.awt.{Color,Dimension,Graphics,Rectangle}
import java.awt.event.{KeyEvent,KeyListener,MouseEvent,MouseMotionListener}
import javax.swing.{JFrame,JPanel,JScrollPane,Scrollable,SwingUtilities}
import scala.collection.mutable.Stack


// An AST for mini-Logo programs.
sealed abstract class Program
case class SetColor (color:Color) extends Program // Set the color of the pen.
case class Forward (n:Double) extends Program // Move the pen forward by "n".  A line is drawn only if the pen is down.
case class Left (degrees:Double) extends Program // Turn the pen's current direction left by "degrees" (expressed in degrees, not radians).
case class Right (degrees:Double) extends Program // Turn the pen's current direction right by "degrees" (expressed in degrees, not radians).
case class PenUp () extends Program     // Raise the pen, so subsequent uses of Forward will not draw lines.
case class PenDown () extends Program   // Lower the pen, so subsequent uses of Forward will draw lines.
case class PenFlip () extends Program   // Raise the pen if currently lowered.  Lower the pen if currently raised.
case class DrawString (s:String) extends Program // Draw a string at the pen's current position.
case class Repeat (numReps:Int, prog:Program) extends Program // Repeat "prog" "numReps" times.
case class Block (progs:List[Program]) extends Program // Run the programs in the List "progs" in sequence.

//added cases classes for task 2:
case class SaveState () extends Program
case class RestoreState () extends Program


object drawing {
  // Entrypoint.
  def main (args:Array[String]) {

    // First, make an array of different mini-Logo programs.

    // The Display will execute/display the first program when "1" is
    // pressed in the GUI, the second program when "2" is pressed in
    // the GUI, etc.

    // The programs are defined in the "samples" object at the bottom
    // of this file.  You are strongly encouraged to write *simple*
    // programs there when you are implementing/testing your interpreter.

    val programs:Array[Program] = Array(samples.square (100), samples.hexagon (50), samples.bigProgram, samples.saveExample)

    // Start the GUI, passing it an array of programs to execute/display.
    // The creation is done in the GUI thread using "invokeLater".
    SwingUtilities.invokeLater (new Runnable () { def run () = new Display (programs) })
  }
}


// The GUI consists of a Swing JFrame, containing a JScrollPane, which
// in turns contains a JPanel.  The mini-Logo interpreter draws into
// the JPanel.

// The JScrollPane can be used to look at pictures that go off the
// edge of the screen (useful when the mini-Logo programs have bugs!).

class Display (programs:Array[Program]) {

  // The size of the JScrollPane is set to 2000 by 2000 by the
  // following definitions for maxX and maxY.

  // Since Swing places the coordinates (0,0) at the top left-hand
  // corner, and increases the y-axis going down, it is convenient to
  // transform the coordinate system so that the y-axis increases going
  // up.  At the same time, we may as well place the origin (0,0) in the
  // middle of the JPanel and start interpreting from (0,0).  Thus the
  // interpreter uses coordinates where x ranges from -1000 (left-hand
  // side) to 1000 (right-hand side), and y ranges from -1000 (bottom of
  // JPanel) to 1000 (top of JPanel).

  // When you draw, you MUST convert from the interpreter's coordinate
  // system to the Swing/JPanel coordinate system.  Given a Graphics
  // object, the way to draw a line from (x,y) to (x2,y2) is then:
  //
  //   g.drawLine (transformX (x), transformY (y), transformX (x2), transformY (y2))
  //
  // This assumes that you already definitions with the following
  // types (the actual expressions that you use in place of "x", "y",
  // "x2", "y2" might be fields of an object, method parameters, local
  // variables, or expressions involving those things):
  //
  //   var x:Int = ...
  //   var y:Int = ...
  //   var x2:Int = ...
  //   var y2:Int = ...

  // You are not required to do bound checking of coordinates.  If
  // drawing occurs off the edgre of the JPanel, so be it.

  val maxX = 2000
  val maxY = 2000
  val scrollIncrement = 20
  def transformX (x:Double) = maxX/2 + x.toInt
  def transformY (y:Double) = maxY/2 - y.toInt

  // currentProgram tells the interpreter which mini-Logo program to
  // display.  It's value is changed when the user presses one of the
  // number keys, "1", "2", etc.
  var currentProgram = 0

  // When you click on the X, make the program exit.

  frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE)

  // Change the JScrollPane to display the center of the JPanel (where
  // the mini-Logo interpreter starts drawing).
  scroll.center ()

  // Make the GUI visible.
  frame.setVisible (true)

  // The panel into which the mini-Logo interpreter draws.
  // Note that the declaration of "object panel" inside "class Display"
  // ensures that there is one "panel" per instance of Display.  It corresponds
  // to subclassing JPanel, instantiating it once for each instance of
  // Display, and placing the instantiation into a final field of the
  // Display instance.
  object panel extends JPanel () {
    setPreferredSize (new Dimension (maxX, maxY))
    setAutoscrolls (true)

    // paintComponent is called whenever the GUI displays itself.
    // This method invokes the interpreter with the current mini-Logo program.

    // Drawing in Java AWT / Swing uses a Graphics object passed as an
    // argument to paintComponent.  See the documentation for
    // java.awt.Graphics if you are not familiar with it.
    override def paintComponent (g:Graphics) {
      super.paintComponent (g)
      // An example of setting the color for drawing.
      g.setColor (Color.RED)
      // An example of drawing a circle (oval with same height and width).
      g.fillOval (transformX (0), transformY (0), 10, 10)

      // Set the initial state for the interpreter.  See the "State"
      // class defined further below.
      val initialState = new State (0, 0, 0.0, true, Color.BLUE)

      // Invoke the interpreter with a state, and the current
      // mini-Logo program, and the g:Graphics needed for drawing.

      // Note that "programs" is an array, and
      // Scala uses the notation "array (i)" to get the "i"th element of
      // "array" rather than "array[i]".  This syntax allows some other
      // cleverness...
      interpret (initialState, programs (currentProgram), g)
    }
  }

  // The JScrollPane wrapping the JPanel.
  object scroll extends JScrollPane (panel) {
    getHorizontalScrollBar.setUnitIncrement (40);
    getVerticalScrollBar.setUnitIncrement (40);

    // Set the JScrollPane to display the center of the JPanel (the origin at (0,0)).
    def center () {
      val dim = frame.getSize
      panel.scrollRectToVisible (new Rectangle (maxX/2 - dim.width/2, maxY/2 - dim.height/2, dim.width, dim.height))
    }
  }

  // The JFrame that contains the JScrollPane.  It reacts to some
  // keypresses, so implements the KeyListener interface.
  object frame extends JFrame ("Display") with KeyListener {
    setSize (1280, 760)
    add (scroll)
    addKeyListener (this)

    override def keyPressed (e:KeyEvent) = { }
    override def keyReleased(e:KeyEvent) = { }
    override def keyTyped (e:KeyEvent) = {
      if (e.getKeyChar == 'q') exit (0)  // Quit program.
      else if (e.getKeyChar >= '1' && e.getKeyChar <= '9') { // Switch to a different mini-Logo program and redraw.
        val newProgram = e.getKeyChar () - '1'
        if (newProgram < programs.length) {
          currentProgram = newProgram
          panel.invalidate ()
          panel.repaint ()
        }
      }
      else if (e.getKeyChar == 'c') scroll.center // Center the JPanel in the JScrollPane.
      // VI or Nethack keys for navigation?  I use Emacs you insensitive clod!
      else if (e.getKeyChar == 'h') { val sb = scroll.getHorizontalScrollBar; sb.setValue (sb.getValue - scrollIncrement) }
      else if (e.getKeyChar == 'j') { val sb = scroll.getVerticalScrollBar;   sb.setValue (sb.getValue + scrollIncrement) }
      else if (e.getKeyChar == 'k') { val sb = scroll.getVerticalScrollBar;   sb.setValue (sb.getValue - scrollIncrement) }
      else if (e.getKeyChar == 'l') { val sb = scroll.getHorizontalScrollBar; sb.setValue (sb.getValue + scrollIncrement) }
    }
  }

  // The state of the turtle/pen for the interpreter:
  //
  // * (x,y)     - current coordinates of the pen.
  // * direction - current direction of the pen (used when moving forward).
  // * penDown   - is the pen down?  If so, a line is drawn when moving forward.
  // * penColor  - current pen color for drawing when moving forward with pen down.
  //
  // The interpreter changes the state as it sees different commands.
  // For example, moving forward will cause (x,y) to change.
  // As an another example, turning left will cause direction to change.
  class State (var x:Double, var y:Double, var direction:Double, var penDown:Boolean, var penColor:Color)



//var created to handle task 2:

  var saveStack:Stack[State] = Stack()


  def interpret (state:State, prog:Program, g:Graphics):Unit = {
    // your code replaces the dummy code below.

    //for (i <- 1 to 10) {
    //  g.drawString ("TODO: your code goes here!", transformX (0), transformY (i * 30))
    //}
    g.setColor(state.penColor)

    prog match {

      case SetColor (color:Color) => state.penColor = (color) // Set the color of the pen.
      case Forward (n:Double) => {
          val x2 = state.x + n * scala.math.cos (scala.math.toRadians (state.direction))
          val y2 = state.y + n * scala.math.sin (scala.math.toRadians (state.direction))
          if (state.penDown) g.drawLine(transformX(state.x), transformY(state.y), transformX(x2), transformY(y2) )
          state.x = x2
          state.y = y2
        }// Move the pen forward by "n".  A line is drawn only if the pen is down.
      case Right (degrees:Double) =>  if ((state.direction - degrees) < 0) state.direction = state.direction - degrees + 360 else state.direction = state.direction - degrees // Turn the pen's current direction right by "degrees" (expressed in degrees, not radians).
      case Left (degrees:Double) => state.direction = (state.direction + degrees) % 360// Turn the pen's current direction left by "degrees" (expressed in degrees, not radians).
      case PenUp () => state.penDown = false    // Raise the pen, so subsequent uses of Forward will not draw lines.
      case PenDown () =>  state.penDown = true // Lower the pen, so subsequent uses of Forward will draw lines.
      case PenFlip () =>  if (state.penDown) state.penDown = false else state.penDown = true // Raise the pen if currently lowered.  Lower the pen if currently raised.
      case DrawString (s:String) => g.drawString (s, transformX(state.x), transformY(state.y) ) // Draw a string at the pen's current position.
      case Repeat (numReps:Int, prog:Program) =>  for (i <- 1 to numReps) interpret(state, prog, g)// Repeat "prog" "numReps" times.
      case Block (progs) => for (p <- progs) interpret(state, p, g) // Run the programs in the List "progs" in sequence.

        //added cases classes for task 2:
      case SaveState () => saveStack.push (new State(state.x, state.y, state.direction, state.penDown, state.penColor))
        //push state onto the stack
      case RestoreState () => {
          val temp = saveStack.head
          state.x = temp.x
          state.y = temp.y
          state.direction = temp.direction
          state.penDown = temp.penDown
          state.penColor = temp.penColor
          saveStack.pop
        }//sets the new state and pops state from the stack

    }
/////////////////////////////////////////////////////////////////////////////////////////////
    // HINT: it is recommended that you recursively call "interpret"
    // when you encounter a "Block" subprogram.

    // HINT: to calculate the movement in Forward(n), you need a little
    // trigonometry, and must ensure conversion from degrees to radians:
    //
    //    val x2 = x + n * scala.math.cos (scala.math.toRadians (direction))
    //    val y2 = y + n * scala.math.sin (scala.math.toRadians (direction))

    // HINT: change the "state" variable as you execute commands.
  }


}


// A bunch of sample mini-Logo programs.  Type inference will figure
// out that the (return) types of methods below are "Program", but it is made explicit
// for pedagogical purposes.

// You can add statements to print out the programs if you like, e.g., "println samples.hexagon (50)".
object samples {

  // Some spacing.  Raises the pen, moves forward, then lowers the pen.
  // Setting the "visible" argument to "true" is useful for debugging, because
  // it makes the spacing movement actually draw a line.
  def spacing (size:Int, visible:Boolean):Program =
    Block (List (if (visible) PenDown () else PenUp (), Forward (size), PenDown ()))

  def polygon (size:Int, edges:Int):Program =
    Repeat (edges, Block (List(Forward (size), Right (360.0 / edges))))
  def square (size:Int):Program = polygon (size, 4)
  def hexagon (size:Int):Program = polygon (size, 6)
  def pentagram (size:Int):Program =
    Repeat (5, Block (List(Forward (size), Right (144))))

  val repeatedHexagon:Program =
    Repeat (6, Block (List (hexagon (30), Right (60), spacing (80, false))))

  def simpleHouse (size:Int):Program =
    Block (List (square (size),
                 Left (45), Forward (scala.math.sqrt (size * size / 2.0)),
                 Right (90), Forward (scala.math.sqrt (size * size / 2.0))))

  def approxCircle (size:Int):Program = polygon (size, 50)

  def radiator (size:Int, reps:Int):Program =
    Repeat (reps, Block (List (Forward (size), Right (90),
                               Forward (size / reps), Right (90),
                               Forward (size), Left (90),
                               Forward (size / reps), Left (90))))

  def parallelLines (size:Int, reps:Int):Program =
    Repeat (reps, Block (List (PenDown (), Forward (size * (1 + reps)), Right (180),
                               PenUp (), Forward (size * (1 + reps)), Right (90), Forward (size), Right (90))))

  def ticTacToe (size:Int):Program =
    Block (List (parallelLines (size, 2),
                 SetColor (Color.GREEN), PenUp (), Forward (size), Right (90), PenDown (),
                 parallelLines (size, 2)))

  def dashedLines (size:Int, numLines:Int, numDashes:Int):Program =
    Repeat (numLines, Block (List (Repeat (numDashes, Block (List (Forward (size), PenFlip ()))),
                                   Right (180), PenUp (), Forward (size * numDashes), Right (90), Forward (size), Right (90), PenDown ())))

  val helloWorld:Program = DrawString ("hello world")

  val showSpacing:Boolean = true

  // A big program that tries all of the above, with some spacing in between them.
  // Change showSpacing above to "false" if you want to see it without a line
  // illustrating movement between different subprograms.
  val bigProgram:Program =
    Block (List (Left (90), square (100), spacing (150, showSpacing),
                 hexagon (50), Right (90), spacing (300, showSpacing),
                 repeatedHexagon, Right (90), spacing (300, showSpacing),
                 pentagram (60), Right (90), spacing (300, showSpacing),
                 SetColor (Color.RED), Right (180), simpleHouse (100), Right (135), spacing (300, showSpacing),
                 approxCircle (5), Right (90), spacing (500, showSpacing),
                 SetColor (Color.DARK_GRAY), radiator (200, 10), Right (90), spacing (200, showSpacing),
                 SetColor (Color.BLACK), ticTacToe (60), spacing (300, showSpacing),
                 SetColor (Color.BLUE), dashedLines (20, 10, 11), Right (45), spacing (500, showSpacing),
                 helloWorld))

  val saveExample :Program =
    Block (List(SaveState(), PenUp(), Forward(200), PenDown(), SetColor(Color.RED), approxCircle(10), RestoreState(), square (100)))

  

  // OPTIONAL ADVANCED COMMENTS: As illustrated above, a mini-Logo
  // "Program" can be created as a value, or can be returned as the
  // result of a Scala function/method.  Scala code can be used to
  // generate mini-Logo programs of different sizes (not just varying
  // numbers in mini-Logo programs).  As an exercise, write Scala code to
  // generate a mini-Logo program that takes a list of coordinates say
  // (x1,y1)::(x2,y2)::... of type "List[(Double,Double)]" and creates a
  // mini-Logo "Program" to draw lines from the current location of the
  // pen to those positions (then moves the turtle back to the initial
  // pen location).


  
  //incomplete attempt at optional advanced comments...
  def miniLogoPoints(xs: List[(Double, Double)]):Program = {
    xs match {
      case Nil => Block(List())
      case y::ys => Block (List(SaveState (), RestoreState()))
    }
  }

  
}