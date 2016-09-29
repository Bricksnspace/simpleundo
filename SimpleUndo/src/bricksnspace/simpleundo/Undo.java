/*
	Copyright 2015 Mario Pascucci <mpascucci@gmail.com>
	This file is part of SimpleUndo

	SimpleUndo is free software: you can redistribute it and/or modify
	it under the terms of the GNU General Public License as published by
	the Free Software Foundation, either version 3 of the License, or
	(at your option) any later version.

	SimpleUndo is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU General Public License for more details.

	You should have received a copy of the GNU General Public License
	along with SimpleUndo.  If not, see <http://www.gnu.org/licenses/>.

*/


package bricksnspace.simpleundo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Undo handling
 * @author Mario Pascucci
 *
 */




public class Undo<T> {

	/** list of actions recorded */
	private List<List<UndoableAction<T>>> undoList;
	/** current recording action */
	private List<UndoableAction<T>> actionList = null;
	/** pointer to current undoable action, -1 if no action */ 
	private int currentAction = -1;
	/** pointer to maximum redoable actions, if == currentAction-> no action */
	private int lastAction = -1;
	/** modified indicator if != currentAction */
	private int markPoint = -1;

	
	
	/**
	 * Prepares a new Undo structure
	 */
	public Undo() {
		
		undoList = new ArrayList<List<UndoableAction<T>>>();
	}
	
	
	/* (non Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String s = String
				.format("Undo [currentAction=%s, lastAction=%s, markPoint=%s]\n",
						currentAction, lastAction,markPoint);
		for (List<UndoableAction<T>> l:undoList) {
			s += "  L: " +l.size() + "[";
			for (UndoableAction<T> a:l) {
				s += a.getOp()+", ";
			}
			s += "]\n";
		}
		return s;
	}


	/**
	 * Generic internal action record in list. 
	 * Reuse a position in array if there was a redo that freed a position
	 * @param action undoable action to record
	 */
	private void recordAction(List<UndoableAction<T>> action) {

		if (undoList.size() > currentAction+1) {
			// reuse an existing index
			currentAction++;
			undoList.set(currentAction, action);
		}
		else {
			undoList.add(action);
			currentAction++;
		}
		lastAction = currentAction;
		if (currentAction <= markPoint) {
			// (n)*undo+new action is recorded after a save
			// so markpoint must go in unreachable value by currentAction
			// because data in memory never can return equals to saved data
			markPoint = -2;
		}
	}
	
	
	
	/**
	 * Reads modification flag
	 * @return true if undo reports modified status
	 */
	public boolean isModified() {
		return markPoint != currentAction;
	}

	
	
	public void markSave() {
		
		markPoint = currentAction;
	}
	
	
	
	/**
	 * Checks if an undo action is available
	 * @return true if an undo is available
	 */
	public boolean isUndoAvailable() {
		
		return currentAction >= 0;
	}
	
	
	
	/**
	 * Checks if a redo operation is available
	 * @return true if there is a redo available
	 */
	public boolean isRedoAvailable() {
		
		return lastAction > currentAction;
	}
	


	
	public void startUndoRecord() {
		
		if (actionList != null) {
			throw new IllegalStateException("[Undo.startUndoRecord] A recording is already in progress");
		}
		actionList = new LinkedList<UndoableAction<T>>();
	}
	
	
	
	public void endUndoRecord() {
		
		if (actionList == null) {
			throw new IllegalStateException("[Undo.endUndoRecord] No recording in progress");
		}
		recordAction(actionList);
		actionList = null;
	}
	
	
	
	/**
	 * Record a delete operation on object t
	 * @param t object deleted
	 */
	public void recordDelete(T t) {
		
		if (actionList == null) {
			throw new IllegalStateException("[Undo.recordDelete] No recording in progress");
		}
		UndoableAction<T> action = new UndoableAction<T>(UndoableOperation.DEL, t);
		actionList.add(action);
	}

	
	
	/** 
	 * Records an adding operation for object t 
	 * @param t added object
	 */
	public void recordAdd(T t) {
		
		if (actionList == null) {
			throw new IllegalStateException("[Undo.recordAdd] No recording in progress");
		}
		UndoableAction<T> action = new UndoableAction<T>(UndoableOperation.ADD, t);
		actionList.add(action);
	}
	
	
	
	/**
	 * Gets next undoable object if any. Does not change Undo status
	 * @return a list of actions recorded
	 */
	public List<UndoableAction<T>> peekNextUndoActions() {
		
		if (currentAction >= 0) {
			return undoList.get(currentAction);
		}
		return null;
	}
	
	
	
	/**
	 * updates pointer to last action from list. Does not modify list itself.
	 */
	public void undo() {
		
		if (currentAction >= 0) {
			currentAction--;
		}	
	}
	
	
	
	/**
	 * Gets next redoable object if any. Does not change undo/redo status
	 * @return a list of actions recorded
	 */
	public List<UndoableAction<T>> peekNextRedoActions() {
		
		if (lastAction > currentAction) {
			return undoList.get(currentAction+1);
		}
		return null;
	}

	
	/**
	 * updates pointer to next redoable action in list. Does not modify list itself.
	 */
	public void redo() {
		
		if (lastAction > currentAction) {
			currentAction++;
		}
	}
	
	
	/**
	 * resets undo to initial status (empty, no action) 
	 */
	public void reset() {
		
		undoList.clear();
		currentAction = -1;
		lastAction = currentAction;
	}
	
	
}
