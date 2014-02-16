/*  
 * TicTacController needs just a minor change from the code
 * in the pure Java application project.
 * We change the TicTacView Object to the GWT EntryPoint class
 * TicTacToeGWT. 
 * The TicTacToeGWT class also serves as the substitute for the 
 * main() entry point for the Java code. 
 */

package edu.gac.mcs270.hvidsten.tictactoe.gwt.client;

import edu.gac.mcs270.hvidsten.tictactoe.gwt.shared.Player;
import edu.gac.mcs270.hvidsten.tictactoe.gwt.shared.Position;

public class TicTacController {
	// Size of board - default to 3x3
	private int numColsRows = 3;
	private TicTacToeGWT gameView;
	private TicTacModel gameModel;
	
	public TicTacController(int boardSize){
		numColsRows = boardSize;
	}
	
	public void setBoardSize(int size) {
		numColsRows = size;
	}
	
	public int getBoardSize() {
		return numColsRows;
	}


	public void setGameView(TicTacToeGWT gv){
		gameView = gv;
		gameView.setGameCtrl(this);
	}
	
	public TicTacToeGWT getGameView() {
		return gameView;
	}

	public void setGameModel(TicTacModel gm){
		gameModel = gm;
		gameModel.setGameCtrl(this);
	}
	
	public TicTacModel getGameModel() {
		return gameModel;
	}
	
	public void setBoardForView(Position[][] board) {
		gameView.setBoard(board);
	}


	public void handlePlayerMove(int i, int j) {
		Position p = gameModel.getPositionAt(i,j);
		if(p.isX() || p.isO()) {
			gameView.sendErrorMsg("Illegal Move");
			return;
		}
		else gameModel.doPlayerMove(p);
	}
	
	public void announceWinner(Player curPlayer) {
		gameView.announceWinner(curPlayer);
	}

	public void announceTie() {
		gameView.announceTie();
	}
	
	public void resetGame() {
		gameModel.resetGame();
	}

	public void updateView() {
		gameView.updateView(gameModel.getBoard());
	}
	
	// All main code (initializing system parts) is now done
	// in the EntryPoint point class in TicTacToeGWT. 
//	public static void main(String[] args) {
//		int boardSize = 3;
//		TicTacController gameCtrl = new TicTacController(boardSize);
//		TicTacModel gameModel = new TicTacModel();
//		TicTacView gameView = new TicTacView();
//		
//		// Wire the MVC architecture together
//		gameCtrl.setGameModel(gameModel);
//		gameCtrl.setGameView(gameView);
//
//		// Start game
//		gameModel.initGame();
//	}

}
