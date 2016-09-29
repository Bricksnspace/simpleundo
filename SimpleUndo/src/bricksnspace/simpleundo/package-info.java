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

/**
 * Basic undo classes and functions
 * <p/>
 * A very basic undo/redo function implemented using Java Generics
 * <p/>
 * Requires that "editable objects" come with unique ID to record
 * and "replay" operations. Editable object must hold state and use ID to replace 
 * modified, added or deleted objects
 * 
 * @author Mario Pascucci
 *
 */
package bricksnspace.simpleundo;