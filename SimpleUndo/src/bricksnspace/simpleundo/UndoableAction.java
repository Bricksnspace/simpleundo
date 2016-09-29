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

/**
 * A single undoable operation record
 * 
 * @author Mario Pascucci
 *
 */
public class UndoableAction<T> {

	private UndoableOperation op;
	private T object;
	

	/**
	 * Create undoable operation record
	 * @param id identifier for operation 
	 * @param o operation to record
	 * @param t o
	 */
	public UndoableAction(UndoableOperation o, T t) {
		
		op = o;
		object = t;
	}
	
	
	

	

	/* (non Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("UndoableAction [op=%s, object=%s]", op, object);
	}






	/**
	 * Operation recorded in this action
	 * @return operation (add, del, mod)
	 */
	public UndoableOperation getOp() {
		return op;
	}
	
	
	/**
	 * Original object modified by this operation 
	 * @return object modified 
	 */
	public T getObject() {
		return object;
	}

	
	/**
	 * Change object modified. Used in "modify" to save object previous status
	 * @param t new object
	 */
	public void setObject(T t) {
		
		object = t;
	}
	

	
}
