/*  
 * Position needs no change from the code
 * in the pure Java application project.
 */

package edu.gac.mcs270.hvidsten.tictactoe.gwt.shared;

public class Position {
	public static String NOT_PLAYED = "-"; // HTML space code
	public static String X_PLAYED = "X";
	public static String O_PLAYED = "O";
	
	private int loc_i = -1, loc_j = -1;
	private String value = NOT_PLAYED;
	
	public Position(int i, int j){
		loc_i = i;
		loc_j = j;
	}

	public String getValue() {
		return value;
	}
	
	public void setValue(String val) {
		value =  val;
	}
	
	public int getLoc_i(){
		return loc_i;
	}
	
	public int getLoc_j(){
		return loc_j;
	}
	
	public void setToXPosition(){
		setValue(X_PLAYED);
	}
	
	public boolean isX(){
		return value.equals(X_PLAYED);
	}
	
	public void setToOPosition(){
		setValue(O_PLAYED);
	}
	
	public boolean isO(){
		return value.equals(O_PLAYED);
	}
	
	public boolean isEmpty() {
		return value.equals(NOT_PLAYED);
	}

	public void reset() {
		// reset to original state
		setValue(NOT_PLAYED);
	}
	
	public boolean equals(Position p){
		return value.equals(p.getValue());
	}
	
}
