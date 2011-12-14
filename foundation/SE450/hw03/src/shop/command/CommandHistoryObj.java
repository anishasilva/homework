package shop.command;
import java.util.Stack;

final class CommandHistoryObj implements CommandHistory {
  Stack<UndoableCommand> _undoStack = new Stack<UndoableCommand>();
  Stack<UndoableCommand> _redoStack = new Stack<UndoableCommand>();
  RerunnableCommand _undoCmd = new RerunnableCommand () {
	  /**
	   * Returns a <code>RerunnableCommand</code> that, when run does the following:
	   * Pop command from <code>undoable</code>, undo it, then push it
	   * onto <code>redoable</code>.
	   */
      public boolean run () {
        boolean result = !_undoStack.empty();
        if (result) {
          UndoableCommand temp = _undoStack.pop();
          temp.undo();
          _redoStack.push(temp);
        }
        return result;
      }
    };
    
  RerunnableCommand _redoCmd = new RerunnableCommand () {
	  
	  /**
	   * Returns a <code>RerunnableCommand</code> that, when run does the following:
	   * Pop command from <code>redoable</code>, redo it, then push it
	   * onto <code>undoable</code>.
	   */
      public boolean run () {
        boolean result = !_redoStack.empty();
        if (result) {
        	UndoableCommand temp = _redoStack.pop();
        	
        	temp.redo();
        	
        	_undoStack.push(temp);
        }
        return result;
      }
    };

  public void add(UndoableCommand cmd) {
	  _undoStack.add(cmd);
	  _redoStack.clear();
  }
  
  public RerunnableCommand getUndo() {
    return _undoCmd;
  }
  
  public RerunnableCommand getRedo() {
    return _redoCmd;
  }
  
  // For testing
  UndoableCommand topUndoCommand() {
    if (_undoStack.empty())
      return null;
    else
      return _undoStack.peek();
  }
  // For testing
  UndoableCommand topRedoCommand() {
    if (_redoStack.empty())
      return null;
    else
      return _redoStack.peek();
  }
}
