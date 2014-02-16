/*
 * Port of Java Application TicTacToe to Google GWT 
 * system.  The View class for the pure Java system 
 * needs to be re-designed to use the Google Web Toolkit 
 * framework.
 * 
 * Mike Hvidsten
 * January 26, 2014
 */

package edu.gac.mcs270.hvidsten.tictactoe.gwt.client;

import edu.gac.mcs270.hvidsten.tictactoe.gwt.shared.FieldVerifier;
import edu.gac.mcs270.hvidsten.tictactoe.gwt.shared.Player;
import edu.gac.mcs270.hvidsten.tictactoe.gwt.shared.Position;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 * (Note: This is auto-generated when you first create a 
 * google Web Toolkit project)
 */
public class TicTacToeGWT implements EntryPoint {
	private VerticalPanel boardPanel = new VerticalPanel();
	private TicTacController gameCtrl;
	private TicTacModel gameModel;
	private Button btns[][], dialogCloseButton; 
	private DialogBox dialogBox;
	private boolean isDialogResetMsg = false;
	
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		int boardSize = 4;
		gameCtrl = new TicTacController(boardSize);
		gameModel = new TicTacModel();
		
		// Wire the MVC architecture together
		gameCtrl.setGameModel(gameModel);
		gameCtrl.setGameView(this);
		
		// Add all GUI components to game board div in html file
		// Note: Game buttons are created by the controller 
		//   sending the setTiles message below
		Button resetBtn = new Button();
		resetBtn.setText("Reset Game");
		resetBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				gameModel.resetGame();
			}
		});
		
		// Create the popup dialog box
		dialogBox = new DialogBox();
		dialogBox.setText("----");
		dialogBox.setAnimationEnabled(true);
		dialogCloseButton = new Button("Close");
		// We can set the id of a widget by accessing its Element
		dialogCloseButton.getElement().setId("closeButton");
		// Add a handler to close the DialogBox
		dialogCloseButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox.hide();
				if(isDialogResetMsg)  
					gameCtrl.resetGame();
			}
		});
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.add(dialogCloseButton);
		dialogBox.setWidget(dialogVPanel);
		
		// Connect GUI widgets to main web page root Element
		//  Note: Use RootPanel.get() to get the entire body Element
		boardPanel.getElement().setId("boardPanel");
		RootPanel.get("gameBoard").add(boardPanel);
		RootPanel.get("bottomPanel").add(resetBtn);
		
		// Start game
		gameModel.initGame();
	}

	
	/////////////////////////////////////////////// 
	// Code converted from old TicTacView class  //
	///////////////////////////////////////////////
	
	public void setGameCtrl(TicTacController ticTacController) {
		gameCtrl=ticTacController;
	}
	
	public void setBoard(Position[][] board) {
		int size =gameCtrl.getBoardSize();
		btns = new Button[size][size];
		for (int i = 0; i < size; i++) {
			HorizontalPanel rowPanel = new HorizontalPanel();
			rowPanel.getElement().setClassName("gameRow");
            for (int j = 0; j < size; j++) {
            	btns[i][j]=makeBoardButton(board[i][j]);
            	rowPanel.add(btns[i][j]);
            }
            boardPanel.add(rowPanel);
		}
	}
	
	public Button makeBoardButton(Position p){
		Button btn = new Button();
		
		btn.setText(Position.NOT_PLAYED);
		btn.setSize("400", "400");

		// We can set the id of a widget by accessing its Element
		//  Look in war directory for TicTacToeGWT.css
		btn.getElement().setClassName("gameButton");
		
		final int i = p.getLoc_i();
		final int j = p.getLoc_j();
		
		btn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				gameCtrl.handlePlayerMove(i,j);
			}
		});
		//btn.addActionListener(new ActionListener() {
       //     public void actionPerformed(ActionEvent evt) {
        //    	gameCtrl.handelPlayerMove(i,j);
		//	}});
		return btn;
	}

	// New method in GWT code to handle popup messages
	public void displayMessage(String msg) {
		dialogBox.setText(msg);
		dialogBox.center();
		dialogCloseButton.setFocus(true);
	}
	
	public void sendErrorMsg(String msg) {
		//JOptionPane.showMessageDialog(this, msg);
		isDialogResetMsg=false;
		displayMessage(msg);
	}

	public void announceWinner(Player curPlayer) {
		//JOptionPane.showMessageDialog(this,
		//		      "Player "+curPlayer.getName()+" has won!");
		isDialogResetMsg = true;
		displayMessage("Player "+curPlayer.getName()+" has won!");
	}
	
	public void announceTie() {
		//JOptionPane.showMessageDialog(this,"Game is a Draw!");
		isDialogResetMsg = true;
		displayMessage("Game is a Draw!");
	}

	public void updateView(Position[][] board) {
		for (int i = 0; i < gameCtrl.getBoardSize(); i++) {
            for (int j = 0; j < gameCtrl.getBoardSize(); j++) {
            	btns[i][j].setText(board[i][j].getValue());
            }
		}
	}


	
	
}
