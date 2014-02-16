/*
 * View class for the TicTacToe game. 
 * Includes all GUI components and user input event handling.
 * 
 * Mike Hvidsten
 * Feb. 10, 2014
 */

package edu.gac.mcs270.hvidsten.tictactoe;


import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

// Main View class is a subclass of JFrame
public class TicTacView extends JFrame {
	private TicTacController gameCtrl;
	private JPanel btnPanel;
	private JButton btns[][]; 
	
	public TicTacView() {
		this.setTitle("Tic-Tac-Toe");
		
		// Layout has main panel which contains the button panel
		GamePanel mainPanel = new GamePanel(this);
		// To capture Mouse events, need to make the Panel  
		//   capable of user input focus
		mainPanel.setFocusable(true);
		addMouseListener(mainPanel);
		// Layout specifies how panel is set up. 
		mainPanel.setLayout(new FlowLayout());
        getContentPane().add(mainPanel);
        mainPanel.add(new JLabel("Type R to reset, S to save."));

        // Layout is 3x3 grid of buttons
        btnPanel = new JPanel(new GridLayout(3, 3));
        mainPanel.add(btnPanel, new GridBagConstraints());
        btnPanel.setMaximumSize(new Dimension(300, 300));

        setPreferredSize(new Dimension(400, 400));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // pack calculates sizes and lays out all components
		pack();
        setVisible(true);
	}
	
	public TicTacController getGameCtrl() {
		return gameCtrl;
	}

	public void setGameCtrl(TicTacController gameCtrl) {
		this.gameCtrl = gameCtrl;
	}
	
	// Creates grid of buttons for game
	public void setBoard(Position[][] board) {
		int size =gameCtrl.getBoardSize();
		btns = new JButton[size][size];
		for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
            	btns[i][j]=makeBoardButton(board[i][j]);
            	btnPanel.add(btns[i][j]);
            }
		}
		pack();
        setVisible(true);
	}
	
	// Creates individual button on board
	public JButton makeBoardButton(Position p){
		JButton btn = new JButton();
		
		Font curFont = this.getFont();
		btn.setPreferredSize(new Dimension(100, 100));
		btn.setFont(new Font(curFont.getFontName(), curFont.getStyle(), 22));
		btn.setFocusable(false);
		// Initialize label to Not Played label "-"
		btn.setText(Position.NOT_PLAYED);
		
		// The i,j location of the button is specified as  
		//  "final" as it needs to be permanently set in the
		//  Inline Interface definition for the ActionListener
		final int i = p.getLoc_i();
		final int j = p.getLoc_j();
		
		btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	gameCtrl.handlePlayerMove(i,j);
			}});
		return btn;
	}

	public void sendErrorMsg(String msg) {
		JOptionPane.showMessageDialog(this,msg);
	}

	public void announceWinner(Player curPlayer) {
		JOptionPane.showMessageDialog(this,
				      "Player "+curPlayer.getName()+" has won!");
		gameCtrl.resetGame();
	}
	
	public void announceTie() {
		JOptionPane.showMessageDialog(this,"Game is a Draw!");
		gameCtrl.resetGame();
	}

	public void updateView(Position[][] board) {
		for (int i = 0; i < gameCtrl.getBoardSize(); i++) {
            for (int j = 0; j < gameCtrl.getBoardSize(); j++) {
            	btns[i][j].setText(board[i][j].getValue());
            }
		}
	}

}

// We create a subclass of JPanel to illustrate how to implement the 
//  MouseListener Interface and to show how we can create a
//  special mapping for key events.  
class GamePanel extends JPanel implements MouseListener{
	private final TicTacView tview;
	
	private static final String RESET = "Reset";
	
    private Action reset = new AbstractAction(RESET) {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println(RESET);
            tview.getGameCtrl().resetGame();
        }
    };
    private static final String SAVE = "Save";
    private Action save = new AbstractAction(SAVE) {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println(SAVE);
            TicTacModel model = tview.getGameCtrl().getGameModel();
            DBManager.saveGame(GamePanel.this, model.getBoard());
        }
    };
	
	public GamePanel(TicTacView v) {
		super();
		tview = v;
		
		this.getInputMap().put(
	        KeyStroke.getKeyStroke(KeyEvent.VK_R, 0), RESET);
	    this.getActionMap().put(RESET, reset);
	    this.getInputMap().put(
	        KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), SAVE);
	    this.getActionMap().put(SAVE, save);
	}
	
	/////////////////////////////////////////
	///  Interface Implementation Methods  //
	/////////////////////////////////////////
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("got mouse clicked");
	}

	public void mouseEntered(MouseEvent e) {
		System.out.println("got mouse entered");
	}

	public void mouseExited(MouseEvent e) {
		System.out.println("got mouse exited");
	}

	public void mousePressed(MouseEvent e) {
		System.out.println("got mouse pressed");
	}

	public void mouseReleased(MouseEvent e) {
		System.out.println("got mouse released");
	}
}
