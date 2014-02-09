package edu.gac.mcs270.hvidsten.tictactoe;

public class Player {
	private String name;
	
	public Player(String myName) {
		name = myName;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String myName){
		name = myName;
	}
}
