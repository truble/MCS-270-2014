package edu.gac.mcs270.hvidsten.tictactoe;

public class TicTacModel {
	private Position board[][];
	private Player curPlayer, xPlayer, oPlayer;
	private TicTacController gameCtrl;
	
	public TicTacModel(){
		xPlayer = new Player("x");
		oPlayer = new Player("o");
		curPlayer = xPlayer;
	}

	public void setGameCtrl(TicTacController gameCtrl) {
		this.gameCtrl = gameCtrl;
	}
	
	public TicTacController getGameCtrl() {
		return gameCtrl;
	}

	public Position[][] getBoard() {
		return board;
	}

	public Position getPositionAt(int i, int j) {
		return board[i][j];
	}
	
	public void initGame() {
		// Create Board Buttons
		int boardSize = gameCtrl.getBoardSize();
		board = new Position[boardSize][boardSize];
		for (int i = 0; i < boardSize ; i++) {
            for (int j = 0; j < boardSize; j++) {
                    board[i][j] = new Position(i, j);
            }
		}
		gameCtrl.setBoardForView(board);
	}
	
	public void resetGame() {
		for (int i = 0; i < gameCtrl.getBoardSize(); i++) {
            for (int j = 0; j < gameCtrl.getBoardSize(); j++) {
                    board[i][j].reset();
            }
		}
		curPlayer = xPlayer;
		gameCtrl.updateView();
	}
	
	// No error checking needed, as it is done by controller
	public void doPlayerMove(Position p) {
		if(curPlayer == xPlayer)
			p.setToXPosition();
		else
			p.setToOPosition();
		updateGameView();
		
		if(isWinGameState()) {
			gameCtrl.announceWinner(curPlayer);
			// Note: game reset is handled by controller
		}
		else if(isCompletedGameState()){
			// Note: game reset is handled by controller
			gameCtrl.announceTie();
		}
		else {
			updatePlayer();
		}
	}

	private boolean isCompletedGameState() {
		boolean isComplete = true;
		// Check rows
		for (int i = 0; i < gameCtrl.getBoardSize(); i++) {
			for (int j = 0; j < gameCtrl.getBoardSize(); j++) {
				if(board[i][j].isEmpty()){
					isComplete=false;
					break; // break out of loop
				}
			}
		}
		return isComplete;
	}

	private boolean isWinGameState() {
		// Check rows
		for (int i = 0; i < gameCtrl.getBoardSize(); i++) {
			if(listHasWin(board[i])){
				System.out.println("row has win");
				return true;
			}
		}
		// Check columns
		for (int j = 0; j < gameCtrl.getBoardSize(); j++) {
			//make array of column entries
			Position col[] = new Position[gameCtrl.getBoardSize()];
			for (int i = 0; i < gameCtrl.getBoardSize(); i++)
				col[i] = board[i][j]; 
			if(listHasWin(col)){
				System.out.println("col has win");
				return true;
			}
		}
		// Check "\" Diagonal
		//  Make array of diagonal entries
		Position diag1[] = new Position[gameCtrl.getBoardSize()];
		for (int i = 0; i < gameCtrl.getBoardSize(); i++)
			diag1[i] = board[i][i]; 
		if(listHasWin(diag1)){
			System.out.println("diag1 has win");
			return true;
		}
		
		// Check "/" Diagonal
		//  Make array of diagonal entries
		Position diag2[] = new Position[gameCtrl.getBoardSize()];
		for (int i = 0; i < gameCtrl.getBoardSize(); i++)
			diag2[i] = board[i][gameCtrl.getBoardSize()-i-1]; 
		if(listHasWin(diag2)){
			System.out.println("diag2 has win");
			return true;
		}
		
		return false;
	}
	
	public boolean listHasWin(Position[] posList){
		boolean listWin = true;
		
		if(posList[0].isEmpty()) return false;
		
		for (int j = 1; j < gameCtrl.getBoardSize(); j++) {
			if(posList[j].isEmpty() || !(posList[j].equals(posList[0]))) {
				listWin=false;
				break; // break out of loop
			}
		}
		return listWin;
	}
	
	private void updateGameView() {
		gameCtrl.updateView();
	}

	private void updatePlayer() {
		if(curPlayer == xPlayer)
			curPlayer = oPlayer;
		else
			curPlayer= xPlayer;
	}

}
